package controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Items;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Items> tblItemList;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtUnitPrice;

    ItemService service = ItemController.getInstance();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Items items = new Items(
                txtItemCode.getText(),
                txtDesc.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        if (service.addItem(items)){
            new Alert(Alert.AlertType.INFORMATION,"Item Added!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Added!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(service.deleteItem(txtItemCode.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Deleted!").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Items items = service.searchItem(txtItemCode.getText());
        if (items != null) {
            setTextFields(items);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "No item found with the given Item Code.").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Items items = new Items(
                txtItemCode.getText(),
                txtDesc.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        if (service.updateItem(items)){
            new Alert(Alert.AlertType.INFORMATION,"Item Updated!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Updated!").show();
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblItemList.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextFields(newValue);
        } ));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadTable();
    }

    private void loadTable(){
        try {
            ObservableList<Items> itemsObservableList = service.getAll();
            tblItemList.setItems(itemsObservableList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading Items: " + e.getMessage()).show();
        }
    }

    private void setTextFields(Items items) {
        if (items == null){
            txtItemCode.setText("");
            txtDesc.setText("");
            txtPackSize.setText("");
            txtUnitPrice.setText("");
            txtQty.setText("");
        }else{
            txtItemCode.setText(items.getItemCode());
            txtDesc.setText(items.getDescription());
            txtPackSize.setText(items.getPackSize());
            txtUnitPrice.setText(String.valueOf(items.getUnitPrice()));
            txtQty.setText(String.valueOf(items.getQty()));
        }
    }
}
