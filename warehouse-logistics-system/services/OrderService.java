package services;

import models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Service responsible for creating and managing orders and their delivery status.
 */
public class OrderService {

    private final List<Order> orders;
    private final OrderCreationService orderCreationService;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    /**
     * Constructs the OrderService with a provided OrderCreationService dependency.
     *
     * @param orderCreationService The service responsible for constructing new orders.
     */
    public OrderService(OrderCreationService orderCreationService) {
        this.orders = new ArrayList<>();
        this.orderCreationService = orderCreationService;
    }

    /**
     * Creates an order and schedules delivery if it's a purchase.
     *
     * @param products A map of product names to quantities.
     * @param isPurchase Whether the order is a purchase (true) or a sale (false).
     * @return The ID of the newly created order.
     */
    public int createOrder(Map<String, Integer> products, boolean isPurchase) {
        Order order = generateOrder(products, isPurchase);
        orders.add(order);

        if (isPurchase) {
            schedulePurchaseDelivery(order);
        }

        return order.getId();
    }

    /**
     * Internal helper method to create an order.
     *
     * @param products The product details.
     * @param isPurchase Whether this is a purchase order.
     * @return The generated Order.
     */
    private Order generateOrder(Map<String, Integer> products, boolean isPurchase) {
        Order.Status status = isPurchase
            ? Order.Status.PROCESSED
            : Order.Status.DELIVERED;

        FinancialTransaction transaction = isPurchase
            ? new PurchaseTransaction(LocalDate.now())
            : new SaleTransaction(LocalDate.now());

        return orderCreationService.createOrder(products, status, transaction);
    }

    /**
     * Schedules the delivery process for a purchase-type order.
     *
     * @param order The order to be scheduled.
     */
    private void schedulePurchaseDelivery(Order order) {
        Runnable stockUpdateTask = orderCreationService.getStockUpdateTask(order);
        deliveryStatusManager(order, stockUpdateTask);
    }

    /**
     * Retrieves all orders.
     *
     * @return A list containing all orders (defensive copy).
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Retrieves all orders that are of type PURCHASE.
     *
     * @return A list of purchase orders.
     */
    public List<Order> getPurchaseTransactions() {
        List<Order> purchaseOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getTransaction().getType() == FinancialTransaction.Type.PURCHASE) {
                purchaseOrders.add(order);
            }
        }
        return purchaseOrders;
    }

    /**
     * Retrieves all orders that are of type SALE.
     *
     * @return A list of sale orders.
     */
    public List<Order> getSaleTransactions() {
        List<Order> saleOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getTransaction().getType() == FinancialTransaction.Type.SALE) {
                saleOrders.add(order);
            }
        }
        return saleOrders;
    }

    /**
     * Finds an order by its ID.
     *
     * @param id The order ID to search for.
     * @return The order with the specified ID, or null if not found.
     */
    public Order getOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    /**
     * Manages the status transitions for purchase orders.
     *
     * PROCESSED → IN_TRANSIT after 10s  
     * IN_TRANSIT → DELIVERED after a further 10s  
     * Runs the given callback once delivery is complete.
     *
     * @param order        The order being delivered.
     * @param onDelivered  The task to run once delivery is complete.
     */
    private void deliveryStatusManager(Order order, Runnable onDelivered) {
        // Transition from PROCESSED to IN_TRANSIT (10 second delay)
        scheduler.schedule(() -> order.setStatus(Order.Status.IN_TRANSIT),
                10, TimeUnit.SECONDS);

        // Transition from IN_TRANSIT to DELIVERED and update stock (10 second delay)
        scheduler.schedule(() -> {
            order.setStatus(Order.Status.DELIVERED);
            onDelivered.run();
        }, 20, TimeUnit.SECONDS);
    }
}
