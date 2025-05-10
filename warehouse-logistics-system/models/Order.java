package models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an order in the system. 
 * Can be a sale or purchase order, and tracks the items involved, 
 * the status of the order, and the associated financial transaction.
 */
public class Order extends AbstractOrder {

    /**
     * Enum representing the possible statuses of an order.
     */
    public enum Status {
        PROCESSED, IN_TRANSIT, DELIVERED
    }

    private final Map<String, Integer> products; // itemId for sale or supplierId:itemId for purchase
    private Status status;
    private final FinancialTransaction transaction;

    /**
     * Constructs a new Order instance.
     *
     * @param id           the order ID
     * @param date         the date of the order
     * @param total        the total cost of the order
     * @param products     a map of product identifiers to quantities
     * @param status       the initial status of the order
     * @param transaction  the financial transaction associated with this order
     */
    public Order(int id, LocalDate date, double total,
                 Map<String, Integer> products,
                 Status status,
                 FinancialTransaction transaction) {
        super(id, date, total);
        this.products = Collections.unmodifiableMap(new HashMap<>(products));
        this.status = status;
        this.transaction = transaction;
    }

    /**
     * Returns the ordered items. Keys are product identifiers, values are quantities.
     *
     * @return an unmodifiable map of product identifiers to quantities
     */
    @Override
    public Map<String, Integer> getItems() {
        return products;
    }

    /**
     * Gets the current status of the order.
     *
     * @return the order's status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets a new status for the order.
     *
     * @param status the new status to assign
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the financial transaction associated with this order.
     *
     * @return the transaction object
     */
    public FinancialTransaction getTransaction() {
        return transaction;
    }

    /**
     * Returns a string representation of the order, including status and transaction details.
     *
     * @return a string summary of the order
     */
    @Override
    public String toString() {
        return super.toString() +
               " | Status: " + status +
               " | Type: " + transaction.getType() +
               "\n" + transaction;
    }
}
