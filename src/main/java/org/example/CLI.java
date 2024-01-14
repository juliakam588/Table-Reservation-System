package org.example;

import org.example.controller.CustomerController;
import org.example.controller.ReservationController;
import org.example.controller.TableController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
        int choice;
        do {
            printMenu();
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    deleteCustomer();
                    break;
                case 3:
                    addReservation();
                    break;
                case 4:
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
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("1. Add a customer");
        System.out.println("2. Delete a customer");
        System.out.println("3. Show available tables");
        System.out.println("4. Add a reservation");
        System.out.println("5. Delete a reservation");
        System.out.println("6. Show reservations");
        System.out.println("7. Edit reservation details");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private void addCustomer() {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter contact information: ");
        String contactInfo = scanner.nextLine();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerName", customerName);
        parameters.put("contactInfo", contactInfo);

        customerController.addCustomer(parameters);

    }

    private void deleteCustomer() {
        System.out.print("Enter customer ID to delete: ");
        int customerId = Integer.parseInt(scanner.nextLine());
        customerController.deleteCustomer(customerId);
        System.out.println("Customer deleted successfully.");
    }

    private void addReservation() {

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter contact information: ");
        String contactInfo = scanner.nextLine();

        System.out.print("Enter start time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine());

        System.out.print("Enter end time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime endTime = LocalDateTime.parse(scanner.nextLine());

        System.out.println("How many people?");
        int peopleTotal = Integer.parseInt(scanner.nextLine());


        System.out.print("Is this a group reservation? (yes/no): ");
        boolean isGroup = scanner.nextLine().trim().equalsIgnoreCase("yes");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerName", customerName);
        parameters.put("contactInfo", contactInfo);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("isGroup", isGroup);
        parameters.put("peopleTotal", peopleTotal);
        reservationController.addReservation(parameters);
    }

    private void deleteReservation() {
        System.out.print("Enter reservation ID to delete: ");
        int reservationId = Integer.parseInt(scanner.nextLine());
        reservationController.deleteReservation(reservationId);
        System.out.println("Reservation deleted successfully.");
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
        System.out.println("Current reservations:");
    }
}
