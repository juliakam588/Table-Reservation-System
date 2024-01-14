package org.example.controller;

import org.example.model.ReservationService;
import org.example.model.entities.Customer;
import org.example.model.entities.Reservation;
import org.example.view.ReservationView;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ReservationController {
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
        reservationView.displayReservations(reservations);
    }

    public void updateReservation(Map<String, Object> parameters) {
        //reservationService.updateReservation(parameters);
    }
}

