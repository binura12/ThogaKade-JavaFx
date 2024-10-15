package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Items;
import model.OrderDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemController implements ItemService{
    private static ItemController instance;
    private ItemController(){}
    public static ItemController getInstance() {
        return instance==null?instance=new ItemController():instance;
    }

    @Override
    public boolean addItem(Items items) {
        String sql = "insert into item values (?,?,?,?,?)";
        try {
            return CrudUtil.execute(sql,
                    items.getItemCode(),
                    items.getDescription(),
                    items.getPackSize(),
                    items.getUnitPrice(),
                    items.getQty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String itemCode) {
        try {
            return CrudUtil.execute("delete from item where ItemCode=?",itemCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Items> getAll() {
        String sql = "select * from item";
        ObservableList<Items> itemsObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()){
                itemsObservableList.add(new Items(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                ));
            }

            return itemsObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Items items) {
        String sql = "update item set Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? where ItemCode=?";
        try {
            return CrudUtil.execute(
                    sql,
                    items.getDescription(),
                    items.getPackSize(),
                    items.getUnitPrice(),
                    items.getQty(),
                    items.getItemCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Items searchItem(String itemCode) {
        String sql = "SELECT * FROM item WHERE ItemCode='"+itemCode+"'";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, itemCode);
            if (resultSet.next()) {
                return new Items(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<String> getItemCodes(){
        ObservableList<String> itemCodes = FXCollections.observableArrayList();
        ObservableList<Items> itemsObservableList = getAll();
        itemsObservableList.forEach(items -> {
            itemCodes.add(items.getItemCode());
        });
        return itemCodes;
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetailList) {
        for (OrderDetail orderDetail: orderDetailList){
            boolean updateStock =  updateStock(orderDetail);
            if (!updateStock){
                return false;
            }
        }
        return true;
    }

    public boolean updateStock(OrderDetail orderDetailList) {
        String sql = "UPDATE item SET QtyOnHand = QtyOnHand-? WHERE ItemCode =?";
        try {
            return CrudUtil.execute(sql, orderDetailList.getQty(), orderDetailList.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
