package controller.cashier;

import controller.cashier.CashierRegistrationController;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Cashier;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CashierRegistrationController implements CashierRegistrationService {

    private static CashierRegistrationController instance;

    private CashierRegistrationController() {
    }

    public static  CashierRegistrationController getInstance() {
        return instance == null ? instance = new CashierRegistrationController() : instance;
    }


    @Override
    public boolean addCashier(Cashier cashier) {
        try {
            String SQL = "INSERT INTO cashier VALUES(?,?,?,?,?,?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,  cashier.getID());
            psTm.setObject(2, cashier.getTitle());
            psTm.setObject(3, cashier.getName());
            psTm.setObject(4, cashier.getAddress());
            psTm.setObject(5, cashier.getUsername());
            psTm.setObject(6, cashier.getPassword());
            psTm.setObject(7, cashier.getEmail());
            psTm.setObject(8, cashier.getContact());
            psTm.setDate(9, Date.valueOf(cashier.getDate()));
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }
}
