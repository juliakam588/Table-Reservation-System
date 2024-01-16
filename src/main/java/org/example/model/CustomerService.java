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

    public String addCustomer(Map<String, Object> parameters) {
        String name = (String) parameters.get("customerName");
        String contactInfo = (String) parameters.get("contactInfo");

        if (!isValidInput(name, contactInfo)) {
            return "Invalid customer details";
        }

        if (isCustomerExist(contactInfo)) {
            return "Customer already exists.";

        }

        return createAndInsertCustomer(name, contactInfo);
    }

    public String deleteCustomer(int customerId) {
        if(customerId < 0) {
            return "Couldn't delete customer. Try again.";
        }
        customerDAO.delete(customerId);
        return "Customer deleted successfully.";
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
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

    private String createAndInsertCustomer(String name, String contactInfo) {
        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setContactInfo(contactInfo);

        customerDAO.insert(newCustomer);
        return "Customer added successfully.";
    }
}
