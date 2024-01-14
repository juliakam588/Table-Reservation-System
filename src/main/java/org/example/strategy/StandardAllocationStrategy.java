package org.example.strategy;

import org.example.model.entities.Table;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StandardAllocationStrategy implements TableAllocationStrategy{
    @Override
    public List<Integer> allocateTables(List<Table> availableTables, int peopleTotal) {
        return availableTables.stream()
                .filter(table -> table.getCapacity() >= peopleTotal)
                .sorted(Comparator.comparingInt(Table::getCapacity))
                .findFirst()
                .map(table -> List.of(table.getId()))
                .orElse(Collections.emptyList());
    }
}
