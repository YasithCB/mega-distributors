package lk.mega.pos.controller;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.mega.pos.util.NavigationUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainFormController {

    public static boolean isThisAdmin = false;

    public AnchorPane apnForChildElements;
    public ImageView imgSettings;
    public ImageView imgHelp;
    public ImageView imgAbout;
    public ImageView imgNotification;
    public Label lblAccountType;
    public JFXButton btnIncome;
    public Label lblDay;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnItemSellingAnalysis;
    public ImageView imgSwitchAccount;
    public JFXButton btnSwitchAccount;

    public static AnchorPane mainApn;

    public void initialize() throws IOException {
        mainApn = apnForChildElements;
        NavigationUtil.setChildApn(mainApn,"DashboardForm");

        btnIncome.setDisable(!isThisAdmin);
        btnItemSellingAnalysis.setDisable(!isThisAdmin);

        lblAccountType.setText(isThisAdmin? "A D M I N" : "U S E R");
        setDateAndTime();

        imgSwitchAccount.setOnMouseClicked(event -> {
            btnSwitchAccount.fire();
        });
        lblAccountType.setOnMouseClicked(event -> {
            btnSwitchAccount.fire();
        });
    }

    public void setDateAndTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            lblDate.setText(LocalDate.now().toString());
            lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            lblDay.setText(LocalDate.now().getDayOfWeek().toString());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void DashboardOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.setChildApn(mainApn,"DashboardForm");
    }

    public void ordersOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.setChildApn(mainApn,"OrdersForm");
    }

    public void itemsOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.setChildApn(mainApn,"ItemsForm");
    }

    public void customersOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.setChildApn(mainApn,"CustomersForm");
    }

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtil.closeApn(mainApn);
    }

    public void switchToAdmin_UserOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.newApn("LoginForm");
    }

    public void incomeOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.setChildApn(mainApn,"IncomeForm");
    }

    public void itemSellingAnalysisOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.setChildApn(mainApn,"ItemSellingAnalysis");
    }
}
