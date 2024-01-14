package org.example.model;

import org.example.model.dao.ReservationDAO;
import org.example.model.dao.TableDAO;
import org.example.model.entities.Reservation;
import org.example.model.entities.Table;

import java.util.List;

public class TableService {
    private TableDAO tableDAO;

    public TableService(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }

    public void addReservation(Table table) {
        tableDAO.insertTable(table);
    }

    public void deleteReservation(int tableID) {
        tableDAO.deleteTable(tableID);
    }

    public List<Table> getAllCustomers() {
        return tableDAO.getAllTables();
    }


}
