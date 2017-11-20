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

import java.net.URISyntaxException;

import static sample.util.Appearance.*;

/**
 * Show a toast in the upper right corner just like the Android does.
 * @author Daniel, Davidson
 * @since 2017-11-06
 */
public final class Toast extends BorderPane {

    private Label title;
    private Label msg;
    private ImageView icon;
    private Paint backgroudColor = Paint.valueOf("#000000");
    public final static String INFORMATION_MESSAGE = "Information";
    public final static String WARNING_MESSAGE = "Warning";
    public final static String ERROR_MESSAGE = "Error";

    /**
     * Toast constructor.
     * @param title Title.
     * @param msg Message.
     * @param iconName Icon name.
     */
    public Toast(String title, String msg, String iconName){
        setTitle(new Label(title));
        setMsg(new Label(msg));
        setIcon(new ImageView( sample.Main.class.getResource(ICONS_PATH + iconName + ICONS_EXT).toString() ) );
        create();
    }

    /**
     * Creates the title of the Toast.
     * @return Returns a Horizontal Box (HBox).
     */
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

    /**
     * Shows a Toast into the screen.
     * @param root Where to insert, in the case: Workspace instance.
     * @param toastType Toast title.
     * @param msg Message.
     * @param icon Toast icon.
     * @param toastDelay Time in the screen, milliseconds.
     * @param fadeInDelay Fade-in delay, milliseconds.
     * @param fadeOutDelay Fade-out delay, milliseconds.
     * @param audioPath Audio, can be null if none.
     */
    public static void show(Workspace root, String toastType, String msg, String icon , int toastDelay, int fadeInDelay, int fadeOutDelay, String audioPath){

        final Toast notif = new Toast(toastType,msg,icon);
        root.getChildren().add(notif);
        notif.setLayoutX(root.getWidth() - ( notif.getMinWidth() )  );
        notif.setLayoutY( 30 );
        Timeline fadeIn = new Timeline();
        KeyFrame fadeInKey = new KeyFrame(Duration.millis(fadeInDelay),new KeyValue(notif.opacityProperty(),1));
        fadeIn.getKeyFrames().add(fadeInKey);

        /* Emit sound. */
        if( audioPath != null ){

            String musicFile = "audio/" + audioPath + ".wav";

            Media sound = null;
            try {
                sound = new Media( sample.Main.class.getResource(musicFile).toURI().toString() );
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

    /**
     * Creates the Toast body.
     * @return Returns a Horizontal-Box (HBox).
     */
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

    /**
     * Creates the Toast itself.
     */
    public void create(){
        setBackground(new Background(new BackgroundFill(backgroudColor,null,null)));
        setPadding(new Insets(10,10,10,10));
        setMinSize(350.0f,0.0);
        setTop(createTitle());
        setCenter(createBody());
        setEffect(new DropShadow(5,Color.valueOf("#222222")));

    }

    /**
     * Gets the Toast message.
     * @return Message.
     */
    public Label getMsg() {
        return msg;
    }

    /**
     * Sets the Toast message.
     * @param msg Message.
     */
    public void setMsg(Label msg) {
        this.msg = msg;
    }

    /**
     * Gets the Toast title.
     * @return Toast title.
     */
    public Label getTitle() {
        return title;
    }

    /**
     * Sets the Toast title.
     * @param title Toast title.
     */
    public void setTitle(Label title) {
        this.title = title;
    }

    /**
     * Gets the Toast icon.
     * @return Toast icon.
     */
    public ImageView getIcon() {
        return icon;
    }

    /**
     * Sets the Toast icon.
     * @param icon Toast icon (ImageView).
     * @apiNote Every Toast should have an icon left-aligned.
     */
    public void setIcon(ImageView icon) {
        this.icon = icon;
    }
}
