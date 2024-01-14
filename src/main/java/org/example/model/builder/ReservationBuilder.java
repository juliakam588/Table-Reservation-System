package org.example.model.builder;

import org.example.model.entities.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationBuilder {
    ReservationBuilder setCustomerId(int customerId);
    ReservationBuilder setStartTime(LocalDateTime startTime);
    ReservationBuilder setEndTime(LocalDateTime endTime);

    ReservationBuilder setTableIds(List<Integer> tableIds);

    Reservation build();

    ReservationBuilder setSpecialSetup(String specialSetup);
}
