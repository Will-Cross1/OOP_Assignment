package ui;

import java.util.Scanner;

import models.InventoryItem;
import services.InventoryService;

public class InventoryMenu {
    private final Scanner scanner;
    private InventoryService inventoryService;

    public InventoryMenu(Scanner scanner, InventoryService inventoryService) {
        this.scanner = scanner;
        this.inventoryService = inventoryService;
    }

    public void run() {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Inventory Management ===");
            System.out.println("1. View Inventory");
            System.out.println("2. Change Prices");
            System.out.println("3. Add new Item");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> viewInventory();
                case 2 -> changePrices();
                case 3 -> addItem();
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewInventory() {
        boolean back = false;

        while (!back) {
            System.out.println("\n-- View Inventory --");
            System.out.println("1. View All Items");
            System.out.println("2. Search by Item ID");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.println("Showing all inventory items...");
                    for (InventoryItem item : inventoryService.getAllInventoryItems()) {
                        System.out.println(item);
                    }
                }
                case 2 -> {
                    System.out.print("Enter Item ID: ");
                    int id = ImportUtils.getUserChoice(scanner);
                    System.out.println("Searching inventory for item ID: " + id);
                    System.out.print(inventoryService.findById(id) + "\n\n");
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void changePrices() {
        System.out.println("Change Prices");
        for (InventoryItem item : inventoryService.getAllInventoryItems()) {
            System.out.println(item);
        }

        System.out.print("Enter Item ID: ");
        int id = ImportUtils.getUserChoice(scanner);
        System.out.print(inventoryService.findById(id) + "\n\n");

        System.out.print("Enter New Price: ");
        double price = ImportUtils.getUserChoiceDouble(scanner);

        inventoryService.updateItemPrice(id, price);
        System.out.print("Updated");
        System.out.print(inventoryService.findById(id) + "\n\n");
    }

    private void addItem() {
        System.out.println("\nAdd Inventory Item");
    
        System.out.print("Enter item name: ");
        String name = ImportUtils.getUserChoiceStr(scanner);
    
        System.out.print("Enter description: ");
        String description = ImportUtils.getUserChoiceStr(scanner);
    
        System.out.print("Enter new unit price: ");
        double unitPrice = ImportUtils.getUserChoiceDouble(scanner);
    
        int nextId = inventoryService.getNextAvailableId();
        inventoryService.addInventoryItem(nextId, name, description, unitPrice, 0);
    
        System.out.println("Item added with ID: " + nextId);
    }
}
