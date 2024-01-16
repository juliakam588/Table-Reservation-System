package org.example.strategy.reservation_display;

import org.example.model.entities.Reservation;

import java.util.List;

public interface ReservationDisplayStrategy {
    List<Reservation> execute(List<Reservation> reservations);
}
