package org.example.view;

import org.example.controller.CustomerController;
import org.example.controller.ReservationController;
import org.example.controller.TableController;
import org.example.view.CustomerView;
import org.example.view.ReservationView;
import org.example.view.TableView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI {
    private Scanner scanner;
    private final ReservationController reservationController;
    private final CustomerController customerController;
    private final TableController tableController;
    private final CustomerView customerView;
    private final ReservationView reservationView;
    private final TableView tableView;


    public CLI(ReservationController reservationController, CustomerController customerController, TableController tableController, CustomerView customerView, ReservationView reservationView, TableView tableView) {
        this.reservationController = reservationController;
        this.customerController = customerController;
        this.tableController = tableController;
        this.customerView = customerView;
        this.reservationView = reservationView;
        this.tableView = tableView;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        int choice = -1;
        do {
            printMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    deleteCustomer();
                    break;
                case 3:
                    showCustomers();
                    break;
                case 4:
                    addReservation();
                    break;
                case 5:
                    deleteReservation();
                    break;
                case 6:
                    showAllReservations();
                    break;
                case 7:
                    updateReservation();
                    break;
                case 8:
                    showTables();
                    break;
                case 9:
                    addTable();
                    break;
                case 10:
                    deleteTable();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
                    choice = -1;
            }
        } while (choice != 0);
    }


    private void printMenu() {
        System.out.println("1. Add a customer");
        System.out.println("2. Delete a customer");
        System.out.println("3. Show customers");
        System.out.println("4. Add a reservation");
        System.out.println("5. Delete a reservation");
        System.out.println("6. Show reservations");
        System.out.println("7. Edit reservation details");
        System.out.println("8. Show tables");
        System.out.println("9. Add a table");
        System.out.println("10. Delete a table");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private void addCustomer() {
        Map<String, Object> parameters = customerView.getNewCustomerData();
        customerController.addCustomer(parameters);
    }

    private void deleteCustomer() {
        int customerId = customerView.getCustomerIdToDelete();
        customerController.deleteCustomer(customerId);
    }

    private void showCustomers() {
        customerController.showAllCustomers();
    }

    private void addReservation() {
        Map<String, Object> parameters = reservationView.getNewReservationData();
        reservationController.addReservation(parameters);
    }

    private void deleteReservation() {
        int reservationId = reservationView.getReservationIdToDelete();
        reservationController.deleteReservation(reservationId);
    }

    private void showAllReservations() {
        System.out.println("Current reservations:");
        reservationController.showAllReservations();
    }

    private void updateReservation() {
        System.out.print("Enter reservation ID to update: ");
        int reservationId = Integer.parseInt(scanner.nextLine());
    }

    private void showTables() {
        String option = tableView.getTableStatusTypeFromUser();
        if (option.equals("a")) {
            tableController.showAllTables();
        } else if (option.equals("b")) {
            tableController.showAvailableTables();
        } else {
            System.out.println("Invalid input");
        }

    }

    private void deleteTable() {
        int tableId = tableView.getTableIdToDelete();
        tableController.deleteTable(tableId);
    }

    private void addTable() {
        int tableCapacity = tableView.getCapacityFromUser();
        tableController.addTable(tableCapacity);
    }
}
