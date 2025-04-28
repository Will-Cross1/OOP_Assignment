package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import models.FinancialTransaction;
import services.InventoryService;
import services.OrderService;

public class CustomerOrderMenu {
    private final Scanner scanner;
    private InventoryService inventoryService;
    private OrderService orderService;

    public CustomerOrderMenu(Scanner scanner, InventoryService inventoryService, OrderService orderService) {
        this.scanner = scanner;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    public void run() {
        System.out.println("\n\nMake Order:");

        Map<String, Integer> saleProducts = new HashMap<>();
        inventoryService.printInventory();

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
                    inventoryService.printInventory();
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
                    FinancialTransaction.Type.SALE
                );
                System.out.println("Order sent!");
                System.out.print(orderService.getOrderById(orderId) + "\n\n");
            } catch (IllegalArgumentException e){
                System.out.print(e.getMessage());
            };
        } else {
            System.out.println("Order canceled");
            return;
        }
    }

    private Map<String, Integer> addItems(Map<String, Integer> saleProducts) {
        System.out.print("Select an ID: ");
        int id = ImportUtils.getUserChoice(scanner);

        System.out.print("Select a quantity: ");
        int quantity = ImportUtils.getUserChoice(scanner);

        saleProducts.put(String.valueOf(id), quantity);
        return saleProducts;
    }
}
