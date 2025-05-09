package models;

/**
 * Represents an item provided by a supplier, extending the base Item class with an additional 
 * attribute for the supplier's price.
 * 
 * This class is used to store and manage details about items sourced from suppliers, including 
 * the item's price specific to the supplier. It extends the `Item` class to inherit basic 
 * item attributes such as ID, name, and description, while also introducing the `supplierPrice` 
 * attribute to represent the cost at which the supplier offers the item.
 * 
 * Provides methods for accessing and modifying the supplier price, as well as a formatted string 
 * representation of the item.
 */
public class SupplierItem extends Item {
    private double supplierPrice;

    // Constructor where ID is passed from outside
    public SupplierItem(int id, String name, String description, double supplierPrice) {
        super(id, name, description);
        this.supplierPrice = supplierPrice;
    }

    // Getter
    public double getSupplierPrice() {
        return supplierPrice;
    }

    // Setter
    public void setSupplierPrice(double supplierPrice) {
        this.supplierPrice = supplierPrice;
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return super.toString() + " | Supplier Price: £" + supplierPrice;
    }
}
