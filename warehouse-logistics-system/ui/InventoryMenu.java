package ui;

import java.util.Scanner;

import models.InventoryItem;
import services.InventoryService;

/**
 * Handles the user interface for inventory management.
 * Provides options to view inventory, change prices, and add new items.
 */
public class InventoryMenu {
    private final Scanner scanner;
    private InventoryService inventoryService;

    /**
     * Constructs a new InventoryMenu.
     *
     * @param scanner           the Scanner for reading user input
     * @param inventoryService  the service responsible for inventory operations
     */
    public InventoryMenu(Scanner scanner, InventoryService inventoryService) {
        this.scanner = scanner;
        this.inventoryService = inventoryService;
    }

    /**
     * Runs the inventory menu loop until the user chooses to exit.
     */
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

    /**
     * Displays the inventory viewing submenu, allowing the user to see all items or search by ID.
     */
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

    /**
     * Prompts the user to select an item by ID and update its price.
     */
    private void changePrices() {
        System.out.println("Change Prices");

        for (InventoryItem item : inventoryService.getAllInventoryItems()) {
            System.out.println(item);
        }

        System.out.print("Enter Item ID: ");
        int id = ImportUtils.getUserChoice(scanner);

        // Validate ID
        InventoryItem selectedItem = inventoryService.findById(id);
        if (id == -1 || selectedItem == null) {
            System.out.println("Invalid ID entered.");
            return;
        }

        System.out.println(selectedItem + "\n");

        System.out.print("Enter New Price: ");
        double price = ImportUtils.getUserChoiceDouble(scanner);

        // Validate Price
        if (price < 0) {
            System.out.println("Invalid price entered.");
            return;
        }

        inventoryService.updateItemPrice(id, price);
        System.out.println("Updated:");
        System.out.println(inventoryService.findById(id) + "\n");
    }

    /**
     * Prompts the user to enter information for a new inventory item and adds it to the inventory.
     */
    private void addItem() {
        System.out.println("\nAdd Inventory Item");

        System.out.print("Enter item name: ");
        String name = ImportUtils.getUserChoiceStr(scanner);

        System.out.print("Enter description: ");
        String description = ImportUtils.getUserChoiceStr(scanner);

        System.out.print("Enter new unit price: ");
        double unitPrice = ImportUtils.getUserChoiceDouble(scanner);

        if (unitPrice < 0) {
            System.out.println("Invalid unit price entered.");
            return;
        }

        int nextId = inventoryService.getNextAvailableId();
        inventoryService.addInventoryItem(nextId, name, description, unitPrice, 0);

        System.out.println("Item added with ID: " + nextId);
    }
}
