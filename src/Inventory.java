import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Inventory class manages the overall inventory.
 * @author Steven Barton
 */
public class Inventory extends Application {

    private final ObservableList<Part> allParts;
    private final ObservableList<Product> allProducts;

    private Stage inventory;

    ArrayList<Integer> partNums;
    ArrayList<Integer> prodNums;

    /**
     * Inventory constructor method, initiates an inventory object backed by an ObservableList.
     */
    public Inventory() {

        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();

        partNums = new ArrayList<Integer>();
        prodNums = new ArrayList<Integer>();

    }

    @Override
    public void start(Stage stage) throws Exception {
        // TODO: How to get the tables to populate with all data from the beginning
        // TODO: How to get the window to change to add/mod/del interfaces on click

        inventory = stage;

        Parent parent = FXMLLoader.load(getClass().getResource("inventoryWindow.fxml"));

        Scene scene = new Scene(parent);

        inventory.setTitle("Inventory");

        inventory.setScene(scene);

        inventory.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Adds a Part object to the inventory.
     * Had a null pointer exception on the if statement, calling the addPart method on an
     * empty allParts list returns a NullPointerException.  I found that I had simply added a
     * void return type to the constructor making it not a constructor.
     * @param newPart InHouse or Outsourced object to be added.
     */
    public boolean addPart(Part newPart) {

        if(allParts.contains(newPart)) {
            return false;
        }

        newPart.setPrice(normalizePrice(newPart.getPrice()));

        return allParts.add(newPart);
    }

    /**
     * Adds a Product object to the inventory.
     * @param newProduct Product object to be added.
     */
    public boolean addProduct(Product newProduct) {

        if(allProducts.contains(newProduct)) {
            return false;
        }

        newProduct.setPrice(normalizePrice(newProduct.getPrice()));

        return allProducts.add(newProduct);
    }

    /**
     * Finds the part corresponding to the ID supplied and returns the Part object.  If a search is
     * performed on an empty inventory an IO exception is thrown.  Surround with a try/catch block.
     * @param partId integer ID of part to look up.
     * @return Part object matching the ID parameter.
     */
    public Part lookupPart(int partId) throws IOException {
        /*
        * Trying an exception throwing method here, would require calls to this method to be
        * surrounded with a try/catch block.
         */
        if(allParts.size() == 0) {
            throw new IOException("Inventory array contains no object with the given ID!");
        }

        Part selectedPart = null;
        Part indexPart = null;

        for (Part allPart : allParts) {
            indexPart = allPart;
            if (indexPart.getId() == partId) {
                selectedPart = indexPart;
                break;
            }
        }

        if(selectedPart == null) {
            throw new IOException("Inventory array contains no object with the given ID!");
        }

        return selectedPart;
    }

    /**
     * Finds the product corresponding to the ID supplied and returns the Product object.
     * @param productId Integer ID of the Product to be looked up.
     * @return Product object matching the ID parameter.
     */
    public Product lookupProduct(int productId) throws IOException {
        /* Threw the exception because I had allParts in the if statement instead of allProducts.
        *  My test was creating products but not parts so the size of allParts was indeed 0, but
        *  that's not what I needed to check here.
         */
        if(allProducts.size() == 0) {
            throw new IOException("Inventory array contains no object with the given ID!");
        }

        Product selectedProduct = null;
        Product indexProduct = null;

        for (Product allProduct : allProducts) {
            indexProduct = allProduct;
            if (indexProduct.getId() == productId) {
                selectedProduct = indexProduct;
                break;
            }
        }

        if(selectedProduct == null) {
            throw new IOException("Inventory array contains no object with the given ID!");
        }

        return selectedProduct;
    }

    /**
     * Searches the inventory for the given part name and returns a list of matching parts.
     * @param partName String value of the part name in the search.
     * @return ObservableList of Part objects matching the string.
     */
    public ObservableList<Part> lookupPart(String partName) {

        partName = partName.toLowerCase();

        ObservableList<Part> returnList = FXCollections.observableArrayList();

        for (Part p : allParts) {

            if (p.getName().toLowerCase().contains(partName)) {
                returnList.add(p);
            }
        }

        return returnList;
    }

    /**
     * Searches the inventory for the given product name and returns a list of matching products.
     * @param productName String value of the product name in the search.
     * @return ObservableList of Product objects matching the string parameter.
     */
    public ObservableList<Product> lookupProduct(String productName) {

        productName = productName.toLowerCase();

        ObservableList<Product> returnList = FXCollections.observableArrayList();

        for (Product p : allProducts) {
            if (p.getName().toLowerCase().contains(productName)) {
                returnList.add(p);
            }
        }

        return returnList;
    }

    /**
     * Takes the part index and updated part object and replaces the old part object in the inventory.
     * @param index Integer index value corresponding to the Part object in the list to be updated.
     * @param selectedPart Part object with which values to update the Part corresponding to the index parameter.
     */
    public void updatePart(int index, Part selectedPart) {
        // Check for an empty list, also check for an index within bounds.
        if(allParts.size() < index + 1) {
            return;
        }
        // Check for the actual existence of the part to be updated.
        try {
            lookupPart(selectedPart.getId());
        }
        catch (IOException e) {
            return;
        }

        selectedPart.setPrice(normalizePrice(selectedPart.getPrice()));

        allParts.set(index, selectedPart);
    }

    /**
     * Takes the product index and updated product object and replaces the old product object in the inventory.
     * @param index Integer value corresponding to the Product object in the list to be updated.
     * @param selectedProduct Product object with which values to update the Product object corresponding to the index
     *                   parameter.
     */
    public void updateProduct(int index, Product selectedProduct) {
        // Check for an empty list, also check for an index within bounds
        if(allProducts.size() < index + 1) {
            return;
        }
        // Check for the actual existence of the product to be updated.
        try {
            lookupProduct(selectedProduct.getId());
        }
        catch (IOException e) {
            return;
        }

        selectedProduct.setPrice(normalizePrice(selectedProduct.getPrice()));

        allProducts.set(index, selectedProduct);
    }

    /**
     * Removes the selected part from the inventory.
     * @param selectedPart Part object representing the object to be removed from the list.
     * @return True when the delete is successful, false when the object already is not there.
     */
    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Removes the selected product from the inventory.
     * @param selectedProduct Product object representing the object to be deleted from the list.
     * @return True if the delete is successful, false if the object is already not there.
     */
    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Returns a list of all parts in inventory.
     * @return ObservableList of all parts (or lack thereof) in the list.
     */
    public ObservableList<Part> getAllParts() /*throws Exception*/ {
        return allParts;
    }

    /**
     * Returns a list of all products in inventory.
     * @return ObservableList of all Products (or lack thereof) in the list.
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Method which returns a valid part ID number for the inventory.  Valid IDs are unique integer values.
     * @return Integer part ID number assignment.
     */
    public int getPartIdNum() {
        if(partNums.size() == 0) {
            partNums.add(1);
            return 1;
        }
        int idNum = partNums.get(partNums.size() - 1) + 1;

        partNums.add(idNum);

        return idNum;
    }

    public int getProdIdNum() {
        if(prodNums.size() == 0) {
            prodNums.add(1);
            return 1;
        }
        int idNum = prodNums.get(prodNums.size() - 1) + 1;

        prodNums.add(idNum);

        return idNum;
    }

    public void sort() {
        allParts.sort(Comparator.comparingInt(Part::getId));
        allProducts.sort(Comparator.comparingInt(Product::getId));
    }

    private double normalizePrice(double price) {
        return Math.round(price *100.0) / 100.0;
    }
}

