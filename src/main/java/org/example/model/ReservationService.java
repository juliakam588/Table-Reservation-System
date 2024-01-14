package org.example.model;

import org.example.model.builder.GroupReservationBuilder;
import org.example.model.builder.ReservationBuilder;
import org.example.model.builder.StandardReservationBuilder;
import org.example.model.dao.CustomerDAO;
import org.example.model.dao.ReservationDAO;
import org.example.model.dao.TableDAO;
import org.example.model.entities.Customer;
import org.example.model.entities.Reservation;
import org.example.model.entities.Table;
import org.example.strategy.GroupAllocationStrategy;
import org.example.strategy.StandardAllocationStrategy;
import org.example.strategy.TableAllocationStrategy;

import java.time.LocalDateTime;
import java.util.*;

public class ReservationService {
    private ReservationDAO reservationDAO;
    private CustomerDAO customerDAO;
    private TableDAO tableDAO;
    private TableAllocationStrategy allocationStrategy;


    public ReservationService(ReservationDAO reservationDAO, TableDAO tableDAO, CustomerDAO customerDAO) {
        this.reservationDAO = reservationDAO;
        this.customerDAO = customerDAO;
        this.tableDAO = tableDAO;
    }

    public void setAllocationStrategy(TableAllocationStrategy allocationStrategy) {
        this.allocationStrategy = allocationStrategy;
    }

    public void addReservation(Map<String, Object> parameters) {
        Customer customer = customerDAO.getCustomerByName((String) parameters.get("customerName"));
        if (customer == null) {
            customer = new Customer();
            customer.setName((String) parameters.get("customerName"));
            customer.setContactInfo((String) parameters.get("contactInfo"));
            int customerId = customerDAO.insert(customer);
            customer.setId(customerId);
        }

        boolean isGroup = (boolean) parameters.get("isGroup");
        this.setAllocationStrategy(isGroup ? new GroupAllocationStrategy() : new StandardAllocationStrategy());


        int peopleTotal = (int) parameters.get("peopleTotal");
        List<Table> availableTables = tableDAO.getAvailableTables();
        List<Integer> suitableTableIds = allocationStrategy.allocateTables(availableTables, peopleTotal);

        ReservationBuilder builder = isGroup ? new GroupReservationBuilder() : new StandardReservationBuilder();
        builder.setCustomerId(customer.getId())
                .setStartTime((LocalDateTime) parameters.get("startTime"))
                .setEndTime((LocalDateTime) parameters.get("endTime"))
                .setTableIds(suitableTableIds);
        if (isGroup) {
            builder.setSpecialSetup((String) parameters.get("specialSetup"));
        }

        Reservation reservation = builder.build();
        reservationDAO.insertReservation(reservation, suitableTableIds);
    }

    public void deleteReservation(int reservationId) {
        reservationDAO.delete(reservationId);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAll();
    }

}


