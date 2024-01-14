package org.example.model;

import org.example.builder.StandardReservationBuilder;
import org.example.model.dao.CustomerDAO;
import org.example.model.dao.ReservationDAO;
import org.example.model.dao.TableDAO;
import org.example.model.entities.Customer;
import org.example.model.entities.Reservation;
import org.example.model.entities.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationService {
    private ReservationDAO reservationDAO;
    private CustomerDAO customerDAO;
    private TableDAO tableDAO;


    public ReservationService(ReservationDAO reservationDAO, TableDAO tableDAO, CustomerDAO customerDAO) {
        this.reservationDAO = reservationDAO;
        this.customerDAO = customerDAO;
        this.tableDAO = tableDAO;
    }

    public void addReservation(Map<String, Object> parameters) {
        Customer customer = customerDAO.getCustomerByName((String) parameters.get("customerName"));
        if (customer == null) {
            customer = new Customer();
            customer.setName((String) parameters.get("customerName"));
            customer.setContactInfo((String) parameters.get("contactInfo"));
            int customerId = customerDAO.insertCustomer(customer);
            customer.setId(customerId);

        }

        int peopleTotal = (int) parameters.get("peopleTotal");
        List<Table> availableTables = tableDAO.getAvailableTables();
        List<Integer> suitableTableIds = findSuitableTables(availableTables, peopleTotal);

        StandardReservationBuilder builder = new StandardReservationBuilder()
                .setCustomerId(customer.getId())
                .setStartTime((LocalDateTime) parameters.get("startTime"))
                .setEndTime((LocalDateTime) parameters.get("endTime"))
                .setPeopleTotal(peopleTotal);

        if ((boolean) parameters.get("isGroup")) {
            builder.setTableIds(List.of(suitableTableIds.toArray(new Integer[0])))
                    .setSpecialSetup((String) parameters.get("specialSetup"));
        }

        Reservation reservation = builder.build();
        reservationDAO.insertReservation(reservation, suitableTableIds);
    }

    public void deleteReservation(int reservationId) {
        reservationDAO.deleteReservation(reservationId);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }

    private List<Integer> findSuitableTables(List<Table> availableTables, int peopleTotal) {
        List<Integer> suitableTableIds = new ArrayList<>();
        int currentTotal = 0;

        for (Table table : availableTables) {
            if (currentTotal + table.getCapacity() <= peopleTotal) {
                suitableTableIds.add(table.getId());
                currentTotal += table.getCapacity();
                if (currentTotal >= peopleTotal) {
                    break;
                }
            }
        }

        return suitableTableIds;
    }

}
