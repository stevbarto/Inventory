/**
 * Add Part controller class
 * @author Steven Barton
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartController {

    Part part;
    int id;
    String name;
    int stock;
    double price;
    int min;
    int max;
    int machineId;
    String companyName;

    boolean inHouse;

    InventoryController invCtrl;


    @FXML
    private Label addPartLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField minField;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label stockLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label minLabel;

    @FXML
    private Label maxLabel;

    @FXML
    private TextField maxField;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private ToggleGroup partType;

    @FXML
    private RadioButton outSourcedRadioButton;

    @FXML
    private TextField variableField;

    @FXML
    private Label variableLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    public void initialize() {
        inHouse = true;

        id = -1;
        name = "-1";
        stock = -1;
        price = -1.00;
        min = -1;
        max = -1;
        machineId = -1;
        companyName = "-1";


    }

    public void inHouseRadioButtonListener() {

        inHouseRadioButton.setOnMouseClicked(event -> {
            variableLabel.setText("Machine ID");
            inHouse = true;
        });
    }

    public void outSourcedRadioButtonListener() {

        outSourcedRadioButton.setOnMouseClicked(event -> {
            variableLabel.setText("Company Name");
            inHouse = false;
        });
    }

    public void saveButtonListener() {
        saveButton.setOnMouseClicked(event -> {

            boolean save = true;

            Stage stage = (Stage) saveButton.getScene().getWindow();

            Inventory inv = invCtrl.getInventory();

            // If all the fields aren't saved, need to stop the save...
            if(nameField.getText().equals("") || stockField.getText().equals("") ||
            priceField.getText().equals("") || minField.getText().equals("") ||
            maxField.getText().equals("") || variableField.getText().equals("")) {
                save = false;
            }

            if(save) {
                // TODO: The part id method is returning identical IDs for some reason...
                int partId = inv.getPartIdNum();

                // Need to save all variables to a new part object...
                if (inHouse) {
                    part = new InHouse(partId, name, price, stock, min, max, machineId);
                } else {
                    part = new Outsourced(partId, name, price, stock, min, max, companyName);
                }

                // Need to add the part object to the inventory from this controller...
                inv.addPart(part);

                invCtrl.load();

                stage.close();
            }
        });
    }

    public void cancelButtonListener() {
        cancelButton.setOnMouseClicked(event -> {

            Stage stage = (Stage) cancelButton.getScene().getWindow();

            stage.close();

        });
    }

    public void nameFieldListener() {
        nameField.setOnKeyReleased(event -> {
            this.name = nameField.getText();
        });
    }

    public void stockFieldListener() {
        stockField.setOnKeyReleased(event -> {

            stockField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(stockField.getText())) {
                try {
                    this.stock = Integer.parseInt(stockField.getText());
                }
                catch (NumberFormatException e) {
                    //
                }
            }
            else {
                stockField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void priceFieldListener() {
        priceField.setOnKeyReleased(event -> {

            priceField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isDouble(priceField.getText())) {
                try {
                    this.price = Double.parseDouble(priceField.getText());
                }
                catch (NumberFormatException e) {
                    // none needed here.
                }
            }
            else {
                priceField.setStyle("-fx-text-inner-color: red;");
            }

        });
    }

    public void minFieldListener() {
        minField.setOnKeyReleased(event -> {

            minField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(minField.getText())) {
                this.min = Integer.parseInt(minField.getText());
            }
            else {
                minField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void maxFieldListener() {
        maxField.setOnKeyReleased(event -> {

            maxField.setStyle("-fx-text-inner-color: black;");

            if(NumberChecker.isInteger(maxField.getText())) {
                this.max = Integer.parseInt(maxField.getText());
            }
            else {
                maxField.setStyle("-fx-text-inner-color: red;");
            }
        });
    }

    public void variableFieldListener() {
        variableField.setOnKeyReleased(event -> {
            if(inHouse) {
                maxField.setStyle("-fx-text-inner-color: black;");

                String input = variableField.getText();

                if(NumberChecker.isInteger(input)) {
                    machineId = Integer.parseInt(variableField.getText());
                }
                else {
                    variableField.setStyle("-fx-text-inner-color: red;");
                }
            }
            else {
                companyName = variableField.getText();
            }
        });
    }

    /**
     * Public method to get the parent InventoryController class.
     * @param invCtrl InventoryController instance passed from the parent controller.
     */
    public void getInventoryController(InventoryController invCtrl) {
        this.invCtrl = invCtrl;
    }
}
