package services;

import models.InventoryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {
    private InventoryService inventory;

    @BeforeEach
    public void setUp() {
        inventory = new InventoryService();
        inventory.addInventoryItem(1, "Screwdriver", "Flathead screwdriver", 3.99, 10); // low stock
        inventory.addInventoryItem(2, "Hammer", "Steel hammer", 8.49, 25);             // sufficient stock
    }
    
    @Test
    public void testAddInventoryItem() {
        inventory.addInventoryItem(3, "Wrench", "Adjustable wrench", 6.99, 15);
        InventoryItem added = inventory.findById(3);
        assertNotNull(added);
        assertEquals("Wrench", added.getName());
    }
    
    @Test
    public void testRemoveItemById() {
        inventory.removeItemById(1);
        assertNull(inventory.findById(1));
    }
    
    @Test
    public void testUpdateItemQuantity() {
        assertTrue(inventory.updateItemQuantity(2, 30));
        assertEquals(30, (int) inventory.getStockLevelById(2));
    }
    
    @Test
    public void testUpdateItemPrice() {
        assertTrue(inventory.updateItemPrice(1, 4.99));
        assertEquals(4.99, inventory.findById(1).getUnitPrice());
    }
    
    @Test
    public void testGetLowStockItems() {
        Map<InventoryItem, Integer> lowStock = inventory.getLowStockItems();
        assertEquals(1, lowStock.size());
        InventoryItem lowItem = lowStock.keySet().iterator().next();
        assertEquals("Screwdriver", lowItem.getName());
        assertEquals(10, (int) lowStock.get(lowItem));
    }
    
    @Test
    public void testGetAllStockLevels() {
        Map<Integer, Integer> stockLevels = inventory.getAllStockLevels();
        assertEquals(2, stockLevels.size());
        assertEquals((Integer)10, stockLevels.get(1));
        assertEquals((Integer)25, stockLevels.get(2));
    }
    
    @Test
    public void testGetNextAvailableId() {
        assertEquals(3, inventory.getNextAvailableId());
        inventory.addInventoryItem(5, "Chisel", "Wood chisel", 2.99, 5);
        assertEquals(6, inventory.getNextAvailableId());
    }
    
    @Test
    public void testGetAllInventoryItems() {
        List<InventoryItem> allItems = inventory.getAllInventoryItems();
        assertEquals(2, allItems.size());
        assertEquals("Screwdriver", allItems.get(0).getName());
        assertEquals("Hammer", allItems.get(1).getName());
    }
    
    @Test
    public void testFindById() {
        InventoryItem item = inventory.findById(2);
        assertNotNull(item);
        assertEquals("Hammer", item.getName());
    }
    
    @Test
    public void testGetStockLevelById() {
        Integer stockLevel = inventory.getStockLevelById(1);
        assertNotNull(stockLevel);
        assertEquals((Integer)10, stockLevel);
    }
    
    @Test
    public void testFindByIdWithInvalidId() {
        InventoryItem item = inventory.findById(999); // ID 999 does not exist
        assertNull(item);
    }
    
    @Test
        public void testUpdateItemQuantityWithInvalidId() {
        boolean result = inventory.updateItemQuantity(999, 50); // ID 999 does not exist
        assertFalse(result);
    }
    
    @Test
    public void testUpdateItemPriceWithInvalidId() {
        boolean result = inventory.updateItemPrice(999, 12.99); // ID 999 does not exist
        assertFalse(result);
    }
}
