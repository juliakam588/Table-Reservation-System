package org.example.strategy.table_allocation;

import org.example.model.entities.Reservation;
import org.example.model.entities.Table;

import java.util.List;

public interface TableAllocationStrategy {
    public List<Integer> allocateTables(List<Table> availableTables, int numberOfGuests);
}
