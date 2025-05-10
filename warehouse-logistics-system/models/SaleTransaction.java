package models;

import java.time.LocalDate;

/**
 * Represents a financial transaction of type SALE.
 * Extends the base {@link FinancialTransaction} class.
 */
public class SaleTransaction extends FinancialTransaction {

    /**
     * Constructs a SaleTransaction with the specified date.
     *
     * @param transactionDate the date of the sale transaction
     */
    public SaleTransaction(LocalDate transactionDate) {
        super(transactionDate);
    }

    /**
     * Returns the type of this transaction, which is {@code Type.SALE}.
     *
     * @return the transaction type (SALE)
     */
    @Override
    public Type getType() {
        return Type.SALE;
    }
}
