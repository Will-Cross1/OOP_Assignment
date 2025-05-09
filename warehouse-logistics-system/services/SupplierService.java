package services;

import models.Supplier;
import models.InventoryItem;
import models.SupplierItem;
import models.SupplierOrderRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing suppliers and their associated items.
 *
 * Provides functionality to add, update, and delete suppliers, manage supplier items,
 * and create records for supplier orders. This service also coordinates with
 * InventoryService to link inventory items with supplier data.
 */
public class SupplierService {
    private final InventoryService inventoryService;
    private List<Supplier> suppliers;
    private int nextId;

    // Constructor
    public SupplierService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.suppliers = new ArrayList<>();
        this.nextId = 1; // Start IDs from 1
    }

    // Supplier Management Operations
    public Supplier addSupplier(String name, String email, String phone, String location) {
        Supplier newSupplier = new Supplier(nextId++, name, email, phone, location);
        suppliers.add(newSupplier);
        return newSupplier;
    }

    public boolean updateSupplier(int id, String newName, String newEmail, String newPhone, String newLocation) {
        Supplier supplier = findSupplierById(id);
        if (supplier != null) {
            supplier.setName(newName);
            supplier.setContact(newEmail, newPhone);
            supplier.setLocation(newLocation);
            return true;
        }
        return false;
    }

    public boolean updateSupplierName(int id, String newName) {
        Supplier supplier = findSupplierById(id);
        if (supplier != null) {
            supplier.setName(newName);
            return true;
        }
        return false;
    }
    
    public boolean updateSupplierContact(int id, String newEmail, String newPhone) {
        Supplier supplier = findSupplierById(id);
        if (supplier != null) {
            supplier.setContact(newEmail, newPhone);
            return true;
        }
        return false;
    }
    
    public boolean updateSupplierLocation(int id, String newLocation) {
        Supplier supplier = findSupplierById(id);
        if (supplier != null) {
            supplier.setLocation(newLocation);
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

    // Supplier Retrieval Operations
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

    public List<SupplierOrderRecord> getOrderHistoryForSupplier(int supplierId) {
        Supplier supplier = findSupplierById(supplierId);
        if (supplier != null) {
            return supplier.getOrderHistory();
        }
        return new ArrayList<>();
    }

    // Supplier Item Management Operations
    /**
     * Create a SupplierItem from an existing InventoryItem and attach it to the supplier
     */
    public SupplierItem createSupplierItem(int supplierId, int inventoryItemId, double supplierPrice) {
        Supplier supplier = findSupplierById(supplierId);
        InventoryItem inventoryItem = inventoryService.findById(inventoryItemId);
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier with ID " + supplierId + " not found.");
        }
    
        SupplierItem supplierItem = new SupplierItem(
            inventoryItemId,
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
            SupplierItem item = supplier.getItemById(itemId);
            if (item != null) {
                supplier.removeItemById(itemId);
                return true;
            }
        }
        return false;
    }
}