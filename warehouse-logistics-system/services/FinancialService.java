package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;

/**
 * FinancialService provides operations for retrieving financial transactions
 * and generating financial reports based on order data.
 */
public class FinancialService {
    private final OrderService orderService;

    /**
     * Constructs a FinancialService with the given OrderService dependency.
     *
     * @param orderService The order service to pull order and transaction data from.
     */
    public FinancialService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieves all financial transactions from all orders.
     *
     * @return A list of all financial transactions.
     */
    public List<FinancialTransaction> getAllTransactions() {
        List<Order> orders = orderService.getAllOrders();
        List<FinancialTransaction> transactions = new ArrayList<>();

        for (Order order : orders) {
            transactions.add(order.getTransaction());
        }

        return transactions;
    }

    /**
     * Retrieves all purchase-type transactions.
     *
     * @return A list of financial transactions where the type is PURCHASE.
     */
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

    /**
     * Retrieves all sale-type transactions.
     *
     * @return A list of financial transactions where the type is SALE.
     */
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

    /**
     * Generates a financial report based on all historical orders.
     *
     * @return A map containing total revenue, total purchases, net income, and profit/loss status.
     */
    public Map<String, Object> generateAllTimeFinancialReport() {
        double totalRevenue = 0;
        double totalPurchases = 0;

        for (Order order : orderService.getAllOrders()) {
            FinancialTransaction transaction = order.getTransaction();
            double total = transaction.getTotal();

            if (transaction.getType() == FinancialTransaction.Type.SALE) {
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
