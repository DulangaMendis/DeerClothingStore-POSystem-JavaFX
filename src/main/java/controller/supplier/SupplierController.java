package controller.supplier;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Supplier;


import java.sql.*;

public class SupplierController implements SupplierService{

    private static SupplierController instance;

    private SupplierController() {
    }

    public static  SupplierController getInstance() {
        return instance == null ? instance = new SupplierController() : instance;
    }


    @Override
    public boolean addSupplier(Supplier supplier) {
        try {
            String SQL = "INSERT INTO supplier VALUES(?,?,?,?,?,?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,  supplier.getID());
            psTm.setObject(2, supplier.getTitle());
            psTm.setObject(3, supplier.getName());
            psTm.setObject(4, supplier.getCompany());
            psTm.setObject(5, supplier.getContact());
            psTm.setObject(6, supplier.getItemCode());
            psTm.setObject(7, supplier.getStatus());
            psTm.setObject(8, supplier.getQTY());
            psTm.setDate(9, Date.valueOf(supplier.getDate()));
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean deleteSupplier(Supplier supplier) {
        try {
            String SQL = "DELETE FROM supplier WHERE supplierid = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, supplier.getID());
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }

    @Override
    public ObservableList<Supplier> getAll() {
        ObservableList<Supplier> supplierObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM supplier";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                // Debugging to check column values
                System.out.println(resultSet.getString("title") + resultSet.getString("supplier_name"));

                // Creating the Supplier object with corrected column name
                Supplier supplier = new Supplier(
                        resultSet.getString("supplierid"),  // Removed the extra space
                        resultSet.getString("title"),
                        resultSet.getString("supplier_name"),
                        resultSet.getString("company"),
                        resultSet.getString("contact"),
                        resultSet.getString("item_code"),
                        resultSet.getString("description"),
                        resultSet.getInt("qty"),
                        resultSet.getDate("date").toLocalDate()
                );
                supplierObservableList.add(supplier);
                System.out.println(supplier);  // For debugging purposes
            }
            return supplierObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        try {
            String SQL = "UPDATE supplier SET title = ?, supplier_name = ?, company = ?, contact = ?, item_code = ?, description = ?, qty = ?, date = ? WHERE supplierid = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, supplier.getTitle());
            psTm.setObject(2, supplier.getName());
            psTm.setObject(3, supplier.getCompany());
            psTm.setObject(4, supplier.getContact());
            psTm.setObject(5, supplier.getItemCode());
            psTm.setObject(6, supplier.getStatus());
            psTm.setObject(7, supplier.getQTY());
            psTm.setDate(8, Date.valueOf(supplier.getDate()));
            psTm.setObject(9, supplier.getID());
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }

    @Override
    public Supplier searchSupplier(String id) {
        try {
            String SQL = "SELECT * FROM supplier WHERE supplierid = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, id);
            ResultSet resultSet = psTm.executeQuery();
            if (resultSet.next()) {
                return new Supplier(
                        resultSet.getString("supplierid"),
                        resultSet.getString("title"),
                        resultSet.getString("supplier_name"),
                        resultSet.getString("company"),
                        resultSet.getString("contact"),
                        resultSet.getString("item_code"),
                        resultSet.getString("description"),
                        resultSet.getInt("qty"),
                        resultSet.getDate("date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return null;
    }

    @Override
    public ObservableList<String> getSupplierIds() {
        ObservableList<String> supplierIds = FXCollections.observableArrayList();
        ObservableList<Supplier> supplierObservableList = getAll();
        supplierObservableList.forEach(supplier -> {
            supplierIds.add(supplier.getID());
        });

        return supplierIds;
    }
}
