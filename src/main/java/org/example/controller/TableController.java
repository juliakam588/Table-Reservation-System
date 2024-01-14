package org.example.controller;

import org.example.model.TableService;
import org.example.model.entities.Table;
import org.example.view.TableView;

import java.util.List;

public class TableController {
    private TableService tableService;
    private TableView tableView;


    public TableController(TableService tableService, TableView tableView) {
        this.tableService = tableService;
        this.tableView = tableView;
    }


    public void addTable(int capacity) {
        try {
            tableService.addTable(capacity);
            System.out.println("Table with capacity " + capacity + " added successfully to the database.");

        } catch (Exception e) {
            System.out.println("Failed to add table: " + e.getMessage());
        }
    }

    public void deleteTable(int tableId) {
        try {
            tableService.deleteTable(tableId);
            System.out.println("Table with ID " + tableId + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("Failed to delete table: " + e.getMessage());
        }
    }

    public void showAvailableTables() {
        try {
            List<Table> availableTables = tableService.getAvailableTables();
            if (!availableTables.isEmpty()) {
                tableView.displayMainRow();
                for (Table table : availableTables) {
                    tableView.displayTable(table.getId(), table.getCapacity(), table.isAvailable());
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void showAllTables() {
        try {
            List<Table> allTables = tableService.getAllTables();
            if (!allTables.isEmpty()) {
                tableView.displayMainRow();
                for (Table table : allTables) {
                    tableView.displayTable(table.getId(), table.getCapacity(), table.isAvailable());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to display all tables: " + e.getMessage());
        }
    }

}
