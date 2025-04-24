package services;

import models.*;

public class FinancialService {
    private OrderService orderService;

    // Constructor
    public FinancialService(OrderService orderService) {
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
}
