package controller.cashier.viewcashier;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Cashier;
import model.Supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierViewFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbtitle;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtaddress;

    @FXML
    private TextField txtcashierid;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtcontact;

    @FXML
    private TextField txtusername;

    @FXML
    private TextField txtpassowrd;

    @FXML
    private JFXDatePicker datepickerdateofjoining;

    @FXML
    private TableView<Cashier> tblviewcashier;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private TableColumn<?, ?> colusername;

    @FXML
    private TableColumn<?, ?> colpassword;

    @FXML
    private TableColumn<?, ?> colemail;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> coldateofjoining;

    @FXML
    private TextField txtsearch;

    CashierViewService service = ViewCashierController.getInstance();


    public void btnsearchonaction(ActionEvent actionEvent) {
        String id = txtsearch.getText(); // Assuming the user enters the supplier ID in the same field
        Cashier cashier = service.searchCashier(id);
        if (cashier != null) {
            setTextToValues(cashier); // Populate the fields with the found supplier data
            new Alert(Alert.AlertType.INFORMATION, "Cashier Found!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Cashier not found!").show();
        }
    }

    private void setTextToValues(Cashier newvalue) {
        txtcashierid.setText(newvalue.getID());
        cmbtitle.setValue(newvalue.getTitle());
        txtname.setText(newvalue.getName());
        txtaddress.setText(newvalue.getAddress());
        txtusername.setText(newvalue.getUsername());
        txtpassowrd.setText(newvalue.getPassword());
        txtemail.setText(newvalue.getEmail());
        txtcontact.setText(newvalue.getContact());
        datepickerdateofjoining.setValue(newvalue.getDate());
        
    }

    public void btnupdateonaction(ActionEvent actionEvent) {

        if(!validateFields()){
            return;
        }

        Cashier cashier = new Cashier(
                txtcashierid.getText(),
                cmbtitle.getValue(),
                txtname.getText(),
                txtaddress.getText(),
                txtusername.getText(),
                txtpassowrd.getText(),
                txtemail.getText(),
                txtcontact.getText(),
                datepickerdateofjoining.getValue()
        );

        if(service.updateCashier(cashier)){
            loadtable();
            new Alert(Alert.AlertType.INFORMATION, "Cashier Updated!").show();
            clearFields();

        } else {
            new Alert(Alert.AlertType.ERROR, "Cashier Not Updated!").show();
        }
    }



    public void btndeleteonaction(ActionEvent actionEvent) {
        String id = txtsearch.getText(); // Assuming the user enters the supplier ID in the same field
        Cashier cashier = service.searchCashier(id); // Get the supplier to delete
        if (cashier != null) {
            if (service.deleteCashier(cashier)) {
                new Alert(Alert.AlertType.INFORMATION, "Cashier Deleted!").show();
                loadtable();// Refresh the table
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Cashier Not Deleted!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Cashier not found!").show();
        }
    }

    private void clearFields() {
        txtcashierid.clear();
        cmbtitle.getSelectionModel().clearSelection();
        txtname.clear();
        txtaddress.clear();
        txtusername.clear();
        txtpassowrd.clear();
        txtemail.clear();
        txtcontact.clear();
        datepickerdateofjoining.setValue(null);
        txtsearch.clear();
    }

    private void loadtable() {
        ObservableList<Cashier> cashierList = FXCollections.observableArrayList(service.getAll());
        tblviewcashier.setItems(cashierList);
    }


    public void btncashierregisterformonaction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/cashier_registration_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btndashboardonaction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dash_board.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colusername.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        coldateofjoining.setCellValueFactory(new PropertyValueFactory<>("Date Of Joining"));
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Mrs");
        cmbtitle.setItems(titles);
        loadtable();
        tblviewcashier.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));


    }

    private boolean validateFields() {
        if (cmbtitle.getValue() == null || txtname.getText().isEmpty() || txtaddress.getText().isEmpty() ||
                txtusername.getText().isEmpty() || txtpassowrd.getText().isEmpty() ||
                txtemail.getText().isEmpty() || txtcontact.getText().isEmpty() ||
                datepickerdateofjoining.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            return false;
        }

        // Optional: Additional validation for email format, contact number, etc.
        if (!txtemail.getText().contains("@")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address!").show();
            return false;
        }

        return true;
    }
}
