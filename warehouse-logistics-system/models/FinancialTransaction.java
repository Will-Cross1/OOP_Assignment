package models;

import java.time.LocalDate;

public class FinancialTransaction {
    public enum Type {
        PURCHASE, SALE
    }

    private Type type;
    private LocalDate transactionDate;
    private double total;

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

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Transaction: " + type + " | Date: " + transactionDate + " | Total: £" + total;
    }
}
