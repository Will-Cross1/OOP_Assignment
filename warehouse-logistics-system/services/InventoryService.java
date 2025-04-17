package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.InventoryItem;

public class InventoryService {
    private List<InventoryItem> inventory;

    public InventoryService() {
        inventory = new ArrayList<>();
    }

    public void addInventoryItem(int id, String name, String description, double unitPrice, int quantity) {
        InventoryItem item = new InventoryItem(id, name, description, unitPrice, quantity);
        inventory.add(item);
    }

    public List<InventoryItem> getAllInventoryItems() {
        return inventory;
    }

    public InventoryItem findById(int id) {
        for (InventoryItem item : inventory) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

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

    public void printInventory() {
        for (InventoryItem item : inventory) {
            System.out.println(item);
        }
    }
}
