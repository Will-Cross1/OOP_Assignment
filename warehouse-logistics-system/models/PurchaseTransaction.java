package models;

import java.time.LocalDate;

/**
 * Represents a financial transaction of type PURCHASE.
 * Extends the base {@link FinancialTransaction} class.
 */
public class PurchaseTransaction extends FinancialTransaction {

    /**
     * Constructs a PurchaseTransaction with the specified date.
     *
     * @param transactionDate the date of the purchase transaction
     */
    public PurchaseTransaction(LocalDate transactionDate) {
        super(transactionDate);
    }

    /**
     * Returns the type of this transaction, which is {@code Type.PURCHASE}.
     *
     * @return the transaction type (PURCHASE)
     */
    @Override
    public Type getType() {
        return Type.PURCHASE;
    }
}
