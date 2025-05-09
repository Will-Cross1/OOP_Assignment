package models;

import java.util.Map;

/**
 * Represents an order in the system, including the products in the order, 
 * its current status, and the associated financial transaction.
 * 
 * The order is associated with a set of products (mapped by item ID or supplier:item ID),
 * and each order has a status that indicates its processing stage (e.g., PROCESSED, IN_TRANSIT, DELIVERED).
 * The financial transaction related to the order is also stored, providing details of the payment or billing.
 * 
 * This class provides methods for managing the order's state and accessing its details.
 */
public class Order {
    public enum Status {
        PROCESSED, IN_TRANSIT, DELIVERED
    }

    private int id;
    private Map<String, Integer> products; // key = item id or supplier:supplierItem
    private Status status;
    private FinancialTransaction transaction;

    // Constructor
    public Order(int id, Map<String, Integer> products,
                 Status status, FinancialTransaction transaction) {
        this.id = id;
        this.products = products;
        this.status = status;
        this.transaction = transaction;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public Status getStatus() {
        return status;
    }

    public FinancialTransaction getTransaction() {
        return transaction;
    }

    // Setters
    public void setStatus(Status status) {
        this.status = status;
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return "Order #" + id + " | Type: " + transaction.getType() +
               " | Status: " + status + "\n" + transaction;
    }
}
