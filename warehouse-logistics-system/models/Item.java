package models;

/**
 * Base class for items, providing common attributes and methods for different types of items.
 * 
 * This abstract class serves as the foundation for various item types (such as `InventoryItem`, 
 * `SupplierItem`, etc.), containing common properties like `id`, `name`, and `description`. 
 * It provides basic getters for these properties and a `toString` method to display the item's 
 * details in a formatted string.
 * 
 * The `Item` class is intended to be extended by other classes that need additional functionality 
 * or attributes specific to their use case.
 */
public abstract class Item {
    private int id;
    private String name;
    private String description;

    // Constructor for setting the name, description, and ID
    public Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // ToString method to display item details
    @Override
    public String toString() {
        return "[" + id + "] " + name + " - " + description;
    }
}
