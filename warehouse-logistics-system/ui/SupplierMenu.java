package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import models.InventoryItem;
import models.Order;
import models.Supplier;
import models.SupplierItem;
import services.SupplierService;
import services.OrderService;
import services.InventoryService;

/**
 * Menu UI class responsible for supplier and purchase order management.
 */
public class SupplierMenu {
    private final Scanner scanner;
    private SupplierService supplierService;
    private OrderService orderService;
    private InventoryService inventoryService;

    public SupplierMenu(Scanner scanner, SupplierService supplierService, OrderService orderService, InventoryService inventoryService) {
        this.scanner = scanner;
        this.supplierService = supplierService;
        this.orderService = orderService;
        this.inventoryService = inventoryService;
    }

    /**
     * Main menu loop for supplier operations.
     */
    public void run() {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Supplier Management ===");
            System.out.println("1. View Suppliers");
            System.out.println("2. Add Supplier");
            System.out.println("3. Update Supplier");
            System.out.println("4. Delete Supplier");
            System.out.println("5. Create Purchase Order");
            System.out.println("6. Track Order Status");
            System.out.println("7. View Order History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> viewSuppliers();
                case 2 -> addSupplier();
                case 3 -> updateSupplier();
                case 4 -> deleteSupplier();
                case 5 -> createPurchaseOrder();
                case 6 -> trackOrderStatus();
                case 7 -> viewSupplierOrderHistory();
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Displays a list of all suppliers or allows searching by ID.
     */
    private void viewSuppliers() {
        boolean back = false;

        while (!back) {
            System.out.println("\n-- View Suppliers --");
            System.out.println("1. View All Suppliers");
            System.out.println("2. Search by Supplier ID");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.println("Showing all suppliers...");
                    for (Supplier supplier : supplierService.getAllSuppliers()) {
                        System.out.println(supplier);
                    }
                }
                case 2 -> {
                    System.out.print("Enter Supplier ID: ");
                    int id = ImportUtils.getUserChoice(scanner);
                    System.out.println("Searching for suppliers ID: " + id);
                    System.out.print(supplierService.findSupplierById(id) + "\n\n");
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Prompts user for supplier details and adds the supplier along with items they can supply.
     */
    private void addSupplier() {
        System.out.println("\n=== Add Supplier ===");

        System.out.print("Enter Supplier Name: ");
        String name = ImportUtils.getUserChoiceStr(scanner);
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Supplier name cannot be empty.");
            return;
        }

        System.out.print("Enter Email: ");
        String email = ImportUtils.getUserChoiceStr(scanner);
        if (email == null || email.trim().isEmpty() || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            System.out.println("Invalid email format.");
            return;
        }

        System.out.print("Enter Phone: ");
        String phone = ImportUtils.getUserChoiceStr(scanner);
        if (phone == null || phone.trim().isEmpty()) {
            System.out.println("Phone number cannot be empty.");
            return;
        }

        System.out.print("Enter Location: ");
        String location = ImportUtils.getUserChoiceStr(scanner);
        if (location == null || location.trim().isEmpty()) {
            System.out.println("Location cannot be empty.");
            return;
        }

        Supplier newSupplier = supplierService.addSupplier(name, email, phone, location);
        int supplierId = newSupplier.getId();

        // Display available inventory items
        for (InventoryItem item : inventoryService.getAllInventoryItems()) {
            System.out.println(item);
        }

        // Add items supplied by the new supplier
        while (true) {
            System.out.print("\nEnter InventoryItem ID to add (or -1 to finish): ");
            int inventoryItemId = ImportUtils.getUserChoice(scanner);

            if (inventoryItemId == -1) break;

            InventoryItem item = inventoryService.findById(inventoryItemId);
            if (item == null) {
                System.out.println("Invalid InventoryItem ID. Please try again.");
                continue;
            }

            System.out.print("Enter supplier price for '" + item.getName() + "': ");
            double supplierPrice = ImportUtils.getUserChoiceDouble(scanner);
            if (supplierPrice < 0) {
                System.out.println("Invalid supplier price. Please try again.");
                continue;
            }

            supplierService.createSupplierItem(supplierId, inventoryItemId, supplierPrice);
            System.out.println("Added item '" + item.getName() + "' to supplier.");
        }

        System.out.println("Supplier added successfully with ID: " + supplierId);
    }


    /**
     * Updates supplier information including contact, location, or supplied items.
     */
    private void updateSupplier() {
        System.out.println("\n=== Update Supplier ===");
        System.out.print("Enter Supplier ID to update: ");
        int id = ImportUtils.getUserChoice(scanner);

        Supplier supplier = supplierService.findSupplierById(id);
        if (supplier == null) {
            System.out.println("Supplier not found.");
            return;
        }

        boolean back = false;
        while (!back) {
            System.out.println("\nSelect what to update:");
            System.out.println("1. Name");
            System.out.println("2. Contact (Email & Phone)");
            System.out.println("3. Location");
            System.out.println("4. Items");
            System.out.println("5. All fields");
            System.out.println("0. Cancel");
            System.out.print("Choice: ");
            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new Name: ");
                    String newName = ImportUtils.getUserChoiceStr(scanner);
                    System.out.println(supplierService.updateSupplierName(id, newName)
                            ? "Name updated." : "Failed to update name.");
                }
                case 2 -> {
                    System.out.print("Enter new Email: ");
                    String newEmail = ImportUtils.getUserChoiceStr(scanner);
                    if (newEmail == null || newEmail.trim().isEmpty() || !newEmail.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                        System.out.println("Invalid email format.");
                        return;
                    }
                    System.out.print("Enter new Phone: ");
                    String newPhone = ImportUtils.getUserChoiceStr(scanner);
                    System.out.println(supplierService.updateSupplierContact(id, newEmail, newPhone)
                            ? "Contact updated." : "Failed to update contact.");
                }
                case 3 -> {
                    System.out.print("Enter new Location: ");
                    String newLocation = ImportUtils.getUserChoiceStr(scanner);
                    System.out.println(supplierService.updateSupplierLocation(id, newLocation)
                            ? "Location updated." : "Failed to update location.");
                }
                case 4 -> {
                    // Manage supplier's item list
                    boolean done = false;
                    while (!done) {
                        System.out.println("\n--- Update Supplier Items ---");
                        System.out.println("1. Add new item");
                        System.out.println("2. Update item price");
                        System.out.println("3. Remove item");
                        System.out.println("0. Back");
                        System.out.print("Choice: ");
                        int itemChoice = ImportUtils.getUserChoice(scanner);

                        switch (itemChoice) {
                            case 1 -> {
                                System.out.print("Enter Inventory Item ID: ");
                                int itemId = ImportUtils.getUserChoice(scanner);
                                System.out.print("Enter Supplier Price: ");
                                double price = ImportUtils.getUserChoiceDouble(scanner);
                                try {
                                    SupplierItem newItem = supplierService.createSupplierItem(id, itemId, price);
                                    System.out.println("Item added: " + newItem.getName());
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            case 2 -> {
                                System.out.print("Enter Supplier Item ID to update price: ");
                                int itemId = ImportUtils.getUserChoice(scanner);
                                System.out.print("Enter new Supplier Price: ");
                                double newPrice = ImportUtils.getUserChoiceDouble(scanner);
                                System.out.println(supplierService.updateSupplierPrice(id, itemId, newPrice)
                                        ? "Item price updated." : "Failed to update item price. Check if item exists.");
                            }
                            case 3 -> {
                                System.out.print("Enter Supplier Item ID to remove: ");
                                int itemId = ImportUtils.getUserChoice(scanner);
                                System.out.println(supplierService.removeSupplierItem(id, itemId)
                                        ? "Item removed." : "Failed to remove item. Check if item exists.");
                            }
                            case 0 -> done = true;
                            default -> System.out.println("Invalid option.");
                        }
                    }
                }
                case 5 -> {
                    System.out.print("Enter new Name: ");
                    String newName = ImportUtils.getUserChoiceStr(scanner);
                    System.out.print("Enter new Email: ");
                    String newEmail = ImportUtils.getUserChoiceStr(scanner);
                    if (newEmail == null || newEmail.trim().isEmpty() || !newEmail.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                        System.out.println("Invalid email format.");
                        return;
                    }
                    System.out.print("Enter new Phone: ");
                    String newPhone = ImportUtils.getUserChoiceStr(scanner);
                    System.out.print("Enter new Location: ");
                    String newLocation = ImportUtils.getUserChoiceStr(scanner);

                    boolean updated = supplierService.updateSupplier(id, newName, newEmail, newPhone, newLocation);

                    System.out.println(updated ? "Supplier updated." : "Nothing was updated.");
                }
                case 0 -> {
                    System.out.println("Update cancelled.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Deletes a supplier by ID.
     */
    private void deleteSupplier() {
        System.out.print("\nEnter Supplier ID to delete: ");
        int id = ImportUtils.getUserChoice(scanner);

        boolean removed = supplierService.deleteSupplier(id);
        System.out.println(removed ? "Supplier deleted successfully." : "Supplier not found.");
    }

    /**
     * Creates a new purchase order with selected items and supplier.
     */
    private void createPurchaseOrder() {
        System.out.println("\nCreate Purchase Order - [Placeholder]");
        Map<String, Integer> saleProducts = new HashMap<>();

        for (Supplier supplier : supplierService.getAllSuppliers()) {
            System.out.println(supplier);
        }

        boolean order = false;

        while (!order) {
            System.out.println("\n1. Display Suppliers");
            System.out.println("2. Add To Order");
            System.out.println("3. Complete Order");
            System.out.println("0. Cancel Order");
            System.out.println("Current Order: " + saleProducts);
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.println();
                    for (Supplier supplier : supplierService.getAllSuppliers()) {
                        System.out.println(supplier);
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
                    true
                );
                System.out.println("Order placed successfully with ID: " + orderId);
            } catch (Exception e) {
                System.out.println("Failed to place order: " + e.getMessage());
            }
        } else {
            System.out.println("Order cancelled.");
        }
    }

    /**
     * Adds items to a sale by selecting suppliers and items. 
     * The user can choose a supplier, view their available items, 
     * and then add the desired items with their quantities to the sale.
     * This method continues until the user stops adding items or finishes with all suppliers.
     * 
     * @param saleProducts A map containing the current products being added to the sale. 
     *                     The key is a concatenation of the supplier ID and item ID, and the value is the quantity.
     * @return The updated map of sale products with added quantities.
     */
    private Map<String, Integer> addItems(Map<String, Integer> saleProducts) {
        while (true) {
            // Display all available suppliers
            System.out.println("\nAvailable Suppliers:");
            for (Supplier supplier : supplierService.getAllSuppliers()) {
                System.out.println(supplier);
            }

            // Prompt the user to enter a supplier ID or -1 to stop adding
            System.out.print("Enter supplier ID (or -1 to stop adding): ");
            int supplierId = ImportUtils.getUserChoice(scanner);
            if (supplierId == -1) break;

            // Find the supplier by ID
            Supplier supplier = supplierService.findSupplierById(supplierId);
            if (supplier == null) {
                System.out.println("Invalid supplier ID.");
                continue;
            }

            // Allow the user to add items from the selected supplier
            boolean doneWithSupplier = false;
            while (!doneWithSupplier) {
                System.out.println("\nItems from " + supplier.getName() + ":");
                System.out.print(supplier);

                // Prompt the user to select an item or finish with the current supplier
                System.out.print("\nEnter item ID to add (or -1 to finish with this supplier): ");
                int itemId = ImportUtils.getUserChoice(scanner);
                if (itemId == -1) {
                    doneWithSupplier = true;
                    continue;
                }

                // Find the item by ID and handle invalid selections
                SupplierItem selectedItem = supplier.getItemById(itemId);
                if (selectedItem == null) {
                    System.out.println("Invalid item ID.");
                    continue;
                }

                // Get the quantity of the item to be added
                System.out.print("Enter quantity: ");
                int quantity = ImportUtils.getUserChoice(scanner);
                if (quantity == -1) break;

                // Update the saleProducts map with the added quantity
                String key = supplierId + ":" + itemId;
                saleProducts.put(key, saleProducts.getOrDefault(key, 0) + quantity);
                System.out.println("Added " + quantity + " of " + selectedItem.getName() + ".");
            }
        }
        return saleProducts;
    }

    /**
     * Displays all orders and allows the user to track the status of each one.
     * This method iterates over all orders and prints each one to the console.
     */
    private void trackOrderStatus() {
        System.out.println("\nTrack Order Status");
        // Display all orders
        for (Order order : orderService.getAllOrders()) {
            System.out.println(order);
        }
    }

    /**
     * Displays the order history for a specific supplier.
     * The user is prompted to enter the supplier's ID, and the method then
     * retrieves and displays the supplier's order history.
     */
    private void viewSupplierOrderHistory() {
        System.out.println("\nView Order History");
        // Prompt for the supplier's ID
        System.out.print("Enter supplier ID: ");
        int id = ImportUtils.getUserChoice(scanner);
        System.out.println("Searching for supplier ID: " + id);
        // Display the supplier's order history
        System.out.print(supplierService.getOrderHistoryForSupplier(id) + "\n\n");
    }
}
