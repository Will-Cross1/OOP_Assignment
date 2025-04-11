package models;

/**
 * Represents an item from a supplier with a specific price
 */
public class SupplierItem extends Item {
    private double supplierPrice;

    // Constructor where ID is passed from outside
    public SupplierItem(int id, String name, String description, double supplierPrice) {
        super(id, name, description);
        this.supplierPrice = supplierPrice;
    }

    // Getter and setter for supplier price
    public double getSupplierPrice() {
        return supplierPrice;
    }

    public void setSupplierPrice(double supplierPrice) {
        this.supplierPrice = supplierPrice;
    }

    @Override
    public String toString() {
        return super.toString() + " | Supplier Price: £" + supplierPrice;
    }
}
