import java.util.HashMap;
import java.util.Map;

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

        // Example of creating a supplier order and purchase order
        // --- SALE ORDER ---
        Map<String, Integer> saleProducts = new HashMap<>();
        saleProducts.put("1", 2);

        orderService.createOrder(
            saleProducts,
            false
        );

        // --- PURCHASE ORDER ---
        Map<String, Integer> purchaseProducts = new HashMap<>();
        purchaseProducts.put("3:3", 5); // Supplier 3, item 3
        purchaseProducts.put("3:2", 5);

        orderService.createOrder(
            purchaseProducts,
            true
        );

        Map<String, Integer> purchaseProducts2 = new HashMap<>();
        purchaseProducts2.put("3:3", 2);
        purchaseProducts2.put("3:2", 58);

        orderService.createOrder(
            purchaseProducts2,
            true
        );

        // Launch UI with services injected
        VisualInterface ui = new VisualInterface(supplierService, inventoryService, orderService, financialService);
        ui.run();
    }
}
