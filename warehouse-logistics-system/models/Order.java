package models;

import java.time.LocalDate;
import java.util.Map;

public class Order {
    public enum Status {
        PROCESSED, IN_TRANSIT, DELIVERED
    }

    private int id;
    private Map<String, Integer> products; // key = item id or supplier:supplierItem
    private LocalDate estimatedArrival;
    private Status status;
    private FinancialTransaction transaction;

    public Order(int id, Map<String, Integer> products, LocalDate estimatedArrival,
                 Status status, FinancialTransaction transaction) {
        this.id = id;
        this.products = products;
        this.estimatedArrival = estimatedArrival;
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

    public LocalDate getEstimatedArrival() {
        return estimatedArrival;
    }

    public Status getStatus() {
        return status;
    }

    public FinancialTransaction getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return "Order #" + id + " | Type: " + transaction.getType() +
               " | Status: " + status + " | ETA: " + estimatedArrival + "\n" + transaction;
    }
}
