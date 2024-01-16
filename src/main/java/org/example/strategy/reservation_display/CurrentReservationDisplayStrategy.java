package org.example.strategy.reservation_display;

import org.example.model.entities.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentReservationDisplayStrategy implements ReservationDisplayStrategy {
    @Override
    public List<Reservation> execute(List<Reservation> reservations) {
        LocalDateTime now = LocalDateTime.now();
        return reservations.stream()
                .filter(reservation -> !reservation.getStartTime().isBefore(now))
                .collect(Collectors.toList());
    }
}
