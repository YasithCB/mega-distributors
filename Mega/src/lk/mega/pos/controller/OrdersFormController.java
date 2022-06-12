package lk.mega.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lk.mega.pos.bo.BOFactory;
import lk.mega.pos.bo.custom.*;
import lk.mega.pos.dto.CustomerDTO;
import lk.mega.pos.dto.ItemDTO;
import lk.mega.pos.dto.OrderDTO;
import lk.mega.pos.dto.OrderDetailDTO;
import lk.mega.pos.presentation.tdm.CartTM;
import lk.mega.pos.presentation.tdm.OrderTM;
import lk.mega.pos.util.ClearDataUtil;
import lk.mega.pos.util.RegexUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrdersFormController {

    public AnchorPane apnPlaceOrder;
    public JFXComboBox<String> cmbItemCode;
    public JFXComboBox<String> cmbCustomerId;
    public Label lblDesc;
    public Label lblPackSize;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public JFXTextField txtOrderQty;
    public JFXTextField txtDiscount;
    public Label lblSubTotal;
    public TableView<CartTM> tblCart;
    public Label lblTotal;
    public AnchorPane apnOrders;
    public TableView<OrderTM> tblOrders;
    public Label lblCustomerTitle;
    public Label lblCustomerName;
    public Label lblAddress;
    public Label lblCity;
    public Label lblProvince;
    public Label lblPostalCode;
    public Label lblOrderId;
    public Label lblDiscountValue;
    public JFXDatePicker orderDate;
    public JFXButton btnUpdateOrder;
    public JFXButton btnDeleteOrder;
    public JFXButton btnAddItem;
    public JFXTextField txtSearchOrder;
    public JFXButton btnConfirmOrder;

    private final OrdersBO ordersBO = (OrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        btnConfirmOrder.setDisable(true);
        btnAddItem.setDisable(true);
        apnOrders.setTranslateX(1000);
        btnUpdateOrder.setDisable(true);
        btnDeleteOrder.setDisable(true);

        clearAllEnteredData();
        autoFillData();
        autoFillToUpdateCartTableData();
        initializeCmb();
        generateOrderId();
        loadOrderTable();
    }

    private void autoFillToUpdateCartTableData() {
        tblCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cmbItemCode.setValue(newValue.getItemCode());
            txtOrderQty.setText(String.valueOf(newValue.getQty()));
            txtDiscount.setText(String.valueOf(Double.parseDouble(String.valueOf(newValue.getDiscount())) * 100 / (Double.parseDouble(lblUnitPrice.getText()) * Double.parseDouble(txtOrderQty.getText()))));
            if (newValue != oldValue)
                calculateSubTotal_discount();
        });
    }

    private void loadOrderTable() {
        tblOrders.getItems().clear();
        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        tblOrders.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            ArrayList<OrderTM> orderTMS = ordersBO.getDataToOrderTable();
            for (OrderTM orderTM : orderTMS) {
                tblOrders.getItems().add(orderTM);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        // autoFill to update
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnUpdateOrder.setDisable(newValue == null);
            btnDeleteOrder.setDisable(newValue == null);

            if (newValue != null){
                lblOrderId.setText(newValue.getOrderId());
                cmbCustomerId.setValue(newValue.getCustomerId());
                orderDate.setValue(newValue.getDate());
                lblTotal.setText(String.valueOf(newValue.getTotal()));
            }
        });
    }

    private void clearAllEnteredData() {
        txtDiscount.setText("0");
        txtOrderQty.setText("0");
        lblUnitPrice.setText("0");
        ClearDataUtil.clearCmb(cmbCustomerId,cmbItemCode);
        ClearDataUtil.clearLabels(lblCustomerTitle,lblCustomerName,lblAddress,lblCity,lblProvince,lblPostalCode,lblDesc,lblPackSize,lblDiscountValue,lblSubTotal,lblTotal,lblQtyOnHand);
    }

    private void autoFillData() {
        // customer details
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Anonymous")){
                try {
                    CustomerDTO customer = customerBO.findCustomer(newValue);
                    // fill
                    lblCustomerTitle.setText(customer.getTitle());
                    lblCustomerName.setText(customer.getName());
                    lblAddress.setText(customer.getAddress());
                    lblCity.setText(customer.getCity());
                    lblProvince.setText(customer.getProvince());
                    lblPostalCode.setText(customer.getPostalCode());

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // item details
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                try {
                    ItemDTO item = itemBO.searchItem(newValue);
                    // fill
                    lblDesc.setText(item.getDescription());
                    lblPackSize.setText(item.getPackSize());
                    lblUnitPrice.setText(item.getUnitPrice().toString());
                    lblQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }

    private void generateOrderId() {
        try {
            lblOrderId.setText(ordersBO.generateOrderId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializeCmb() {
        try {
            // Customer ID
            ArrayList<CustomerDTO> allCustomers = ordersBO.getAllCustomers();
            for (CustomerDTO customerDTO: allCustomers) {
                if (customerDTO.getId().equals("C000"))
                    continue;
                cmbCustomerId.getItems().add(customerDTO.getId());
            }
            cmbCustomerId.getItems().add("Anonymous");

            // Item Code
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO itemDTO : allItems) {
                cmbItemCode.getItems().add(itemDTO.getCode());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void confirmOrderOnAction(ActionEvent actionEvent) {
        boolean saveOrder = false;
        try {
            saveOrder = ordersBO.saveOrder(new OrderDTO(
                    lblOrderId.getText(),
                    LocalDate.now(),
                    cmbCustomerId.getValue().equals("Anonymous")? "C000" : cmbCustomerId.getValue(),
                    tblCart.getItems().stream().map(tm -> new OrderDetailDTO(lblOrderId.getText(), tm.getItemCode(), tm.getQty(), tm.getDiscount())).collect(Collectors.toList()),
                    Double.parseDouble(lblTotal.getText())
            ));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (!saveOrder){
            new Alert(Alert.AlertType.ERROR,"Order hasn't Completed! Check again").show();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to print a Invoice?", ButtonType.YES,ButtonType.NO);
            alert.setHeaderText("Order has Completed!");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)){
                // print report
            }else {
                // dont print
            }

            clearAllEnteredData();
            generateOrderId();
            tblCart.getItems().clear();
        }
    }

    public void viewAllOrdersOnAction(ActionEvent actionEvent) {
        TranslateTransition slideOrders = new TranslateTransition(Duration.seconds(0.2),apnOrders);
        slideOrders.setToX(0);
        slideOrders.play();
        lblOrderId.setText("");
    }

    public void addItemOnAction(ActionEvent actionEvent) {
        btnConfirmOrder.setDisable(false);
        // introduce columns to table
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblCart.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        // check if already have that item
        ObservableList<CartTM> cartItems = tblCart.getItems();
        for (CartTM cartTM : cartItems) {
            if (cartTM.getItemCode().equals(cmbItemCode.getValue())){
                cartItems.add(new CartTM(
                        cartTM.getItemCode(),
                        cartTM.getDescription(),
                        cartTM.getPackSize(),
                        cartTM.getUnitPrice(),
                        BigDecimal.valueOf(Double.parseDouble(String.valueOf(cartTM.getDiscount())) + Double.parseDouble(lblDiscountValue.getText())).setScale(2,BigDecimal.ROUND_HALF_EVEN) ,
                        cartTM.getQty() + Integer.parseInt(txtOrderQty.getText()),
                        BigDecimal.valueOf(Double.parseDouble(String.valueOf(cartTM.getSubTotal())) + Double.parseDouble(lblSubTotal.getText())).setScale(2,BigDecimal.ROUND_HALF_EVEN)
                ));
                cartItems.remove(cartTM);
                return;
            }
        }

        // add
        tblCart.getItems().add(new CartTM(
                cmbItemCode.getValue(),
                lblDesc.getText(),
                lblPackSize.getText(),
                new BigDecimal(lblUnitPrice.getText()).setScale(2,BigDecimal.ROUND_HALF_EVEN),
                BigDecimal.valueOf(calculateDiscountValue()).setScale(2,BigDecimal.ROUND_HALF_EVEN),
                Integer.parseInt(txtOrderQty.getText()),
                new BigDecimal(lblSubTotal.getText()).setScale(2,BigDecimal.ROUND_HALF_EVEN)
        ));

        // update total
        calculateTotal();

        cmbItemCode.getSelectionModel().clearSelection();
        ClearDataUtil.clearLabels(lblDesc,lblPackSize,lblQtyOnHand);
        txtOrderQty.setText("0");
        txtDiscount.setText("0");
        lblUnitPrice.setText("0");
        calculateSubTotal_discount();
    }

    private void calculateTotal() {
        // total
        double total = 0;
        ObservableList<CartTM> items = tblCart.getItems();
        for (CartTM cartTM : items) total += Double.parseDouble(String.valueOf(cartTM.getSubTotal()));
        lblTotal.setText(String.valueOf(total));
    }

    private double calculateDiscountValue() {
        return Double.parseDouble(lblUnitPrice.getText()) * Double.parseDouble(txtOrderQty.getText()) * Double.parseDouble(txtDiscount.getText()) / 100;
    }

    public void txtDiscountOnKeyReleased(KeyEvent keyEvent) {
        validate();
        calculateSubTotal_discount();
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            btnAddItem.fire();
        }
    }

    private void calculateSubTotal_discount() {
        if (!btnAddItem.isDisable() && !txtOrderQty.getText().equals("") && !txtDiscount.getText().equals("")){
            // discount Value
            lblDiscountValue.setText(String.valueOf(BigDecimal.valueOf(calculateDiscountValue()).setScale(2,BigDecimal.ROUND_HALF_EVEN)));
            // sub total
            lblSubTotal.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(lblUnitPrice.getText())
                    * Double.parseDouble(txtOrderQty.getText())
                    - Double.parseDouble(lblDiscountValue.getText()))
                    .setScale(2,BigDecimal.ROUND_HALF_EVEN)
            ));
        }
    }

    public void txtOrderQtyOnKeyReleased(KeyEvent keyEvent) {
        validate();
        calculateSubTotal_discount();
    }

    private void validate() {
        btnAddItem.setDisable(cmbCustomerId.getValue() == null || cmbItemCode.getValue() == null);

        if (!RegexUtil.qty.matcher(txtOrderQty.getText()).matches()) {
            btnAddItem.setDisable(true);
            txtOrderQty.setFocusColor(Paint.valueOf("red"));
        }else {
            btnAddItem.setDisable(false);
            txtOrderQty.setFocusColor(Paint.valueOf("#e7a755"));
            if (!RegexUtil.qty.matcher(txtDiscount.getText()).matches()){
                btnAddItem.setDisable(true);
                txtDiscount.setFocusColor(Paint.valueOf("red"));
            }else {
                btnAddItem.setDisable(false);
                txtDiscount.setFocusColor(Paint.valueOf("#e7a755"));
            }
        }
    }

    public void backOnAction(ActionEvent actionEvent) {
        TranslateTransition slideOrders = new TranslateTransition(Duration.seconds(0.2),apnOrders);
        slideOrders.setToX(1000);
        slideOrders.play();
        generateOrderId();
        initialize();
    }

    public void btnUpdateOrderOnAction(ActionEvent actionEvent) {
        try {
            if (ordersBO.updateOrder(new OrderDTO(
                    lblOrderId.getText(),
                    orderDate.getValue(),
                    cmbCustomerId.getValue(),
                    Double.parseDouble(lblTotal.getText())
            ))) {
                new Alert(Alert.AlertType.CONFIRMATION,"Order Updated!").show();
            }else
                new Alert(Alert.AlertType.ERROR,"Order Not Updated!").show();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        loadOrderTable();
        clearAllEnteredData();
        orderDate.setValue(null);
    }

    public void btnDeleteOrderOnAction(ActionEvent actionEvent) {
        try {
            if (ordersBO.deleteOrder(lblOrderId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION,"Order Deleted!").show();
            }else
                new Alert(Alert.AlertType.ERROR,"Order Not Deleted!").show();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        loadOrderTable();
        clearAllEnteredData();
        orderDate.setValue(null);
    }

    public void txtSearchOrderOnKeyReleased(KeyEvent keyEvent) {
        if (!txtSearchOrder.getText().equals("")){
            tblOrders.getItems().clear();
            try {
                ArrayList<OrderTM> orders = ordersBO.getDataToOrderTable();
                for (OrderTM order : orders) {
                    if (order.getOrderId().toLowerCase().contains(txtSearchOrder.getText().toLowerCase())){
                        tblOrders.getItems().add(order);
                    }
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else
            loadOrderTable();
    }
}
