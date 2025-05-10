package models;

import java.time.LocalDate;

/**
 * Represents a financial transaction with a date and total amount.
 * This is an abstract base class to be extended by specific transaction types such as
 * {@link PurchaseTransaction} or {@link SaleTransaction}.
 */
public abstract class FinancialTransaction {
    
    /**
     * Enum representing the type of financial transaction.
     */
    public enum Type {
        PURCHASE, SALE
    }

    private final LocalDate transactionDate;
    private double total;

    /**
     * Constructs a FinancialTransaction with the given transaction date.
     *
     * @param transactionDate the date of the transaction
     */
    public FinancialTransaction(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Returns the type of this financial transaction.
     * Must be implemented by subclasses.
     *
     * @return the transaction type
     */
    public abstract Type getType();

    /**
     * Returns the date of the transaction.
     *
     * @return the transaction date
     */
    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    /**
     * Returns the total monetary value of the transaction.
     *
     * @return the transaction total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total monetary value of the transaction.
     *
     * @param total the transaction total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Returns a string representation of the transaction, including type, date, and total.
     *
     * @return string summary of the transaction
     */
    @Override
    public String toString() {
        return "Transaction: " + getType() +
               " | Date: " + transactionDate +
               " | Total: £" + total;
    }
}
