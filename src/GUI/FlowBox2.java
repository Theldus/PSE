package GUI;

import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import sample.Component;
import sample.Coordinates;
import sample.Dimension;

import java.io.File;

/**
 * Created by Daniel on 09/09/2017.
 */
public class FlowBox2 extends BorderPane {

    private final static String FONT_NAME = "Yu Gothic";
    private final static String BACKGROUND_COLOR = "#111111";
    private final static  String FOREGROUND_COLOR = "#FFFFFF";
    private final static String TEXT_COLOR = "#FFFFFF";
    private final static float FONT_SIZE = 13.0f;

    private Image image;

    private static Insets defaultPadding = new Insets(5.0f,2.5f,5.0f,2.5f);
    private static CornerRadii defaultCornerRadii = new CornerRadii(20.0f);
    private static BorderWidths defaultBorderWidths = new BorderWidths(2.0f);
    private static Border defaultBorder = new Border(   new BorderStroke(Paint.valueOf(FOREGROUND_COLOR),
                                                        BorderStrokeStyle.SOLID,
                                                        defaultCornerRadii,
                                                        defaultBorderWidths) );
    private final AnchorPane root;
    private final static Dimension dimension = new Dimension(74.0f,64.0f);

    private Coordinates origin = new Coordinates();
    private Coordinates cursor = new Coordinates();
    private Coordinates destiny = new Coordinates();

    private final Header header;
    private final Node node;
    private final ToolPane toolPane;
    private String actionIcon;

    public FlowBox2(String title,AnchorPane root,String imagePath ){
        this.setMinSize(dimension.getWidth(),dimension.getHeight());
        this.root = root;
        actionIcon = imagePath;
        this.header = new Header(title);
        this.node = new Node();
        this.toolPane = new ToolPane();
        setProperties();
        initialize();
        setEvents();
        install();
    }

    public void install(){
        root.getChildren().add(this);
    }

    public void initialize(){

        setTop(header);
        setCenter(node);

    }

    private void setProperties(){
        setMinSize(dimension.getWidth(),dimension.getHeight());
        setPadding(defaultPadding);
        //setBackgroundColor(this,FOREGROUND_COLOR,null,null);
    }

    public void setEvents(){

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                header.setVisible(true);
                node.getChildren().add(node.input);
                node.getChildren().add(node.output);
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                header.setVisible(false);
                node.getChildren().remove(node.input);
                node.getChildren().remove(node.output);
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


    private ImageView createIcon(String pathName, Dimension dimension ){
        pathName = "src/images/" + pathName;
        ImageView imageView = new ImageView( new File(pathName).toURI().toString() );
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitWidth(dimension.getWidth());
        imageView.setFitHeight(dimension.getHeight());
        return imageView;
    }

    private class Header extends GridPane{

        private Label title;
        private HBox container;

        private ImageView defaultDeleteIcon;
        private ImageView enteredDeleteIcon;
        private ImageView pressedDeleteIcon;
        private AnchorPane deleteIcon;

        private ImageView defaultSettingIcon;
        private ImageView enteredSettingIcon;
        private ImageView pressedSettingIcon;
        private AnchorPane settingIcon;

        public Header( String title ){
            this.title = createTitle(title);
            this.container = createContainer();
            setProperties();
            setEvents();
            initilize();
        }

        private void setProperties(){
            setMinSize(getMinWidth(),0.0f);
            setPadding(defaultPadding);
            //setBackgroundColor(this,"#333333",null,null);
            //setGridLinesVisible(true);
            setAlignment(Pos.CENTER);
            setHgap(20.0f);
        }

        private void initilize(){
            add( title, 0,0 );
            add( container, 1,0 );
        }

        private Label createTitle(String title){
            final Label auxTitle = new Label(title);
            auxTitle.setTextFill(Paint.valueOf(TEXT_COLOR));
            auxTitle.setFont( Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );
            auxTitle.setMaxWidth( 150.0f );
            return auxTitle;
        }

        private HBox createContainer(){
            final HBox container = new HBox(5.0f);
            container.setAlignment(Pos.CENTER_RIGHT);

            final Dimension imageDimension = new Dimension(13.0f,13.0f);

            // Create and config Delete icon
            defaultDeleteIcon = createIcon( "defaultDeleteIcon.png", imageDimension );
            deleteIcon = new AnchorPane(defaultDeleteIcon);
            enteredDeleteIcon = createIcon( "enteredDeleteIcon.png", imageDimension );
            pressedDeleteIcon = createIcon("pressedDeleteIcon.png", imageDimension );

            //Create and config Setting icon
            defaultSettingIcon = createIcon("defaultSettingIcon.png", imageDimension );
            settingIcon = new AnchorPane(defaultSettingIcon);
            enteredSettingIcon = createIcon("enteredSettingIcon.png", imageDimension);
            pressedSettingIcon = createIcon("pressedSettingIcon.png",imageDimension);

            //add icons
            container.getChildren().add(settingIcon);
            container.getChildren().add(deleteIcon);

            return container;
        }

        private void setEvents(){

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
                    FlowBox2.this.root.getChildren().remove(FlowBox2.this );
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
                        //add controlPanel
                        setBottom( toolPane );
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
        }

        public String getTitle() {
            return title.getText();
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }
    }

    private class Node extends AnchorPane{

        private Circle input;
        private Circle output;
        private ImageView defaultActionIcon;
        private ImageView pressedActionIcon;
        private ImageView enteredActionIcon;
        private AnchorPane actionIcon;

        public Node(){
            setProperties();
            initialize();
            setEvents();
        }

        private void setProperties(){
            setMinSize(FlowBox2.this.getMinWidth(),FlowBox2.this.getMinHeight());
            setMaxSize(getMinWidth(),getMinHeight());
            setPadding(defaultPadding);
            setBackgroundColor(this, BACKGROUND_COLOR,defaultCornerRadii,null);
            setBorder( defaultBorder );
            setMargin(this,new Insets(0,0,15,0));
        }

        public void initialize(){

            setInput(createChannel());
            getInput().setLayoutX(0.0f);
            //getChildren().add(getInput());

            setOutput(createChannel());
            getOutput().setLayoutX(FlowBox2.this.getMinWidth());
            //getChildren().add(getOutput());

            getChildren().add(createContainer());

        }

        public Circle createChannel(){
            Circle channel = new Circle(5.0f);
            channel.setFill(Paint.valueOf(BACKGROUND_COLOR));
            channel.setStroke(Paint.valueOf(FOREGROUND_COLOR));
            channel.setStrokeWidth(2.0f);
            channel.setLayoutY(getMinHeight()/2);
            channel.setEffect( new DropShadow() );
            return channel;
        }

        public HBox createContainer(){
            HBox container = new HBox();
            container.setAlignment(Pos.CENTER);
            container.setMinSize(getMinWidth(),getMinHeight());
            defaultActionIcon = createIcon(FlowBox2.this.actionIcon, new Dimension(25.0f,25.0f) );
            container.getChildren().add(getDefaultActionIcon());
            //container.setBackground(new Background(new BackgroundFill(Paint.valueOf("#06bdcf"),null,null)));
            return container;
        }

        public Circle getInput() {
            return input;
        }

        public void setInput(Circle input) {
            this.input = input;
        }

        public Circle getOutput() {
            return output;
        }

        public void setOutput(Circle output) {
            this.output = output;
        }

        public ImageView getDefaultActionIcon() {
            return defaultActionIcon;
        }

        public void setDefaultActionIcon(ImageView defaultActionIcon) {
            this.defaultActionIcon = defaultActionIcon;
        }
    }

    private class ToolPane extends VBox{

        private Component emptyPane;

        public ToolPane(){
            setMinSize(getMinWidth(),200.0f);
            setProperty();

            emptyPane = new Component("Ferramentas") {
                @Override
                public Control createComponent(HBox layoutManager) {
                    layoutManager.setAlignment(Pos.CENTER);

                    Label empty = new Label("Empty");
                    empty.setTextFill(Paint.valueOf(TEXT_COLOR));
                    empty.setFont( Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );
                    return empty;
                }
            };

            addComponent( emptyPane );
        }

        public void setProperty(){
            setSpacing(4.0f);
            setPadding(defaultPadding);
            setBackgroundColor(this,"#000000",defaultCornerRadii,null);
            setOpacity(0.7f);
            setSpacing(4.0f);
            setMargin(this,new Insets(30,30,30,30));
        }

        public void addComponent( Pane component ){
            getChildren().add( component);
        }

        public boolean isEmpty(){
            return ( this.getChildren().get(0) ==  emptyPane );
        }

    }

    public void setFooterPane( Component... components ){

        if( toolPane.isEmpty() )
            toolPane.getChildren().remove(0);

        for( Component component : components ){
            toolPane.addComponent(component);
        }
    }

    public void setBackgroundColor( Pane component, String hexColor, CornerRadii cornerRadii, Insets insets ){
        component.setBackground(new Background(new BackgroundFill(Paint.valueOf(hexColor),cornerRadii,insets)));
    }

    private AnchorPane getRoot() {
        return root;
    }
}
