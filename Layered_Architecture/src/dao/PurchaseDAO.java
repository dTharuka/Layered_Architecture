package dao;

import com.jfoenix.controls.JFXComboBox;
import model.ItemDTO;

import java.sql.SQLException;

public interface PurchaseDAO {
    boolean existItem(String code) throws SQLException, ClassNotFoundException;

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    void loadAllCustomerIds(JFXComboBox<String> cmbCustomerId) throws SQLException, ClassNotFoundException;

    void loadAllItemCode(JFXComboBox<String> cmbItemCode) throws SQLException, ClassNotFoundException;

    String generateNewOrderId();

    ItemDTO findItem(String code) throws SQLException, ClassNotFoundException;
}
