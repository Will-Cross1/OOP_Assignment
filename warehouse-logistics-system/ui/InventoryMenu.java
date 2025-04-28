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
                case 1 -> viewInventory();
                case 2 -> changePrices();
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewInventory() {
        boolean back = false;

        while (!back) {
            System.out.println("\n-- View Inventory --");
            System.out.println("1. View All Items");
            System.out.println("2. Search by Item ID");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> System.out.println("Showing all inventory items... [Placeholder]");
                case 2 -> {
                    System.out.print("Enter Item ID: ");
                    String id = scanner.nextLine();
                    System.out.println("Searching inventory for item ID: " + id + " [Placeholder]");
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void changePrices() {
        System.out.println("Change Prices - [Placeholder]");
    }
}
