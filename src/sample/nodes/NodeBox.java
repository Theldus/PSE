package sample.nodes;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.NodeBoxObserver;
import sample.Workspace.Workspace;
import sample.util.Coordinates;
import sample.util.Dimension;
import sample.util.Edge;

import javax.xml.stream.EventFilter;
import java.io.File;

import static sample.util.Appearance.*;

public abstract class NodeBox extends BorderPane implements NodeBoxObserver{

    private Image image;

    private static Insets defaultPadding = new Insets(5.0f,2.5f,5.0f,2.5f);
    private static CornerRadii defaultCornerRadii = new CornerRadii(20.0f);
    private static BorderWidths defaultBorderWidths = new BorderWidths(2.0f);
    private static Border defaultBorder = new Border(new BorderStroke(Paint.valueOf(FOREGROUND_COLOR),
            BorderStrokeStyle.SOLID,
            defaultCornerRadii,
            defaultBorderWidths) );
    private final Workspace root;
    private final static Dimension dimension = new Dimension(74.0f,64.0f);

    private NodeBoxDraggable nodeBoxDraggable;

    private Coordinates wkc;
    private Coordinates origin = new Coordinates();
    private Coordinates cursor = new Coordinates();
    private Coordinates destiny = new Coordinates();

    private final Header header;
    private final Node node;
    private String actionIcon;

    public NodeBox(String title, Workspace root, String actionIconName ){
        this.setMinSize(dimension.getWidth(),dimension.getHeight());
        this.nodeBoxDraggable = new NodeBoxDraggable(this);
        this.actionIcon = actionIconName;
        this.root = root;
        this.root.addObserver(this);
        this.header = new Header(title);
        this.node = new Node();
        Blend blendMode = new Blend();
        blendMode.setMode(BlendMode.SRC_OVER);
        this.setEffect(blendMode);
        setProperties();
        initialize();
        setEvents();
    }

    public abstract void install();

    @Override
    public void update(Coordinates coordinates){
        wkc = coordinates;
    }

    public void initialize(){
        setTop(getHeader());
        setCenter(getNode());
    }

    private void setProperties(){
        setMinSize(dimension.getWidth(),dimension.getHeight());
        setPadding(defaultPadding);
    }

    public void setEvents(){
        nodeBoxDraggable.make();
    }


    private ImageView createIcon(String pathName, Dimension dimension ){
        pathName = "icons/" + pathName;
        ImageView imageView = new ImageView( sample.Main.class.getResource(pathName).toString() );
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitWidth(dimension.getWidth());
        imageView.setFitHeight(dimension.getHeight());
        return imageView;
    }

    public Header getHeader() {
        return header;
    }

    public Node getNode() {
        return node;
    }

    public class Header extends GridPane{

        private Label title;
        private HBox container;

        private ImageView defaultDeleteIcon;
        private ImageView enteredDeleteIcon;
        private ImageView pressedDeleteIcon;
        private AnchorPane deleteIcon;

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
            defaultDeleteIcon = createIcon( "DefaultDeleteIcon.png", imageDimension );
            deleteIcon = new AnchorPane(defaultDeleteIcon);
            enteredDeleteIcon = createIcon( "EnteredDeleteIcon.png", imageDimension );

            container.getChildren().add(deleteIcon);

            return container;
        }

        private void setEvents(){

            deleteIcon.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    NodeBox.this.getRoot().remove(NodeBox.this );
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
        }

        public String getTitle() {
            return title.getText();
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }
    }

    public class Node extends AnchorPane{

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
            setMinSize(NodeBox.this.getMinWidth(),NodeBox.this.getMinHeight());
            setMaxSize(getMinWidth(),getMinHeight());
            setPadding(defaultPadding);
            setBackgroundColor(this, BACKGROUND_COLOR,defaultCornerRadii,null);
            setBorder( defaultBorder );
            setMargin(this,new Insets(0,0,15,0));
        }

        public void initialize(){

            setInput(createChannel());
            getInput().setLayoutX(0.0f);
            getChildren().add(getInput());

            setOutput(createChannel());
            getOutput().setLayoutX(NodeBox.this.getMinWidth());
            getChildren().add(getOutput());

            getChildren().add(createContainer());

            /* Move input and output to front. */
            input.toFront();
            output.toFront();
        }

        public void setEvents(){

            input.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    NodeBoxController controller =  NodeBoxController.getInstance();
                    controller.initConnection(NodeBox.this, Edge.IO.Input);
                    event.consume();
                }
            });

            output.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    NodeBoxController controller =  NodeBoxController.getInstance();
                    controller.initConnection(NodeBox.this, Edge.IO.Output);
                    event.consume();
                }
            });
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

        public void add( Circle circle ){
            this.getChildren().add(circle);
        }
        public void remove( Circle circle){
            this.getChildren().remove(circle);
        }

        public HBox createContainer(){
            HBox container = new HBox();
            container.setAlignment(Pos.CENTER);
            container.setMinSize(getMinWidth(),getMinHeight());
            defaultActionIcon = createIcon(NodeBox.this.actionIcon, new Dimension(25.0f,25.0f) );
            container.getChildren().add(getDefaultActionIcon());
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

    public void setBackgroundColor( Pane component, String hexColor, CornerRadii cornerRadii, Insets insets ){
        component.setBackground(new Background(new BackgroundFill(Paint.valueOf(hexColor),cornerRadii,insets)));
    }

    protected Workspace getRoot() {
        return  root;
    }
}
