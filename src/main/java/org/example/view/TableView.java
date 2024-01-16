package org.example.view;
import java.util.Scanner;

public class TableView {
    private static final int ID_WIDTH = 10;
    private static final int SIZE_WIDTH = 12;
    private static final int STATUS_WIDTH = 10;

    public void displayMainRow() {
        System.out.printf("%-" + ID_WIDTH + "s", "Table ID");
        System.out.printf("%-" + SIZE_WIDTH + "s", "Table Size");
        System.out.printf("%-" + STATUS_WIDTH + "s", "Status");
        System.out.println();
    }

    public void displayTable(int id, int tableSize, boolean isAvailable) {
        System.out.printf("%-" + ID_WIDTH + "d", id);
        System.out.printf("%-" + SIZE_WIDTH + "d", tableSize);

        String status = isAvailable ? "free" : "taken";
        System.out.printf("%-" + STATUS_WIDTH + "s", status);

        System.out.println();
    }    public void displayMessage(String message) {
        System.out.println(message);
    }

    public int getCapacityFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter capacity: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public int getTableIdToDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter table ID to delete: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String getTableStatusTypeFromUser() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("a - show all tables");
            System.out.println("b - show only available tables");
            System.out.print("Enter your choice: ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("a") && !input.equals("b"));
        return input;
    }
}


