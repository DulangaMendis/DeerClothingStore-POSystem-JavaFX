package controller.item;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Item;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    public TextField txtsearch;
    public TableView<Item> tableitem;

    @FXML
    private TableColumn<?, ?> colitemcode;

    @FXML
    private TableColumn<?, ?> coltype;

    @FXML
    private TableColumn<?, ?> colunitprice;

    @FXML
    private TableColumn<?, ?> colpacksize;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private TableColumn<?, ?> coldescription;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private JFXTextField txtitemcode;

    @FXML
    private ComboBox<String> cmbtype;

    @FXML
    private JFXTextField txtunitprice;

    @FXML
    private JFXTextField txtpacksize;

    @FXML
    private JFXTextField txtqty;

    @FXML
    private JFXTextArea txtareadescription;

    @FXML
    private JFXDatePicker datepickerdate;

    ItemService service = ItemController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colitemcode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colpacksize.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("Date"));

        ObservableList<String> types = FXCollections.observableArrayList("Kids", "Ladies", "Gents");
        cmbtype.setItems(types);

        loadItemsToTable();

        // Add listener to TableView selection
        tableitem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setTextToValues(newValue);
        });
    }


    private void loadItemsToTable() {
        ObservableList<Item> itemObservableList = service.getAll();
        tableitem.setItems(itemObservableList);
    }


    @FXML
    public void btnaddonaction(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        Item item = new Item(
                txtitemcode.getText(),
                cmbtype.getValue(),
                txtareadescription.getText(),
                txtpacksize.getText(),
                Double.parseDouble(txtunitprice.getText()),
                Integer.parseInt(txtqty.getText()),
                datepickerdate.getValue()
        );

        if (service.addItem(item)) {
            loadItemsToTable();
            new Alert(Alert.AlertType.INFORMATION, "Item Added!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item Not Added!").show();
        }

        clearFields();
    }


    @FXML
    public void btnupdateonaction(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        Item selectedItem = tableitem.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setId(txtitemcode.getText());
            selectedItem.setType(cmbtype.getValue());
            selectedItem.setUnitPrice(Double.parseDouble(txtunitprice.getText()));
            selectedItem.setPackSize(txtpacksize.getText());
            selectedItem.setQty(Integer.parseInt(txtqty.getText()));
            selectedItem.setDescription(txtareadescription.getText());
            selectedItem.setDate(datepickerdate.getValue());

            if (service.updateItem(selectedItem)) {
                loadItemsToTable();
                new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item Not Updated!").show();
            }

            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "No item selected to update!").show();
        }
    }


    @FXML
    public void btndeleteonaction(ActionEvent event) {
        Item selectedItem = tableitem.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (service.deleteItem(selectedItem)) {
                loadItemsToTable();
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item Not Deleted!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "No item selected to delete!").show();
        }
        clearFields();
    }


    @FXML
    public void btnsearchonaction(ActionEvent event) {
        String searchCode = txtsearch.getText().trim();
        Item item = service.searchItem(searchCode);

        if (item != null) {
            setTextToValues(item);
            new Alert(Alert.AlertType.INFORMATION, "Item Found!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item not found!").show();
        }
    }


    private boolean validateInputs() {
        if (txtitemcode.getText().isEmpty() ||
                cmbtype.getValue() == null ||
                txtunitprice.getText().isEmpty() ||
                txtpacksize.getText().isEmpty() ||
                txtqty.getText().isEmpty() ||
                txtareadescription.getText().isEmpty() ||
                datepickerdate.getValue() == null) {

            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
            return false;
        }

        try {
            Double.parseDouble(txtunitprice.getText());
            Integer.parseInt(txtqty.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Price and Quantity must be valid numbers!").show();
            return false;
        }

        return true;
    }


    private void clearFields() {
        txtitemcode.clear();
        cmbtype.setValue(null);
        txtunitprice.clear();
        txtpacksize.clear();
        txtqty.clear();
        txtareadescription.clear();
        datepickerdate.setValue(null);
    }


    private void setTextToValues(Item item) {
        if (item != null) {
            txtitemcode.setText(item.getId());
            cmbtype.setValue(item.getType());
            txtunitprice.setText(String.valueOf(item.getUnitPrice()));
            txtpacksize.setText(item.getPackSize());
            txtqty.setText(String.valueOf(item.getQty()));
            txtareadescription.setText(item.getDescription());
            datepickerdate.setValue(item.getDate());
        }
    }


    @FXML
    public void btnreloadonaction(ActionEvent event) {
        loadItemsToTable();
    }


    @FXML
    public void btnclearonaction(ActionEvent event) {
        clearFields();
    }


    @FXML
    public void btndashboardonaction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dash_board.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void btnsignoutonaction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dash.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
