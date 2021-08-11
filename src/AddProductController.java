/**
 * Add Product controller class
 * @author Steven Barton
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductController {

    InventoryController invCtrl;

    Inventory inventory;

    ObservableList<Part> associatedParts;

    Product product;

    String name;
    int stock;
    double price;
    int min;
    int max;

    @FXML
    private Label addProductLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label inventoryLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label maxLabel;

    @FXML
    private Label minLabel;

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productInventoryField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productMaxField;

    @FXML
    private TextField productMinField;

    @FXML
    private TextField partSearchBar;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private Button addAssociatedPartButton;

    @FXML
    private Button removeAssociatedPartButton;

    @FXML
    private Button saveProductButton;

    @FXML
    private Button cancelProductButton;

    @FXML
    private TableView<Part> associatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIdColumn1;

    @FXML
    private TableColumn<Part, String> partNameColumn1;

    @FXML
    private TableColumn<Part, Integer> partInventoryColumn1;

    @FXML
    private TableColumn<Part, Double> partCostColumn1;

    public void initialize() {

        productNameField.setOnAction(e -> productNameFieldListener());

        productInventoryField.setOnAction(e -> productStockFieldListener());

        productPriceField.setOnAction(e -> productPriceFieldListener());

        productMaxField.setOnAction(e -> productMaxFieldListener());

        productMinField.setOnAction(e -> productMinFieldListener());

        partSearchBar.setOnAction(e -> partSearchBarListener());

        addAssociatedPartButton.setOnAction(e -> partAddButtonListener());

        removeAssociatedPartButton.setOnAction(e -> partRemoveButtonListener());

        saveProductButton.setOnAction(e -> saveButtonListener());

        cancelProductButton.setOnAction(e -> cancelButtonListener());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partIdColumn1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partCostColumn1.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        name = "-1";
        stock = -1;
        price = 1.00;
        min = -1;
        max = -1;
        associatedParts = FXCollections.observableArrayList();


    }

    public void getInventoryController(InventoryController inventoryController) {
        this.invCtrl = inventoryController;
    }

    public void setItems(Inventory inv) {
        partsTable.setItems(inv.getAllParts());
    }

    public void setInventory(Inventory items) {
        inventory = items;
    }

    public void productNameFieldListener() {
        productNameField.setOnKeyReleased(event -> {
            this.name = productNameField.getText();
        });
    }

    public void productStockFieldListener() {
        productInventoryField.setOnKeyReleased(event -> {

            productInventoryField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(productInventoryField.getText())) {
                try {
                    this.stock = Integer.parseInt(productInventoryField.getText());
                }
                catch (NumberFormatException e) {
                    //
                }
            }
            else {
                productInventoryField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void productPriceFieldListener() {
        productPriceField.setOnKeyReleased(event -> {

            productPriceField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isDouble(productPriceField.getText())) {
                try {
                    this.price = Double.parseDouble(productPriceField.getText());
                }
                catch (NumberFormatException e) {
                    // none needed here.
                }
            }
            else {
                productPriceField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void productMaxFieldListener() {
        productMaxField.setOnKeyReleased(event -> {

            productMaxField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(productMaxField.getText())) {
                this.max = Integer.parseInt(productMaxField.getText());
            }
            else {
                productMaxField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void productMinFieldListener() {
        productMinField.setOnKeyReleased(event -> {

            productMinField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(productMinField.getText())) {
                this.min = Integer.parseInt(productMinField.getText());
            }
            else {
                productMinField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void partSearchBarListener() {
        partSearchBar.setOnKeyReleased(event -> {

            String input = partSearchBar.getText();
            boolean isInt = NumberChecker.isInteger(input);

            if(input.length() >= 1 && isInt) {
                // Perform a search of parts based on part ID
                ObservableList<Part> result = FXCollections.observableArrayList();

                try {
                    int idSearch = Integer.parseInt(input);
                    result.add(invCtrl.getInventory().lookupPart(idSearch));
                } catch (IOException e) {
                    //e.printStackTrace();
                }

                partsTable.getSelectionModel().select(result.get(0));

                // partsTable.setItems(result);
            }
            else {
                // Perform a search of parts based on a String
                partsTable.setItems(invCtrl.getInventory().lookupPart(input));
            }
        });
    }

    public void partAddButtonListener() {
        addAssociatedPartButton.setOnMouseClicked(event -> {

            if(partsTable.getSelectionModel().getSelectedItem() == null) {

            }
            else {
                associatedParts.add(partsTable.getSelectionModel().getSelectedItem());

                associatedPartsTable.setItems(associatedParts);
            }
        });
    }

    public void partRemoveButtonListener() {
        removeAssociatedPartButton.setOnMouseClicked(event -> {

            if(associatedPartsTable.getSelectionModel().getSelectedItem() == null) {

            }
            else {
                associatedParts.remove(associatedPartsTable.getSelectionModel().getSelectedItem());

                associatedPartsTable.setItems(associatedParts);
            }
        });
    }

    public void saveButtonListener() {
        saveProductButton.setOnMouseClicked(event -> {


            boolean save = true;

            Stage stage = (Stage) saveProductButton.getScene().getWindow();

            Inventory inv = invCtrl.getInventory();

            // If all the fields aren't saved, need to stop the save...
            if(productNameField.getText().equals("") || productInventoryField.getText().equals("") ||
                    productPriceField.getText().equals("") || productMinField.getText().equals("") ||
                    productMaxField.getText().equals("")) {
                save = false;
            }

            if(save) {
                // TODO: The part id method is returning identical IDs for some reason...same here?
                int productId = inv.getProdIdNum();

                // Need to save all variables to a new part object...
                product = new Product(productId, name, price, stock, min, max);

                // Need to add the part object to the inventory from this controller...
                inv.addProduct(product);

                invCtrl.load();

                stage.close();
            }
        });
    }

    public void cancelButtonListener() {
        cancelProductButton.setOnMouseClicked(event -> {

            Stage stage = (Stage) cancelProductButton.getScene().getWindow();

            stage.close();

        });
    }

}

