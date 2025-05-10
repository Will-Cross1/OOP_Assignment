package models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a historical record of a supplier order.
 * Stores the ordered items and their quantities, but no status or financial transaction.
 */
public class SupplierOrderRecord extends AbstractOrder {

    private final Map<Integer, Integer> items; // Maps SupplierItemId to quantity ordered

    /**
     * Constructs a new SupplierOrderRecord.
     *
     * @param orderId the unique ID of the order
     * @param date    the date the order was placed
     * @param total   the total cost of the order
     * @param items   a map of supplier item IDs to their ordered quantities
     */
    public SupplierOrderRecord(int orderId, LocalDate date, double total, Map<Integer, Integer> items) {
        super(orderId, date, total);
        this.items = Collections.unmodifiableMap(new HashMap<>(items));
    }

    /**
     * Returns the items in the order.
     *
     * @return an unmodifiable map of supplier item IDs to quantities
     */
    @Override
    public Map<Integer, Integer> getItems() {
        return items;
    }

    /**
     * Returns a string representation of the supplier order record,
     * including the base order details and the item map.
     *
     * @return a string summary of the order record
     */
    @Override
    public String toString() {
        return super.toString() + " | Items (ID=Qty): " + items;
    }
}
