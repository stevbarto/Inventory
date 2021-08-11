/**
 * Inventory controller class
 * @author Steven Barton
 */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryController {

    Inventory inv;

    @FXML // fx:id="mainLabel"
    private Label mainLabel; // Value injected by FXMLLoader

    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader

    @FXML // fx:id="partsPane"
    private Pane partsPane; // Value injected by FXMLLoader

    @FXML // fx:id="partsPaneLabel"
    private Label partsPaneLabel; // Value injected by FXMLLoader

    @FXML // fx:id="partsSearchField"
    private TextField partsSearchField; // Value injected by FXMLLoader

    @FXML // fx:id="partsTable"
    private TableView<Part> partsTable; // Value injected by FXMLLoader

    @FXML // fx:id="partIdColumn"
    private TableColumn<Part, Integer> partIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="partNameColumn"
    private TableColumn<Part, String> partNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="partInventoryColumn"
    private TableColumn<Part, Integer> partInventoryColumn; // Value injected by FXMLLoader

    @FXML // fx:id="partPriceColumn"
    private TableColumn<Part, Double> partPriceColumn; // Value injected by FXMLLoader

    @FXML // fx:id="addPartsButton"
    private Button addPartsButton; // Value injected by FXMLLoader

    @FXML // fx:id="modifyPartsButton"
    private Button modifyPartsButton; // Value injected by FXMLLoader

    @FXML // fx:id="deletePartsButton"
    private Button deletePartsButton; // Value injected by FXMLLoader

    @FXML // fx:id="productsPane"
    private Pane productsPane; // Value injected by FXMLLoader

    @FXML // fx:id="productsPaneLabel"
    private Label productsPaneLabel; // Value injected by FXMLLoader

    @FXML // fx:id="productsSearchField"
    private TextField productsSearchField; // Value injected by FXMLLoader

    @FXML // fx:id="productsTable"
    private TableView<Product> productsTable; // Value injected by FXMLLoader

    @FXML // fx:id="productIdColumn"
    private TableColumn<Product, Integer> productIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="productNameColumn"
    private TableColumn<Product, String> productNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="productInventoryColumn"
    private TableColumn<Product, Integer> productInventoryColumn; // Value injected by FXMLLoader

    @FXML // fx:id="productPriceColumn"
    private TableColumn<Product, Double> productPriceColumn; // Value injected by FXMLLoader

    @FXML // fx:id="addProductsButton"
    private Button addProductsButton; // Value injected by FXMLLoader

    @FXML // fx:id="modifyProductsButton"
    private Button modifyProductsButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteProductsButton"
    private Button deleteProductsButton; // Value injected by FXMLLoader

    /**
     * Initialize method...
     *
     */
    public void initialize() {
        inv = new Inventory();

        // Set up the columns for the partTable tableView.
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        // Set up the columns for the productTable tableView.
        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        // load data
        load();
    }

    public void exitButtonListener() {
        Platform.exit();
    }

    /**
     * Listener for the parts search bar allowing searches
     */
    public void partSearchBarListener() {
        partsSearchField.setOnKeyReleased(event -> {

            String input = partsSearchField.getText();
            boolean isInt = NumberChecker.isInteger(input);

            if(input.length() >= 1 && isInt) {
                // Perform a search of parts based on part ID
                ObservableList<Part> result = FXCollections.observableArrayList();

                try {
                    int idSearch = Integer.parseInt(input);
                    result.add(inv.lookupPart(idSearch));
                } catch (IOException e) {
                    //e.printStackTrace();
                }

                if(result.size() > 0) {
                    partsTable.getSelectionModel().select(result.get(0));
                }
                // partsTable.setItems(result);
            }
            else {
                // Perform a search of parts based on a String
                partsTable.setItems(inv.lookupPart(input));
            }
        });
    }

    /**
     * Listener for the products search bar allowing searches
     */
    public void productSearchBarListener() {
        productsSearchField.setOnKeyReleased(event -> {

            String input = productsSearchField.getText();
            boolean isInt = NumberChecker.isInteger(input);

            if(input.length() >= 1 && isInt) {
                // Perform a search of products based on product ID
                ObservableList<Product> result = FXCollections.observableArrayList();

                try {
                    int idSearch = Integer.parseInt(input);
                    result.add(inv.lookupProduct(idSearch));
                } catch (IOException e) {
                    //e.printStackTrace();
                }

                if(result.size() > 0) {
                    productsTable.getSelectionModel().select(result.get(0));
                }

                // productsTable.setItems(result);
            }
            else {
                // Perform a search of products based on a String
                productsTable.setItems(inv.lookupProduct(input));
            }
        });
    }

    /**
     *
     */
    public void partsAddButtonListener() {
        addPartsButton.setOnMouseClicked(event -> {

            Stage addPart = new Stage();

            Parent parent = null;

            FXMLLoader loader = null;

            try {
                loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
                //parent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
                parent = loader.load();
            }
            catch (Exception e) {
                //
            }

            Scene scene = new Scene(parent);

            AddPartController addPartController = loader.getController();

            // Now access methods for addPartController to pass data.
            addPartController.getInventoryController(this);

            addPart.setTitle("Add Part");

            addPart.setScene(scene);

            //addPart.initModality(Modality.WINDOW_MODAL);
            //addPart.initOwner(inv.getStage());

            addPart.show();

        });
    }

    /**
     *
     */
    public void partsUpdateButtonListener () {
       modifyPartsButton.setOnMouseClicked(event -> {
           modifyPartsButton.setStyle("-fx-text-inner-color: black;");

           if(partsTable.getSelectionModel().getSelectedItem() == null) {
               modifyPartsButton.setStyle("-fx-text-inner-color: red;");
           }
           else {
               modifyPartsButton.setStyle("-fx-text-inner-color: black;");

               Part getPart = partsTable.getSelectionModel().getSelectedItem();

               Stage stage = new Stage();

               FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ModifyPart.fxml"));

               ModifyPartController controller = new ModifyPartController();

               controller.setPart(getPart);

               controller.setInventoryController(this);

               loader.setController(controller);

               Parent parent = null;

               try {
                   parent = loader.load();
               } catch (IOException e) {
                   //
               }

               Scene scene = new Scene(parent);

               stage.setTitle("Modify Part");

               stage.setScene(scene);

               stage.show();
           }
       });
    }

    /**
     *
     */
    public void partsDeleteButtonListener() {
        deletePartsButton.setOnMouseClicked(event -> {

            if(partsTable.getSelectionModel().getSelectedItem() == null) {

            }
            else {
                inv.deletePart(partsTable.getSelectionModel().getSelectedItem());

                partsTable.setItems(inv.getAllParts());
            }
        });
    }

    /**
     *
     */
    public void prodAddButtonListener () {
        addProductsButton.setOnMouseClicked(event -> {

            Stage addProd = new Stage();

            Parent parent = null;

            FXMLLoader loader = null;

            try {
                loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
                //parent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
                parent = loader.load();
            }
            catch (Exception e) {
                //
            }

            AddProductController addProdController = loader.getController();

            addProdController.getInventoryController(this);

            addProdController.setItems(inv);

            Scene scene = new Scene(parent);

            addProd.setTitle("Add Product");

            addProd.setScene(scene);

            addProd.show();

        });
    }

    /**
     *
     */
    public void prodUpdateButtonListener () {
        modifyProductsButton.setOnMouseClicked(event -> {

            modifyProductsButton.setStyle("-fx-text-inner-color: black;");

            if(productsTable.getSelectionModel().getSelectedItem() == null) {
                modifyProductsButton.setStyle("-fx-text-inner-color: red;");
            }
            else {
                modifyProductsButton.setStyle("-fx-text-inner-color: black;");

                Product getProduct = productsTable.getSelectionModel().getSelectedItem();

                Stage modProd = new Stage();

                Parent parent = null;

                FXMLLoader loader = null;

                try {
                    loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
                    //parent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
                    parent = loader.load();
                } catch (Exception e) {
                    //
                }

                ModifyProductController modProdController = loader.getController();

                modProdController.getInventoryController(this);

                modProdController.setProduct(getProduct);

                modProdController.setItems(inv);

                Scene scene = new Scene(parent);

                modProd.setTitle("Modify Product");

                modProd.setScene(scene);

                modProd.show();
            }
        });
    }

    /**
     *
     */
    public void prodDeleteButtonListener () {
        deleteProductsButton.setOnMouseClicked(event -> {

            if(productsTable.getSelectionModel().getSelectedItem() == null) {

            }
            else {
                inv.deleteProduct(productsTable.getSelectionModel().getSelectedItem());

                productsTable.setItems(inv.getAllProducts());
            }
        });
    }

    /**
     * Method to retrieve an instance of the base inventory,
     * @return The base inventory object.
     */
    public Inventory getInventory() {
        return this.inv;
    }

    public void load() {
        inv.sort();
        partsTable.setItems(inv.getAllParts());
        partsTable.refresh();
        productsTable.setItems(inv.getAllProducts());
        productsTable.refresh();
    }
}

