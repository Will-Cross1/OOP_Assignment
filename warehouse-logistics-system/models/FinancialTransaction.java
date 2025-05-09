package models;

import java.time.LocalDate;

/**
 * Represents a financial transaction, either a purchase or sale, with associated details 
 * such as the transaction type, date, and total amount.
 * 
 * The class captures the type of transaction (PURCHASE or SALE), the date on which it occurred, 
 * and the total monetary value of the transaction. The total can be set after creation, 
 * allowing for updates in case the transaction value changes.
 * 
 * This class provides methods for retrieving transaction details and displaying them in a readable format.
 * 
 * This model is stored inside each order, allowing for easy tracking of financial transactions related to orders.
 */
public class FinancialTransaction {
    public enum Type {
        PURCHASE, SALE
    }

    private Type type;
    private LocalDate transactionDate;
    private double total;

    // Constructor
    public FinancialTransaction(Type type, LocalDate transactionDate) {
        this.type = type;
        this.transactionDate = transactionDate;
    }

    // Getters
    public Type getType() {
        return type;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public double getTotal() {
        return total;
    }

    // Setter
    public void setTotal(double total) {
        this.total = total;
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return "Transaction: " + type + " | Date: " + transactionDate + " | Total: £" + total;
    }
}
