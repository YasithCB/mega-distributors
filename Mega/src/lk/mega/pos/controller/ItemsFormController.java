package lk.mega.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import lk.mega.pos.bo.BOFactory;
import lk.mega.pos.bo.custom.ItemBO;
import lk.mega.pos.bo.custom.impl.ItemBOImpl;
import lk.mega.pos.dto.CustomerDTO;
import lk.mega.pos.dto.ItemDTO;
import lk.mega.pos.util.ClearDataUtil;
import lk.mega.pos.util.RegexUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsFormController {
    public TableView<ItemDTO> tblItems;
    public JFXTextField txtDescription;
    public Label lblItemCode;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSave_Update;
    public JFXButton btnDelete;
    public Pane paneForAdmin;
    public JFXTextField txtSearch;

    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize(){
        btnSave_Update.setDisable(true);
        btnDelete.setDisable(true);
        paneForAdmin.setDisable(!MainFormController.isThisAdmin);
        clearAllEnteredData();
        loadTable();
        generateNewCode();
        autoSetDataToUpdate();
    }

    private void autoSetDataToUpdate() {
        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            btnSave_Update.setText(newValue == null ? "Save" : "Update");
            btnDelete.setDisable(newValue == null);
            btnSave_Update.setDisable(newValue == null);

            if (newValue != null){
                lblItemCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtPackSize.setText(newValue.getPackSize());
                txtUnitPrice.setText(newValue.getUnitPrice().toString());
                txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
            }
        });
    }

    private void generateNewCode() {
        try {
            lblItemCode.setText(itemBO.generateNewItemCode());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {
        tblItems.getItems().clear();

        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItems.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO itemDTO : allItems) {
                tblItems.getItems().add(itemDTO);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void save_UpdateOnAction(ActionEvent actionEvent) {
        ItemDTO selectedItem = new ItemDTO(
                lblItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                new BigDecimal(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );

        if (btnSave_Update.getText().equals("Save")){
            // save Item
            try {
                if (itemBO.saveItem(selectedItem)) {
                    new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                }else
                    new Alert(Alert.AlertType.ERROR,"Item Not Saved!").show();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else {
            // update item
            try {
                if (itemBO.updateItem(selectedItem)) {
                    new Alert(Alert.AlertType.INFORMATION,"Item Updated!").show();
                }else
                    new Alert(Alert.AlertType.ERROR,"Item Not Updated!").show();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

        initialize();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            if (itemBO.deleteItem(lblItemCode.getText())) {
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
            }else
                new Alert(Alert.AlertType.ERROR,"Item Not Deleted!").show();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        initialize();
    }

    public void newItemOnAction(ActionEvent actionEvent) {
        generateNewCode();
        clearAllEnteredData();
    }

    private void clearAllEnteredData() {
        tblItems.getSelectionModel().clearSelection();
        ClearDataUtil.clearTextFields(txtDescription,txtPackSize,txtUnitPrice,txtQtyOnHand);
    }

    public void txtSearchOnKeyReleased(KeyEvent keyEvent) {
        if (!txtSearch.getText().equals("")){
            tblItems.getItems().clear();
            try {
                ArrayList<ItemDTO> orders = itemBO.getAllItems();
                for (ItemDTO itemDTO : orders) {
                    if (itemDTO.getCode().toLowerCase().contains(txtSearch.getText().toLowerCase())){
                        tblItems.getItems().add(itemDTO);
                    }
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else
            loadTable();
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        validate();
    }

    private void validate() {

        if (!RegexUtil.price.matcher(txtUnitPrice.getText()).matches()){
            btnSave_Update.setDisable(true);
            txtUnitPrice.setFocusColor(Paint.valueOf("red"));
        }else {
            btnSave_Update.setDisable(false);
            txtUnitPrice.setFocusColor(Paint.valueOf("#e7a755"));
            if (!RegexUtil.qty.matcher(txtQtyOnHand.getText()).matches()){
                btnSave_Update.setDisable(true);
                txtQtyOnHand.setFocusColor(Paint.valueOf("red"));
            }else {
                btnSave_Update.setDisable(false);
                txtQtyOnHand.setFocusColor(Paint.valueOf("#e7a755"));
            }
        }

        if (txtDescription.getText().equals("") || txtPackSize.getText().equals("") || txtUnitPrice.getText().equals("") || txtQtyOnHand.getText().equals("")) {
            btnSave_Update.setDisable(true);
        }
    }
}
