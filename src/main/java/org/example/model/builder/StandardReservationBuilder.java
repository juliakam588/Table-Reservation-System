package org.example.model.builder;

import org.example.model.entities.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StandardReservationBuilder implements ReservationBuilder{
    private int customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> tableIds = new ArrayList<>();
    private String specialSetup;
    private boolean isGroup;
    private String customerName;

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

    public StandardReservationBuilder setTableIds(List<Integer> tableIds) {
        this.tableIds = new ArrayList<>(tableIds);
        return this;
    }


    public StandardReservationBuilder setSpecialSetup(String specialSetup) {
        this.specialSetup = specialSetup;
        return this;
    }

    public StandardReservationBuilder setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
        return this;
    }

    public StandardReservationBuilder setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public Reservation build() {
        Reservation reservation = new Reservation();
        reservation.setCustomerId(this.customerId);
        reservation.setStartTime(this.startTime);
        reservation.setEndTime(this.endTime);
        reservation.setTableIds(this.tableIds);
        reservation.setSpecialSetup(this.specialSetup);
        reservation.setIsGroup(false);
        reservation.setCustomerName(customerName);
        return reservation;
    }
}
