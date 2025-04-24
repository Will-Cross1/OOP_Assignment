package services;

import models.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderCreationService {
    private final InventoryService inventoryService;
    private final SupplierService supplierService;
    private int nextOrderId = 1;

    // Constructor
    public OrderCreationService(
        InventoryService inventoryService,
        SupplierService supplierService
    ) {
        this.inventoryService = inventoryService;
        this.supplierService = supplierService;
    }

    public Order createOrder(
        Map<String, Integer> products,
        Order.Status status,
        FinancialTransaction.Type type
    ) {
        if (!isOrderValid(products, type)) {
            throw new IllegalArgumentException("Order validation failed: invalid items or insufficient stock.");
        }
        FinancialTransaction transaction = new FinancialTransaction(type, LocalDate.now());
        Order order = new Order(nextOrderId++, products, status, transaction);

        double total = calculateTransactionTotal(order);
        applyStockChanges(order);

        transaction.setTotal(total);

        if (type == FinancialTransaction.Type.PURCHASE) {
            updateSupplierOrderHistories(order);
        }

        return order;
    }

    // Order Validation
    private boolean isOrderValid(Map<String, Integer> products, FinancialTransaction.Type type) {
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String key = entry.getKey();
            int quantity = entry.getValue();

            if (type == FinancialTransaction.Type.SALE) {
                int itemId;
                try {
                    itemId = Integer.parseInt(key);
                } catch (NumberFormatException e) {
                    return false;
                }

                InventoryItem item = inventoryService.findById(itemId);
                if (item == null || item.getQuantity() < quantity) {
                    return false;
                }

            } else if (type == FinancialTransaction.Type.PURCHASE) {
                String[] parts = key.split(":");
                if (parts.length != 2) return false;

                try {
                    int supplierId = Integer.parseInt(parts[0]);
                    int supplierItemId = Integer.parseInt(parts[1]);

                    Supplier supplier = supplierService.findSupplierById(supplierId);
                    if (supplier == null || supplier.getItemById(supplierItemId) == null) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }

    // Calculate transaction total
    private double calculateTransactionTotal(Order order) {
        double total = 0.0;

        for (Map.Entry<String, Integer> entry : order.getProducts().entrySet()) {
            String key = entry.getKey();
            int quantity = entry.getValue();

            if (order.getTransaction().getType() == FinancialTransaction.Type.SALE) {
                int itemId = Integer.parseInt(key);
                InventoryItem item = inventoryService.findById(itemId);
                if (item != null) {
                    total += item.getUnitPrice() * quantity;
                }
            } else if (order.getTransaction().getType() == FinancialTransaction.Type.PURCHASE) {
                total += getPurchaseEntryTotal(key, quantity);
            }
        }

        return total;
    }

    // Apply stock changes after order creation
    private void applyStockChanges(Order order) {
        for (Map.Entry<String, Integer> entry : order.getProducts().entrySet()) {
            String key = entry.getKey();
            int quantity = entry.getValue();

            if (order.getTransaction().getType() == FinancialTransaction.Type.SALE) {
                int itemId = Integer.parseInt(key);
                InventoryItem item = inventoryService.findById(itemId);
                if (item != null) {
                    item.setQuantity(item.getQuantity() - quantity);
                }
            }
        }
    }

    // Used to calculate total
    private double getPurchaseEntryTotal(String key, int quantity) {
        SupplierItem supplierItem = getSupplierItemFromKey(key);
        if (supplierItem == null) return 0.0;

        return supplierItem.getSupplierPrice() * quantity;
    }

    // Used when updating inventory on purchase
    private void updatePurchaseStock(String key, int quantity) {
        SupplierItem supplierItem = getSupplierItemFromKey(key);
        if (supplierItem == null) return;

        InventoryItem stockItem = inventoryService.findById(supplierItem.getId());
        if (stockItem != null) {
            stockItem.setQuantity(stockItem.getQuantity() + quantity);
        }
    }

    // Common method for getting the supplier item from key while checking the key
    private SupplierItem getSupplierItemFromKey(String key) {
        String[] parts = key.split(":");
        if (parts.length != 2) return null;

        try {
            int supplierId = Integer.parseInt(parts[0]);
            int supplierItemId = Integer.parseInt(parts[1]);

            Supplier supplier = supplierService.findSupplierById(supplierId);
            if (supplier == null) return null;

            SupplierItem supplierItem = supplier.getItemById(supplierItemId);
            if (supplierItem == null) return null;

            return supplierItem;
        } catch (NumberFormatException e) {
            return null; // If the key is not valid, return null
        }
    }

    public Runnable getStockUpdateTask(Order order) {
        return () -> {
            if (order.getTransaction().getType() == FinancialTransaction.Type.PURCHASE &&
                order.getStatus() == Order.Status.DELIVERED) {
                Map<String, Integer> entrys = order.getProducts();
                for (Map.Entry<String, Integer> entry : entrys.entrySet()) {
                    //System.out.println(entry);
                    updatePurchaseStock(entry.getKey(), entry.getValue());
                }
            }
        };
    }

    // Order History Update
    private void updateSupplierOrderHistories(Order order) {
        Map<Integer, Map<Integer, Integer>> supplierItemGroups = new HashMap<>();

        for (Map.Entry<String, Integer> entry : order.getProducts().entrySet()) {
            String key = entry.getKey();
            int quantity = entry.getValue();
            String[] parts = key.split(":");
            int supplierId = Integer.parseInt(parts[0]);
            int supplierItemId = Integer.parseInt(parts[1]);

            supplierItemGroups.putIfAbsent(supplierId, new HashMap<>());
            supplierItemGroups.get(supplierId).put(supplierItemId, quantity);
        }

        for (Map.Entry<Integer, Map<Integer, Integer>> entry : supplierItemGroups.entrySet()) {
            int supplierId = entry.getKey();
            Supplier supplier = supplierService.findSupplierById(supplierId);
            if (supplier != null) {
                Map<Integer, Integer> items = entry.getValue();
                double total = 0;
                for (var item : items.entrySet()) {
                    int quantity = item.getValue();
                    double price = supplier.getItemById(item.getKey()).getSupplierPrice();
                    total += quantity * price;
                }
                supplier.addOrderRecord(new SupplierOrderRecord(
                    order.getId(),
                    order.getTransaction().getTransactionDate(),
                    total,
                    items
                ));
            }
        }
    }
}
