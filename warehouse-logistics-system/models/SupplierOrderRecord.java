package models;

import java.time.LocalDate;
import java.util.Map;

/**
 * Represents a record of an order placed with a supplier.
 *
 * Each record contains the order ID, date of the order, total cost,
 * and a map of item IDs to quantities ordered.
 * This class is primarily used for tracking a supplier's order history.
 * 
 * This class is used instead of the Order class for supplier orders,
 * as it is more focused on the supplier's perspective.
 * It contains only the necessary information for the supplier,
 * such as the order ID, date, total cost, and items ordered.
 */
public class SupplierOrderRecord {
    private int orderId;
    private LocalDate date;
    private double total;
    private Map<Integer, Integer> items; // SupplierItemId -> Quantity

    // Constructor
    public SupplierOrderRecord(int orderId, LocalDate date, double total, Map<Integer, Integer> items) {
        this.orderId = orderId;
        this.date = date;
        this.total = total;
        this.items = items;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return "Order #" + orderId + " on " + date + " (item ID=Quantity): " + items + " total " + total;
    }
}
