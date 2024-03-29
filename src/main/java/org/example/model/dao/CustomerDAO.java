package org.example.model.dao;

import org.example.model.entities.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements DAO<Customer>{

    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public int insert(Customer customer) {
        String query = "INSERT INTO customers (name, contact_info) VALUES (?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getContactInfo());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting customer", e);
        }
    }

    public Customer get(int id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customer", e);
        }
        return null;
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                customers.add(mapRowToCustomer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customers", e);
        }
        return customers;
    }

    public void update(Customer customer) {
        String query = "UPDATE customers SET name = ?, contact_info = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getContactInfo());
            preparedStatement.setInt(3, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating customer", e);
        }
    }

    public void delete(int id) {
        String queryCheckExists = "SELECT COUNT(*) FROM customers WHERE id = ?";
        String queryDeleteCustomer = "DELETE FROM customers WHERE id = ?";

        try (PreparedStatement checkExistsStatement = connection.prepareStatement(queryCheckExists)) {
            checkExistsStatement.setInt(1, id);
            try (ResultSet resultSet = checkExistsStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    throw new RuntimeException("Customer with ID " + id + " does not exist.");
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(queryDeleteCustomer)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting customer", e);
        }
    }

    public boolean customerExists(String contactInfo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers WHERE contact_info = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contactInfo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private Customer mapRowToCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setName(resultSet.getString("name"));
        customer.setContactInfo(resultSet.getString("contact_info"));
        return customer;
    }


    public Customer getCustomerByName(String name) {
        String query = "SELECT * FROM customers WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setId(resultSet.getInt("id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setContactInfo(resultSet.getString("contact_info"));
                    return customer;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customer by name", e);
        }
        return null;
    }

}