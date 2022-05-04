package dao;

import com.jfoenix.controls.JFXComboBox;
import db.DBConnection;
import javafx.scene.control.Alert;
import model.ItemDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dao.SQLUtil.executeQuery;

public class PurchaseOrderDAOImpl implements PurchaseDAO{
    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = executeQuery("SELECT code FROM Item WHERE code=?", code);
        return resultSet.next();
    }



    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = executeQuery("SELECT id FROM Customer WHERE id=?", id);
        return resultSet.next();
    }

    @Override
    public void loadAllCustomerIds(JFXComboBox<String> cmbCustomerId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            cmbCustomerId.getItems().add(rst.getString("id"));
        }
    }

    @Override
    public void loadAllItemCode(JFXComboBox<String> cmbItemCode) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            cmbItemCode.getItems().add(rst.getString("code"));
        }
    }

    @Override
    public String generateNewOrderId() {
        try {
            ResultSet rst = SQLUtil.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

            return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";

    }

    @Override
    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        try {
            ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item WHERE code=?", code);
            rst.next();
            return new ItemDTO(code, rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }
}
