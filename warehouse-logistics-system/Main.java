import models.*;
import services.*;
import ui.VisualInterface;
/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        SupplierService supplierService = new SupplierService();

        // Create base items with the same name and description and the same ID
        InventoryItem invItem1 = new InventoryItem(1, "Laptop", "15-inch portable computer", 1000.0, 10);
        InventoryItem invItem2 = new InventoryItem(2, "Mouse", "Wireless optical mouse", 25.0, 50);
        InventoryItem invItem3 = new InventoryItem(3, "Keyboard", "Mechanical keyboard", 70.0, 30);

        // Add InventoryItems to the inventory list
        inventoryService.addInventoryItem(invItem1);
        inventoryService.addInventoryItem(invItem2);
        inventoryService.addInventoryItem(invItem3);

        // Create suppliers and assign supplier items
        supplierService.addSupplier("Acme Supplies", "acme@supplies.com", "02081234567", "London");
        supplierService.addSupplier("Global Distributors", "contact@global.com", "01709876543", "Manchester");
        supplierService.addSupplier("Shell Company", "we_commit@tax_evasion.co.uk", "01234567890", "Gurnsey");

        supplierService.createSupplierItem(1, invItem1, 1423);
        supplierService.createSupplierItem(1, invItem1, 1400);
        supplierService.createSupplierItem(2, invItem2, 20);
        supplierService.createSupplierItem(3, invItem1, 1423);
        supplierService.createSupplierItem(3, invItem2, 23);
        supplierService.createSupplierItem(3, invItem3, 64);

        // Launch UI with services injected
        VisualInterface ui = new VisualInterface(supplierService, inventoryService);
        ui.run();
    }
}
