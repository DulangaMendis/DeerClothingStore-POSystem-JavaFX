package controller.supplier;


import javafx.collections.ObservableList;
import model.Supplier;

public interface SupplierService {

    boolean addSupplier(Supplier supplier);

    boolean deleteSupplier(Supplier supplier);

    ObservableList<Supplier> getAll();

    boolean updateSupplier(Supplier supplier);

    Supplier searchSupplier(String id);

    ObservableList<String> getSupplierIds();




}
