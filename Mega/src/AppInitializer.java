import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppInitializer extends Application {

    double x,y;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("lk/mega/pos/presentation/MainForm.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.centerOnScreen();

        parent.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        parent.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setTitle("Mega");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }
}
