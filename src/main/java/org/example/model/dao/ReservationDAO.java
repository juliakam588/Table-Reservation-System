package org.example.model.dao;
import org.example.model.entities.Reservation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReservationDAO implements DAO<Reservation>{
    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    public Reservation get(int id) {
        String query = "SELECT * FROM reservations WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToReservation(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching reservation", e);
        }
        return null;
    }

    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservations.add(mapRowToReservation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching reservations", e);
        }
        return reservations;

    }


    public void insertReservation(Reservation reservation, List<Integer> tableIds) {
        String insertReservationQuery = "INSERT INTO reservations (customer_id, start_time, end_time, special_setup, is_group, customer_name) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementTable = null;
        PreparedStatement updateTableStatement = null;

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(insertReservationQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, reservation.getCustomerId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(reservation.getStartTime()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(reservation.getEndTime()));
            preparedStatement.setString(4, reservation.getSpecialSetup());
            preparedStatement.setBoolean(5, reservation.getIsGroup());
            preparedStatement.setString(6, reservation.getCustomerName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating reservation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating reservation failed, no ID obtained.");
                }
            }

            String insertReservationTableQuery = "INSERT INTO reservation_tables (reservation_id, table_id) VALUES (?, ?)";
            preparedStatementTable = connection.prepareStatement(insertReservationTableQuery);
            for (Integer tableId : tableIds) {
                preparedStatementTable.setInt(1, reservation.getId());
                preparedStatementTable.setInt(2, tableId);
                preparedStatementTable.executeUpdate();
            }

            String updateTableQuery = "UPDATE tables SET available = false WHERE id = ?";
            updateTableStatement = connection.prepareStatement(updateTableQuery);
            for (Integer tableId : tableIds) {
                updateTableStatement.setInt(1, tableId);
                updateTableStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Error rolling back reservation transaction", ex);
            }
            throw new RuntimeException("Error during reservation transaction", e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (preparedStatementTable != null) preparedStatementTable.close();
                if (updateTableStatement != null) updateTableStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing prepared statements", e);
            }
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error setting auto commit to true", e);
            }
        }
    }

    public void update(Reservation reservation, List<Integer> newTableIds) {
        String query = "UPDATE reservations SET customer_id = ?, start_time = ?, end_time = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, reservation.getCustomerId());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(reservation.getStartTime()));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(reservation.getEndTime()));
                preparedStatement.setString(4, reservation.getSpecialSetup());
                preparedStatement.setBoolean(5, reservation.getIsGroup());
                preparedStatement.setString(6, reservation.getCustomerName());
                preparedStatement.setInt(7, reservation.getId());
                preparedStatement.executeUpdate();
            }

            try (PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM reservation_tables WHERE reservation_id = ?")) {
                deleteStatement.setInt(1, reservation.getId());
                deleteStatement.executeUpdate();
            }

            try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO reservation_tables (reservation_id, table_id) VALUES (?, ?)")) {
                for (Integer tableId : newTableIds) {
                    insertStatement.setInt(1, reservation.getId());
                    insertStatement.setInt(2, tableId);
                    insertStatement.executeUpdate();
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Error rolling back update transaction", ex);
            }
            throw new RuntimeException("Error updating reservation", e);
        }
    }

    public void delete(int id) {
        String queryCheckExists = "SELECT COUNT(*) FROM reservations WHERE id = ?";
        String queryDeleteReservation = "DELETE FROM reservations WHERE id = ?";
        String queryDeleteReservationTables = "DELETE FROM reservation_tables WHERE reservation_id = ?";
        String queryUpdateTables = "UPDATE tables SET available = TRUE WHERE id IN (SELECT table_id FROM reservation_tables WHERE reservation_id = ?)";
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement checkExistsStatement = connection.prepareStatement(queryCheckExists)) {
                checkExistsStatement.setInt(1, id);
                try (ResultSet resultSet = checkExistsStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) == 0) {
                        throw new RuntimeException("Reservation with ID " + id + " does not exist.");
                    }
                }
            }


            try (PreparedStatement updateTablesStatement = connection.prepareStatement(queryUpdateTables)) {
                updateTablesStatement.setInt(1, id);
                updateTablesStatement.executeUpdate();
            }

            try (PreparedStatement deleteRelationStatement = connection.prepareStatement(queryDeleteReservationTables)) {
                deleteRelationStatement.setInt(1, id);
                deleteRelationStatement.executeUpdate();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(queryDeleteReservation)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Error rolling back delete transaction", ex);
            }
            throw new RuntimeException("Error deleting reservation", e);
        }
    }
    private Reservation mapRowToReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getInt("id"));
        reservation.setCustomerId(resultSet.getInt("customer_id"));
        reservation.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
        reservation.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());
        reservation.setSpecialSetup(resultSet.getString("special_setup"));
        reservation.setIsGroup(resultSet.getBoolean("is_group"));
        reservation.setCustomerName(resultSet.getString("customer_name"));
        reservation.setTableIds(getTableIdsForReservation(reservation.getId()));

        return reservation;
    }

    private List<Integer> getTableIdsForReservation(int reservationId) throws SQLException {
        List<Integer> tableIds = new ArrayList<>();
        String query = "SELECT table_id FROM reservation_tables WHERE reservation_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservationId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tableIds.add(resultSet.getInt("table_id"));
                }
            }
        }
        return tableIds;
    }

    public boolean isReservationTimeAvailable(LocalDateTime startTime, LocalDateTime endTime) {
        String query = "SELECT COUNT(*) AS reservation_count FROM reservations "
                + "WHERE (start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(endTime));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startTime));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(endTime));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(startTime));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("reservation_count") == 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking reservation time availability", e);
        }

        return true;
    }


    @Override
    public int insert(Reservation reservation) {
        return 0;
    }

    @Override
    public void update(Reservation reservation) {

    }
}