package ui;

import java.util.Scanner;

public class CustomerOrderMenu {
    private final Scanner scanner;

    public CustomerOrderMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
    
        while (!back) {
            System.out.println("\n=== Customer Order ===");
            System.out.println("1. Make Order");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
    
            int choice = ImportUtils.getUserChoice(scanner);
    
            switch (choice) {
                case 1 -> System.out.println("Make Order - [Placeholder]");
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
