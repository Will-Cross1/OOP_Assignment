package services;

import models.*;

import java.time.LocalDate;
import java.util.*;

public class OrderService {

    private final List<Order> orders;
    private final OrderCreationService orderCreationService;

    public OrderService(OrderCreationService orderCreationService) {
        this.orders = new ArrayList<>();
        this.orderCreationService = orderCreationService;
    }

    public void createOrder(Map<String, Integer> products, LocalDate arrival, Order.Status status, FinancialTransaction.Type type) {
        Order order = orderCreationService.createOrder(products, arrival, status, type);
        orders.add(order);
    }

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
}
