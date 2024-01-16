package org.example.model.builder;

import org.example.model.entities.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupReservationBuilder implements ReservationBuilder {
    private StandardReservationBuilder standardBuilder = new StandardReservationBuilder();
    private List<Integer> groupTableIds = new ArrayList<>();
    private String specialSetup = null;
    private boolean isGroup;

    @Override
    public GroupReservationBuilder setCustomerId(int customerId) {
        standardBuilder.setCustomerId(customerId);
        return this;
    }

    @Override
    public GroupReservationBuilder setStartTime(LocalDateTime startTime) {
        standardBuilder.setStartTime(startTime);
        return this;
    }

    @Override
    public GroupReservationBuilder setEndTime(LocalDateTime endTime) {
        standardBuilder.setEndTime(endTime);
        return this;
    }

    @Override
    public GroupReservationBuilder setTableIds(List<Integer> tableIds) {
        this.groupTableIds = new ArrayList<>(tableIds);
        return this;
    }


    public GroupReservationBuilder setSpecialSetup(String specialSetup) {
        this.specialSetup = specialSetup;
        return this;
    }

    @Override
    public GroupReservationBuilder setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
        return this;
    }

    public GroupReservationBuilder setCustomerName(String customerName) {
        standardBuilder.setCustomerName(customerName);
        return this;
    }

    @Override
    public Reservation build() {
        Reservation reservation = standardBuilder.build();
        reservation.setTableIds(groupTableIds);
        reservation.setSpecialSetup(specialSetup);
        reservation.setIsGroup(true);
        return reservation;
    }
}