package controller.item;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;
import model.Supplier;

import java.sql.*;

public class ItemController implements ItemService {

    private static ItemController instance;

    private ItemController(){
    }

    public static  ItemController getInstance() {
        return instance == null ? instance = new ItemController() : instance;
    }

    @Override
    public boolean addItem(Item item) {
        try {
            String SQL = "INSERT INTO item VALUES(?,?,?,?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, item.getId());
            psTm.setObject(2, item.getType());
            psTm.setObject(3, item.getDescription());
            psTm.setObject(4, item.getPackSize());
            psTm.setObject(5, item.getUnitPrice());
            psTm.setObject(6, item.getQty());
            psTm.setDate(7, Date.valueOf(item.getDate()));
            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean deleteItem(Item item) {
        try {
            String SQL = "DELETE FROM item WHERE id = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, item.getId());
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }

    @Override
    public ObservableList<Item> getAll() {
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM item";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("description"),
                        resultSet.getString("packSize"),
                        resultSet.getDouble("unitPrice"),
                        resultSet.getInt("qty"),
                        resultSet.getDate("date").toLocalDate()
                );
                itemList.add(item);
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            String SQL = "UPDATE item SET type = ?, description = ?, packSize = ?, unitPrice = ?, qty = ?, date = ? WHERE id = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, item.getType());
            psTm.setObject(2, item.getDescription());
            psTm.setObject(3, item.getPackSize());
            psTm.setObject(4, item.getUnitPrice());
            psTm.setObject(5, item.getQty());
            psTm.setDate(6, Date.valueOf(item.getDate()));
            psTm.setObject(7, item.getId());
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error : " + e.getMessage()).show();
        }
        return false;
    }

    @Override
    public Item searchItem(String id) {
        try {
            String SQL = "SELECT * FROM item WHERE id = ?";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, id);
            ResultSet resultSet = psTm.executeQuery();
            if (resultSet.next()) {
                return new Item(
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("description"),
                        resultSet.getString("packSize"),
                        resultSet.getDouble("unitPrice"),
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
    public ObservableList<String> geItemIds() {
        ObservableList<String> itemIds = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT id FROM item";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
                itemIds.add(resultSet.getString("id"));
            }
            return itemIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
