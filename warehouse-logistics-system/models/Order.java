package models;

import java.time.LocalDate;
import java.util.Map;

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
