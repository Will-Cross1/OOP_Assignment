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
                case 1 -> System.out.println("View Finantial Transactions - [Placeholder]");
                    // all
                    // purchase
                    // sale
                case 2 -> System.out.println("View Orders - [Placeholder]");
                    // all
                    // id
                    // purchase
                    // sale
                case 3 -> System.out.println("Generate Financial Report - [Placeholder]");
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
