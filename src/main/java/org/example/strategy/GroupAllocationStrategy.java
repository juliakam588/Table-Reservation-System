package org.example.strategy;

import org.example.model.entities.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GroupAllocationStrategy implements TableAllocationStrategy{
    @Override
    public List<Integer> allocateTables(List<Table> availableTables, int peopleTotal) {
        availableTables.sort(Comparator.comparingInt(Table::getCapacity).reversed());
        List<Integer> suitableTableIds = new ArrayList<>();
        int currentTotal = 0;

        for (Table table : availableTables) {
            if (currentTotal + table.getCapacity() <= peopleTotal || (currentTotal < peopleTotal && currentTotal + table.getCapacity() - peopleTotal < table.getCapacity())) {
                suitableTableIds.add(table.getId());
                currentTotal += table.getCapacity();
                if (currentTotal >= peopleTotal) {
                    break;
                }
            }
        }

        if (currentTotal < peopleTotal) {
            return Collections.emptyList();
        }

        return suitableTableIds;
    }
}
