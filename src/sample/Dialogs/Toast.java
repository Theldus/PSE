package sample.Dialogs;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import sample.Workspace.Workspace;

import java.io.File;

import static sample.util.Appearance.FONT_NAME;
import static sample.util.Appearance.FONT_SIZE;
import static sample.util.Appearance.TEXT_COLOR;

/**
 * Created by Daniel on 06/11/2017.
 */
public final class Toast extends BorderPane {

    private Label title = new Label("Information");
    private Label msg;
    private ImageView icon = new ImageView( sample.Main.class.getResource("icons/CheckmarkIcon.png").toString() );
    private Paint backgroudColor = Paint.valueOf("#000000");

    public Toast(String msg){
        setMsg(new Label(msg));
        create();
    }

    private HBox createTitle(){
        final HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(10,5,10,5));

        this.title.setTextFill(Paint.valueOf(TEXT_COLOR));
        this.title.setFont( Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );
        this.title.setMaxWidth( 150.0f );

        layout.getChildren().add(this.title);
        return layout;

    }

    public static void show(Workspace root, String msg, int toastDelay, int fadeInDelay, int fadeOutDelay){

        final Toast notif = new Toast(msg);
        root.getChildren().add(notif);
        notif.setLayoutX(root.getWidth() - ( notif.getMinWidth() + 40 )  );
        notif.setLayoutY( 30 );
        System.out.println(root.getLayoutX() + " " + root.getLayoutY() );
        Timeline fadeIn = new Timeline();
        KeyFrame fadeInKey = new KeyFrame(Duration.millis(fadeInDelay),new KeyValue(notif.opacityProperty(),1));
        fadeIn.getKeyFrames().add(fadeInKey);
        fadeIn.setOnFinished( (actionEvent) ->

            new Thread(()->{

                try{
                    Thread.sleep(toastDelay);
                }
                catch (InterruptedException ex){
                    ex.printStackTrace();
                }

                Timeline fadeOutTimeline = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue (notif.opacityProperty(), 0));
                fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                root.getChildren().remove(notif);
                fadeOutTimeline.play();

            }).run()
        );

        fadeIn.play();

    }

    private HBox createBody(){
        final HBox layout = new HBox(10);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(10,5,10,5));

        this.msg.setTextFill(Paint.valueOf(TEXT_COLOR));
        this.msg.setFont( Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );

        layout.getChildren().add(icon);
        layout.getChildren().add(msg);

        return layout;
    }

    public void create(){
        setBackground(new Background(new BackgroundFill(backgroudColor,null,null)));
        setPadding(new Insets(10,10,10,10));
        setMinSize(270,90);
        setTop(createTitle());
        setCenter(createBody());
        setEffect(new DropShadow(5,Color.valueOf("#222222")));

    }

    public Label getMsg() {
        return msg;
    }

    public void setMsg(Label msg) {
        this.msg = msg;
    }

}
