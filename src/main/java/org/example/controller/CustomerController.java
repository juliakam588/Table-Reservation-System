package org.example.controller;

import org.example.model.CustomerService;
import org.example.model.entities.Customer;
import org.example.view.CustomerView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CustomerController {
    private CustomerService customerService;
    private CustomerView customerView;

    public CustomerController(CustomerService customerService, CustomerView customerView) {
        this.customerService = customerService;
        this.customerView = customerView;
    }

    public void addCustomer() {
        try {
            Map<String, Object> parameters = customerView.getNewCustomerData();
            String result = customerService.addCustomer(parameters);
            customerView.displayMessage(result);
        } catch (Exception e) {
            customerView.displayMessage("Failed to add customer: " + e.getMessage());
        }
    }

    public void deleteCustomer() {
        try {
            int customerId = customerView.getCustomerIdToDelete();
            String result = customerService.deleteCustomer(customerId);
            customerView.displayMessage(result);
        } catch(RuntimeException e) {
            customerView.displayMessage("Error: " + e.getMessage());
        }
    }

    public void showAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        customerView.displayMainRow();
        for (Customer customer : customers) {
            customerView.displayCustomer(customer.getId(), customer.getName(), customer.getContactInfo());
        }
    }

}