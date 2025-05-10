package services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import services.*;
import models.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class SupplierServiceTest {

    private SupplierService supplierService;
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        // Create a fresh instance of InventoryService and SupplierService before each test
        inventoryService = new InventoryService();
        supplierService = new SupplierService(inventoryService);
        
        // Adding some inventory items for testing
        inventoryService.addInventoryItem(1, "Item A", "Description of Item A", 10.0, 100);
        inventoryService.addInventoryItem(2, "Item B", "Description of Item B", 20.0, 50);
    }

    @Test
    public void testAddSupplier() {
        Supplier supplier = supplierService.addSupplier("Supplier 1", "supplier1@example.com", "123456789", "Location 1");

        assertNotNull(supplier, "Supplier should be created.");
        assertEquals(1, supplier.getId(), "Supplier ID should be 1.");
        assertEquals("Supplier 1", supplier.getName(), "Supplier name should be 'Supplier 1'.");
    }

    @Test
    public void testUpdateSupplier() {
        Supplier supplier = supplierService.addSupplier("Supplier 2", "supplier2@example.com", "987654321", "Location 2");

        boolean updated = supplierService.updateSupplier(supplier.getId(), "Updated Supplier", "updated@example.com", "111222333", "New Location");

        assertTrue(updated, "Supplier should be updated.");
        assertEquals("Updated Supplier", supplier.getName(), "Supplier name should be updated.");
        assertEquals("updated@example.com", supplier.getEmail(), "Supplier email should be updated.");
        assertEquals("New Location", supplier.getLocation(), "Supplier location should be updated.");
    }

    @Test
    public void testDeleteSupplier() {
        Supplier supplier = supplierService.addSupplier("Supplier 3", "supplier3@example.com", "123123123", "Location 3");

        boolean deleted = supplierService.deleteSupplier(supplier.getId());

        assertTrue(deleted, "Supplier should be deleted.");
        assertNull(supplierService.findSupplierById(supplier.getId()), "Supplier should not be found after deletion.");
    }

    @Test
    public void testCreateSupplierItem() {
        Supplier supplier = supplierService.addSupplier("Supplier 4", "supplier4@example.com", "555555555", "Location 4");

        // Create a SupplierItem with supplier price
        SupplierItem supplierItem = supplierService.createSupplierItem(supplier.getId(), 1, 12.5);

        assertNotNull(supplierItem, "Supplier item should be created.");
        assertEquals(1, supplierItem.getId(), "Item ID should match.");
        assertEquals("Item A", supplierItem.getName(), "Item name should match.");
        assertEquals("Description of Item A", supplierItem.getDescription(), "Item description should match.");
        assertEquals(12.5, supplierItem.getSupplierPrice(), "Supplier price should be 12.5.");
        assertTrue(supplier.getItems().contains(supplierItem), "Supplier should contain the item.");
    }

    @Test
    public void testUpdateSupplierPrice() {
        Supplier supplier = supplierService.addSupplier("Supplier 5", "supplier5@example.com", "666666666", "Location 5");
        SupplierItem supplierItem = supplierService.createSupplierItem(supplier.getId(), 1, 10.0);

        boolean updated = supplierService.updateSupplierPrice(supplier.getId(), supplierItem.getId(), 15.0);

        assertTrue(updated, "Supplier item price should be updated.");
        assertEquals(15.0, supplierItem.getSupplierPrice(), "Updated price should be 15.0.");
    }

    @Test
    public void testRemoveSupplierItem() {
        Supplier supplier = supplierService.addSupplier("Supplier 6", "supplier6@example.com", "777777777", "Location 6");
        SupplierItem supplierItem = supplierService.createSupplierItem(supplier.getId(), 2, 25.0);

        boolean removed = supplierService.removeSupplierItem(supplier.getId(), supplierItem.getId());

        assertTrue(removed, "Supplier item should be removed.");
        assertNull(supplier.getItemById(supplierItem.getId()), "Item should be removed from supplier.");
    }

    @Test
    public void testGetAllSuppliers() {
        supplierService.addSupplier("Supplier 7", "supplier7@example.com", "888888888", "Location 7");
        supplierService.addSupplier("Supplier 8", "supplier8@example.com", "999999999", "Location 8");

        List<Supplier> suppliers = supplierService.getAllSuppliers();

        assertEquals(2, suppliers.size(), "There should be 2 suppliers.");
    }
    
    @Test
    public void testGetOrderHistoryForSupplier() {
        Supplier supplier = supplierService.addSupplier("Supplier 9", "supplier9@example.com", "000000000", "Location 9");
    
        // Create example item maps for the orders
        Map<Integer, Integer> items1 = new HashMap<>();
        items1.put(1, 5); // Item ID 1, quantity 5
    
        Map<Integer, Integer> items2 = new HashMap<>();
        items2.put(2, 10); // Item ID 2, quantity 10
    
        // Correct constructor usage
        SupplierOrderRecord order1 = new SupplierOrderRecord(1, LocalDate.of(2023, 1, 1), 100.0, items1);
        SupplierOrderRecord order2 = new SupplierOrderRecord(2, LocalDate.of(2023, 1, 2), 200.0, items2);
    
        supplier.addOrderRecord(order1);
        supplier.addOrderRecord(order2);
    
        List<SupplierOrderRecord> orderHistory = supplierService.getOrderHistoryForSupplier(supplier.getId());
    
        assertNotNull(orderHistory, "Order history should be returned.");
        assertEquals(2, orderHistory.size(), "There should be 2 orders in the history.");
        assertTrue(orderHistory.contains(order1), "Order 1 should be in the order history.");
        assertTrue(orderHistory.contains(order2), "Order 2 should be in the order history.");
    }


    @Test
    public void testFindSupplierById() {
        Supplier supplier = supplierService.addSupplier("Supplier 10", "supplier10@example.com", "111111111", "Location 10");

        Supplier foundSupplier = supplierService.findSupplierById(supplier.getId());

        assertNotNull(foundSupplier, "Supplier should be found by ID.");
        assertEquals(supplier.getId(), foundSupplier.getId(), "Supplier ID should match.");
    }

    @Test
    public void testUpdateSupplierLocation() {
        Supplier supplier = supplierService.addSupplier("Supplier 11", "supplier11@example.com", "222222222", "Location 11");

        boolean updated = supplierService.updateSupplierLocation(supplier.getId(), "New Location");

        assertTrue(updated, "Supplier location should be updated.");
        assertEquals("New Location", supplier.getLocation(), "Supplier location should be 'New Location'.");
    }

    @Test
    public void testUpdateSupplierContact() {
        Supplier supplier = supplierService.addSupplier("Supplier 12", "supplier12@example.com", "333333333", "Location 12");

        boolean updated = supplierService.updateSupplierContact(supplier.getId(), "newemail@example.com", "444444444");

        assertTrue(updated, "Supplier contact should be updated.");
        assertEquals("newemail@example.com", supplier.getEmail(), "Supplier email should be 'newemail@example.com'.");
        assertEquals("444444444", supplier.getPhone(), "Supplier phone should be '444444444'.");
    }

    @Test
    public void testUpdateSupplierName() {
        Supplier supplier = supplierService.addSupplier("Supplier 13", "supplier13@example.com", "555555555", "Location 13");

        boolean updated = supplierService.updateSupplierName(supplier.getId(), "Updated Supplier Name");

        assertTrue(updated, "Supplier name should be updated.");
        assertEquals("Updated Supplier Name", supplier.getName(), "Supplier name should be 'Updated Supplier Name'.");
    }
    
    @Test
    public void testUpdateNonExistentSupplierFails() {
        boolean updated = supplierService.updateSupplier(999, "Name", "email@example.com", "000000000", "Location");
        assertFalse(updated, "Updating a non-existent supplier should fail.");
    }
    
    @Test
    public void testDeleteNonExistentSupplierFails() {
        boolean deleted = supplierService.deleteSupplier(999);
        assertFalse(deleted, "Deleting a non-existent supplier should fail.");
    }
    
    @Test
    public void testFindNonExistentSupplierReturnsNull() {
        Supplier supplier = supplierService.findSupplierById(999);
        assertNull(supplier, "Finding a non-existent supplier should return null.");
    }
    
    @Test
    public void testUpdateSupplierNameFailsForInvalidId() {
        boolean updated = supplierService.updateSupplierName(999, "Doesn't Exist");
        assertFalse(updated, "Updating name for non-existent supplier should fail.");
    }
    
    @Test
    public void testUpdateSupplierLocationFailsForInvalidId() {
        boolean updated = supplierService.updateSupplierLocation(999, "Nowhere");
        assertFalse(updated, "Updating location for non-existent supplier should fail.");
    }
    
    @Test
    public void testUpdateSupplierContactFailsForInvalidId() {
        boolean updated = supplierService.updateSupplierContact(999, "fail@example.com", "123123123");
        assertFalse(updated, "Updating contact for non-existent supplier should fail.");
    }
    
    @Test
    public void testUpdateSupplierItemPriceFailsWithInvalidSupplierId() {
        boolean updated = supplierService.updateSupplierPrice(999, 1, 20.0);
        assertFalse(updated, "Updating price with invalid supplier ID should fail.");
    }
    
    @Test
    public void testUpdateSupplierItemPriceFailsWithInvalidItemId() {
        Supplier supplier = supplierService.addSupplier("Test", "test@example.com", "123", "TestLoc");
        boolean updated = supplierService.updateSupplierPrice(supplier.getId(), 999, 20.0);
        assertFalse(updated, "Updating price with invalid item ID should fail.");
    }
    
    @Test
    public void testRemoveSupplierItemFailsWithInvalidSupplierId() {
        boolean removed = supplierService.removeSupplierItem(999, 1);
        assertFalse(removed, "Removing supplier item with invalid supplier ID should fail.");
    }
    
    @Test
    public void testRemoveSupplierItemFailsWithInvalidItemId() {
        Supplier supplier = supplierService.addSupplier("Test", "test@example.com", "123", "TestLoc");
        boolean removed = supplierService.removeSupplierItem(supplier.getId(), 999);
        assertFalse(removed, "Removing supplier item with invalid item ID should fail.");
    }
    
    @Test
    public void testGetOrderHistoryFailsForInvalidSupplier() {
        List<SupplierOrderRecord> history = supplierService.getOrderHistoryForSupplier(999);
        assertTrue(history.isEmpty(), "Order history for invalid supplier should be empty.");
    }

}



