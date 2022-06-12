package lk.mega.pos.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NavigationUtil {
    static double x,y;

    public static void setChildApn(AnchorPane apn, String location) throws IOException {
        apn.getChildren().clear();
        apn.getChildren().add(FXMLLoader.load(NavigationUtil.class.getResource("../presentation/"+location+".fxml")));
    }

    public static void replaceApn(AnchorPane apn, String location) throws IOException {
        Stage stage = (Stage) apn.getScene().getWindow();
        Parent root = FXMLLoader.load(NavigationUtil.class.getResource("../presentation/"+location+".fxml"));
        stage.setScene(new Scene(root));

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.centerOnScreen();
        stage.show();
    }

    public static void newApn(String location) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(NavigationUtil.class.getResource("../presentation/" + location + ".fxml"))));
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void closeApn(AnchorPane apn){
        Stage stage = (Stage) apn.getScene().getWindow();
        stage.close();
    }
}
