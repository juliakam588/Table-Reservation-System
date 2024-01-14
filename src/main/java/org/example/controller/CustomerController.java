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

    public void addCustomer(Map<String, Object> parameters) {
        customerService.addCustomer(parameters);
    }

    public void deleteCustomer(int customerId) {
        customerService.deleteCustomer(customerId);
    }

    public void showAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        customerView.displayMainRow();
        for (Customer customer : customers) {
            customerView.displayCustomer(customer.getId(), customer.getName(), customer.getContactInfo());
        }
    }

}