package lk.mega.pos.util;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

public class ClearDataUtil {

    public static void clearCmb(JFXComboBox...cmb){
        for (JFXComboBox comboBox : cmb) {
            comboBox.getSelectionModel().clearSelection();
        }
    }

    public static void clearTextFields(JFXTextField...txt){
        for (JFXTextField field : txt) {
            field.setText("");
        }
    }

    public static void clearLabels(Label...lbl){
        for (Label label : lbl) {
            label.setText("");
        }
    }

}
