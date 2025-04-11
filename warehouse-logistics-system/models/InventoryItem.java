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

    // Getters and setters for price and quantity
    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseStock(int amount) {
        this.quantity += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= quantity) {
            this.quantity -= amount;
        } else {
            System.out.println("Not enough stock to remove " + amount + " items.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Price: £" + unitPrice + " | Stock: " + quantity;
    }
}
