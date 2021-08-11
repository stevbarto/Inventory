/**
 * Modify Product controller class
 * @author Steven Barton
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyProductController {

    InventoryController invCtrl;

    Inventory inv;

    Product product;

    Product archiveProduct;

    @FXML
    private Label modProductButton;

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
    private TextField productMinFIeld;

    @FXML
    private TextField partsSearchField;

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
    private TableColumn<Part, Double> partPriceColumn1;

    public void initialize() {
        productNameField.setOnAction(e -> productNameFieldListener());

        productInventoryField.setOnAction(e -> productInventoryFieldListener());

        productPriceField.setOnAction(e -> productPriceFieldListener());

        productMaxField.setOnAction(e -> productMaxFieldListener());

        productMinFIeld.setOnAction(e -> productMinFieldListener());

        partsSearchField.setOnAction(e -> partSearchBarListener());

        addAssociatedPartButton.setOnAction(e -> addAssociatedPartButtonListener());

        removeAssociatedPartButton.setOnAction(e -> removeAssociatedPartButtonListener());

        saveProductButton.setOnAction(e -> saveButtonListener());

        cancelProductButton.setOnAction(e -> cancelButtonListener());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partIdColumn1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn1.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

    }

    public void getInventoryController(InventoryController inventoryController) {
        this.invCtrl = inventoryController;
    }

    public void setItems(Inventory inv) {
        partsTable.setItems(inv.getAllParts());
    }

    public void setInventory(Inventory items) {
        inv = items;
    }

    public void setProduct(Product p) {
        this.product = p;

        productIdField.setText(Integer.toString(product.getId()));

        productNameField.setText(product.getName());

        productInventoryField.setText(Integer.toString(product.getStock()));

        productPriceField.setText(Double.toString(product.getPrice()));

        productMinFIeld.setText(Integer.toString(product.getMin()));

        productMaxField.setText(Integer.toString(product.getMax()));
    }

    public void productNameFieldListener() {
        productNameField.setOnKeyReleased(event -> {
            this.product.setName(productNameField.getText());
        });
    }

    public void productInventoryFieldListener() {
        productInventoryField.setOnKeyReleased(event -> {

            productInventoryField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(productInventoryField.getText())) {
                try {
                    this.product.setStock(Integer.parseInt(productInventoryField.getText()));
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
                    this.product.setPrice(Double.parseDouble(productPriceField.getText()));
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

    public void productMinFieldListener() {
        productMinFIeld.setOnKeyReleased(event -> {

            productMinFIeld.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(productMinFIeld.getText())) {
                this.product.setMin(Integer.parseInt(productMinFIeld.getText()));
            }
            else {
                productMinFIeld.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void productMaxFieldListener() {
        productMaxField.setOnKeyReleased(event -> {

            productMaxField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(productMaxField.getText())) {
                this.product.setMax(Integer.parseInt(productMaxField.getText()));
            }
            else {
                productMaxField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void partSearchBarListener() {
        partsSearchField.setOnKeyReleased(event -> {

            String input = partsSearchField.getText();
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

    public void addAssociatedPartButtonListener() {
        addAssociatedPartButton.setOnMouseClicked(event -> {

            if(partsTable.getSelectionModel().getSelectedItem() == null) {

            }
            else {
                try {
                    product.addAssociatedPart(partsTable.getSelectionModel().getSelectedItem());
                } catch (Exception e) {
                    //
                }

                associatedPartsTable.setItems(product.getAllAssociatedParts());
            }
        });
    }

    public void removeAssociatedPartButtonListener() {
        removeAssociatedPartButton.setOnMouseClicked(event -> {

            if(associatedPartsTable.getSelectionModel().getSelectedItem() == null) {

            }
            else {
                product.deleteAssociatedPart(associatedPartsTable.getSelectionModel().getSelectedItem());

                associatedPartsTable.setItems(product.getAllAssociatedParts());
            }
        });
    }

    public void saveButtonListener() {
        saveProductButton.setOnMouseClicked(event -> {

            archiveProduct = product;

            boolean save = true;

            Stage stage = (Stage) saveProductButton.getScene().getWindow();

            Inventory inv = invCtrl.getInventory();

            // If all the fields aren't saved, need to stop the save...
            if(productNameField.getText().equals("") || productInventoryField.getText().equals("") ||
                    productPriceField.getText().equals("") || productMinFIeld.getText().equals("") ||
                    productMaxField.getText().equals("")) {
                save = false;
            }
            else if(!NumberChecker.isInteger(productInventoryField.getText()) || !NumberChecker.isDouble(productPriceField.getText()) ||
                    !NumberChecker.isInteger(productMinFIeld.getText()) || !NumberChecker.isInteger(productMaxField.getText())) {
                save = false;
            }
            else {
                save = true;
            }

            if(save) {
                inv.deleteProduct(archiveProduct);

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

