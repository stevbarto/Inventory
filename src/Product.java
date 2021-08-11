import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

/**
 * Product class which generates product objects for the inventory class.
 * @author Steven Barton
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Default constructor for the product class.
     * @param id Integer value, Product ID for inventory.
     * @param name String value, Product name for inventory.
     * @param price Double value, price of the Product.
     * @param stock Integer value, how many in stock.
     * @param min Integer value, minimum to be in stock.
     * @param max Integer value, maximum to be in stock.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the id for the product object.
     * @param id Integer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the id for the product object.
     * @return Integer ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the name value for the product object.
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name value for the product object.
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the price value for the product object.
     * @param price Double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the price value for the product object.
     * @return Double price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Sets the in stock value for the product object.
     * @param stock Integer
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns the in stock value for the product object.
     * @return Integer stock
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Sets the min value for the product object.
     * @param min Integer
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Returns the min value for the product object.
     * @return Integer min
     */
    public int getMin() {
        return this.min;
    }

    /**
     * Sets the max value for the product object.
     * @param max Integer
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Returns the max value for the product object.
     * @return Integer max
     */
    public int getMax() {
        return this.max;
    }

    /**
     * Adds the provided part to the list of associated parts for the product object.
     * @param part Part Object to be added.
     * @throws IOException when a null part additino is attempted.
     */
    public void addAssociatedPart(Part part) throws Exception {

        if(part == null) {
            throw new IOException("Null part cannot be added to the Product!");
        }

        associatedParts.add(part);
    }

    /**
     * Removes the provided part object from the associated part list for the product.
     * @param selectedAssociatedPart Part object representing the part to delete from the Product.
     * @return True if the delete is successful, false if the part was already non-existent.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Returns the list of all associated parts for the product object.
     * @return ObservableList of all Part objects associated with this Product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
