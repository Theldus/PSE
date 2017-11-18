package sample.dialogs;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import sample.workspace.Workspace;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static sample.util.Appearance.*;

/**
 * Created by Daniel on 06/11/2017.
 */
public final class Toast extends BorderPane {

    private Label title;
    private Label msg;
    private ImageView icon;
    private Paint backgroudColor = Paint.valueOf("#000000");
    public final static String INFORMATION_MESSAGE = "Information";
    public final static String WARNING_MESSAGE = "Warning";
    public final static String ERROR_MESSAGE = "Error";

    public Toast(String title, String msg, String iconName){
        setTitle(new Label(title));
        setMsg(new Label(msg));
        setIcon(new ImageView( sample.Main.class.getResource(ICONS_PATH + iconName + ICONS_EXT).toString() ) );
        create();
    }

    private HBox createTitle(){
        final HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(10,5,10,5));

        this.getTitle().setTextFill(Paint.valueOf(TEXT_COLOR));
        this.getTitle().setFont( Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );
        this.getTitle().setMaxWidth( 150.0f );

        layout.getChildren().add(this.getTitle());
        return layout;

    }

    public static void show(Workspace root, String toastType, String msg, String icon , int toastDelay, int fadeInDelay, int fadeOutDelay, String audioPath){

        final Toast notif = new Toast(toastType,msg,icon);
        root.getChildren().add(notif);
        notif.setLayoutX(root.getWidth() - ( notif.getMinWidth() )  );
        notif.setLayoutY( 30 );
        Timeline fadeIn = new Timeline();
        KeyFrame fadeInKey = new KeyFrame(Duration.millis(fadeInDelay),new KeyValue(notif.opacityProperty(),1));
        fadeIn.getKeyFrames().add(fadeInKey);

        //Emit sound
        if( audioPath != null ){

            String musicFile = "audio/" + audioPath + ".wav";     // For example

            Media sound = null;
            try {
                sound = new Media( new File( sample.Main.class.getResource(musicFile).toURI().toString() ).toString() );
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

        }

        fadeIn.setOnFinished( (actionEvent) ->

            new Thread(()->{

                try {
                    Thread.sleep(toastDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        this.msg.setMaxWidth(320.0f);
        this.msg.setFont( Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );

        layout.getChildren().add(getIcon());
        layout.getChildren().add(msg);

        return layout;
    }

    public void create(){
        setBackground(new Background(new BackgroundFill(backgroudColor,null,null)));
        setPadding(new Insets(10,10,10,10));
        setMinSize(350.0f,0.0);
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

    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }
}
