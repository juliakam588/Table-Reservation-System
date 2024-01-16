package org.example.strategy.reservation_display;

import org.example.model.entities.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerReservationDisplayStrategy implements ReservationDisplayStrategy{
    private final String customerName;

    public CustomerReservationDisplayStrategy(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public List<Reservation> execute(List<Reservation> reservations) {
        return reservations.stream()
                .filter(reservation -> reservation.getCustomerName().equals(customerName))
                .collect(Collectors.toList());
    }
}
