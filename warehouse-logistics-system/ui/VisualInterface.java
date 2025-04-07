package ui;

import java.util.Scanner;
/**
 * Write a description of class VisualInterface here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisualInterface
{
    private Scanner scanner;

    /**
     * Constructor for objects of class VisualInterface
     */
    public VisualInterface()
    {
        scanner = new Scanner(System.in);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void run() {
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    supplierMenu();
                    break;
                case 2:
                    inventoryMenu();
                    break;
                case 3:
                    System.out.println("Creating user order.");
                    // Call order processing menu later
                    break;
                case 4:
                    financeMenu();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
    
    private void printMainMenu() {
        System.out.println("\n=== BNU Warehouse Management System ===");
        System.out.println("1. Supplier Management");
        System.out.println("2. Inventory Management");
        System.out.println("3. Make Order");
        System.out.println("4. Financial Reports");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }
    
    private void supplierMenu() {
        boolean back = false;
    
        while (!back) {
            System.out.println("\n=== Supplier Management ===");
            System.out.println("1. View Suppliers");
            System.out.println("2. Add Supplier");
            System.out.println("3. Update Supplier");
            System.out.println("4. Delete Supplier");
            System.out.println("5. Create Purchase Order");
            System.out.println("6. Track Order Status");
            System.out.println("7. Manage Deliveries");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
    
            int choice = getUserChoice();
    
            switch (choice) {
                case 1:
                    System.out.println("View Suppliers - [Placeholder]");
                    break;
                case 2:
                    System.out.println("Add Supplier - [Placeholder]");
                    break;
                case 3:
                    System.out.println("Update Supplier - [Placeholder]");
                    break;
                case 4:
                    System.out.println("Delete Supplier - [Placeholder]");
                    break;
                case 5:
                    System.out.println("Create Purchase Order - [Placeholder]");
                    break;
                case 6:
                    System.out.println("Track Order Status - [Placeholder]");
                    break;
                case 7:
                    System.out.println("Manage Deliveries - [Placeholder]");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private void inventoryMenu() {
        boolean back = false;
    
        while (!back) {
            System.out.println("\n=== Inventory Management ===");
            System.out.println("1. View Inventory Levels");
            System.out.println("2. Monitor Low Stock Alerts");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
    
            int choice = getUserChoice();
    
            switch (choice) {
                case 1:
                    System.out.println("View Inventory - [Placeholder]");
                    break;
                case 2:
                    System.out.println("Monitor Stock Alerts - [Placeholder]");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private void financeMenu() {
        boolean back = false;
    
        while (!back) {
            System.out.println("\n=== Financial Accounts ===");
            System.out.println("1. View Financial Transactions");
            System.out.println("2. Generate Sales Summary");
            System.out.println("3. Generate Expense Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
    
            int choice = getUserChoice();
    
            switch (choice) {
                case 1:
                    System.out.println("View Finantial Transactions - [Placeholder]");
                    break;
                case 2:
                    System.out.println("Generate Sales Summary - [Placeholder]");
                    break;
                case 3:
                    System.out.println("Generate Expenses Report - [Placeholder]");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }
}
