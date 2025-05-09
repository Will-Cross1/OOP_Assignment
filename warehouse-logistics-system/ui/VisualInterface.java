package ui;

import java.util.Map;
import java.util.Scanner;

import models.InventoryItem;
import services.*;

/**
 * VisualInterface class provides a simple text-based user interface
 * for interacting with the warehouse management system. 
 * It allows navigation through suppliers, inventory, orders, and financial reports.
 */
public class VisualInterface {
    private Scanner scanner;
    private SupplierService supplierService;
    private InventoryService inventoryService;
    private OrderService orderService;
    private FinancialService financialService;

    /**
     * Constructor to initialise the VisualInterface with required services.
     * 
     * @param supplierService handles supplier-related operations
     * @param inventoryService manages inventory items
     * @param orderService processes orders
     * @param financialService generates financial reports
     */
    public VisualInterface(SupplierService supplierService, InventoryService inventoryService, OrderService orderService, FinancialService financialService) {
        this.scanner = new Scanner(System.in);
        this.supplierService = supplierService;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
        this.financialService = financialService;
    }

    /**
     * Launches the main UI loop, displaying the menu and routing to the appropriate submenus.
     * Continues running until the user chooses to exit.
     */
    public void run() {
        boolean running = true;

        while (running) {
            // Display warning if there are any low stock items
            Map<InventoryItem, Integer> lowStockItems = inventoryService.getLowStockItems();
            if (!lowStockItems.isEmpty()) {
                System.out.println("\n*** WARNING: Low Stock Items ***");
                for (Map.Entry<InventoryItem, Integer> entry : lowStockItems.entrySet()) {
                    InventoryItem item = entry.getKey();
                    System.out.printf("- %s (ID: %d): %d units in stock%n", item.getName(), item.getId(), entry.getValue());
                }
                System.out.println("*********************************");
            }
            
            // Display main menu options
            System.out.println("\n=== BNU Warehouse Management System ===");
            System.out.println("1. Supplier Management");
            System.out.println("2. Inventory Management");
            System.out.println("3. Make Customer Order");
            System.out.println("4. Financial Reporting");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
            int choice = ImportUtils.getUserChoice(scanner);

            // Route to appropriate submenu or action based on user input
            switch (choice) {
                case 1 -> new SupplierMenu(scanner, supplierService, orderService, inventoryService).run(); // Manage suppliers
                case 2 -> new InventoryMenu(scanner, inventoryService).run(); // Manage inventory
                case 3 -> new CustomerOrderMenu(scanner, inventoryService, orderService).run(); // Create customer orders
                case 4 -> new FinanceMenu(scanner, orderService, financialService).run(); // View financial reports
                case 0 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        // Cleanup
        scanner.close();
        System.exit(0);
    }
}
