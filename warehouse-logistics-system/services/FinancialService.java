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
        return getTransactionsOfType(PurchaseTransaction.class); // Filters and casts to PurchaseTransaction
    }

    /**
     * Retrieves all sale transactions.
     *
     * @return A list of all sale transactions.
     */
    public List<SaleTransaction> getSaleTransactions() {
        return getTransactionsOfType(SaleTransaction.class); // Filters and casts to SaleTransaction
    }

    /**
     * Internal helper method to filter and cast financial transactions by type.
     *
     * @param type The class type to filter by (e.g., PurchaseTransaction.class).
     * @return A list of transactions of the specified type.
     * @param <T> A subclass of FinancialTransaction.
     */
    private <T extends FinancialTransaction> List<T> getTransactionsOfType(Class<T> type) {
        return orderService.getAllOrders().stream()
            .map(Order::getTransaction)
            .filter(type::isInstance)  // Filtering by the specified transaction type
            .map(type::cast)           // Casting to the specified transaction type
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

            if (transaction.getType() == FinancialTransaction.Type.SALE) {
                totalRevenue += total;
            } else if (transaction.getType() == FinancialTransaction.Type.PURCHASE) {
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
