package org.example.controller;

import org.example.model.TableService;
import org.example.model.entities.Table;
import org.example.view.TableView;

public class TableController {
    private TableService tableService;
    private TableView tableView;


    public TableController(TableService tableService, TableView tableView) {
        this.tableService = tableService;
        this.tableView = tableView;
    }




}
