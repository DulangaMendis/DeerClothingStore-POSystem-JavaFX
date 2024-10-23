package controller.item;

import javafx.collections.ObservableList;
import model.Item;


public interface ItemService {
    boolean addItem(Item item);

    boolean deleteItem(Item item);

    ObservableList<Item> getAll();

    boolean updateItem(Item item);

    Item searchItem(String id);

    ObservableList<String> geItemIds();
}
