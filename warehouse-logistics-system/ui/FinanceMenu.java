package ui;

import java.util.Map;
import java.util.Scanner;

import models.FinancialTransaction;
import models.Order;
import services.OrderService;
import services.FinancialService;

/**
 * Handles the finance-related menu for viewing transactions, orders,
 * and generating financial reports.
 */
public class FinanceMenu {
    private final Scanner scanner;
    private OrderService orderService;
    private FinancialService financialService;

    /**
     * Constructs a FinanceMenu with the provided services.
     *
     * @param scanner           the Scanner for user input
     * @param orderService      the service for retrieving orders
     * @param financialService  the service for managing financial transactions
     */
    public FinanceMenu(Scanner scanner, OrderService orderService, FinancialService financialService) {
        this.scanner = scanner;
        this.orderService = orderService;
        this.financialService = financialService;
    }

    /**
     * Launches the finance menu loop, allowing users to select
     * different financial views and actions.
     */
    public void run() {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Financial Accounts ===");
            System.out.println("1. View Financial Transactions");
            System.out.println("2. View Orders");
            System.out.println("3. Generate Financial Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> viewTransactions();
                case 2 -> viewOrders();
                case 3 -> generateReport();
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Displays the transaction submenu, allowing filtering by type (all, purchase, sale).
     */
    private void viewTransactions() {
        boolean back = false;

        while (!back) {
            System.out.println("\n-- View Financial Transactions --");
            System.out.println("1. View All Transactions");
            System.out.println("2. View Purchase Transactions");
            System.out.println("3. View Sale Transactions");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.println("Showing all transactions...");
                    for (FinancialTransaction transaction : financialService.getAllTransactions()) {
                        System.out.println(transaction);
                    }
                }
                case 2 -> {
                    System.out.println("Showing purchase transactions...");
                    for (FinancialTransaction transaction : financialService.getPurchaseTransactions()) {
                        System.out.println(transaction);
                    }
                }
                case 3 -> {
                    System.out.println("Showing sale transactions...");
                    for (FinancialTransaction transaction : financialService.getSaleTransactions()) {
                        System.out.println(transaction);
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Displays the order submenu, allowing filtering and search by order ID or transaction type.
     */
    private void viewOrders() {
        boolean back = false;

        while (!back) {
            System.out.println("\n-- View Orders --");
            System.out.println("1. View All Orders");
            System.out.println("2. Search by Order ID");
            System.out.println("3. View Purchase Orders");
            System.out.println("4. View Sale Orders");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.println("Showing all orders...");
                    for (Order order : orderService.getAllOrders()) {
                        System.out.println(order);
                    }
                }
                case 2 -> {
                    System.out.print("Enter Order ID: ");
                    int id = ImportUtils.getUserChoice(scanner);
                    System.out.println("Searching for order ID: " + id);
                    System.out.print(orderService.getOrderById(id) + "\n\n");
                }
                case 3 -> {
                    System.out.println("Showing purchase orders...");
                    for (Order order : orderService.getPurchaseTransactions()) {
                        System.out.println(order);
                    }
                }
                case 4 -> {
                    System.out.println("Showing sale orders...");
                    for (Order order : orderService.getSaleTransactions()) {
                        System.out.println(order);
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Generates and displays a simple all-time financial report including total revenue,
     * purchase costs, net income, and whether the result is profit or loss.
     */
    private void generateReport() {
        System.out.println("Generating financial report... ");

        Map<String, Object> report = financialService.generateAllTimeFinancialReport();

        System.out.println("=== All-Time Financial Report ===");
        System.out.println("Total Sales Revenue: £" + report.get("totalRevenue"));
        System.out.println("Total Purchase Costs: £" + report.get("totalPurchases"));
        System.out.println("Net Income: £" + report.get("netIncome"));
        System.out.println((boolean) report.get("isProfit") ? "Profit" : "Loss");
        System.out.println("=================================");
    }
}
