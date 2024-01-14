package org.example.view;

import org.example.model.entities.Customer;
import org.example.model.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public class CustomerView {
    public void displayMainRow() {
        System.out.print("Customer ids\t");
        System.out.print("Customer names\t");
        System.out.print("Contact info");

    }

    public void displayCustomer(int id, String customerName, String contactInfo) {
        System.out.println("Customer #" + id);
        System.out.print("\t" + customerName);
        System.out.print("\t" + contactInfo);
        System.out.println();
    }

    public void displayCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getId());
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("Contact details: " + customer.getContactInfo());
        }
    }
}

