package services;

import models.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service responsible for creating orders, validating them, 
 * updating inventory, and managing supplier histories.
 */
public class OrderCreationService {
    private final InventoryService inventoryService;
    private final SupplierService supplierService;
    private final AtomicInteger nextOrderId = new AtomicInteger(1);

    /**
     * Constructs an OrderCreationService with dependencies on inventory and supplier services.
     */
    public OrderCreationService(
        InventoryService inventoryService,
        SupplierService supplierService
    ) {
        this.inventoryService = inventoryService;
        this.supplierService = supplierService;
    }

    /**
     * Creates a new order based on the product list, status, and financial transaction.
     * Validates the order, updates inventory, and tracks supplier orders if applicable.
     *
     * @param products Map of product IDs to quantities.
     * @param status Order status.
     * @param transaction Associated financial transaction.
     * @return A new Order instance.
     */
    public Order createOrder(
        Map<String, Integer> products,
        Order.Status status,
        FinancialTransaction transaction
    ) {
        if (!isOrderValid(products, transaction)) {
            throw new IllegalArgumentException("Order validation failed: invalid items or insufficient stock.");
        }

        int orderId = nextOrderId.getAndIncrement();
        double total = calculateTransactionTotal(products, transaction);

        transaction.setTotal(total);

        Order order = new Order(
            orderId,
            LocalDate.now(),
            total,
            products,
            status,
            transaction
        );

        applyStockChanges(order);

        if (transaction.getType() == FinancialTransaction.Type.PURCHASE) {
            updateSupplierOrderHistories(order);
        }

        return order;
    }

    /**
     * Validates a proposed order depending on whether it's a sale or purchase.
     *
     * @param products Map of product identifiers to quantities.
     * @param transaction The financial transaction being validated.
     * @return True if the order is valid, otherwise false.
     */
    private boolean isOrderValid(Map<String, Integer> products, FinancialTransaction transaction) {
        if (transaction.getType() == FinancialTransaction.Type.SALE) {
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                try {
                    int itemId = Integer.parseInt(entry.getKey());
                    InventoryItem item = inventoryService.findById(itemId);
                    if (item == null || item.getQuantity() < entry.getValue()) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        } else if (transaction.getType() == FinancialTransaction.Type.PURCHASE) {
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                String[] parts = entry.getKey().split(":");
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

    /**
     * Calculates the total cost of a transaction based on the products involved.
     *
     * @param products Map of product identifiers to quantities.
     * @param transaction The associated financial transaction.
     * @return The total cost as a double.
     */
    private double calculateTransactionTotal(Map<String, Integer> products, FinancialTransaction transaction) {
        double total = 0.0;

        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String key = entry.getKey();
            int quantity = entry.getValue();

            if (transaction.getType() == FinancialTransaction.Type.SALE) {
                int itemId = Integer.parseInt(key);
                InventoryItem item = inventoryService.findById(itemId);
                if (item != null) {
                    total += item.getPrice() * quantity;
                }
            } else if (transaction.getType() == FinancialTransaction.Type.PURCHASE) {
                total += getPurchaseEntryTotal(key, quantity);
            }
        }

        return total;
    }

    /**
     * Applies stock quantity changes for a given order if it is a sale.
     *
     * @param order The order whose items affect inventory.
     */
    private void applyStockChanges(Order order) {
        if (order.getTransaction().getType() == FinancialTransaction.Type.SALE) {
            for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
                int itemId = Integer.parseInt(entry.getKey());
                InventoryItem item = inventoryService.findById(itemId);
                if (item != null) {
                    item.setQuantity(item.getQuantity() - entry.getValue());
                }
            }
        }
    }

    /**
     * Retrieves the supplier item from a key of the format "supplierId:itemId".
     *
     * @param key The compound identifier key.
     * @return The SupplierItem instance or null if not found.
     */
    private SupplierItem getSupplierItemFromKey(String key) {
        String[] parts = key.split(":");
        if (parts.length != 2) return null;

        try {
            int supplierId = Integer.parseInt(parts[0]);
            int supplierItemId = Integer.parseInt(parts[1]);

            Supplier supplier = supplierService.findSupplierById(supplierId);
            if (supplier == null) return null;

            return supplier.getItemById(supplierItemId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Calculates the total cost of a single purchase entry.
     *
     * @param key The supplier item key.
     * @param quantity Quantity ordered.
     * @return Total cost for the item.
     */
    private double getPurchaseEntryTotal(String key, int quantity) {
        SupplierItem supplierItem = getSupplierItemFromKey(key);
        return (supplierItem != null) ? supplierItem.getPrice() * quantity : 0.0;
    }

    /**
     * Updates the stock levels for a purchased item.
     *
     * @param key The supplier item key.
     * @param quantity Quantity received.
     */
    private void updatePurchaseStock(String key, int quantity) {
        SupplierItem supplierItem = getSupplierItemFromKey(key);
        if (supplierItem == null) return;

        InventoryItem stockItem = inventoryService.findById(supplierItem.getId());
        if (stockItem != null) {
            stockItem.setQuantity(stockItem.getQuantity() + quantity);
        }
    }

    /**
     * Creates a task to apply stock updates for delivered purchase orders.
     *
     * @param order The order to be processed.
     * @return A Runnable task that performs stock updates.
     */
    public Runnable getStockUpdateTask(Order order) {
        return () -> {
            if (order.getTransaction().getType() == FinancialTransaction.Type.PURCHASE &&
                order.getStatus() == Order.Status.DELIVERED) {
                for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
                    updatePurchaseStock(entry.getKey(), entry.getValue());
                }
            }
        };
    }

    /**
     * Records a new order in the supplier's order history.
     *
     * @param order The purchase order to record.
     */
    private void updateSupplierOrderHistories(Order order) {
        Map<Integer, Map<Integer, Integer>> supplierItemGroups = new HashMap<>();

        for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
            String[] parts = entry.getKey().split(":");
            int supplierId = Integer.parseInt(parts[0]);
            int supplierItemId = Integer.parseInt(parts[1]);
            int quantity = entry.getValue();

            supplierItemGroups
                .computeIfAbsent(supplierId, k -> new HashMap<>())
                .put(supplierItemId, quantity);
        }

        for (Map.Entry<Integer, Map<Integer, Integer>> entry : supplierItemGroups.entrySet()) {
            int supplierId = entry.getKey();
            Supplier supplier = supplierService.findSupplierById(supplierId);
            if (supplier == null) continue;

            Map<Integer, Integer> items = entry.getValue();
            double total = 0.0;
            for (Map.Entry<Integer, Integer> itemEntry : items.entrySet()) {
                SupplierItem item = supplier.getItemById(itemEntry.getKey());
                if (item != null) {
                    total += itemEntry.getValue() * item.getPrice();
                }
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
