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
        LocalDate estimatedArrival,
        Order.Status status,
        FinancialTransaction.Type type
    ) {
        if (!isOrderValid(products, type)) {
            throw new IllegalArgumentException("Order validation failed: invalid items or insufficient stock.");
        }

        FinancialTransaction transaction = new FinancialTransaction(type, LocalDate.now());
        Order order = new Order(nextOrderId++, products, estimatedArrival, status, transaction);

        double total = processTransaction(order); // apply stock changes + calculate total
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

    // Transaction Processing
    private double processTransaction(Order order) {
        double total = 0.0;
    
        for (Map.Entry<String, Integer> entry : order.getProducts().entrySet()) {
            String key = entry.getKey();
            int quantity = entry.getValue();
    
            if (order.getTransaction().getType() == FinancialTransaction.Type.SALE) {
                int itemId = Integer.parseInt(key);
                InventoryItem item = inventoryService.findById(itemId);
                if (item != null) {
                    total += item.getUnitPrice() * quantity;
                    item.setQuantity(item.getQuantity() - quantity);
                }
            } else if (order.getTransaction().getType() == FinancialTransaction.Type.PURCHASE) {
                total += processPurchaseEntry(order, key, quantity, false);
            }
        }
    
        return total;
    }

    private double processPurchaseEntry(Order order, String key, int quantity, boolean updateStock) {
        String[] parts = key.split(":");
        if (parts.length != 2) return 0.0;
    
        try {
            int supplierId = Integer.parseInt(parts[0]);
            int supplierItemId = Integer.parseInt(parts[1]);
    
            Supplier supplier = supplierService.findSupplierById(supplierId);
            if (supplier == null) return 0.0;
    
            SupplierItem supplierItem = supplier.getItemById(supplierItemId);
            if (supplierItem == null) return 0.0;
    
            InventoryItem stockItem = inventoryService.findById(supplierItem.getId());
            if (stockItem == null) return 0.0;
    
            if (updateStock) {
                stockItem.setQuantity(stockItem.getQuantity() + quantity);
            }
    
            return supplierItem.getSupplierPrice() * quantity;
    
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public Runnable getStockUpdateTask(Order order) {
        return () -> {
            if (order.getTransaction().getType() == FinancialTransaction.Type.PURCHASE &&
                order.getStatus() == Order.Status.DELIVERED) {
                for (Map.Entry<String, Integer> entry : order.getProducts().entrySet()) {
                    processPurchaseEntry(order, entry.getKey(), entry.getValue(), true);
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
                supplier.addOrderRecord(new SupplierOrderRecord(
                    order.getId(),
                    order.getTransaction().getTransactionDate(),
                    entry.getValue()
                ));
            }
        }
    }
}
