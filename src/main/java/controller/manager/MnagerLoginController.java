package controller.manager;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MnagerLoginController {
    public JFXPasswordField pwfpassword;
    public JFXTextField txtusername;


    private Connection connection;
    private PreparedStatement pstm;



    public void cashierbtnonaction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dash.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkmanager() throws SQLException, ClassNotFoundException {
        if(txtusername.getText().isEmpty() || pwfpassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Icorrect Username/Passowrd");
            alert.showAndWait();
        }else{
            String selectData = "Select username , password From manager Where username = ? and password = ?";

           connection = DBConnection.getInstance().getConnection();

           try {

               pstm = connection.prepareStatement(selectData);
               pstm.setString(1,txtusername.getText());
               pstm.setString(2,pwfpassword.getText());

               ResultSet result = pstm.executeQuery();

               if(result.next()){
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Information Message");
                   alert.setHeaderText(null);
                   alert.setContentText("Succsessfully Login");
                   alert.showAndWait();
                   Stage stage = new Stage();
                   try {
                       stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/cashier_registration_form.fxml"))));
                       stage.show();
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }

               }else{
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Error Message");
                   alert.setHeaderText(null);
                   alert.setContentText("Icorrect Username/Password");
                   alert.showAndWait();
               }

           } catch (SQLException e) {
               throw new RuntimeException(e);
           }


        }
    }


    public void loginbutton() throws SQLException, ClassNotFoundException {
        if(txtusername.getText().isEmpty() || pwfpassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Icorrect Username/Passowrd");
            alert.showAndWait();
        }else{
            String selectData = "Select username , password From manager Where username = ? and password = ?";

            connection = DBConnection.getInstance().getConnection();

            try {

                pstm = connection.prepareStatement(selectData);
                pstm.setString(1,txtusername.getText());
                pstm.setString(2,pwfpassword.getText());

                ResultSet result = pstm.executeQuery();

                if(result.next()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succsessfully Login");
                    alert.showAndWait();
                    Stage stage = new Stage();
                    try {
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dash_board.fxml"))));
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Icorrect Username/Password");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public void btnloginonaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loginbutton();
    }

    public void btncashierregistrationonaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        checkmanager();
    }

}
