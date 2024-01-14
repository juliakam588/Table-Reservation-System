package org.example.view;

import org.example.model.entities.Reservation;

import java.time.LocalDate;
import java.util.List;


public class ReservationView {

    public void displayMainRow() {
        System.out.print("Reservation ids\t");
        System.out.print("Customer names\t");
        System.out.print("Valid Till\t");
        System.out.print("Table size");

    }

    public void displayReservations(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            System.out.println("Reservation ID: " + reservation.getId());
            System.out.println("Customer ID: " + reservation.getCustomerId());
            System.out.println("Start Time: " + reservation.getStartTime());
            System.out.println("End Time: " + reservation.getEndTime());
        }
    }
}
