package sample.sideMenu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import sample.dialogs.Toast;
import sample.json.NodeBoxData;
import sample.MainController;
import sample.workspace.Workspace;
import sample.nodes.NodeBox;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.chrono.MinguoChronology;

import static sample.util.Appearance.*;
import static sample.util.Appearance.FONT_SIZE;

/**
 * ItemView class, each item in the algorithm
 * selection menu is actually an ItemView.
 * @author Daniel
 * @since 2017-11-02
 */
public class ItemView extends HBox {

    /* Private data. */
    private NodeBoxData content;
    private Label title;
    private Text description;

    /**
     * ItemView constructor
     * @param content NodeBoxData object
     */
    public ItemView(NodeBoxData content){
        this.content = content;
        createItemView();
    }

    /**
     * Creates the left side Icon
     * @return Returns a StackPane
     */
    public StackPane createIcon(){
        StackPane layout = new StackPane();
        layout.setAlignment(Pos.CENTER);

        final ImageView imageView = new ImageView(
                sample.Main.class.getResource(ICONS_PATH + content.getIconPath() + ICONS_EXT).toString() );

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitHeight(25.0f);
        imageView.setFitWidth(25.0f);

        layout.getChildren().add(imageView);

        return layout;
    }

    /**
     * Creates the title and description text
     * @return Returns a VBox containing the texts.
     */
    public VBox createText(){

        final VBox layout = new VBox(5.0f);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setMaxWidth(300.0f);

        title = new Label(content.getName());
        title.setTextFill(javafx.scene.paint.Paint.valueOf(TEXT_COLOR));
        title.setFont( javafx.scene.text.Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );

        description = new Text(content.getDescription());
        description.setFill(Paint.valueOf("#ffffff"));
        description.setFont( javafx.scene.text.Font.font(FONT_NAME, FontWeight.BOLD,12.0f) );
        description.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);

        layout.getChildren().add(title);
        layout.getChildren().add(description);

        return layout;

    }

    /**
     * Creates the ItemView.
     */
    public void createItemView(){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(5.0f);
        this.setPadding(new Insets(10,10,10,10));
        this.getChildren().add( createIcon() );
        this.getChildren().add( createText() );
        setEvent();
    }

    /**
     * Setup all the events.
     */
    public void setEvent(){

        /* MouseEntered event. */
        this.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ItemView.this.title.setTextFill(Paint.valueOf("#00ffb2"));
                ItemView.this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#131313"),null,null)));
                event.consume();
            }
        });

        /* MouseExited event. */
        this.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //description.setFill(Paint.valueOf("#AAAAAA"));
                ItemView.this.title.setTextFill(Paint.valueOf("#ffffff"));
                ItemView.this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#000000"),null,null)));
                event.consume();
            }
        });

        /* MouseClicked event, here we instantiate the NodeBox properly. */
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                try {
                    NodeBox nodeBox;

                    /* NodeBoxes default.*/
                    if (content.getClassLoader() == null){
                        nodeBox = (NodeBox) Class.forName("sample.nodes."+content.getClassName())
                                .getConstructor(String.class,Workspace.class,String.class)
                                .newInstance(content.getName(),
                                MainController.getInstance().getCurrentWorkspace(),
                                content.getIconPath());
                    }

                    /* NodeBoxes loaded dynamically, aka plugin. */
                    else{
                        Class classObj = content.getClassLoader().loadClass( content.getClassName() );

                        nodeBox = (NodeBox) classObj.getConstructor(String.class, Workspace.class, String.class)
                                .newInstance(content.getName(),
                                MainController.getInstance().getCurrentWorkspace(),
                                content.getIconPath());
                    }

                    /* Adds into the Workspace. */
                    MainController.getInstance().getCurrentWorkspace().getChildren().add(nodeBox);

                    /* Set static position node. */
                    nodeBox.setLayoutX(350.0f);
                    nodeBox.setLayoutY(0);

                    nodeBox.toBack();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    event.consume();
                }
            }
        });
    }
}
