package lk.mega.pos.controller;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.mega.pos.bo.BOFactory;
import lk.mega.pos.bo.custom.ItemBO;
import lk.mega.pos.bo.custom.impl.ItemBOImpl;
import lk.mega.pos.dto.ItemDTO;
import lk.mega.pos.presentation.tdm.ItemSellingTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemSellingAnalysisController {
    public AreaChart<String,Number> chartSales;
    public TableView<ItemSellingTM> tblItemSelling;
    public Label lblMostMovableItem;
    public Label lblLeastMovableItem;
    public Label lblAllItemVariations;

    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize(){
        chartSetup();
        tableSetup();
        labelsSetup();
    }

    private void labelsSetup() {
        try {
            lblMostMovableItem.setText(itemBO.mostMovedItem());
            lblLeastMovableItem.setText(itemBO.leastMovedItem());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        lblAllItemVariations.setText(String.valueOf(tblItemSelling.getItems().size()));
    }

    private void tableSetup() {
        tblItemSelling.getItems().clear();
        tblItemSelling.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemSelling.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItemSelling.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItemSelling.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItemSelling.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("soldCount"));
        tblItemSelling.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("totalIncome"));

        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO itemDTO : allItems) {
                tblItemSelling.getItems().add(new ItemSellingTM(
                        itemDTO.getCode(),
                        itemDTO.getDescription(),
                        itemDTO.getUnitPrice(),
                        itemDTO.getQtyOnHand(),
                        itemDTO.getSoldCount()
                ));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void chartSetup() {
        XYChart.Series series = new XYChart.Series();
        series.setName("Sold qty");

        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO itemDTO : allItems) {
                series.getData().add(new XYChart.Data(itemDTO.getCode(),itemDTO.getSoldCount()) );
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        chartSales.getData().add(series);
    }
}
