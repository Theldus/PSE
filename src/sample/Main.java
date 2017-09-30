package sample;

import GUI.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml");

        Workspace workspace = new Workspace(new Dimension(1024.0,768.0));

        FlowBox2 flowBox2[] = new FlowBox2[3];

        flowBox2[0] = new FlowBox2("Aquisição",workspace,"defaultActionIcon.png");
        flowBox2[1] = new FlowBox2("Filtro",workspace,"defaultFilterIcon.png");
        flowBox2[2] = new FlowBox2("Exibição",workspace,"defaultPlayIcon.png");

        primaryStage.setTitle("Hello World");
        primaryStage.setScene( new Scene(workspace) );
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
