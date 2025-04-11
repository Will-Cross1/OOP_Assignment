package models;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String location;
    private List<SupplierItem> items;  // Renamed from 'products' to better reflect usage
    // private List<Order> orderHistory; // You can uncomment this when needed

    public Supplier(int id, String name, String email, String phone, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.items = new ArrayList<>();
        // this.orderHistory = new ArrayList<>();
    }

    // --- Update Methods ---
    public void updateContact(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public void updateLocation(String location) {
        this.location = location;
    }

    public void updateName(String name) {
        this.name = name;
    }

    // --- Supplier Items ---
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

    public List<SupplierItem> getItems() {
        return new ArrayList<>(items); // Return copy to avoid external mutation
    }

    // --- Getters ---
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

    //public void addOrder(Order order) {
    //    orderHistory.add(order);
    //}

    //public List<Order> getOrderHistory() {
    //    return orderHistory;
    //}

    // --- Output ---
    @Override
    public String toString() {
        return "[" + id + "] " + name + " - " + email + " | " + phone + " | " + location + items;
    }
}
