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

/**
 * Main class. Here is the the entry point
 * of this PSE Image.
 * @author Daniel
 */
public class
Main extends Application {

    /**
     * Starts the application.
     * @param primaryStage Stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        PSEMainLayout pseMainLayout = new PSEMainLayout(primaryStage);
        pseMainLayout.show();

    }

    /**
     * Main function
     * @param args Argument list.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
