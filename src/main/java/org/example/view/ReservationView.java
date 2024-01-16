package org.example.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ReservationView {
    private Scanner scanner = new Scanner(System.in);
    private static final int ID_WIDTH = 15;
    private static final int CUSTOMER_ID_WIDTH = 15;
    private static final int CUSTOMER_NAME_WIDTH = 25;
    private static final int TIME_WIDTH = 20;
    private static final int TABLE_IDS_WIDTH = 25;
    private static final int SPECIAL_STATUS_WIDTH = 15;

    public void displayMainRow() {
        System.out.printf("%-" + ID_WIDTH + "s", "Reservation ID");
        System.out.printf("%-" + CUSTOMER_ID_WIDTH + "s", "Customer ID");
        System.out.printf("%-" + CUSTOMER_NAME_WIDTH + "s", "Customer Name");
        System.out.printf("%-" + TIME_WIDTH + "s", "Start Time");
        System.out.printf("%-" + TIME_WIDTH + "s", "End Time");
        System.out.printf("%-" + TABLE_IDS_WIDTH + "s", "Table Id(s)");
        System.out.printf("%-" + SPECIAL_STATUS_WIDTH + "s", "Special Status");
        System.out.println();
    }


    public void displayReservation(int reservationId, int customerId, String startTime, String endTime, String tableIds, String specialStatus, boolean isGroup, String customerName) {
        System.out.printf("%-" + ID_WIDTH + "d", reservationId);
        System.out.printf("%-" + CUSTOMER_ID_WIDTH + "d", customerId);
        System.out.printf("%-" + CUSTOMER_NAME_WIDTH + "s", customerName);
        System.out.printf("%-" + TIME_WIDTH + "s", startTime);
        System.out.printf("%-" + TIME_WIDTH + "s", endTime);
        System.out.printf("%-" + TABLE_IDS_WIDTH + "s", tableIds);
        if (isGroup) {
            System.out.printf("%-" + SPECIAL_STATUS_WIDTH + "s", specialStatus);
        } else {
            System.out.printf("%-" + SPECIAL_STATUS_WIDTH + "s", "N/A");
        }


        System.out.println();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public Map<String, Object> getNewReservationData() {
        String customerName = getStringInput("Enter customer name: ");
        String contactInfo = getStringInput("Enter contact information: ");
        LocalDateTime startTime = getDateTimeInput("Enter start time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime endTime = getDateTimeInput("Enter end time (yyyy-MM-ddTHH:mm): ");
        int peopleTotal = getPositiveIntegerInput("How many people? ");
        boolean isGroup = getBooleanInput("Is this a group reservation? (yes/no): ");
        String specialSetup = "-";
        if (isGroup) {
            specialSetup = getStringInput("Enter special setup details for group reservation: ");
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerName", customerName);
        parameters.put("contactInfo", contactInfo);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("peopleTotal", peopleTotal);
        parameters.put("isGroup", isGroup);
        parameters.put("specialSetup", specialSetup);
        return parameters;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public String getCustomerNameForReservations() {
        return getStringInput("Enter customer name to find: ");
    }
    private LocalDateTime getDateTimeInput(String prompt) {
        LocalDateTime dateTime;
        do {
            System.out.print(prompt);
            try {
                dateTime = LocalDateTime.parse(scanner.nextLine());
                return dateTime;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        } while (true);
    }

    private int getPositiveIntegerInput(String prompt) {
        int number;
        do {
            System.out.print(prompt);
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number > 0) {
                    return number;
                } else {
                    System.out.println("The number must be positive.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Please try again.");
            }
        } while (true);
    }

    private boolean getBooleanInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        } while (true);
    }

    public int getReservationIdToDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter reservation ID to delete: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String getReservationDisplayOption() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("a - show all reservations");
            System.out.println("b - show only current reservations");
            System.out.println("c - show reservations of a particular customer");
            System.out.print("Enter your choice: ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("a") && !input.equals("b") && !input.equals("c"));
        return input;
    }
}
