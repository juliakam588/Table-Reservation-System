package org.example.controller;

import org.example.model.ReservationService;
import org.example.model.entities.Customer;
import org.example.model.entities.Reservation;
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

    public void addReservation(Map<String, Object> parameters) {
        reservationService.addReservation(parameters);
    }

    public void deleteReservation(int id) {
        reservationService.deleteReservation(id);
    }

    public void showAllReservations() {

            List<Reservation> reservations = reservationService.getAllReservations();
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
                        r.getSpecialSetup());
            }

    }

        public void updateReservation(Map<String, Object> parameters){
            //reservationService.updateReservation(parameters);
        }
    }


