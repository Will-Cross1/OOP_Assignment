import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;
import services.*;
import ui.VisualInterface;
/**
 * Main class for the Warehouse Logistics System.
 */
public class Main
{

    /**
     * The main method to run the warehouse logistics system.
     * It sets up the services, inventory, suppliers, and processes orders.
     * It also launches the user interface for interaction.
     */
    public static void main(String[] args) {

        // --- SERVICE SETUP ---
        InventoryService inventoryService = new InventoryService();
        SupplierService supplierService = new SupplierService(inventoryService);
        OrderCreationService orderCreationService = new OrderCreationService(inventoryService, supplierService);
        OrderService orderService = new OrderService(orderCreationService);
        FinancialService financialService = new FinancialService(orderService);

        // --- INVENTORY SETUP ---
        inventoryService.addInventoryItem(1, "Laptop", "15-inch portable computer", 1000.0, 10);
        inventoryService.addInventoryItem(2, "Mouse", "Wireless optical mouse", 25.0, 50);
        inventoryService.addInventoryItem(3, "Keyboard", "Mechanical keyboard", 70.0, 30);

        // --- SUPPLIER SETUP ---
        supplierService.addSupplier("Acme Supplies", "acme@supplies.com", "02081234567", "London");
        supplierService.addSupplier("Global Distributors", "contact@global.com", "01709876543", "Birmingham");
        supplierService.addSupplier("Shell Company", "we_commit@tax_evasion.co.uk", "01234567890", "Gurnsey");

        supplierService.createSupplierItem(1, 1, 1423);
        supplierService.createSupplierItem(1, 1, 1400);
        supplierService.createSupplierItem(2, 2, 20);
        supplierService.createSupplierItem(3, 1, 1423);
        supplierService.createSupplierItem(3, 2, 23);
        supplierService.createSupplierItem(3, 3, 64);

        // System.out.println("\n=== ALL SUPPLIERS AND THEIR ITEMS ===");
        // for (Supplier s : supplierService.getAllSuppliers()) {
        //     System.out.println(s);
        //     for (SupplierItem item : s.getItems()) {
        //         System.out.println("  - " + item);
        //     }
        // }

        // --- SALE ORDER ---
        Map<String, Integer> saleProducts = new HashMap<>();
        saleProducts.put("1", 2);

        orderService.createOrder(
            saleProducts,
            FinancialTransaction.Type.SALE
        );

        // --- PURCHASE ORDER ---
        Map<String, Integer> purchaseProducts = new HashMap<>();
        purchaseProducts.put("3:3", 5); // Supplier 3, item 3
        purchaseProducts.put("3:2", 5);

        orderService.createOrder(
            purchaseProducts,
            FinancialTransaction.Type.PURCHASE
        );

        Map<String, Integer> purchaseProducts2 = new HashMap<>();
        purchaseProducts2.put("3:3", 2);
        purchaseProducts2.put("3:2", 58);

        orderService.createOrder(
            purchaseProducts2,
            FinancialTransaction.Type.PURCHASE
        );

        // inventoryService.printInventory();

        // // --- PRINT ORDERS ---
        // System.out.println("\n=== ALL ORDERS ===");
        // try {
        //     Thread.sleep(5000);
        //     for (Order order : orderService.getAllOrders()) {
        //         System.out.println(order);
        //     }

        //     Thread.sleep(10000);
        //     for (Order order : orderService.getAllOrders()) {
        //         System.out.println(order);
        //     }

        //     Thread.sleep(5000);
        //     for (Order order : orderService.getAllOrders()) {
        //         System.out.println(order);
        //     }
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // // --- INVENTORY SERVICE NEW METHODS ---
        // System.out.println("\n=== UPDATED PRICE & QUANTITY ===");
        // //inventoryService.updateItemQuantity(2, 100);
        // inventoryService.updateItemPrice(2, 30.0);
        // inventoryService.printInventory();

        // System.out.println("\n=== STOCK LEVEL BY ID (Item 2) ===");
        // System.out.println("Stock for Item 2: " + inventoryService.getStockLevelById(2));

        // System.out.println("\n=== ALL STOCK LEVELS ===");
        // Map<Integer, Integer> stockLevels = inventoryService.getAllStockLevels();
        // for (Map.Entry<Integer, Integer> entry : stockLevels.entrySet()) {
        //     System.out.println("Item ID " + entry.getKey() + ": Quantity " + entry.getValue());
        // }

        // // --- SUPPLIER SERVICE NEW METHODS ---
        // System.out.println("\n=== UPDATED SUPPLIER INFO ===");
        // supplierService.updateSupplier(2, "Global Mega Corp", "mega@corp.com", "01111222222", "Manchester");
        // System.out.println(supplierService.findSupplierById(2));

        // System.out.println("\n=== UPDATED SUPPLIER PRICE FOR ITEM ===");
        // supplierService.updateSupplierPrice(3, 3, 60.0); // supplierId=3, itemId=3
        // System.out.println("Supplier 3, item 3 price updated:");
        // for (SupplierItem item : supplierService.findSupplierById(3).getItems()) {
        //     if (item.getId() == 3) {
        //         System.out.println(item);
        //     }
        // }

        // System.out.println("\n=== REMOVE SUPPLIER ITEM (Supplier 1, Item 1) ===");
        // supplierService.removeSupplierItem(1, 1);
        // for (SupplierItem item : supplierService.findSupplierById(1).getItems()) {
        //     System.out.println(item);
        // }

        // System.out.println("\n=== ORDER HISTORY FOR SUPPLIER 3 ===");
        // List<SupplierOrderRecord> orderHistory = supplierService.getOrderHistoryForSupplier(3);
        // for (SupplierOrderRecord record : orderHistory) {
        //     System.out.println(record);
        // }
        // // Step 3: Print all-time financial report
        // System.out.println("All-Time Financial Report:");
        // financialService.printAllTimeFinancialReport();

        // Launch UI with services injected
        VisualInterface ui = new VisualInterface(supplierService, inventoryService, orderService, financialService);
        ui.run();
    }
}
