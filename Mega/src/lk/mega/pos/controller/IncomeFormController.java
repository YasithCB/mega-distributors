package lk.mega.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.mega.pos.bo.BOFactory;
import lk.mega.pos.bo.custom.OrdersBO;
import lk.mega.pos.bo.custom.impl.OrdersBOImpl;
import lk.mega.pos.presentation.tdm.OrderTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class IncomeFormController {
    public AnchorPane apnMain;
    public Label lblAccountType;
    public JFXDatePicker dpckrSelectDate;
    public JFXComboBox<String> cmbSelectMonth;
    public JFXComboBox<Integer> cmbSelectYear;
    public Label lblIncome;
    public AreaChart<String,Number> chartIncome;
    public JFXComboBox<String> cmbSeason;
    public AnchorPane apnTable;
    public TableView<OrderTM> tblOrders;
    public JFXButton btnTable_Chart;

    private final OrdersBO ordersBO = (OrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    public void initialize(){
        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        tblOrders.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrders.setTranslateY(500);
        initializeCmb();
        setupChartAllTime();
        setupTableAllTime();
        calculateTotalIncomeAllTime();

        showSeason();
    }

    private void calculateTotalIncomeAllTime() {
        double total = 0;
        for (OrderTM orderTM : tblOrders.getItems()) {
            total += orderTM.getTotal();
        }
        lblIncome.setText(String.format("%.2f",total));
    }

    private void showSeason() {
        disableAllDateSelectors();

        cmbSeason.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue){
                case "Day" : {
                    cmbSelectYear.setVisible(false);
                    cmbSelectMonth.setVisible(false);
                    dpckrSelectDate.setVisible(true);
                    calculateTotalIncomeOnDay();
                    setupChartAllTime();
                    setupTableOnDay();
                    return;
                }
                case "Month" : {
                    cmbSelectYear.setVisible(true);
                    cmbSelectMonth.setVisible(true);
                    dpckrSelectDate.setVisible(false);
                    calculateTotalIncomeOnMonth();
                    setupTableMonth();
                    setupChartMonth();
                    return;
                }
                case "Year" : {
                    cmbSelectYear.setVisible(true);
                    cmbSelectMonth.setVisible(false);
                    dpckrSelectDate.setVisible(false);
                    calculateTotalIncomeOnYear();
                    setupTableYear();
                    setupChartAllTime();
                    return;
                }
                case "All Time" : {
                    disableAllDateSelectors();
                    setupChartAllTime();
                    setupTableAllTime();
                }
            }
        });
    }

    private void setupTableOnDay() {
        dpckrSelectDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            tblOrders.getItems().clear();
            try {
                ArrayList<OrderTM> orderTMS = ordersBO.getDataToOrderTableOnDay(newValue);
                for (OrderTM orderTM : orderTMS) {
                    tblOrders.getItems().add(orderTM);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void setupTableYear() {
        cmbSelectYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tblOrders.getItems().clear();
            try {
                ArrayList<OrderTM> orderTMS = ordersBO.getDataToOrderTableOnYear(newValue);
                for (OrderTM orderTM : orderTMS) {
                    tblOrders.getItems().add(orderTM);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void setupTableMonth() {
        cmbSelectMonth.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            tblOrders.getItems().clear();
            try {
                ArrayList<OrderTM> orderTMS = ordersBO.getDataToOrderTableOnMonth(cmbSelectYear.getValue(), newValue.intValue() + 1);
                for (OrderTM orderTM : orderTMS) {
                    tblOrders.getItems().add(orderTM);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void setupTableAllTime() {
        tblOrders.getItems().clear();
        try {
            ArrayList<OrderTM> allOrders = ordersBO.getDataToOrderTable();
            for (OrderTM order : allOrders) {
                tblOrders.getItems().add(order);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void calculateTotalIncomeOnYear() {
        cmbSelectYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                lblIncome.setText(String.format("%.2f",ordersBO.getTotalIncomeOnYear(newValue)));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void calculateTotalIncomeOnMonth() {
        cmbSelectMonth.getSelectionModel().selectedIndexProperty().addListener((observableM, oldValueM, newValueM) -> {
            setTotalLabelForMonth(cmbSelectYear.getValue(),newValueM.intValue()+1);
        });
        cmbSelectYear.getSelectionModel().selectedItemProperty().addListener((observableY, oldValueY, newValueY) -> {
            setTotalLabelForMonth(newValueY,cmbSelectMonth.getSelectionModel().getSelectedIndex()+1);
        });

    }

    private void setTotalLabelForMonth(int year, int month) {
        try {
            lblIncome.setText(String.format("%.2f",ordersBO.getTotalIncomeOnMonth(year,month)));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void calculateTotalIncomeOnDay() {
        dpckrSelectDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                lblIncome.setText(String.valueOf(ordersBO.getTotalIncomeOnDay(newValue)));
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void disableAllDateSelectors() {
        cmbSelectYear.setVisible(false);
        cmbSelectMonth.setVisible(false);
        dpckrSelectDate.setVisible(false);
    }

    private void setupChartMonth() {
        cmbSelectMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (cmbSelectYear.getValue() == null) {
                cmbSelectYear.setValue(1);
            }
            chartIncome.getData().clear();
            XYChart.Series series = new XYChart.Series();
            series.setName(newValue);

            for (int i = 1; i < 29 ; i++) {
                try {
                    series.getData().add(new XYChart.Data<>(i+"",ordersBO.getTotalIncomeOnDay(LocalDate.of(cmbSelectYear.getValue(),cmbSelectMonth.getSelectionModel().getSelectedIndex()+1,i))));
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
            chartIncome.getData().add(series);
        });
    }

    private void setupChartAllTime() {
        chartIncome.getData().clear();

        XYChart.Series series2022 = new XYChart.Series();
        series2022.setName("2022");
        XYChart.Series series2021 = new XYChart.Series();
        series2021.setName("2021");

        for (int i = 1; i < 13 ; i++) {
            addMonthDataToSeries(series2022,2022,i);
            addMonthDataToSeries(series2021,2021,i);
        }

        chartIncome.getData().addAll(series2022,series2021);
    }

    private void addMonthDataToSeries(XYChart.Series series2022,int year, int month) {
        try {
            series2022.getData().add(new XYChart.Data<>(getMonthsList().get(month-1),ordersBO.getTotalIncomeOnMonth(year,month)));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializeCmb() {
        cmbSelectMonth.setItems(getMonthsList());

        cmbSelectYear.getItems().add(2020);
        cmbSelectYear.getItems().add(2021);
        cmbSelectYear.getItems().add(2022);
        cmbSelectYear.getItems().add(2023);
        cmbSelectYear.getItems().add(2024);
        cmbSelectYear.getItems().add(2025);

        cmbSeason.getItems().add("Month");
        cmbSeason.getItems().add("Day");
        cmbSeason.getItems().add("Year");
        cmbSeason.getItems().add("All Time");
    }

    private ObservableList<String> getMonthsList() {
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        return months;
    }

    public void switchTable_ChartOnAction(ActionEvent actionEvent) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2),tblOrders);
        if (btnTable_Chart.getText().equals("Go to Table View")){
            chartIncome.setVisible(false);
            btnTable_Chart.setText("Go to Chart View");
            transition.setToY(0);
            transition.play();
        }else {
            btnTable_Chart.setText("Go to Table View");
            transition.setToY(500);
            transition.play();
            transition.setOnFinished(event -> {
                chartIncome.setVisible(true);
            });
        }
    }
}
