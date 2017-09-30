package GUI;

import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.Coordinates;
import sample.Dimension;
import java.io.File;

/**
 * Created by Daniel on 07/09/2017.
 */
public class FlowBox extends BorderPane{

    private final static String FONT_NAME = "Yu Gothic";
    private final static String BACKGROUND_COLOR = "#FFFFFF";
    private final static  String FOREGROUND_COLOR = "#06bdcf";
    private final static String TEXT_COLOR_DARK = "#111111";
    private final static String TEXT_COLOR = "#FFFFFF";

    private final static float FONT_SIZE = 12;
    private final static Border defaultBorder = new Border( new BorderStroke(Paint.valueOf("#333333"),BorderStrokeStyle.SOLID,new CornerRadii(10.0),new BorderWidths(2)) );

    private Label title;

    private ImageView defaultDeleteIcon;
    private ImageView enteredDeleteIcon;
    private ImageView pressedDeleteIcon;
    private AnchorPane deleteIcon;

    private ImageView defaultSettingIcon;
    private ImageView enteredSettingIcon;
    private ImageView pressedSettingIcon;
    private AnchorPane settingIcon;

    private ImageView defaultActionIcon;
    private ImageView enteredActionIcon;
    private ImageView pressedActionIcon;
    private AnchorPane actionIcon;

    private Coordinates origin = new Coordinates();
    private Coordinates cursor = new Coordinates();
    private Coordinates destiny = new Coordinates();

    private VBox container;
    private GridPane header;
    private AnchorPane parent;

    public FlowBox( AnchorPane parent, Dimension dimension ){
        setMinSize(dimension.getWidth(),dimension.getHeight());
        this.parent = parent;
        setProperties();
        createIcons();
        init();
        setHandlers();
    }

    private void createIcons(){

        Dimension imageDimension = new Dimension(15.0f,15.0f);
        defaultDeleteIcon = createIcon( "defaultDeleteIcon.png", imageDimension );
        deleteIcon = new AnchorPane(defaultDeleteIcon);
        enteredDeleteIcon = createIcon( "enteredDeleteIcon.png", imageDimension );
        pressedDeleteIcon = createIcon("pressedDeleteIcon.png", imageDimension );

        defaultSettingIcon = createIcon("defaultSettingIcon.png", imageDimension );
        settingIcon = new AnchorPane(defaultSettingIcon);
        enteredSettingIcon = createIcon("enteredSettingIcon.png", imageDimension);
        pressedSettingIcon = createIcon("pressedSettingIcon.png",imageDimension);

        defaultActionIcon = createIcon("defaultActionIcon.png", new Dimension(25.0f,25.0f));
        actionIcon = new AnchorPane(defaultActionIcon);

    }

    private ImageView createIcon( String pathName, Dimension dimension ){
        pathName = "src/images/" + pathName;
        ImageView imageView = new ImageView( new File(pathName).toURI().toString() );
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitWidth(dimension.getWidth());
        imageView.setFitHeight(dimension.getHeight());
        return imageView;
    }

    private void setHandlers( ){

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getHeader().setVisible(true);
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getHeader().setVisible(false);
            }
        });

        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                origin.setCoordinates(event.getSceneX(),event.getSceneY());
                cursor.setCoordinates(  event.getSceneX() - event.getX(),
                        event.getSceneY() - event.getY());
                setCursor(Cursor.MOVE);
            }
        });

        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                destiny.setCoordinates(event.getSceneX(),event.getSceneY());
                destiny.translation( origin );
                destiny.setCoordinates( cursor.getX() + destiny.getX() ,cursor.getY() + destiny.getY() );
                relocate(destiny.getX(),destiny.getY() );
            }
        });

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.DEFAULT);
            }
        });
    }

    public void setProperties(){

        setMinSize(80.0f,60.0f);
        setPadding(new Insets(10,10,10,10));
        setMargin(this,new Insets(20,20,20,20));

    }

    private GridPane createHeader(){

        final Label title = new Label("Pre-Process");
        title.setTextFill(Paint.valueOf(TEXT_COLOR));
        title.setFont( Font.font(FONT_NAME, FontWeight.BOLD,FONT_SIZE) );

        GridPane gridPaneLayout = new GridPane();
        //gridPaneLayout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFFFF"),null,null)));
        gridPaneLayout.setPadding(new Insets(10,10,10,10));

        deleteIcon.addEventFilter(MouseEvent.MOUSE_DRAGGED,new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        deleteIcon.getChildren().remove(0);
                        deleteIcon.getChildren().add(pressedDeleteIcon);
                        event.consume();
                    }
        });

        deleteIcon.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                parent.getChildren().remove(FlowBox.this);
                event.consume();
            }
        });

        deleteIcon.addEventFilter(MouseEvent.MOUSE_ENTERED,new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteIcon.getChildren().remove( 0 );
                deleteIcon.getChildren().add( enteredDeleteIcon );
                event.consume();
            }
        });

        deleteIcon.addEventFilter(MouseEvent.MOUSE_EXITED,new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               deleteIcon.getChildren().remove(0);
               deleteIcon.getChildren().add(defaultDeleteIcon);
               event.consume();
            }
        });

        settingIcon.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    private boolean clicked = true;
                    public void handle(MouseEvent event) {

                        if( clicked ){
                            setBottom(createToolPane());
                            clicked = false;
                        }
                        else{
                            setBottom(null);
                            clicked = true;
                        }
                        event.consume();
                    }
        });

        settingIcon.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                settingIcon.getChildren().remove(0);
                settingIcon.getChildren().add(pressedSettingIcon);
                event.consume();
            }
        });


        settingIcon.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                settingIcon.getChildren().remove(0);
                settingIcon.getChildren().add(enteredSettingIcon);
                event.consume();
            }
        });

        settingIcon.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                settingIcon.getChildren().remove(0);
                settingIcon.getChildren().add(defaultSettingIcon);
                event.consume();
            }
        });

        HBox container = new HBox(4);
        container.setAlignment(Pos.CENTER_RIGHT);
        container.getChildren().add(settingIcon);
        container.getChildren().add(deleteIcon);
        //iconContainer.setBackground(new Background(new BackgroundFill(Paint.valueOf("#555555"),null,null)));

        gridPaneLayout.add( title, 0,0 );
        gridPaneLayout.add( container, 1,0 );
        gridPaneLayout.setAlignment(Pos.CENTER);
        gridPaneLayout.setMinSize(getMinWidth(),.0f);
        gridPaneLayout.setHgap(60.0f);

        return gridPaneLayout;
    }

    private class Node extends AnchorPane{

        public Node( Dimension dimension ){
            setMinSize(dimension.getWidth(),dimension.getHeight());
        }


    }

    private AnchorPane createBody(){

        //Create Node
        AnchorPane node = new AnchorPane();
        node.setMinSize(80.0f,60.0f);
        node.setMaxSize(node.getMinWidth(),node.getMinHeight());
        node.setBorder( new Border( new BorderStroke(Paint.valueOf(BACKGROUND_COLOR),BorderStrokeStyle.SOLID,new CornerRadii(20.0),new BorderWidths(2.0f))));

        //create Channels
        //Input
        Circle input = new Circle( 7 );
        input.setFill(Paint.valueOf("#222222"));
        input.setStroke(Paint.valueOf(BACKGROUND_COLOR));
        input.setStrokeWidth(2.0f);
        input.setLayoutY(30.0f);
        input.setLayoutX(0.0f);

        //Output
        Circle output = new Circle( 7 );
        output.setFill(Paint.valueOf("#222222"));
        output.setStroke(Paint.valueOf(BACKGROUND_COLOR));
        output.setStrokeWidth(2.0f);
        output.setLayoutY(30.0f);
        output.setLayoutX(80.0f);

        //Icon
        defaultActionIcon = createIcon("defaultActionIcon.png", new Dimension(25.0f,25.0f) );
        actionIcon = new AnchorPane(defaultActionIcon);

        //Layout
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setMinSize(getMinWidth(),getMinHeight());
        //container.setBackground(new Background(new BackgroundFill(Paint.valueOf("#06bdcf"),null,null)));
        container.getChildren().add(defaultActionIcon);
        container.setBlendMode(BlendMode.SRC_OVER);

        node.getChildren().add(input);
        node.getChildren().add(container);
        node.getChildren().add(output);

        return node;

    }

    private VBox createToolPane(){

        final VBox container = new VBox(5);
        container.setMinSize(getMinWidth(),200);
        //container.setBorder( defaultBorder );
        setMargin(container,new Insets(10,10,10,10));
        container.setBackground(new Background(new BackgroundFill(Paint.valueOf("#000000"),new CornerRadii(10.0f),null)));
        container.setOpacity(0.2);
        container.setEffect( null );
        return container;

    }

    public void init(){

        setHeader(createHeader());
        setTop(getHeader());
        getHeader().setVisible(false);
        setCenter(createBody());
        setBottom(null);

    }

    public String getTitle(){
        return this.title.getText();
    }

    public void setTitle(String title){
        this.title.setText(title);
    }


    public GridPane getHeader() {
        return header;
    }

    public void setHeader(GridPane header) {
        this.header = header;
    }

    public VBox getContainer() {
        return container;
    }

    public void setContainer(VBox container) {
        this.container = container;
    }
}
