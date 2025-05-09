package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.InventoryItem;

/**
 * InventoryService manages the inventory of items in a warehouse.
 * It provides methods to add, remove, update, and retrieve inventory items.
 * The service also includes functionality to check stock levels and generate reports.
 * 
 * This class is responsible for maintaining the inventory state and providing
 * operations related to inventory management.
 * It uses a list to store inventory items and provides methods to manipulate
 * and retrieve these items based on various criteria.
 */
public class InventoryService {
    private List<InventoryItem> inventory;

    // Constructor
    public InventoryService() {
        inventory = new ArrayList<>();
    }

    // Inventory Management Operations
    public void addInventoryItem(int id, String name, String description, double unitPrice, int quantity) {
        InventoryItem item = new InventoryItem(id, name, description, unitPrice, quantity);
        inventory.add(item);
    }

    /**
     * Removes an inventory item by its ID.
     * 
     * @param id The ID of the item to remove.
     */
    public void removeItemById(int id) {
        inventory.removeIf(item -> item.getId() == id);
    }

    /**
     * Update the quantity of an inventory item by its ID.
     */
    public boolean updateItemQuantity(int id, int newQuantity) {
        InventoryItem item = findById(id);
        if (item != null) {
            item.setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    /**
     * Update the price of an inventory item by its ID.
     */
    public boolean updateItemPrice(int id, double newPrice) {
        InventoryItem item = findById(id);
        if (item != null) {
            item.setUnitPrice(newPrice);
            return true;
        }
        return false;
    }

    // Inventory Retrieval Operations
    public List<InventoryItem> getAllInventoryItems() {
        return new ArrayList<>(inventory);
    }

    /**
     * Finds and returns an inventory item by its ID.
     * 
     * @param id The ID to search for.
     * @return The matching InventoryItem, or null if not found.
     */
    public InventoryItem findById(int id) {
        for (InventoryItem item : inventory) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    /**
     * Returns a map of items with stock levels below 20.
     */
    public Map<InventoryItem, Integer> getLowStockItems() {
        Map<InventoryItem, Integer> lowStock = new HashMap<>();
        for (InventoryItem item : inventory) {
            if (item.getQuantity() < 20) {
                lowStock.put(item, item.getQuantity());
            }
        }
        return lowStock;
    }

    /**
     * Get the next available ID for InventoryItem.
     */
    public int getNextAvailableId() {
        int maxId = 0;
        for (InventoryItem item : inventory) {
            if (item.getId() > maxId) {
                maxId = item.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Get the stock level for a specific item by ID.
     */
    public Integer getStockLevelById(int id) {
        InventoryItem item = findById(id);
        return item != null ? item.getQuantity() : null;
    }

    /**
     * Get stock levels for all items as a map of ID -> quantity.
     */
    public Map<Integer, Integer> getAllStockLevels() {
        Map<Integer, Integer> stockLevels = new HashMap<>();
        for (InventoryItem item : inventory) {
            stockLevels.put(item.getId(), item.getQuantity());
        }
        return stockLevels;
    }
}
