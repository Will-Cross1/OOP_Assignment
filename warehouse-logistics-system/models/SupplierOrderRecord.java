package models;

import java.time.LocalDate;
import java.util.Map;

public class SupplierOrderRecord {
    private int orderId;
    private LocalDate date;
    private Map<Integer, Integer> items; // SupplierItemId -> Quantity

    // Constructor
    public SupplierOrderRecord(int orderId, LocalDate date, Map<Integer, Integer> items) {
        this.orderId = orderId;
        this.date = date;
        this.items = items;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return "Order #" + orderId + " on " + date + ": " + items;
    }
}
