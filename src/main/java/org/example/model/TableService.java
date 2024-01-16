package org.example.model;

import org.example.model.dao.TableDAO;
import org.example.model.entities.Table;

import java.util.List;

import static org.example.model.entities.Table.MAX_TABLE_CAPACITY;

public class TableService {
    private TableDAO tableDAO;

    public TableService(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }

    public String addTable(int tableCapacity) {
        if (tableCapacity <= 0 || tableCapacity > MAX_TABLE_CAPACITY) {
            return "Invalid table capacity. It must be a positive number up to " + MAX_TABLE_CAPACITY + ".";
        }
        Table table = new Table();
        table.setCapacity(tableCapacity);
        tableDAO.insert(table);
        return "Table with capacity " + tableCapacity + " added successfully to the database.";
    }

    public String deleteTable(int tableId) {
        Table tableToDelete = getTableById(tableId);
        if (tableToDelete == null) {
            return "Table with ID " + tableId + " does not exist.";
        }
        if (!isTableAvailable(tableId)) {
           return "Table with ID " + tableId + " is currently reserved and cannot be deleted.";
        }
        tableDAO.delete(tableId);
        return "Table with ID " + tableId + " deleted successfully.";
    }
    public List<Table> getAvailableTables() {
        List<Table> availableTables = tableDAO.getAvailableTables();
        if(availableTables.isEmpty()) {
            System.out.println("No available tables at the moment.");
            return null;
        }
        return availableTables;
    }

    public List<Table> getAllTables() {
        return tableDAO.getAll();
    }

    public Table getTableById(int tableId) {
        return tableDAO.get(tableId);
    }

    public boolean isTableAvailable(int tableId) {
        Table table = getTableById(tableId);
        return table != null && table.isAvailable();
    }
}