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


    public void addTable() {
        try {
            int tableCapacity = tableView.getCapacityFromUser();
            String result = tableService.addTable(tableCapacity);
            tableView.displayMessage(result);
        } catch (Exception e) {
            tableView.displayMessage("Failed to add table: " + e.getMessage());
        }
    }

    public void deleteTable() {
        try {
            int tableId = tableView.getTableIdToDelete();
            String result = tableService.deleteTable(tableId);
            tableView.displayMessage(result);
        } catch (Exception e) {
            tableView.displayMessage("Failed to delete table: " + e.getMessage());
        }
    }

    public void showTables() {
        String option = tableView.getTableStatusTypeFromUser();
        switch (option) {
            case "a" -> showAllTables();
            case "b" -> showAvailableTables();
            default -> tableView.displayMessage("Invalid input");
        }
    }

    private void showAvailableTables() {
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

    private void showAllTables() {
        try {
            List<Table> allTables = tableService.getAllTables();
            if (!allTables.isEmpty()) {
                tableView.displayMainRow();
                for (Table table : allTables) {
                    tableView.displayTable(table.getId(), table.getCapacity(), table.isAvailable());
                }
            }
        } catch (Exception e) {
            tableView.displayMessage("Failed to display all tables: " + e.getMessage());
        }
    }

}
