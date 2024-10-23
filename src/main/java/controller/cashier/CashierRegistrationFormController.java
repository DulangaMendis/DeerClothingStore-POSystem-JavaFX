package controller.cashier;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cashier;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CashierRegistrationFormController implements Initializable{

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

    CashierRegistrationService service = CashierRegistrationController.getInstance();

    public void btnaddcashieronaction(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return; // If validation fails, stop further execution
        }
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Mrs");
        cmbtitle.setItems(titles);
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

        if(service.addCashier(cashier)){
            new Alert(Alert.AlertType.INFORMATION,"Cashier Registration Successfully !!").show();
            clearFields();
        }else {
            new Alert(Alert.AlertType.ERROR,"Cashier Registration Failed :(").show();
        }
    }

    private boolean validateInputs() {
        String cashierId = txtcashierid.getText();
        if (cashierId == null || !cashierId.startsWith("C")) {
            new Alert(Alert.AlertType.ERROR, "Cashier ID must start with 'C'").show();
            return false;
        }


        String title = cmbtitle.getValue();
        if (title == null || title.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a title").show();
            return false;
        }


        String name = txtname.getText();
        if (name == null || name.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty").show();
            return false;
        }


        String address = txtaddress.getText();
        if (address == null || address.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Address cannot be empty").show();
            return false;
        }


        String username = txtusername.getText();
        if (username == null || username.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username cannot be empty").show();
            return false;
        }


        String password = txtpassowrd.getText();
        if (password == null || password.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password cannot be empty").show();
            return false;
        }
        if (!password.startsWith("DCSC")) {
            new Alert(Alert.AlertType.ERROR, "Password must start with 'DCSC'").show();
            return false;
        }


        String email = txtemail.getText();
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid email").show();
            return false;
        }


        String contact = txtcontact.getText();
        if (contact == null || !contact.matches("^[0-9]{10}$")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid 10-digit contact number").show();
            return false;
        }


        LocalDate dateOfJoining = datepickerdateofjoining.getValue();
        if (dateOfJoining == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a valid Date of Joining").show();
            return false;
        }


        return true;
    }

    public void btnbacktodashboardonaction(ActionEvent actionEvent) {
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
        ObservableList<String> titles = FXCollections.observableArrayList("Mr", "Miss", "Mrs");
        cmbtitle.setItems(titles);
    }

    private void clearFields() {
        cmbtitle.setValue(null);
        txtname.clear();
        txtaddress.clear();
        txtcontact.clear();
        txtusername.clear();
        txtpassowrd.clear();
        txtemail.clear();
        datepickerdateofjoining.setValue(null);
    }

    public void btnviewcashierdetailsonaction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/cashier_view_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
