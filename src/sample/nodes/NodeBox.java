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

import java.util.ArrayList;

import static sample.util.Appearance.*;

/**
 *  NodeBox class. The main definition of what is an 'Node' is here.
 *  @apiNote All filters/nodes should extend NodeBox class.
 *  @author Daniel, Davidson.
 *  @version v1.0
 */
public abstract class NodeBox extends BorderPane implements NodeBoxObserver{

    private Image image;

    /**
     * Fields that defines how a NodeBox will look like.
     */
    private static Insets defaultPadding = new Insets(5.0f,2.5f,5.0f,2.5f);
    private static CornerRadii defaultCornerRadii = new CornerRadii(20.0f);
    private static BorderWidths defaultBorderWidths = new BorderWidths(2.0f);
    private static Border defaultBorder = new Border(new BorderStroke(Paint.valueOf(FOREGROUND_COLOR),
            BorderStrokeStyle.SOLID,
            defaultCornerRadii,
            defaultBorderWidths) );
    private final Workspace root;
    private final static Dimension dimension = new Dimension(74.0f,64.0f);

    /**
     * Edges list. Every NodeBox should knows all the lines
     * connected into it.
     */
    private ArrayList<Edge> edgesList;

    /**
     * Defines all the mouse events for a Node.
     */
    private NodeBoxDraggable nodeBoxDraggable;

    private final Header header;
    private final Node node;
    private String actionIcon;

    /**
     * Processing input/output images.
     */
    private Image inputImage;
    private Image outputImage;

    /**
     * Setup the appearance: title, icon and workspace.
     * @param title NodeBox title
     * @param root Root element that should contain this NodeBox.
     * @param actionIconName NodeBox icon path.
     */
    public NodeBox(String title, Workspace root, String actionIconName ){
        this.setMinSize(dimension.getWidth(),dimension.getHeight());
        this.nodeBoxDraggable = new NodeBoxDraggable(this);
        this.actionIcon = actionIconName;
        this.root = root;
        this.root.addNode(this);
        this.header = new Header(title);
        this.node = new Node();
        this.edgesList = new ArrayList<>();
        Blend blendMode = new Blend();
        blendMode.setMode(BlendMode.SRC_OVER);
        this.setEffect(blendMode);
        setProperties();
        initialize();
        setEvents();
    }

    /**
     * Installs the NodeBox, i.e: sets everything up to work.
     * At the moment, this just adds itself into the Workspace.
     * @see GaussianFilter
     * @see Histogram
     */
    public abstract void install();

    /**
     * Updates the mouse movement coordinates.
     * @param image Current image
     */
    @Override
    public void update(Image image){
    }

    /**
     * Initialize the NodeBox on the screen.
     */
    public void initialize(){
        setTop(getHeader());
        setCenter(getNode());
    }

    /**
     * Sets the dimensions.
     */
    private void setProperties(){
        setMinSize(dimension.getWidth(),dimension.getHeight());
        setPadding(defaultPadding);
    }

    /**
     * Sets the events, i.e: the mouse events, like drag, click
     * and so on.
     */
    public void setEvents(){
        nodeBoxDraggable.make();
    }

    /**
     * Creates the NodeBox icon that will be in the center of it.
     * @param pathName Path to the icon, including the filename.
     * @param dimension Icon dimensions.
     * @return Returns an ImageView with the image loaded into it.
     */
    private ImageView createIcon(String pathName, Dimension dimension ){
        pathName = "icons/" + pathName;
        ImageView imageView = new ImageView( sample.Main.class.getResource(pathName).toString() );
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitWidth(dimension.getWidth());
        imageView.setFitHeight(dimension.getHeight());
        return imageView;
    }

    /**
     * Gets the NodeBox header.
     * @return NodeBox header.
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Gets the NodeBox Node.
     * @return NodeBox Node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Gets the edgeList.
     */
    public ArrayList<Edge> getEdgeList() {
        return edgesList;
    }

    /**
     * Adds an Edge into the edgeList.
     * @param e Edge to be added.
     */
    public void addEdge(Edge e) {
        edgesList.add(e);
    }

    /**
     * Remove an Edge from the edgeList.
     * @param e Edge to be removed.
     * @return Returns true if the list contained the
     *         specified edge.
     */
    public boolean removeEdge(Edge e){
        return edgesList.remove(e);
    }

    /**
     * Header class. It's basically a GridPane made up
     * of the NodeBox title and the closing 'X' button.
     * Therefore, it should be used in conjunction with
     * the NodeBox and not individually.
     */
    public class Header extends GridPane{

        /**
         * Header title and container.
         */
        private Label title;
        private HBox container;

        /**
         * Default icons for the Header.
         */
        private ImageView defaultDeleteIcon;
        private ImageView enteredDeleteIcon;
        private ImageView pressedDeleteIcon;
        private AnchorPane deleteIcon;

        /**
         * Initializes the Header with the title, container,
         * properties and events.
         * @param title Header title.
         */
        public Header( String title ){
            this.title = createTitle(title);
            this.container = createContainer();
            setProperties();
            setEvents();
            initialize();
        }

        /**
         * Sets the basic properties for the Header, like
         * dimensions and alignments.
         */
        private void setProperties(){
            setMinSize(getMinWidth(),0.0f);
            setPadding(defaultPadding);
            setAlignment(Pos.CENTER);
            setHgap(20.0f);
        }

        /**
         * Initializes the Header, i.e: adds the components
         * inside the GridPane.
         */
        private void initialize(){
            add( title, 0,0 );
            add( container, 1,0 );
        }

        /**
         * Setup the Header title and adjust the positions.
         * @param title Header title.
         * @return Returns a label aligned containing the title.
         */
        private Label createTitle(String title){
            final Label auxTitle = new Label(title);
            auxTitle.setTextFill(Paint.valueOf(TEXT_COLOR));
            auxTitle.setFont( Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );
            auxTitle.setMaxWidth( 150.0f );
            return auxTitle;
        }

        /**
         * Creates a container that will contain the 'x' button.
         * @return Horizontal box containing the 'X' button.
         */
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

        /**
         * Configures the mouse events needed to: change the color
         * of the button when the mouse on top of it and remove the
         * NodeBox when clicked.
         */
        private void setEvents(){

            deleteIcon.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    /* Removing all the lines. */
                    for (int i = 0; i < edgesList.size(); i++)
                    {
                        Edge edgeTemp = edgesList.get(i);

                        /* From workspace. */
                        getRoot().getChildren().remove(edgeTemp.getLine());

                        /* From another NodeBox */
                        if (edgeTemp.getNodeBoxSource() == NodeBox.this)
                            edgeTemp.getNodeBoxTarget().removeEdge(edgeTemp);
                        else
                            edgeTemp.getNodeBoxSource().removeEdge(edgeTemp);
                    }

                    /* TODO: Send a stub image to all connected NodeBoxes inputs. */

                    /* Clear all the lines from this NodeBox. */
                    NodeBox.this.edgesList.clear();

                    /* Remove NodeBox. */
                    getRoot().removeNode(NodeBox.this);
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

        /**
         * Get Header title.
         * @return Header title.
         */
        public String getTitle() {
            return title.getText();
        }

        /**
         * Set Header title.
         * @param title
         */
        public void setTitle(String title) {
            this.title.setText(title);
        }
    }

    /**
     * Node class. Every NodeBox must have one Node. A node is responsible to
     * control the Input/Output buttons and the icon which represents the node
     * functionality.
     *
     * Do not mix this name with a node Graph! there's nothing to do with it and
     * much less with the Java's Node (javafx.scene.Node).
     */
    public class Node extends AnchorPane{

        /**
         * IO buttons.
         */
        private Circle input;
        private Circle output;

        /**
         * Default images.
         */
        private ImageView defaultActionIcon;
        private ImageView pressedActionIcon;
        private ImageView enteredActionIcon;
        private AnchorPane actionIcon;

        /**
         * Initializes the Node.
         */
        public Node(){
            setProperties();
            initialize();
            setEvents();
        }

        /**
         * Setup the layout properties of a Node.
         */
        private void setProperties(){
            setMinSize(NodeBox.this.getMinWidth(),NodeBox.this.getMinHeight());
            setMaxSize(getMinWidth(),getMinHeight());
            setPadding(defaultPadding);
            setBackgroundColor(this, BACKGROUND_COLOR, defaultCornerRadii,null);
            setBorder( defaultBorder );
            setMargin(this,new Insets(0,0,15,0));
        }

        /**
         * Configures the IO buttons.
         */
        public void initialize(){

            setInput(createIOButton());
            getInput().setLayoutX(0.0f);
            getChildren().add(getInput());

            setOutput(createIOButton());
            getOutput().setLayoutX(NodeBox.this.getMinWidth());
            getChildren().add(getOutput());

            getChildren().add(createContainer());

            /* Move input and output to front. */
            input.toFront();
            output.toFront();
        }

        /**
         * Sets up the events needed to control the I/O buttons.
         */
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

        /**
         * Sets the appearance of an IO button.
         * @return
         */
        public Circle createIOButton(){
            Circle channel = new Circle(5.0f);
            channel.setFill(Paint.valueOf(BACKGROUND_COLOR));
            channel.setStroke(Paint.valueOf(FOREGROUND_COLOR));
            channel.setStrokeWidth(2.0f);
            channel.setLayoutY(getMinHeight()/2);
            channel.setEffect( new DropShadow() );
            return channel;
        }

        /**
         * Adds an IO button.
         * @param circle Circle element to be added.
         */
        public void add(Circle circle){
            this.getChildren().add(circle);
        }

        /**
         * Removes an IO button.
         * @param circle Circle element to be removed.
         */
        public void remove(Circle circle){
            this.getChildren().remove(circle);
        }

        /**
         * Creates the Horizontal box container that will contains the NodeBox
         * icon.
         * @return Horizontal box container.
         */
        public HBox createContainer(){
            HBox container = new HBox();
            container.setAlignment(Pos.CENTER);
            container.setMinSize(getMinWidth(),getMinHeight());
            defaultActionIcon = createIcon(NodeBox.this.actionIcon, new Dimension(25.0f,25.0f) );
            container.getChildren().add(getDefaultActionIcon());
            return container;
        }

        /**
         * Gets the Circle input.
         * @return Returns the input Circle object.
         */
        public Circle getInput() {
            return input;
        }

        /**
         * Sets the Circle input.
         * @param input Input Circle to be set.
         */
        public void setInput(Circle input) {
            this.input = input;
        }

        /**
         * Gets the Circle output.
         * @return Returns the output Circle object.
         */
        public Circle getOutput() {
            return output;
        }

        /**
         * Sets the Circle output.
         * @param output Output Circle to be set.
         */
        public void setOutput(Circle output) {
            this.output = output;
        }

        /**
         * Gets the defaultActionIcon, i.e: the default image of a NodeBox.
         * @return Returns the defaultActionIcon.
         */
        public ImageView getDefaultActionIcon() {
            return defaultActionIcon;
        }

        /**
         * Sets the defaultActionIcon, i.e: the default image of a NodeBox.
         * @param defaultActionIcon de
         */
        public void setDefaultActionIcon(ImageView defaultActionIcon) {
            this.defaultActionIcon = defaultActionIcon;
        }
    }

    /**
     * Sets the Node background color.
     * @param component Target component to be set the color.
     * @param hexColor Color value, in WEB format (#AABBCC).
     * @param cornerRadii Background radius.
     * @param insets Insets.
     */
    public void setBackgroundColor( Pane component, String hexColor, CornerRadii cornerRadii, Insets insets ){
        component.setBackground(new Background(new BackgroundFill(Paint.valueOf(hexColor),cornerRadii,insets)));
    }

    /**
     * Gets the Workspace. Since we support (in the future) multiple
     * Workspaces, it's important to know which Workspace we are
     * currently using for this Node.
     * @return Current Workspace.
     */
    protected Workspace getRoot() {
        return  root;
    }
}
