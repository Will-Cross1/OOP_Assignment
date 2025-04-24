package models;

/**
 * Represents an item in the warehouse inventory
 */
public class InventoryItem extends Item {
    private double unitPrice;
    private int quantity;

    // Constructor where ID is passed from outside
    public InventoryItem(int id, String name, String description, double unitPrice, int quantity) {
        super(id, name, description);
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    // Getters
    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return super.toString() + " | Price: £" + unitPrice + " | Stock: " + quantity;
    }
}
