/**
 * Modify Part controller class
 * @author Steven Barton
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ModifyPartController {

    String name;
    int stock;
    double price;
    int max;
    int min;
    String companyName;
    int machineId;

    InventoryController invCtrl;

    Part part;
    Part archivePart;

    boolean save;

    boolean isInHouse;

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

        save = true;

        nameField.setOnAction(e -> nameFieldListener());

        stockField.setOnAction(e -> stockFieldListener());

        priceField.setOnAction(e -> priceFieldListener());

        minField.setOnAction(e -> minFieldListener());

        maxField.setOnAction(e -> maxFieldListener());

        variableField.setOnAction(e -> variableFieldListener());

        saveButton.setOnAction(e -> saveButtonListener());

        cancelButton.setOnAction(e -> cancelButtonListener());

        inHouseRadioButton.setOnAction(e -> inHouseRadioButtonListener());

        outSourcedRadioButton.setOnAction(e -> outSourcedRadioButtonListener());

        archivePart = part;

        if (part instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            InHouse inHouse = (InHouse) part;
            isInHouse = true;
            variableField.setText(inHouse.getMachineId() + "");
        } else {
            outSourcedRadioButton.setSelected(true);
            Outsourced outsourced = (Outsourced) part;
            isInHouse = false;
            variableLabel.setText("Company Name");
            variableField.setText(outsourced.getCompanyName());
        }

        idTextField.setText(Integer.toString(part.getId()));

        nameField.setText(part.getName());

        stockField.setText(Integer.toString(part.getStock()));

        priceField.setText(Double.toString(part.getPrice()));

        minField.setText(Integer.toString(part.getMin()));

        maxField.setText(Integer.toString(part.getMax()));

        // Need to get inhouse/outsourced value and handle button selection and text accordingly
    }

    public void setInventoryController(InventoryController c) {
        this.invCtrl = c;
    }

    public void setPart(Part p) {
        this.part = p;
    }

    public void saveButtonListener() {

        Stage stage = (Stage) saveButton.getScene().getWindow();

        Inventory inv = invCtrl.getInventory();

        // If all the fields aren't saved, need to stop the save...
        if (nameField.getText().equals("") || stockField.getText().equals("") ||
                priceField.getText().equals("") || minField.getText().equals("") ||
                maxField.getText().equals("") || variableField.getText().equals("")) {
            // Should have a message display when fields are blank...
            save = false;
        }
        else if(!NumberChecker.isInteger(stockField.getText()) || !NumberChecker.isDouble(priceField.getText()) ||
                !NumberChecker.isInteger(minField.getText()) || !NumberChecker.isInteger(maxField.getText())) {
            save = false;
        }
        else {
            save = true;
        }

        if (save) {
            inv.deletePart(archivePart);

            inv.addPart(part);

            invCtrl.load();

            stage.close();
        }

    }

    public void cancelButtonListener() {

        Stage stage = (Stage) cancelButton.getScene().getWindow();

        stage.close();

    }

    public void inHouseRadioButtonListener() {
        variableLabel.setText("Machine ID");
        isInHouse = true;
    }

    public void outSourcedRadioButtonListener() {
        variableLabel.setText("Company Name");
        isInHouse = false;
    }

    public void nameFieldListener() {
        this.name = nameField.getText();
        part.setName(name);
    }

    public void priceFieldListener() {
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

    }


    public void stockFieldListener() {
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
    }

    public void minFieldListener() {
        minField.setStyle("-fx-text-inner-color: black;");

        if(NumberChecker.isInteger(minField.getText())) {
            this.min = Integer.parseInt(minField.getText());
        }
        else {
            minField.setStyle("-fx-text-inner-color: red;");
        }
    }

    public void maxFieldListener() {
        maxField.setStyle("-fx-text-inner-color: black;");

        if(NumberChecker.isInteger(maxField.getText())) {
            this.max = Integer.parseInt(maxField.getText());
        }
        else {
            maxField.setStyle("-fx-text-inner-color: red;");
        }
    }

    public void variableFieldListener() {
        if(isInHouse) {
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
    }

}
