package lk.mega.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.mega.pos.util.NavigationUtil;
import lk.mega.pos.util.RegexUtil;

import java.io.IOException;

public class LoginFormController {
    public Label lblSwitchAdmin_User;
    public AnchorPane apnLogin;
    public JFXTextField txtUserName;
    public JFXPasswordField pwPassword;
    public JFXButton btnClose;
    public JFXButton btnSwitch;

    public void initialize(){
        btnSwitch.setDisable(true);
        setTitle();
    }

    private void setTitle() {
        if (MainFormController.isThisAdmin) {
            lblSwitchAdmin_User.setText("Switch to User");
        }else
            lblSwitchAdmin_User.setText("Switch to Admin");
    }

    public void btnSwitchOnAction(ActionEvent actionEvent) {
        if (MainFormController.isThisAdmin){
            if (txtUserName.getText().equals("User") && pwPassword.getText().equals("1234")){
                MainFormController.isThisAdmin = !MainFormController.isThisAdmin;
                new Alert(Alert.AlertType.INFORMATION,"You are Now User!").show();
                btnClose.fire();
            }
            else
                new Alert(Alert.AlertType.ERROR,"Username or Password Incorrect!").show();
        }else {
            if (txtUserName.getText().equals("Admin") && pwPassword.getText().equals("1234")){
                MainFormController.isThisAdmin = !MainFormController.isThisAdmin;
                new Alert(Alert.AlertType.INFORMATION,"You are Now Admin!").show();
                btnClose.fire();
            }
            else
                new Alert(Alert.AlertType.ERROR,"Username or Password Incorrect!").show();
        }
    }

    public void btnCloseOnAction(ActionEvent actionEvent) throws IOException {
        NavigationUtil.closeApn(apnLogin);
        NavigationUtil.replaceApn(MainFormController.mainApn,"MainForm");
    }

    public void unOnKeyReleased(KeyEvent keyEvent) {
        validate();
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            pwPassword.requestFocus();
        }
    }

    public void pwOnKeyReleased(KeyEvent keyEvent) {
        validate();
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            btnSwitch.fire();
        }
    }

    private void validate() {

        if (!RegexUtil.name.matcher(txtUserName.getText()).matches()){
            btnSwitch.setDisable(true);
            txtUserName.setFocusColor(Paint.valueOf("red"));
        }else {
            btnSwitch.setDisable(false);
            txtUserName.setFocusColor(Paint.valueOf("#e7a755"));
            if (!RegexUtil.qty.matcher(pwPassword.getText()).matches()){
                btnSwitch.setDisable(true);
                pwPassword.setFocusColor(Paint.valueOf("red"));
            }else {
                btnSwitch.setDisable(false);
                pwPassword.setFocusColor(Paint.valueOf("#e7a755"));
            }
        }

        if (txtUserName.getText().equals("") || pwPassword.getText().equals("")) {
            btnSwitch.setDisable(true);
        }
    }
}
