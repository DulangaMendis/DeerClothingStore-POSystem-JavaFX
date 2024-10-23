package controller.supplier;

import db.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckManager {

    @FXML
    private PasswordField pwcheckingpassowrd;

    private Connection connection;
    private PreparedStatement pstm;



    public void checkmanagersecurity() throws SQLException, ClassNotFoundException {
        if (pwcheckingpassowrd.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Icorrect Username/Passowrd");
            alert.showAndWait();
        } else {
            String selectData = "Select password From manager Where password = ?";

            connection = DBConnection.getInstance().getConnection();

            try {

                pstm = connection.prepareStatement(selectData);
                pstm.setString(1, pwcheckingpassowrd.getText());

                ResultSet result = pstm.executeQuery();

                if (result.next()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succsessfully");
                    alert.showAndWait();
                    Stage stage = new Stage();
                    try {
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml"))));
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Icorrect Username/Password , Please Enter Your Manager Password");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public void btnenteronaction(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        checkmanagersecurity();
    }
}
