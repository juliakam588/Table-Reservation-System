package org.example.strategy.reservation_display;

import org.example.model.entities.Reservation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StartTimeReservationDisplayStrategy implements ReservationDisplayStrategy{
    @Override
    public List<Reservation> execute(List<Reservation> reservations) {
        return reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .collect(Collectors.toList());
    }
}
