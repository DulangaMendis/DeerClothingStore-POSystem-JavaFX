package controller.cashier.viewcashier;

import controller.cashier.CashierRegistrationController;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Cashier;
import model.Supplier;

import java.sql.*;

public class ViewCashierController implements CashierViewService {
    private static ViewCashierController instance;

    private ViewCashierController() {
    }

    public static  ViewCashierController getInstance() {
        return instance == null ? instance = new ViewCashierController() : instance;
    }

    @Override
    public boolean deleteCashier(Cashier cashier) {
        try {
            String SQL = "DELETE FROM cashier WHERE cashierid = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, cashier.getID());
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }

    @Override
    public ObservableList<Cashier> getAll() {
        ObservableList<Cashier> supplierObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM cashier";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {

                System.out.println(resultSet.getString("title") + resultSet.getString("name"));


                Cashier cashier = new Cashier(
                        resultSet.getString("cashierid"),  // Removed the extra space
                        resultSet.getString("title"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("contact"),
                        resultSet.getDate("date_of_joining").toLocalDate()
                );
                supplierObservableList.add(cashier);
                System.out.println(cashier);  // For debugging purposes
            }
            return supplierObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCashier(Cashier cashier) {
        try {
            String SQL = "UPDATE cashier SET title = ?, name = ?, address = ?, username = ?, password = ?, email = ?, contact = ? , date_of_joining =? WHERE cashierid = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, cashier.getTitle());
            psTm.setObject(2, cashier.getName());
            psTm.setObject(3, cashier.getAddress());
            psTm.setObject(4, cashier.getUsername());
            psTm.setObject(5, cashier.getPassword());
            psTm.setObject(6, cashier.getEmail());
            psTm.setObject(7, cashier.getContact());;
            psTm.setObject(8, cashier.getID());
            psTm.setObject(9, cashier.getDate());// Update based on ID
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }

    @Override
    public Cashier searchCashier(String id) {
        try {
            String SQL = "SELECT * FROM cashier WHERE cashierid = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, id);
            ResultSet resultSet = psTm.executeQuery();
            if (resultSet.next()) {
                return new Cashier(
                        resultSet.getString("cashierid"),
                        resultSet.getString("title"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("contact"),
                        resultSet.getDate("date_of_joining").toLocalDate()
                );
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return null;
    }

    @Override
    public String getCashierIds() {
        ObservableList<String> cashierIDs = FXCollections.observableArrayList();
        ObservableList<Cashier> cashierObservableList = getAll();
        cashierObservableList.forEach(cashier -> {
            cashierIDs.add(cashier.getID());
        });

        return cashierIDs.toString();
    }
}
