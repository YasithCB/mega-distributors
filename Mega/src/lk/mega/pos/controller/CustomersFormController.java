package lk.mega.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import lk.mega.pos.bo.BOFactory;
import lk.mega.pos.bo.custom.CustomerBO;
import lk.mega.pos.bo.custom.impl.CustomerBOImpl;
import lk.mega.pos.dto.CustomerDTO;
import lk.mega.pos.presentation.tdm.OrderTM;
import lk.mega.pos.util.ClearDataUtil;
import lk.mega.pos.util.RegexUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersFormController {
    public JFXTextField txtCustName;
    public Label lblCustId;
    public JFXTextField txtCustAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public JFXComboBox<String> cmbCustTitle;
    public TableView<CustomerDTO> tblCustomers;
    public JFXButton btnSave_update;
    public JFXButton btnDelete;
    public JFXTextField txtSearch;

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        btnSave_update.setDisable(true);
        btnDelete.setDisable(true);
        autoFillToUpdateCustomer();
        initializeCmb();
        clearAllEnteredData();
        generateCustomerId();
        loadTable();
    }

    private void autoFillToUpdateCustomer() {
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            btnSave_update.setText(newValue == null? "Save" : "Update");
            btnDelete.setDisable(newValue == null);
            btnSave_update.setDisable(newValue == null);

            if (newValue != null){
                cmbCustTitle.setValue(newValue.getTitle());
                lblCustId.setText(newValue.getId());
                txtCustName.setText(newValue.getName());
                txtCustAddress.setText(newValue.getAddress());
                txtCity.setText(newValue.getCity());
                txtProvince.setText(newValue.getProvince());
                txtPostalCode.setText(newValue.getProvince());
            }
        });
    }

    private void generateCustomerId() {
        try {
            lblCustId.setText(customerBO.generateCustomerId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {
        tblCustomers.getItems().clear();

        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("city"));
        tblCustomers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("province"));
        tblCustomers.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO dto: allCustomers) {
                if (dto.getId().equals("C000"))
                    continue;
                tblCustomers.getItems().add(new CustomerDTO(
                        dto.getId(),
                        dto.getTitle(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getCity(),
                        dto.getProvince(),
                        dto.getPostalCode()
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializeCmb() {
        ObservableList<String> ol = FXCollections.observableArrayList();
        ol.add("Mr");
        ol.add("Mrs");
        ol.add("Ms");
        ol.add("Miss");
        cmbCustTitle.setItems(ol);
    }

    public void btnSave_updateOnAction(ActionEvent actionEvent) {
        CustomerDTO selectedCustomer = new CustomerDTO(
                lblCustId.getText(),
                cmbCustTitle.getValue(),
                txtCustName.getText(),
                txtCustAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );

        if (btnSave_update.getText().equals("Save")){
            // save customer
            try {
                if (customerBO.saveCustomer(selectedCustomer)) {
                    new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                }else
                    new Alert(Alert.AlertType.ERROR,"Customer Not Saved!").show();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else {
            // update customer
            try {
                if (customerBO.updateCustomer(selectedCustomer)) {
                    new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
                }else
                    new Alert(Alert.AlertType.ERROR,"Customer Not Updated!").show();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

        initialize();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            if (customerBO.deleteCustomer(lblCustId.getText())) {
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
            }else
                new Alert(Alert.AlertType.ERROR,"Customer Not Deleted!").show();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        initialize();
    }

    public void addNewOnAction(ActionEvent actionEvent) {
        clearAllEnteredData();
    }

    private void clearAllEnteredData() {
        tblCustomers.getSelectionModel().clearSelection();
        ClearDataUtil.clearCmb(cmbCustTitle);
        ClearDataUtil.clearTextFields(txtCustName,txtCustAddress,txtCity,txtProvince,txtPostalCode);
    }

    public void txtSearchOnKeyReleased(KeyEvent keyEvent) {
        if (!txtSearch.getText().equals("")){
            tblCustomers.getItems().clear();
            try {
                ArrayList<CustomerDTO> orders = customerBO.getAllCustomers();
                for (CustomerDTO customerDTO : orders) {
                    if (customerDTO.getId().toLowerCase().contains(txtSearch.getText().toLowerCase())){
                        tblCustomers.getItems().add(customerDTO);
                    }
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else
            loadTable();
    }

    public void onKeyRelease(KeyEvent keyEvent) {
        validate();
    }

    private void validate() {

        if (!RegexUtil.name.matcher(txtCustName.getText()).matches()){
            btnSave_update.setDisable(true);
            txtCustName.setFocusColor(Paint.valueOf("red"));
        }else {
            btnSave_update.setDisable(false);
            txtCustName.setFocusColor(Paint.valueOf("#e7a755"));
            if (!RegexUtil.name.matcher(txtCity.getText()).matches()){
                btnSave_update.setDisable(true);
                txtCity.setFocusColor(Paint.valueOf("red"));
            }else {
                btnSave_update.setDisable(false);
                txtCity.setFocusColor(Paint.valueOf("#e7a755"));
                if (!RegexUtil.name.matcher(txtProvince.getText()).matches()){
                    btnSave_update.setDisable(true);
                    txtProvince.setFocusColor(Paint.valueOf("red"));
                }else {
                    btnSave_update.setDisable(false);
                    txtProvince.setFocusColor(Paint.valueOf("#e7a755"));
                    if (!RegexUtil.postalCode.matcher(txtPostalCode.getText()).matches()){
                        btnSave_update.setDisable(true);
                        txtPostalCode.setFocusColor(Paint.valueOf("red"));
                    }else {
                        btnSave_update.setDisable(false);
                        txtPostalCode.setFocusColor(Paint.valueOf("#e7a755"));
                    }
                }
            }
        }

        if (txtCustName.getText().equals("") || txtCustAddress.getText().equals("") || txtCity.getText().equals("")
                || txtProvince.getText().equals("") || txtPostalCode.getText().equals("")) {
            btnSave_update.setDisable(true);
        }
    }
}
