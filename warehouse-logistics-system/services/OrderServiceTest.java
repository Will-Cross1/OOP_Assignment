package services;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderCreationService orderCreationService;

    @BeforeEach
    public void setUp() {
        // --- INVENTORY SETUP ---
        InventoryService inventoryService = new InventoryService();
        SupplierService supplierService = new SupplierService(inventoryService);
        OrderCreationService orderCreationService = new OrderCreationService(inventoryService, supplierService);
        orderService = new OrderService(orderCreationService);

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
    public void testCreateOrderSale() {
        Map<String, Integer> productsSale = new HashMap<>();
        productsSale.put("1", 3); // Example product (Laptop)
        int orderId = orderService.createOrder(productsSale, false); // Sale order

        // Verify the order was created and added to the list
        Order order = orderService.getOrderById(orderId);
        assertNotNull(order, "Order should be created");
        assertEquals(Order.Status.DELIVERED, order.getStatus(), "Sale order should have DELIVERED status");
    }

    @Test
    public void testCreateOrderPurchase() {
        Map<String, Integer> productsPurchase = new HashMap<>();
        productsPurchase.put("3:2", 5); // Example product (Keyboard from Supplier 3)
        int orderId = orderService.createOrder(productsPurchase, true); // Purchase order

        // Verify the order was created and added to the list
        Order order = orderService.getOrderById(orderId);
        assertNotNull(order, "Order should be created");

        // Verify the order's status is PROCESSED
        assertEquals(Order.Status.PROCESSED, order.getStatus(), "Purchase order should have PROCESSED status");
        
        // Not testing the delivery schedular as it is only to simulate delivery times
    }

    @Test
    public void testGenerateOrder() {
        // Testing if generateOrder correctly sets the order status and transaction type
        Map<String, Integer> products = new HashMap<>();
        products.put("1", 1); // Example product (Laptop)
    
        // Create a sale order and get its ID
        int saleOrderId = orderService.createOrder(products, false); // Sale order
        Order saleOrder = orderService.getOrderById(saleOrderId); // Retrieve the created order by ID
        assertEquals(Order.Status.DELIVERED, saleOrder.getStatus(), "Sale order should have DELIVERED status");
    
        // Create a purchase order and get its ID
        Map<String, Integer> purchaseProducts = new HashMap<>();
        purchaseProducts.put("2:2", 2); // Example product (Mouse)
        int purchaseOrderId = orderService.createOrder(purchaseProducts, true); // Purchase order
        Order purchaseOrder = orderService.getOrderById(purchaseOrderId); // Retrieve the created order by ID
        assertEquals(Order.Status.PROCESSED, purchaseOrder.getStatus(), "Purchase order should have PROCESSED status");
    }


    @Test
    public void testGetAllOrders() {
        var orders = orderService.getAllOrders();
        assertEquals(3, orders.size(), "There should be 3 orders");
    }

    @Test
    public void testGetPurchaseTransactions() {
        var purchaseOrders = orderService.getPurchaseTransactions();
        assertEquals(2, purchaseOrders.size(), "There should be 2 purchase orders");
    }

    @Test
    public void testGetSaleTransactions() {
        var saleOrders = orderService.getSaleTransactions();
        assertEquals(1, saleOrders.size(), "There should be 1 sale order");
    }

    @Test
    public void testGetOrderByIdPositive() {
        // Create an order and verify that it can be retrieved by ID
        Map<String, Integer> productsSale = new HashMap<>();
        productsSale.put("2", 5);  // Example product (Mouse)
        int orderId = orderService.createOrder(productsSale, false); // Sale order

        Order order = orderService.getOrderById(orderId);
        assertNotNull(order, "Order should not be null");
        assertEquals(orderId, order.getId(), "Order ID should match");
    }

    @Test
    public void testGetOrderByIdNegative() {
        // Try to get an order with a non-existing ID
        Order order = orderService.getOrderById(999);  // ID 999 does not exist
        assertNull(order, "Order should be null when not found");
    }
}
