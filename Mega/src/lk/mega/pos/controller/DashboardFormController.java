package lk.mega.pos.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import lk.mega.pos.bo.BOFactory;
import lk.mega.pos.bo.custom.CustomerBO;
import lk.mega.pos.bo.custom.ItemBO;
import lk.mega.pos.bo.custom.OrdersBO;
import lk.mega.pos.bo.custom.impl.ItemBOImpl;
import lk.mega.pos.bo.custom.impl.OrdersBOImpl;
import lk.mega.pos.dto.CustomerDTO;
import lk.mega.pos.dto.ItemDTO;
import lk.mega.pos.presentation.tdm.OrderTM;
import lk.mega.pos.util.NavigationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;

public class DashboardFormController {
    public PieChart pieChartItemQty;
    public Label lblLeastQty;

    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    private final OrdersBO ordersBO = (OrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    public Label lblCustomer;
    public Label lblItem;
    public Label lblOrder;

    public void initialize(){
        pieChartSetup();
        labelSetup();
    }

    private void labelSetup() {
        try {
            lblLeastQty.setText(itemBO.leastStockItem().getCode() + " - Only " + itemBO.leastStockItem().getQtyOnHand() + " items left");

            lblCustomer.setText(String.valueOf(customerBO.getAllCustomers().size()));
            lblItem.setText(String.valueOf(itemBO.getAllItems().size()));
            lblOrder.setText(String.valueOf(ordersBO.getAllOrders().size()));
            //
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void pieChartSetup() {
        // pie chart Item Qty
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                chartData.add(new PieChart.Data(item.getCode(), item.getQtyOnHand()));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        chartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName()," ",data.pieValueProperty())
                )
        );
        // add data to the chart
        pieChartItemQty.setData(chartData);
    }

    public void goToItemTableOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.setChildApn(MainFormController.mainApn,"ItemsForm");
    }
}
