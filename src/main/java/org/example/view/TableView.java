package org.example.view;

import java.time.LocalDate;

public class TableView {
    public void displayMainRow() {
        System.out.print("Table ids\t");
        System.out.print("Table size\t");
        System.out.print("Status");

    }

    public void displayTable(int id, int tableSize, boolean isAvailable) {
        System.out.println("Table #" + id);
        System.out.print("\t" + tableSize + "\t");
        if (isAvailable) {
            System.out.print("free");
        } else {
            System.out.print("taken");
        }
        System.out.println();
    }
}


