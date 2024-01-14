package org.example.model;

import org.example.model.dao.CustomerDAO;
import org.example.model.entities.Customer;
import org.example.model.entities.Reservation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public boolean addCustomer(Map<String, Object> parameters) {
        String name = (String) parameters.get("customerName");
        String contactInfo = (String) parameters.get("contactInfo");

        if (!isValidInput(name, contactInfo)) {
            return false;
        }

        if (isCustomerExist(contactInfo)) {
            System.out.println("Customer already exists.");
            return false;
        }

        return createAndInsertCustomer(name, contactInfo);
    }

    public void deleteCustomer(int customerId) {
        customerDAO.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    private boolean isValidInput(String name, String contactInfo) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }
        if (contactInfo == null || contactInfo.trim().isEmpty()) {
            System.out.println("Contact details cannot be empty.");
            return false;
        }
        return true;
    }

    private boolean isCustomerExist(String contactInfo) {
        try {
            return customerDAO.customerExists(contactInfo);
        } catch (SQLException e) {
            System.out.println("Error while searching the customer. " + e.getMessage());
            return true;
        }
    }

    private boolean createAndInsertCustomer(String name, String contactInfo) {
        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setContactInfo(contactInfo);

        customerDAO.insertCustomer(newCustomer);
        System.out.println("Customer added successfully.");
        return true;
    }
}
