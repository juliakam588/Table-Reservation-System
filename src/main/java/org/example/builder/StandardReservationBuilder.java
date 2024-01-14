package org.example.builder;

import org.example.model.entities.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StandardReservationBuilder implements ReservationBuilder{
    private int customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> tableIds = new ArrayList<>();
    private int peopleTotal;
    private String specialSetup = "";

    public StandardReservationBuilder setCustomerId(int customerId) {
        this.customerId = customerId;
        return this;
    }

    public StandardReservationBuilder setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public StandardReservationBuilder setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public StandardReservationBuilder addTableId(int tableId) {
        this.tableIds.add(tableId);
        return this;
    }

    public StandardReservationBuilder setTableIds(List<Integer> tableIds) {
        this.tableIds = new ArrayList<>(tableIds);
        return this;
    }

    public StandardReservationBuilder setPeopleTotal(int peopleTotal) {
        this.peopleTotal = peopleTotal;
        return this;
    }

    public StandardReservationBuilder setSpecialSetup(String specialSetup) {
        this.specialSetup = specialSetup;
        return this;
    }

    public Reservation build() {
        Reservation reservation = new Reservation();
        reservation.setCustomerId(this.customerId);
        reservation.setStartTime(this.startTime);
        reservation.setEndTime(this.endTime);
        reservation.setTableIds(this.tableIds.toArray(new Integer[0]));
        reservation.setPeopleTotal(this.peopleTotal);
        reservation.setSpecialSetup(this.specialSetup);
        return reservation;
    }
}
