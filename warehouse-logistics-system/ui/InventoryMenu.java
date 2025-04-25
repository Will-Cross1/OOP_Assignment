package ui;

import java.util.Scanner;

public class InventoryMenu {
    private final Scanner scanner;

    public InventoryMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
    
        while (!back) {
            System.out.println("\n=== Inventory Management ===");
            System.out.println("1. View Inventory");
            System.out.println("2. Change Prices");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
    
            int choice = ImportUtils.getUserChoice(scanner);
    
            switch (choice) {
                case 1 -> System.out.println("View Inventory - [Placeholder]");
                case 2 -> System.out.println("Change Prices - [Placeholder]");
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
