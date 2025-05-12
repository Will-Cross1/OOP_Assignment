package models;

/**
 * Represents an item in the warehouse inventory, extending the base `Item` class with additional 
 * attributes for unit price and quantity.
 * 
 * This class is used to manage inventory items, including the unit price and available stock 
 * quantity in the warehouse. It extends the `Item` class to inherit basic item attributes such as 
 * ID, name, and description, while introducing the `unitPrice` and `quantity` attributes to represent 
 * the price per unit and the quantity of items available in stock.
 * 
 * Provides methods for accessing and modifying the unit price and quantity, as well as a formatted 
 * string representation of the item.
 */
public class InventoryItem extends Item {
    private int quantity;

    // Constructor where ID is passed from outside
    public InventoryItem(int id, String name, String description, double unitPrice, int quantity) {
        super(id, name, description, unitPrice);
        this.quantity = quantity;
    }

    // Getter
    public int getQuantity() {
        return quantity;
    }

    // Setter
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return super.toString() + " | Stock: " + quantity;
    }
}
