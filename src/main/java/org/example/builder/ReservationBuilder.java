package org.example.builder;

import org.example.model.entities.Reservation;

import java.time.LocalDateTime;

public interface ReservationBuilder {
    ReservationBuilder setCustomerId(int customerId);
    ReservationBuilder setStartTime(LocalDateTime startTime);
    ReservationBuilder setEndTime(LocalDateTime endTime);
    ReservationBuilder addTableId(int tableId);
    Reservation build();
}
