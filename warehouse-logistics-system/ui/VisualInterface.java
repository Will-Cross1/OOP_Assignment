package ui;

import java.util.Scanner;

import services.*;
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
            System.out.println("3. Make Customer Order");
            System.out.println("4. Financial Reporting");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
            int choice = ImportUtils.getUserChoice(scanner);

            switch (choice) {
                case 1 -> new SupplierMenu(scanner).run();
                case 2 -> new InventoryMenu(scanner).run();
                case 3 -> new CustomerOrderMenu(scanner).run();
                case 4 -> new FinanceMenu(scanner).run();
                case 0 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
