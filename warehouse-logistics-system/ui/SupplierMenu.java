package ui;

import java.util.Scanner;

public class SupplierMenu {
    private final Scanner scanner;

    public SupplierMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean back = false;
    
        while (!back) {
            System.out.println("\n=== Supplier Management ===");
            System.out.println("1. View Suppliers");
            System.out.println("2. Add Supplier");
            System.out.println("3. Update Supplier");
            System.out.println("4. Delete Supplier");
            System.out.println("5. Create Purchase Order");
            System.out.println("6. Track Order Status");
            System.out.println("7. Update Supplier Items");
            System.out.println("8. View Order History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
    
            int choice = ImportUtils.getUserChoice(scanner);
    
            switch (choice) {
                case 1 -> viewSuppliers();
                case 2 -> addSupplier();
                case 3 -> updateSupplier();
                case 4 -> deleteSupplier();
                case 5 -> System.out.println("Create Purchase Order - [Placeholder]");
                case 6 -> System.out.println("Track Order Status - [Placeholder]");
                case 7 -> System.out.println("Update Supplier Items - [Placeholder]");
                case 8 -> System.out.println("View Order History - [Placeholder]");
                case 0 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
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
        int id = ImportUtils.getUserChoice(scanner);
    
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
            int choice = ImportUtils.getUserChoice(scanner);
    
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
        int id = ImportUtils.getUserChoice(scanner);

        boolean removed = supplierService.deleteSupplier(id);
        if (removed) {
            System.out.println("Supplier deleted successfully.");
        } else {
            System.out.println("Supplier not found.");
        }
    }
}
