package org.example.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class ReservationValidator {
    public ReservationValidator() {
    }

    private static final LocalTime OPEN_TIME = LocalTime.of(10, 0); // 10:00
    private static final LocalTime CLOSE_TIME = LocalTime.of(22, 0); // 22:00

    public boolean isWithinBusinessHours(LocalDateTime startTime, LocalDateTime endTime) {
        LocalTime start = startTime.toLocalTime();
        LocalTime end = endTime.toLocalTime();
        LocalTime closingTime = CLOSE_TIME;

        if (!startTime.toLocalDate().isEqual(endTime.toLocalDate())) {
            closingTime = LocalTime.MAX;
        }

        return !start.isBefore(OPEN_TIME) && !end.isAfter(closingTime) && start.isBefore(end);
    }
    public boolean validateReservationParameters(Map<String, Object> parameters) {
        if (!validateCustomerInformation(parameters.get("customerName"), parameters.get("contactInfo"))) {
            return false;
        }

        if (!validateReservationTime((LocalDateTime) parameters.get("startTime"), (LocalDateTime) parameters.get("endTime"))) {
            return false;
        }

        if(!isWithinBusinessHours((LocalDateTime) parameters.get("startTime"), (LocalDateTime) parameters.get("endTime"))) {
            return false;
        }

        if (!validatePeopleTotal((int) parameters.get("peopleTotal"))) {
            return false;
        }

        return true;
    }

    private boolean validateCustomerInformation(Object customerName, Object contactInfo) {
        if (customerName == null || contactInfo == null) {
            return false;
        }

        String name = customerName.toString();
        String contact = contactInfo.toString();
        return !name.trim().isEmpty() && !contact.trim().isEmpty();
    }

    private boolean validateReservationTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }

        return !startTime.isAfter(endTime) && !startTime.isEqual(endTime);
    }

    private boolean validatePeopleTotal(int peopleTotal) {
        return peopleTotal > 0;
    }

}
