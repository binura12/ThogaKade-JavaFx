package controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Items;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private DatePicker dob;

    @FXML
    private TableView<Customer> tblCustomerList;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    CustomerService service = CustomerController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Add Titles to Combo Box

        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        cmbTitle.setItems(titles);

        tblCustomerList.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextFields(newValue);
        } ));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        loadTable();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                dob.getValue(),Double.
                parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
        if (service.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        if(service.deleteCustomer(txtId.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Deleted!").show();
        }
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Customer customer = service.searchCustomer(txtId.getText());
        if (customer != null) {
            setTextFields(customer);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "No customer found with the given Item Code.").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    private void loadTable(){
        try {
            ObservableList<Customer> customerObservableList = service.getAll();
            tblCustomerList.setItems(customerObservableList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading customers: " + e.getMessage()).show();
        }
    }

    private void setTextFields(Customer customer) {
        if (customer == null){
            txtId.setText("");
            txtName.setText("");
            txtSalary.setText("");
            txtAddress.setText("");
            txtCity.setText("");
            txtProvince.setText("");
            txtPostalCode.setText("");
            cmbTitle.setValue(null);
            dob.setValue(null);
        }else{
            txtId.setText(customer.getId());
            cmbTitle.setValue(customer.getCustTitle());
            txtName.setText(customer.getCustName());
            dob.setValue(customer.getDob());
            txtSalary.setText(String.valueOf(customer.getSalary()));
            txtAddress.setText(customer.getCustAddress());
            txtCity.setText(customer.getCity());
            txtProvince.setText(customer.getProvince());
            txtPostalCode.setText(customer.getPostalCode());
        }
    }
}