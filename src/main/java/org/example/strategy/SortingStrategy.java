package org.example.strategy;

import org.example.model.entities.Reservation;

import java.util.List;

public interface SortingStrategy {
    void executeSorting(List<Reservation> reservations);
}
