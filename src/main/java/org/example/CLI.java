package org.example;

import org.example.controller.CustomerController;
import org.example.controller.ReservationController;
import org.example.controller.TableController;

import java.util.Scanner;

public class CLI {
    private Scanner scanner;
    private final ReservationController reservationController;
    private final CustomerController customerController;
    private final TableController tableController;


    public CLI(ReservationController reservationController, CustomerController customerController, TableController tableController) {
        this.reservationController = reservationController;
        this.customerController = customerController;
        this.tableController = tableController;
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
                    showTables();
                    break;
                case 8:
                    addTable();
                    break;
                case 9:
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
        String header = "\n🍴 Restaurant Table Reservation System 🍴\n";
        String underline = new String(new char[header.length()]).replace("\0", "─");

        String[] options = {
                "1. Add a Customer",
                "2. Delete a Customer",
                "3. Show Customers",
                "4. Add a Reservation",
                "5. Delete a Reservation",
                "6. Show Reservations",
                "7. Show Tables",
                "8. Add a Table",
                "9. Delete a Table",
                "0. Exit"
        };

        System.out.println(header);
        System.out.println(underline);
        for (String option : options) {
            System.out.println(option);
        }
        System.out.println(underline);
        System.out.print("🔎 What would you like to do? Enter your choice: ");

    }


    private void addCustomer() {
        customerController.addCustomer();
    }

    private void deleteCustomer() {
        customerController.deleteCustomer();
    }

    private void showCustomers() {
        customerController.showAllCustomers();
    }

    private void addReservation() {
        reservationController.addReservation();
    }

    private void deleteReservation() {
        reservationController.deleteReservation();
    }

    private void showAllReservations() {
        reservationController.showReservations();
    }

    private void showTables() {
        tableController.showTables();
    }

    private void deleteTable() {
        tableController.deleteTable();
    }

    private void addTable() {
        tableController.addTable();
    }
}
