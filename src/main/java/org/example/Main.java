package org.example;

import org.example.controller.CustomerController;
import org.example.controller.ReservationController;
import org.example.controller.TableController;
import org.example.model.CustomerService;
import org.example.model.ReservationService;
import org.example.model.TableService;
import org.example.model.dao.CustomerDAO;
import org.example.model.dao.ReservationDAO;
import org.example.model.dao.TableDAO;
import org.example.model.singleton.DatabaseConnection;
import org.example.view.CLI;
import org.example.view.CustomerView;
import org.example.view.ReservationView;
import org.example.view.TableView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        try (Connection connection = databaseConnection.getConnection()) {
            ReservationDAO reservationDAO = new ReservationDAO(connection);
            TableDAO tableDAO = new TableDAO(connection);
            CustomerDAO customerDAO = new CustomerDAO(connection);

            CLI cli = getCli(reservationDAO, tableDAO, customerDAO);
            cli.run();

        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while connecting to the database. " + e.getMessage());
        }

    }

    private static CLI getCli(ReservationDAO reservationDAO, TableDAO tableDAO, CustomerDAO customerDAO) {
        ReservationService reservationService = new ReservationService(reservationDAO, tableDAO, customerDAO);
        CustomerService customerService = new CustomerService(customerDAO);
        TableService tableService = new TableService(tableDAO);
        ReservationView reservationView = new ReservationView();
        CustomerView customerView = new CustomerView();
        TableView tableView = new TableView();

        ReservationController reservationController =new ReservationController(reservationService,reservationView);
        CustomerController customerController = new CustomerController(customerService,customerView);
        TableController tableController = new TableController(tableService, tableView);
        return new CLI(reservationController,customerController,tableController, customerView, reservationView, tableView);
    }
}