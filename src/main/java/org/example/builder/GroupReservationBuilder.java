package org.example.builder;

import org.example.model.entities.Reservation;

import java.time.LocalDateTime;

public class GroupReservationBuilder implements ReservationBuilder{
    @Override
    public ReservationBuilder setCustomerId(int customerId) {
        return null;
    }

    @Override
    public ReservationBuilder setStartTime(LocalDateTime startTime) {
        return null;
    }

    @Override
    public ReservationBuilder setEndTime(LocalDateTime endTime) {
        return null;
    }

    @Override
    public ReservationBuilder addTableId(int tableId) {
        return null;
    }

    @Override
    public Reservation build() {
        return null;
    }
}
