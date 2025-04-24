package services;

import models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderService {

    private final List<Order> orders;
    private final OrderCreationService orderCreationService;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    // Constructor
    public OrderService(OrderCreationService orderCreationService) {
        this.orders = new ArrayList<>();
        this.orderCreationService = orderCreationService;
    }

    // Order Creation
    public void createOrder(Map<String, Integer> products, LocalDate arrival, FinancialTransaction.Type type) {
        Order.Status status = Order.Status.PROCESSED;
        if (type == FinancialTransaction.Type.SALE) {
            status = Order.Status.DELIVERED;
        }
        Order order = orderCreationService.createOrder(products, arrival, status, type);
        orders.add(order);
        if (type == FinancialTransaction.Type.PURCHASE) {
            deliveryStatusManager(order, orderCreationService.getStockUpdateTask(order));
        };
    }

    // Order Retrieval
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders); // defensive copy
    }

    public Order getOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    // Schedule and change the status of the purchase order
    private void deliveryStatusManager(Order order, Runnable onDelivered) {
        // Initial state transition after 10 seconds (from PROCESSED to IN_TRANSIT)
        scheduler.schedule(() -> order.setStatus(Order.Status.IN_TRANSIT),
                10, TimeUnit.SECONDS);

        // Second state transition after another 10 seconds (from IN_TRANSIT to DELIVERED)
        scheduler.schedule(() -> {
            order.setStatus(Order.Status.DELIVERED);
            onDelivered.run(); // apply stock only when status is DELIVERED
        }, 20, TimeUnit.SECONDS);
    }
}
