package services;

import models.*;

import java.util.Map;

public class FinancialService {
    private InventoryService inventoryService;
    private SupplierService supplierService;
    private OrderService orderService;

    // Constructor
    public FinancialService(InventoryService inventoryService, SupplierService supplierService, OrderService orderService) {
        this.inventoryService = inventoryService;
        this.supplierService = supplierService;
        this.orderService = orderService;
    }

    // All-Time Financial Reporting
    public void printAllTimeFinancialReport() {
        double totalRevenue = 0;
        double totalPurchases = 0;

        for (Order order : orderService.getAllOrders()) {
            double total = order.getTransaction().getTotal();

            if (order.getTransaction().getType() == FinancialTransaction.Type.SALE) {
                totalRevenue += total;
            } else {
                totalPurchases += total;
            }
        }

        double netIncome = totalRevenue - totalPurchases;

        System.out.println("=== All-Time Financial Report ===");
        System.out.println("Total Sales Revenue: £" + totalRevenue);
        System.out.println("Total Purchase Costs: £" + totalPurchases);
        System.out.println("Net Income: £" + netIncome);
        System.out.println(netIncome >= 0 ? "Profit" : "Loss");
        System.out.println("=================================");
    }

    // Item-Specific Financial Reporting
    public void printFinancialReportByItem(int itemId) {
        double revenue = 0;
        double cost = 0;

        for (Order order : orderService.getAllOrders()) {
            for (Map.Entry<String, Integer> entry : order.getProducts().entrySet()) {
                String key = entry.getKey();
                int quantity = entry.getValue();
                boolean matches = false;

                if (order.getTransaction().getType() == FinancialTransaction.Type.SALE) {
                    try {
                        if (Integer.parseInt(key) == itemId) matches = true;
                    } catch (NumberFormatException ignored) {}
                } else if (order.getTransaction().getType() == FinancialTransaction.Type.PURCHASE) {
                    String[] parts = key.split(":");
                    if (parts.length == 2 && Integer.parseInt(parts[1]) == itemId) {
                        matches = true;
                    }
                }

                if (matches) {
                    double unitPrice = 0;
                    if (order.getTransaction().getType() == FinancialTransaction.Type.SALE) {
                        InventoryItem item = inventoryService.findById(itemId);
                        if (item != null) unitPrice = item.getUnitPrice();
                        revenue += unitPrice * quantity;
                    } else {
                        for (Supplier supplier : supplierService.getAllSuppliers()) {
                            SupplierItem sItem = supplier.getItemById(itemId);
                            if (sItem != null) {
                                unitPrice = sItem.getSupplierPrice();
                                break;
                            }
                        }
                        cost += unitPrice * quantity;
                    }
                }
            }
        }

        double net = revenue - cost;

        System.out.println("=== Financial Report for Item ID " + itemId + " ===");
        System.out.println("Revenue: £" + revenue);
        System.out.println("Purchase Cost: £" + cost);
        System.out.println("Net: £" + net);
        System.out.println(net >= 0 ? "Profit" : "Loss");
        System.out.println("===============================================");
    }
}
