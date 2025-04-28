package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;

public class FinancialService {
    private OrderService orderService;

    // Constructor
    public FinancialService(OrderService orderService) {
        this.orderService = orderService;
    }

    public List<FinancialTransaction> getAllTransactions() {
        List<Order> orders = orderService.getAllOrders();
        List<FinancialTransaction> transactions = new ArrayList<>();

        for (Order order : orders) {
            transactions.add(order.getTransaction());
        }

        return transactions;
    }

    public List<FinancialTransaction> getPurchaseTransactions() {
        List<FinancialTransaction> allTransactions = getAllTransactions();
        List<FinancialTransaction> purchaseTransactions = new ArrayList<>();

        for (FinancialTransaction transaction : allTransactions) {
            if (transaction.getType() == FinancialTransaction.Type.PURCHASE) {
                purchaseTransactions.add(transaction);
            }
        }

        return purchaseTransactions;
    }

    public List<FinancialTransaction> getSaleTransactions() {
        List<FinancialTransaction> allTransactions = getAllTransactions();
        List<FinancialTransaction> saleTransactions = new ArrayList<>();

        for (FinancialTransaction transaction : allTransactions) {
            if (transaction.getType() == FinancialTransaction.Type.SALE) {
                saleTransactions.add(transaction);
            }
        }

        return saleTransactions;
    }

    // All-Time Financial Reporting
    public Map<String, Object> generateAllTimeFinancialReport() {
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
        boolean isProfit = netIncome >= 0;

        Map<String, Object> report = new HashMap<>();
        report.put("totalRevenue", totalRevenue);
        report.put("totalPurchases", totalPurchases);
        report.put("netIncome", netIncome);
        report.put("isProfit", isProfit);

        return report;
    }
}
