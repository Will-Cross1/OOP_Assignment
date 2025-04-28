package ui;

import java.util.Scanner;

public class FinanceMenu {
    private final Scanner scanner;

    public FinanceMenu(Scanner scanner) {
        this.scanner = scanner;
    }

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
                case 1 -> System.out.println("Showing all transactions... [Placeholder]");
                case 2 -> System.out.println("Showing purchase transactions... [Placeholder]");
                case 3 -> System.out.println("Showing sale transactions... [Placeholder]");
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

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
                case 1 -> System.out.println("Showing all orders... [Placeholder]");
                case 2 -> {
                    System.out.print("Enter Order ID: ");
                    String id = scanner.nextLine();
                    System.out.println("Searching for order ID: " + id + " [Placeholder]");
                }
                case 3 -> System.out.println("Showing purchase orders... [Placeholder]");
                case 4 -> System.out.println("Showing sale orders... [Placeholder]");
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void generateReport() {
        System.out.println("Generating financial report... [Placeholder]");
    }
}
