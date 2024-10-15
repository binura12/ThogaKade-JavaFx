package controller.item;

import javafx.collections.ObservableList;
import model.Items;
import model.OrderDetail;

import java.util.List;

public interface ItemService {
    boolean addItem(Items items);
    boolean deleteItem(String itemCode);
    ObservableList<Items> getAll();
    boolean updateItem(Items items);
    Items searchItem (String itemCode);
    ObservableList<String> getItemCodes();
    boolean updateStock(List<OrderDetail> orderDetailList);
}
