package org.example.model;

import org.example.controller.ReservationValidator;
import org.example.model.builder.GroupReservationBuilder;
import org.example.model.builder.ReservationBuilder;
import org.example.model.builder.StandardReservationBuilder;
import org.example.model.dao.CustomerDAO;
import org.example.model.dao.ReservationDAO;
import org.example.model.dao.TableDAO;
import org.example.model.entities.Customer;
import org.example.model.entities.Reservation;
import org.example.model.entities.Table;
import org.example.strategy.reservation_display.ReservationDisplayStrategy;
import org.example.strategy.table_allocation.GroupAllocationStrategy;
import org.example.strategy.table_allocation.StandardAllocationStrategy;
import org.example.strategy.table_allocation.TableAllocationStrategy;

import java.time.LocalDateTime;
import java.util.*;

public class ReservationService {
    private ReservationDAO reservationDAO;
    private CustomerDAO customerDAO;
    private TableDAO tableDAO;
    private TableAllocationStrategy allocationStrategy;
    private ReservationValidator reservationValidator = new ReservationValidator();

    private ReservationDisplayStrategy displayStrategy;


    public ReservationService(ReservationDAO reservationDAO, TableDAO tableDAO, CustomerDAO customerDAO) {
        this.reservationDAO = reservationDAO;
        this.customerDAO = customerDAO;
        this.tableDAO = tableDAO;
    }

    public void setAllocationStrategy(TableAllocationStrategy allocationStrategy) {
        this.allocationStrategy = allocationStrategy;
    }
    public void setDisplayStrategy(ReservationDisplayStrategy displayStrategy) {
        this.displayStrategy = displayStrategy;
    }

    public String addReservation(Map<String, Object> parameters) {
        if (!reservationValidator.validateReservationParameters(parameters)) {
            return "Validation failed for reservation parameters.";
        }

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
        if (suitableTableIds.isEmpty()) {
            return "Failed to find suitable tables for the reservation.";
        }

        ReservationBuilder builder = isGroup ? new GroupReservationBuilder() : new StandardReservationBuilder();
        builder.setCustomerId(customer.getId())
                .setStartTime((LocalDateTime) parameters.get("startTime"))
                .setEndTime((LocalDateTime) parameters.get("endTime"))
                .setTableIds(suitableTableIds)
                .setCustomerName(customer.getName());
        if (isGroup) {
            builder.setSpecialSetup((String) parameters.get("specialSetup"));
        }

        Reservation reservation = builder.build();
        reservationDAO.insertReservation(reservation, suitableTableIds);
        return "Reservation added successfully.";    }

    public String deleteReservation(int reservationId) {
        if(reservationId < 0) {
            return "Couldn't delete reservation. Try again.";
        }
        reservationDAO.delete(reservationId);
        return "Reservation deleted successfully.";
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAll();
    }

    public List<Reservation> executeDisplayStrategy() {
        if (displayStrategy == null) {
            throw new IllegalStateException("Display strategy not set");
        }
        List<Reservation> allReservations = getAllReservations();
        return displayStrategy.execute(allReservations);
    }
}


