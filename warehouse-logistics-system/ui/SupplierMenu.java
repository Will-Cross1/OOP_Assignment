package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import models.FinancialTransaction;
import models.InventoryItem;
import models.Order;
import models.Supplier;
import models.SupplierItem;
import services.SupplierService;
import services.OrderService;
import services.InventoryService;

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

    private void addSupplier() {
        System.out.println("\n=== Add Supplier ===");

        System.out.print("Enter Supplier Name: ");
        String name = ImportUtils.getUserChoiceStr(scanner);

        System.out.print("Enter Email: ");
        String email = ImportUtils.getUserChoiceStr(scanner);

        System.out.print("Enter Phone: ");
        String phone = ImportUtils.getUserChoiceStr(scanner);

        System.out.print("Enter Location: ");
        String location = ImportUtils.getUserChoiceStr(scanner);

        //Supplier is added, then supplier items are added
        Supplier newSupplier = supplierService.addSupplier(name, email, phone, location);
        int supplierId = newSupplier.getId();

        // Display all available inventory items
        for (InventoryItem item : inventoryService.getAllInventoryItems()) {
            System.out.println(item);
        }

        // Allow user to add multiple supplier items
        while (true) {
            System.out.print("\nEnter InventoryItem ID to add (or -1 to finish): ");
            int inventoryItemId = ImportUtils.getUserChoice(scanner);

            if (inventoryItemId == -1) {
                break;
            }
            InventoryItem item = inventoryService.findById(inventoryItemId);
            if (item == null) {
                System.out.println("Invalid InventoryItem ID. Please try again.");
                continue;
            }

            System.out.print("Enter supplier price for '" + item.getName() + "': ");
            double supplierPrice = ImportUtils.getUserChoiceDouble(scanner);

            supplierService.createSupplierItem(supplierId, inventoryItemId, supplierPrice);
            System.out.println("Added item '" + item.getName() + "' to supplier.");
        }

        System.out.println("Supplier added successfully with ID: " + supplierId);
    }

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
                    if (supplierService.updateSupplierName(id, newName)) {
                        System.out.println("Name updated.");
                    } else {
                        System.out.println("Failed to update name.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter new Email: ");
                    String newEmail = ImportUtils.getUserChoiceStr(scanner);
                    System.out.print("Enter new Phone: ");
                    String newPhone = ImportUtils.getUserChoiceStr(scanner);
                    if (supplierService.updateSupplierContact(id, newEmail, newPhone)) {
                        System.out.println("Contact updated.");
                    } else {
                        System.out.println("Failed to update contact.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter new Location: ");
                    String newLocation = ImportUtils.getUserChoiceStr(scanner);
                    if (supplierService.updateSupplierLocation(id, newLocation)) {
                        System.out.println("Location updated.");
                    } else {
                        System.out.println("Failed to update location.");
                    }
                }
                case 4 -> {
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
                                if (supplierService.updateSupplierPrice(id, itemId, newPrice)) {
                                    System.out.println("Item price updated.");
                                } else {
                                    System.out.println("Failed to update item price. Check if item exists.");
                                }
                            }
                            case 3 -> {
                                System.out.print("Enter Supplier Item ID to remove: ");
                                int itemId = ImportUtils.getUserChoice(scanner);
                                if (supplierService.removeSupplierItem(id, itemId)) {
                                    System.out.println("Item removed.");
                                } else {
                                    System.out.println("Failed to remove item. Check if item exists.");
                                }
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
                    System.out.print("Enter new Phone: ");
                    String newPhone = ImportUtils.getUserChoiceStr(scanner);
                    System.out.print("Enter new Location: ");
                    String newLocation = ImportUtils.getUserChoiceStr(scanner);
    
                    boolean updated = supplierService.updateSupplier(id, newName, newEmail, newPhone, newLocation);
    
                    if (updated) {
                        System.out.println("Supplier updated.");
                    } else {
                        System.out.println("Nothing was updated.");
                    }
                }
                case 0 -> {
                    System.out.println("Update cancelled.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void deleteSupplier() {
        System.out.print("\nEnter Supplier ID to delete: ");
        int id = ImportUtils.getUserChoice(scanner);

        boolean removed = supplierService.deleteSupplier(id);
        if (removed) {
            System.out.println("Supplier deleted successfully.");
        } else {
            System.out.println("Supplier not found.");
        }
    }

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
                    System.out.println("\n");
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
                    FinancialTransaction.Type.PURCHASE
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
        while (true) {
            System.out.println("\nAvailable Suppliers:");
            for (Supplier supplier : supplierService.getAllSuppliers()) {
                System.out.println(supplier);
            }

            System.out.print("Enter supplier ID (or -1 to stop adding): ");
            int supplierId = ImportUtils.getUserChoice(scanner);
            if (supplierId == -1) break;

            Supplier supplier = supplierService.findSupplierById(supplierId);
            if (supplier == null) {
                System.out.println("Invalid supplier ID.");
                continue;
            }

            boolean doneWithSupplier = false;
            while (!doneWithSupplier) {
                System.out.println("\nItems from " + supplier.getName() + ":");
                System.out.print(supplier);

                System.out.print("\nEnter item ID to add (or -1 to finish with this supplier): ");
                int itemId = ImportUtils.getUserChoice(scanner);
                if (itemId == -1) {
                    doneWithSupplier = true;
                    continue;
                }

                SupplierItem selectedItem = supplier.getItemById(itemId);
                if (selectedItem == null) {
                    System.out.println("Invalid item ID.");
                    continue;
                }

                System.out.print("Enter quantity: ");
                int quantity = ImportUtils.getUserChoice(scanner);

                String key = supplierId + ":" + itemId;
                saleProducts.put(key, saleProducts.getOrDefault(key, 0) + quantity);
                System.out.println("Added " + quantity + " of " + selectedItem.getName() + ".");
            }
        }
        return saleProducts;
    }

    private void trackOrderStatus() {
        System.out.println("\nTrack Order Status");
        for (Order order : orderService.getAllOrders()) {
            System.out.println(order);
        }
    }

    private void viewSupplierOrderHistory() {
        System.out.println("\nView Order History");
        System.out.print("Enter supplier ID: ");
        int id = ImportUtils.getUserChoice(scanner);
        System.out.println("Searching for suppliers ID: " + id);
        System.out.print(supplierService.getOrderHistoryForSupplier(id) + "\n\n");
    }
}
