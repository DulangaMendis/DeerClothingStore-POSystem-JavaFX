package controller.supplier;

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
import model.Supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class SupplierFormController implements Initializable {

    public TextField txtsearch;
    public TableView <Supplier> tablesuuplier;


    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colcompany;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> colitemcode;

    @FXML
    private TableColumn<?, ?> colstatus;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private JFXTextField txtsupplierid;

    @FXML
    private ComboBox<String> cmbtitle;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtcompany;

    @FXML
    private JFXTextField txtcontact;

    @FXML
    private JFXTextField txtitem;

    @FXML
    private JFXTextField txtqty;

    @FXML
    private JFXTextArea txtareastatus;

    @FXML
    private JFXDatePicker datepickerdate;

    SupplierService service = SupplierController.getInstance();


    @FXML
    void btnaddonaction(ActionEvent event) {

        if (!validateInputs()) {
            return;
        }
        Supplier supplier = new Supplier(
                txtsupplierid.getText(),
                cmbtitle.getValue(),
                txtname.getText(),
                txtcompany.getText(),
                txtcontact.getText(),
                txtitem.getText(),
                txtareastatus.getText(),
                Integer.parseInt(txtqty.getText()),
                datepickerdate.getValue()
        );

        if(service.addSupplier(supplier)){
            loadtable();
            new Alert(Alert.AlertType.INFORMATION,"Supplier Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Supplier Not Added :(").show();
        }

    }

    private boolean validateInputs() {
        if (txtsupplierid.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Supplier ID cannot be empty!").show();
            return false;
        }

        // Check if title (ComboBox) is selected
        if (cmbtitle.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a title!").show();
            return false;
        }

        // Check if name is empty
        if (txtname.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty!").show();
            return false;
        }

        // Check if company is empty
        if (txtcompany.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Company cannot be empty!").show();
            return false;
        }

        // Check if contact is a valid number
        if (!txtcontact.getText().matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Contact must be a valid number!").show();
            return false;
        }

        // Check if item code is empty
        if (txtitem.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Item code cannot be empty!").show();
            return false;
        }

        // Check if quantity is a valid number
        try {
            int qty = Integer.parseInt(txtqty.getText());
            if (qty < 0) {
                new Alert(Alert.AlertType.ERROR, "Quantity cannot be negative!").show();
                return false;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Quantity must be a valid number!").show();
            return false;
        }

        // Check if status is empty
        if (txtareastatus.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Status cannot be empty!").show();
            return false;
        }

        // Check if date is selected
        if (datepickerdate.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a date!").show();
            return false;
        }

        return true;
    }

    @FXML
    void btndashboardonaction(ActionEvent event) {
    }

    @FXML
    void btnreloadonaction(ActionEvent event) {
        loadtable();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colcompany.setCellValueFactory(new PropertyValueFactory<>("Company"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colitemcode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("QTY"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Mrs");
        cmbtitle.setItems(titles);
        loadtable();
        tablesuuplier.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));


    }

    private void loadtable() {
        ObservableList<Supplier> supplierObservableList = service.getAll();
        tablesuuplier.setItems(supplierObservableList);
    }

    private void setTextToValues(Supplier newValue) {
        txtsupplierid.setText(newValue.getID());
        cmbtitle.setValue(newValue.getTitle());
        txtname.setText(newValue.getName());
        txtcompany.setText(newValue.getCompany());
        txtcontact.setText(newValue.getContact());
        txtitem.setText(newValue.getItemCode());
        txtareastatus.setText(newValue.getStatus());
        txtqty.setText(String.valueOf(newValue.getQTY()));
        datepickerdate.setValue(newValue.getDate());

    }

//search suppliers----
    public void btnsearchonaction(ActionEvent actionEvent) {
        String id = txtsearch.getText(); // Assuming the user enters the supplier ID in the same field
        Supplier supplier = service.searchSupplier(id);
        if (supplier != null) {
            setTextToValues(supplier); // Populate the fields with the found supplier data
            new Alert(Alert.AlertType.INFORMATION, "Supplier Found!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier not found!").show();
        }
    }

    //update suppliers details----------

    public void btnupdateonaction(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }

        Supplier supplier = new Supplier(
                txtsupplierid.getText(),
                cmbtitle.getValue(),
                txtname.getText(),
                txtcompany.getText(),
                txtcontact.getText(),
                txtitem.getText(),
                txtareastatus.getText(),
                Integer.parseInt(txtqty.getText()),
                datepickerdate.getValue()
        );

        if(service.updateSupplier(supplier)){
            loadtable();
            new Alert(Alert.AlertType.INFORMATION, "Supplier Updated!").show();
            clearFields();

        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier Not Updated!").show();
        }

    }

    //delete suppliers---------
    public void btndeleteonaction(ActionEvent actionEvent) {
        String id = txtsupplierid.getText(); // Assuming the user enters the supplier ID in the same field
        Supplier supplier = service.searchSupplier(id); // Get the supplier to delete
        if (supplier != null) {
            if (service.deleteSupplier(supplier)) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted!").show();
                loadtable();// Refresh the table
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Deleted!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier not found!").show();
        }
    }

    //clear fields----------------
    private void clearFields() {
        txtsupplierid.clear();
        cmbtitle.setValue(null);
        txtname.clear();
        txtcompany.clear();
        txtcontact.clear();
        txtitem.clear();
        txtareastatus.clear();
        txtqty.clear();
        datepickerdate.setValue(null);
    }

    //clear fields button mannually-------------
    public void btnclearonaction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnaddcashieronaction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/cashier_registration_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
