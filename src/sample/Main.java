package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sample.sideMenu.VMenuBar;
import sample.sideMenu.VMenuItem;

import java.awt.*;

public class
Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        PSEMainLayout pseMainLayout = new PSEMainLayout(primaryStage);
        pseMainLayout.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
