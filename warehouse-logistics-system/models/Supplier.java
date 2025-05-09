package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a supplier who provides items and tracks order history.
 *
 * This class holds supplier details such as contact information and location,
 * along with the list of items they supply and their order history.
 * It provides functionality to manage items (add, update, remove) and record orders.
 * Defensive copying is used to protect internal collections from external modification.
 */
public class Supplier {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String location;
    private List<SupplierItem> items;
    private List<SupplierOrderRecord> orderHistory;

    // Constructor
    public Supplier(int id, String name, String email, String phone, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.items = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public List<SupplierItem> getItems() {
        return new ArrayList<>(items); // Return copy to avoid external mutation
    }

    public SupplierItem getItemById(int itemId) {
        for (SupplierItem item : items) {
            if (item.getId() == itemId) {
                return item;
            }
        }
        return null;
    }

    public List<SupplierOrderRecord> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Supplier Item management
    public void addItem(SupplierItem newItem) {
        // Check if item with same ID exists, and replace it
        for (int i = 0; i < items.size(); i++) {
            SupplierItem existing = items.get(i);
            if (existing.getId() == newItem.getId()) {
                items.set(i, newItem);
                return;
            }
        }
        items.add(newItem); // Add new item if no match found
    }

    public void removeItemById(int itemId) {
        items.removeIf(item -> item.getId() == itemId);
    }

    // Order History management
    public void addOrderRecord(SupplierOrderRecord record) {
        orderHistory.add(record);
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return "[" + id + "] " + name + " - " + email + " | " + phone + " | " + location + items;
    }
}
