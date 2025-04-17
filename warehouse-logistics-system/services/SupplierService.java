package services;

import models.Supplier;
import models.InventoryItem;
import models.SupplierItem;
import models.SupplierOrderRecord;

import java.util.ArrayList;
import java.util.List;

public class SupplierService {
    private List<Supplier> suppliers;
    private int nextId;

    public SupplierService() {
        this.suppliers = new ArrayList<>();
        this.nextId = 1; // Start IDs from 1
    }

    public Supplier addSupplier(String name, String email, String phone, String location) {
        Supplier newSupplier = new Supplier(nextId++, name, email, phone, location);
        suppliers.add(newSupplier);
        return newSupplier;
    }

    public List<Supplier> getAllSuppliers() {
        return new ArrayList<>(suppliers); // Return a copy for safety
    }

    public Supplier findSupplierById(int id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId() == id) {
                return supplier;
            }
        }
        return null;
    }

    public boolean updateSupplier(int id, String newName, String newEmail, String newPhone, String newLocation) {
        Supplier supplier = findSupplierById(id);
        if (supplier != null) {
            supplier.updateName(newName);
            supplier.updateContact(newEmail, newPhone);
            supplier.updateLocation(newLocation);
            return true;
        }
        return false;
    }

    public boolean deleteSupplier(int id) {
        Supplier supplier = findSupplierById(id);
        if (supplier != null) {
            suppliers.remove(supplier);
            return true;
        }
        return false;
    }
    /**
     * Create a SupplierItem from an existing InventoryItem and attach it to the supplier
     */
    public SupplierItem createSupplierItem(int supplierId, InventoryItem inventoryItem, double supplierPrice) {
        Supplier supplier = findSupplierById(supplierId);
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier with ID " + supplierId + " not found.");
        }
    
        SupplierItem supplierItem = new SupplierItem(
            inventoryItem.getId(),
            inventoryItem.getName(),
            inventoryItem.getDescription(),
            supplierPrice
        );
    
        supplier.addItem(supplierItem);
        return supplierItem;
    }

    public boolean updateSupplierPrice(int supplierId, int itemId, double newPrice) {
        Supplier supplier = findSupplierById(supplierId);
        if (supplier != null) {
            SupplierItem item = supplier.getItemById(itemId);
            if (item != null) {
                item.setSupplierPrice(newPrice);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeSupplierItem(int supplierId, int itemId) {
        Supplier supplier = findSupplierById(supplierId);
        if (supplier != null) {
            supplier.removeItemById(itemId);
        }
        return false;
    }

    public List<SupplierOrderRecord> getOrderHistoryForSupplier(int supplierId) {
        Supplier supplier = findSupplierById(supplierId);
        if (supplier != null) {
            return supplier.getOrderHistory();
        }
        return new ArrayList<>();
    }
}