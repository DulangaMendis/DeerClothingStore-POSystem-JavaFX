package controller.cashier.viewcashier;

import javafx.collections.ObservableList;
import model.Cashier;
import model.Supplier;

public interface CashierViewService {
    boolean deleteCashier(Cashier cashier);

    ObservableList<Cashier> getAll();

    boolean updateCashier(Cashier cashier);



    Cashier searchCashier(String id);

    String getCashierIds();
}
