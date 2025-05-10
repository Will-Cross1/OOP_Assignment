package services;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialServiceTest {

    private FinancialService financialService;
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        // --- INVENTORY SETUP ---
        InventoryService inventoryService = new InventoryService();
        SupplierService supplierService = new SupplierService(inventoryService);
        OrderCreationService orderCreationService = new OrderCreationService(inventoryService, supplierService);
        orderService = new OrderService(orderCreationService);
        financialService = new FinancialService(orderService);

        // --- INVENTORY DATA ---
        inventoryService.addInventoryItem(1, "Laptop", "15-inch portable computer", 1000.0, 10);
        inventoryService.addInventoryItem(2, "Mouse", "Wireless optical mouse", 25.0, 50);
        inventoryService.addInventoryItem(3, "Keyboard", "Mechanical keyboard", 70.0, 30);

        // --- SUPPLIER DATA ---
        supplierService.addSupplier("Acme Supplies", "acme@supplies.com", "02081234567", "London");
        supplierService.addSupplier("Global Distributors", "contact@global.com", "01709876543", "Birmingham");
        supplierService.addSupplier("Shell Company", "we_commit@tax_evasion.co.uk", "01234567890", "Gurnsey");

        // --- SUPPLIER ITEM DATA ---
        supplierService.createSupplierItem(1, 1, 1400);
        supplierService.createSupplierItem(2, 2, 20);
        supplierService.createSupplierItem(3, 1, 1423);
        supplierService.createSupplierItem(3, 2, 23);
        supplierService.createSupplierItem(3, 3, 64);

        // --- SALE ORDER ---
        Map<String, Integer> saleProducts = new HashMap<>();
        saleProducts.put("1", 2);  // Laptop (ID 1), Quantity 2

        orderService.createOrder(saleProducts, false); // false = Sale order

        // --- PURCHASE ORDERS ---
        Map<String, Integer> purchaseProducts = new HashMap<>();
        purchaseProducts.put("3:3", 5); // Supplier 3, item 3 (Keyboard, 5)
        purchaseProducts.put("3:2", 5); // Supplier 3, item 2 (Mouse, 5)

        orderService.createOrder(purchaseProducts, true); // true = Purchase order

        Map<String, Integer> purchaseProducts2 = new HashMap<>();
        purchaseProducts2.put("3:3", 2); // Supplier 3, item 3 (Keyboard, 2)
        purchaseProducts2.put("3:2", 58); // Supplier 3, item 2 (Mouse, 58)

        orderService.createOrder(purchaseProducts2, true); // true = Purchase order
    }

    @Test
    public void testGetAllTransactions() {
        var transactions = financialService.getAllTransactions();
        assertEquals(3, transactions.size(), "There should be 3 transactions");
    }

    @Test
    public void testGetPurchaseTransactions() {
        var purchaseTransactions = financialService.getPurchaseTransactions();
        assertEquals(2, purchaseTransactions.size(), "There should be 2 purchase transactions");
    }

    @Test
    public void testGetSaleTransactions() {
        var saleTransactions = financialService.getSaleTransactions();
        assertEquals(1, saleTransactions.size(), "There should be 1 sale transaction");
    }

    @Test
    public void testGenerateAllTimeFinancialReport() {
        var report = financialService.generateAllTimeFinancialReport();

        assertEquals(2000.0, report.get("totalRevenue"), "Total revenue should be 2000.0");
        assertEquals(1897.0, report.get("totalPurchases"), "Total purchases should be 1897.0");
        assertEquals(103.0, report.get("netIncome"), "Net income should be 175.0");
        assertTrue((Boolean) report.get("isProfit"), "The report should indicate a profit");
    }
}
