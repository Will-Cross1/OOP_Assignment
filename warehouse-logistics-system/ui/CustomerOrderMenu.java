package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import models.InventoryItem;
import services.InventoryService;
import services.OrderService;

/**
 * Handles the customer order menu interface.
 * Allows users to view inventory, add items to an order, and complete or cancel an order.
 */
public class CustomerOrderMenu {
    private final Scanner scanner;
    private InventoryService inventoryService;
    private OrderService orderService;

    /**
     * Constructs a CustomerOrderMenu with the required services.
     *
     * @param scanner           the Scanner for user input
     * @param inventoryService  the service for managing inventory items
     * @param orderService      the service for handling customer orders
     */
    public CustomerOrderMenu(Scanner scanner, InventoryService inventoryService, OrderService orderService) {
        this.scanner = scanner;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    /**
     * Runs the customer order process.
     * Displays inventory, allows adding items to an order, and processes the order on confirmation.
     */
    public void run() {
        System.out.println("\n\nMake Order:");

        Map<String, Integer> saleProducts = new HashMap<>();
        for (InventoryItem item : inventoryService.getAllInventoryItems()) {
            System.out.println(item);
        }

        boolean order = false;

        while (!order) {
            System.out.println("\n1. Display Inventory");
            System.out.println("2. Add To Order");
            System.out.println("3. Complete Order");
            System.out.println("0. Cancel Order");
            System.out.println("Current Order: " + saleProducts);
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.println("\n");
                    for (InventoryItem item : inventoryService.getAllInventoryItems()) {
                        System.out.println(item);
                    }
                }
                case 2 -> addItems(saleProducts);
                case 3 -> order = true;
                case 0 -> {
                    System.out.println("Order canceled");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        System.out.println("Current Order: " + saleProducts);
        System.out.print("Confirm? (y/n): ");
        String confirm = ImportUtils.getUserChoiceStr(scanner);
        if (confirm.equals("y")) {
            try {
                int orderId = orderService.createOrder(
                    saleProducts,
                    false
                );
                System.out.println("Order sent!");
                System.out.print(orderService.getOrderById(orderId) + "\n\n");
            } catch (IllegalArgumentException e){
                System.out.print(e.getMessage());
            }
        } else {
            System.out.println("Order canceled");
        }
    }

    /**
     * Prompts the user to enter an item ID and quantity, and adds the item to the order.
     *
     * @param saleProducts the current map of product IDs and their quantities
     * @return the updated map of sale products
     */
    private Map<String, Integer> addItems(Map<String, Integer> saleProducts) {
        System.out.print("Select item ID: ");
        int id = ImportUtils.getUserChoice(scanner);

        System.out.print("Select a quantity: ");
        int quantity = ImportUtils.getUserChoice(scanner);

        saleProducts.put(String.valueOf(id), quantity);
        return saleProducts;
    }
}
