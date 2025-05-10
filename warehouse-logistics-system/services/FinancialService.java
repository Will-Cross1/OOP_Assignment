package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return orderService.getAllOrders().stream()
            .map(Order::getTransaction)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves all purchase transactions.
     *
     * @return A list of all purchase transactions.
     */
    public List<PurchaseTransaction> getPurchaseTransactions() {
        return orderService.getAllOrders().stream()
            .map(Order::getTransaction)
            .filter(PurchaseTransaction.class::isInstance) // Filtering purchase transactions
            .map(PurchaseTransaction.class::cast)  // Casting to PurchaseTransaction
            .collect(Collectors.toList());
    }

    /**
     * Retrieves all sale transactions.
     *
     * @return A list of all sale transactions.
     */
    public List<SaleTransaction> getSaleTransactions() {
        return orderService.getAllOrders().stream()
            .map(Order::getTransaction)
            .filter(SaleTransaction.class::isInstance) // Filtering sale transactions
            .map(SaleTransaction.class::cast)  // Casting to SaleTransaction
            .collect(Collectors.toList());
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

            if (transaction instanceof SaleTransaction) {
                totalRevenue += total;
            } else if (transaction instanceof PurchaseTransaction) {
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
