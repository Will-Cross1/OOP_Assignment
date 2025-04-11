package services;

import java.util.ArrayList;
import java.util.List;
import models.InventoryItem;

public class InventoryService {
    private List<InventoryItem> inventory;

    public InventoryService() {
        inventory = new ArrayList<>();
    }

    public void addInventoryItem(InventoryItem item) {
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

    public void printInventory() {
        for (InventoryItem item : inventory) {
            System.out.println(item);
        }
    }
}
