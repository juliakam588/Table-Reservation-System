package org.example.model.dao;

import org.example.model.entities.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    private Connection connection;

    public TableDAO(Connection connection) {
        this.connection = connection;
    }

    public Table getTable(int id) {
        String query = "SELECT * FROM tables WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int tableId = resultSet.getInt("id");
                int capacity = resultSet.getInt("capacity");

                Table table = new Table();
                table.setId(tableId);
                table.setCapacity(capacity);

                return table;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();
        String query = "SELECT * FROM tables";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int capacity = resultSet.getInt("capacity");

                Table table = new Table();
                table.setId(id);
                table.setCapacity(capacity);

                tables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public void insertTable(Table table) {
        String query = "INSERT INTO tables (id, capacity) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, table.getId());
            preparedStatement.setInt(2, table.getCapacity());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTable(Table table) {
        String query = "UPDATE tables SET capacity = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, table.getCapacity());
            preparedStatement.setInt(2, table.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void deleteTable(int id) {
        String query = "DELETE FROM tables WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Table> getAvailableTables() {
        List<Table> availableTables = new ArrayList<>();
        String query = "SELECT * FROM tables WHERE available = true";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Table table = new Table();
                    table.setId(resultSet.getInt("id"));
                    table.setCapacity(resultSet.getInt("capacity"));
                    table.setAvailable(resultSet.getBoolean("available"));
                    availableTables.add(table);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching available tables", e);
        }
        return availableTables;
    }

}

