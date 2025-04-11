package ui;

import java.util.Scanner;

import models.Supplier;
import services.SupplierService;
import services.InventoryService;
/**
 * Write a description of class VisualInterface here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisualInterface
{
    private Scanner scanner;
    private SupplierService supplierService;
    private InventoryService inventoryService;
    /**
     * Constructor for objects of class VisualInterface
     */
    public VisualInterface(SupplierService supplierService, InventoryService inventoryService)
    {
        this.scanner = new Scanner(System.in);
        this.supplierService = supplierService;
        this.inventoryService = inventoryService;
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
            System.out.println("\n=== BNU Warehouse Management System ===");
            System.out.println("1. Supplier Management");
            System.out.println("2. Inventory Management");
            System.out.println("3. Make Order");
            System.out.println("4. Financial Reports");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
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
                    viewSuppliers();
                    break;
                case 2:
                    addSupplier();
                    break;
                case 3:
                    updateSupplier();
                    break;
                case 4:
                    deleteSupplier();
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

    private void viewSuppliers() {
        System.out.println("\n-- Supplier List --");
        for (Supplier s : supplierService.getAllSuppliers()) {
            System.out.println(s);
        }
    }

    private void addSupplier() {
        System.out.println("=== Add Supplier ===");

        System.out.print("Enter Supplier Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        Supplier newSupplier = supplierService.addSupplier(name, email, phone, location);
        System.out.println("Supplier added successfully with ID: " + newSupplier.getId());
    }

    private void updateSupplier() {
        System.out.println("=== Update Supplier ===");
        System.out.print("Enter Supplier ID to update: ");
        int id = getUserChoice();
    
        Supplier supplier = supplierService.findSupplierById(id);
        if (supplier == null) {
            System.out.println("Supplier not found.");
            return;
        }
    
        boolean back = false;
        while (!back) {
            System.out.println("\nSelect what to update:");
            System.out.println("1. Name");
            System.out.println("2. Contact (Email & Phone)");
            System.out.println("3. Location");
            System.out.println("4. All fields");
            System.out.println("0. Cancel");
            System.out.print("Choice: ");
            int choice = getUserChoice();
    
            String name = supplier.getName();
            String email = supplier.getEmail();
            String phone = supplier.getPhone();
            String location = supplier.getLocation();
    
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new Name: ");
                    name = scanner.nextLine();
                }
                case 2 -> {
                    System.out.print("Enter new Email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter new Phone: ");
                    phone = scanner.nextLine();
                }
                case 3 -> {
                    System.out.print("Enter new Location: ");
                    location = scanner.nextLine();
                }
                case 4 -> {
                    System.out.print("Enter new Name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter new Email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter new Phone: ");
                    phone = scanner.nextLine();
                    System.out.print("Enter new Location: ");
                    location = scanner.nextLine();
                }
                case 0 -> {
                    System.out.println("Update cancelled.");
                    return;
                }
                default -> {
                    System.out.println("Invalid option.");
                    continue;
                }
            }
    
            boolean updated = supplierService.updateSupplier(id, name, email, phone, location);
            if (updated) {
                System.out.println("Supplier updated successfully.");
            } else {
                System.out.println("Failed to update supplier.");
            }
    
            back = true;
        }
    }

    private void deleteSupplier() {
        System.out.print("Enter Supplier ID to delete: ");
        int id = getUserChoice();

        boolean removed = supplierService.deleteSupplier(id);
        if (removed) {
            System.out.println("Supplier deleted successfully.");
        } else {
            System.out.println("Supplier not found.");
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
