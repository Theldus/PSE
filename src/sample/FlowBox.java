package sample;

import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * Created by Daniel on 31/08/2017.
 */
public abstract class FlowBox extends BorderPane implements BasicUI{

    protected final String FONT_NAME = "Yu Gothic";
    protected final String BACKGROUND_COLOR = "#222222";
    protected final String FOREGROUND_COLOR = "#06bdcf";
    protected final String TEXT_COLOR = "#17ee87";
    protected final String TEXT_TITLE_COLOR = "#f1f1f1";
    protected final double FONT_SIZE = 12.0;
    private final Insets padding = new Insets(10,12,10,12);
    private Header header = new Header(new Dimension(getMinWidth(),50.0));;
    private Body body = new Body(new Dimension(getMinWidth(), getMinHeight() - header.getMinHeight()));;
    private Coordinates origin = new Coordinates();
    private Coordinates cursor = new Coordinates();
    private Coordinates destiny = new Coordinates();
    private boolean clicked = false;

    public FlowBox(String title,Dimension dimension) {
        setMinSize(230,0);
        setProperties();
        initComponents();
        setName(title);
    }

    @Override
    public void initComponents() {
        setTop(header);
        setCenter(body);
        //setCenter(body);

        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                origin.setCoordinates(event.getSceneX(),event.getSceneY());
                cursor.setCoordinates(  event.getSceneX() - event.getX(),
                                        event.getSceneY() - event.getY());
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

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if( clicked ){
                    setCenter(body);
                    clicked = false;
                }
                else{
                    setCenter(null);
                    clicked = true;
                }
                event.consume();
            }
        });
    }

    public void addComponent(Component component) {
        addComponent((Node)component);
    }

    public void addComponent(Node component){
        body.addComponent(component);
    }

    @Override
    public void setProperties() {
        setBackground(new Background(new BackgroundFill(Paint.valueOf(BACKGROUND_COLOR),null,null)));
    }

    class Header extends HBox implements BasicUI{

        private Label title;
        private Rectangle rectangle = new Rectangle(9,9);

        public Header( Dimension dimension ){
            setMinSize(dimension.getWidth(),dimension.getHeight());
            setProperties();
            initComponents();
        }

        public void setProperties(){
            setPadding(padding);
            setAlignment(Pos.CENTER);
        }

        public void initComponents(){
            setTitle(new Label());
            title.setFont( Font.font(FONT_NAME, FontWeight.BOLD,FONT_SIZE) );
            title.setTextFill(Paint.valueOf(TEXT_TITLE_COLOR));
            rectangle.setStroke(Paint.valueOf("#333333"));
            rectangle.setStrokeWidth(3);
            addComponent(rectangle);
            addComponent(title);
        }

        public void addComponent( Node component ){
            this.getChildren().add(component);
        }

        public Label getTitle(){
            return title;
        }

        public void setTitle(Label title) {
            this.title = title;
        }
    }

    class Body extends BorderPane implements BasicUI{

        private VBox controlPane;
        private  Rectangle outChannel;
        private Rectangle inChannel;

        public Body(Dimension dimension){
            setMinSize(dimension.getWidth(),dimension.getHeight());
            System.out.println(this.getMinWidth() + " " + this.getMinHeight());
            setProperties();
            initComponents();
        }

        public HBox createChannel( String type, Pos pos ){

            Rectangle layoutChannel = new Rectangle(9,9);
            layoutChannel.setStrokeWidth(2.0);

            final HBox layout = new HBox(5);
            layout.setPadding(padding);
            layout.setAlignment(pos);
            layoutChannel.setFill(Paint.valueOf(BACKGROUND_COLOR));
            //layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#999999"),null,null)));

            final Label channelLabel = new Label(type);
            channelLabel.setFont( Font.font(FONT_NAME, FontWeight.EXTRA_LIGHT,10.0) );
            channelLabel.setTextFill(Paint.valueOf(TEXT_COLOR));

            if( type.equals("Input") ){

                inChannel = layoutChannel;
                layoutChannel.setStroke(Paint.valueOf("#17ee87"));
                layout.getChildren().add( inChannel );
                layout.getChildren().add(channelLabel);

            }
            else {

                outChannel = layoutChannel;
                layoutChannel.setStroke(Paint.valueOf("#f60a51"));
                layout.getChildren().add(channelLabel);
                layout.getChildren().add( outChannel );

            }

            return layout;
        }

        private VBox createControlPane(){

            controlPane = new VBox(4);
            //controlPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#111111"),null,null)));
            controlPane.setAlignment(Pos.CENTER);
            controlPane.setPadding(padding);

            return controlPane;
        }

        @Override
        public void initComponents() {
            setTop(createChannel("Output",Pos.BOTTOM_RIGHT));
            setCenter( createControlPane() );
            setBottom(createChannel("Input",Pos.TOP_LEFT));
        }

        @Override
        public void addComponent(Node component) {
            controlPane.getChildren().add(component);
        }

        @Override
        public void setProperties() {
            //setBackground(new Background(new BackgroundFill(Paint.valueOf("#e01449"),null,null)));
        }
    }

    private String getName() {
        return header.getTitle().getText();
    }

    private void setName(String name) {
        header.getTitle().setText(name);
    }
}



