package models;

/**
 * Base class for items
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
