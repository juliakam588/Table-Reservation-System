package org.example.controller;

import org.example.model.ReservationService;
import org.example.model.entities.Customer;
import org.example.model.entities.Reservation;
import org.example.strategy.reservation_display.CurrentReservationDisplayStrategy;
import org.example.strategy.reservation_display.CustomerReservationDisplayStrategy;
import org.example.strategy.reservation_display.StartTimeReservationDisplayStrategy;
import org.example.view.ReservationView;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ReservationController {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private ReservationService reservationService;
    private ReservationView reservationView;

    public ReservationController(ReservationService reservationService, ReservationView reservationView) {
        this.reservationService = reservationService;
        this.reservationView = reservationView;
    }

    public void addReservation() {
        try {
            Map<String, Object> parameters = reservationView.getNewReservationData();
            String result = reservationService.addReservation(parameters);
            reservationView.displayMessage(result);
        } catch (Exception e) {
            reservationView.displayMessage("Error: " + e.getMessage());
        }
    }

    public void deleteReservation() {
        try {
            int reservationId = reservationView.getReservationIdToDelete();
            String result = reservationService.deleteReservation(reservationId);
            reservationView.displayMessage(result);
        } catch (RuntimeException e) {
            reservationView.displayMessage("Error: " + e.getMessage());
        }
    }

    public void showReservations() {
        String option = reservationView.getReservationDisplayOption();
        switch (option) {
            case "a":
                reservationService.setDisplayStrategy(new StartTimeReservationDisplayStrategy());
                break;
            case "b":
                reservationService.setDisplayStrategy(new CurrentReservationDisplayStrategy());
                break;
            case "c":
                String customerName = reservationView.getCustomerNameForReservations();
                reservationService.setDisplayStrategy(new CustomerReservationDisplayStrategy(customerName));
                break;
            default:
                reservationView.displayMessage("Invalid input");
                return;
        }

        List<Reservation> reservations = reservationService.executeDisplayStrategy();
        displayReservations(reservations);
    }


    private void displayReservations(List<Reservation> reservations) {
        reservationView.displayMessage("Reservations:");

        reservationView.displayMainRow();
        for (Reservation r : reservations) {
            String startTimeStr = r.getStartTime().format(dateTimeFormatter);
            String endTimeStr = r.getEndTime().format(dateTimeFormatter);
            StringJoiner joiner = new StringJoiner(" ");
            r.getTableIds().forEach(number -> joiner.add(String.format("%d", number)));

            reservationView.displayReservation(
                    r.getId(),
                    r.getCustomerId(),
                    startTimeStr,
                    endTimeStr,
                    joiner.toString(),
                    r.getSpecialSetup(),
                    r.getIsGroup(),
                    r.getCustomerName());
        }

    }


}


