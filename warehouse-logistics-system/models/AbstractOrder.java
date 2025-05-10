package models;

import java.time.LocalDate;
import java.util.Map;

/**
 * An abstract base class representing a general order.
 * Contains common fields like ID, date, and total amount.
 * Subclasses must define how items are stored and retrieved.
 */
public abstract class AbstractOrder {
    private final int id;               // Unique order ID
    private final LocalDate date;       // Date the order was created
    private final double total;         // Total cost of the order

    /**
     * Constructs an abstract order with the specified ID, date, and total.
     *
     * @param id    the unique identifier for the order
     * @param date  the date the order was placed
     * @param total the total cost of the order
     */
    public AbstractOrder(int id, LocalDate date, double total) {
        this.id = id;
        this.date = date;
        this.total = total;
    }

    /**
     * Returns the order ID.
     *
     * @return the ID of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the date the order was placed.
     *
     * @return the order date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the total cost of the order.
     *
     * @return the total amount
     */
    public double getTotal() {
        return total;
    }

    /**
     * Abstract method to return the items in the order.
     * The key type is intentionally left generic, allowing flexibility in subclasses.
     *
     * @return a map of item identifiers to their quantities
     */
    public abstract Map<?, Integer> getItems();

    /**
     * Returns a string representation of the order with ID, date, and total.
     *
     * @return a formatted string describing the order
     */
    @Override
    public String toString() {
        return "Order #" + id + " on " + date + " total: £" + total;
    }
}
