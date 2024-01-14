package org.example.model.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Reservation {
    private int id;
    private int customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> tableIds;
    private int peopleTotal;
    private String specialSetup;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setTableIds(List<Integer> tableIds) {
        this.tableIds = tableIds;
    }
    public List<Integer> getTableIds() {
        return tableIds;
    }

    public void setPeopleTotal(int peopleTotal) {
        this.peopleTotal = peopleTotal;
    }

    public int getPeopleTotal() {
        return peopleTotal;
    }

    public void setSpecialSetup(String specialSetup) {
        this.specialSetup = specialSetup;
    }

    public String getSpecialSetup() {
        return specialSetup;
    }
}
