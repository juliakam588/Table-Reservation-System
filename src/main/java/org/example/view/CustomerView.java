package org.example.view;

import org.example.model.entities.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CustomerView {

    private static final int ID_WIDTH = 12;
    private static final int NAME_WIDTH = 20;
    private static final int CONTACT_INFO_WIDTH = 15;

    public void displayMainRow() {
        System.out.printf("%-" + ID_WIDTH + "s", "Customer ID");
        System.out.printf("%-" + NAME_WIDTH + "s", "Customer Name");
        System.out.printf("%-" + CONTACT_INFO_WIDTH + "s", "Contact Info");
        System.out.println();
    }

    public void displayCustomer(int id, String customerName, String contactInfo) {
        System.out.printf("%-" + ID_WIDTH + "d", id);
        System.out.printf("%-" + NAME_WIDTH + "s", customerName);
        System.out.printf("%-" + CONTACT_INFO_WIDTH + "s", contactInfo);
        System.out.println();
    }
    public void displayMessage(String message) {
        System.out.println(message);
    }
    public Map<String, Object> getNewCustomerData() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter contact information: ");
        String contactInfo = scanner.nextLine();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerName", customerName);
        parameters.put("contactInfo", contactInfo);
        return parameters;
    }

    public int getCustomerIdToDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer ID to delete: ");
        return Integer.parseInt(scanner.nextLine());
    }

}

