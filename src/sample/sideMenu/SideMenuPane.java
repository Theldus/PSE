package sample.sideMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.util.Dimension;

/**
 * SideMenuPane
 * @author Daniel
 * @since 2017-11-02
 */
public class SideMenuPane extends VBox {

    /* Private data. */
    private final static String FONT_NAME = "Yu Gothic";
    private final static float FONT_SIZE = 15.0f;
    private final double WIDTH = 300.f;
    private Label title;
    private Insets padding = new Insets(10,5,10,5);
    private ScrollPane scrollPane;
    private VBox container;
    private boolean isScrollable = false;

    /**
     * SideMenuPane constructor.
     * @param title SideMenuPane title.
     */
    public SideMenuPane(String title){
        this.title = new Label(title);
        this.scrollPane = new ScrollPane();
        this.container = new VBox(0.0f);
        this.container.setBackground(new Background(new BackgroundFill(Paint.valueOf("#000000"),null,null)));
        this.container.setMinHeight(1268.0f);
        setMinWidth(WIDTH);
        setMinHeight(1024);
        setAlignment(Pos.TOP_LEFT);
        //setPadding(padding);
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#000000"),null,null)));
        addTitle();
    }

    /**
     * Set up the title into the SideMenu.
     */
    public void addTitle(){
        this.title.setTextFill(Paint.valueOf("#FFFFFF"));
        this.title.setFont(Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE));
        this.title.setPadding(new Insets(30,10,10,10));
        this.getChildren().add(this.title);
    }

    /**
     * Configures the ScrollPane.
     */
    public void install(){
        if(isScrollable()){
            addScrollPane();
            this.getChildren().add(scrollPane);
        }
        else {
            this.getChildren().add(container);
        }
    }

    /**
     * Sets the ScrollPane size given a dimension.
     * @param dimension Dimension object.
     */
    public void setScrollPaneSize(Dimension dimension){
        this.scrollPane.setMaxHeight(dimension.getHeight());
    }

    /**
     * Sets the ScrollPane
     */
    public void addScrollPane(){

        this.scrollPane.getStylesheets().add(sample.Main.class.getResource("sideMenu/ScrollPaneStyle.css").toString() );
        this.scrollPane.setMaxHeight(669.0f);
        this.scrollPane.setContent(container);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.scrollPane.setContent(container);
    }

    /**
     * Sets the title
     * @param title SideMenuPane title.
     */
    public void setTitle(String title){
        this.title.setText(title);
    }

    /**
     * Gets the title.
     * @return Returns SideMenuPane title.
     */
    public String getTitle(){
        return this.title.getText();
    }

    /**
     * Adds an arbitrary element into the SideMenuPane.
     * @param item Item to be added.
     */
    public void addItem( Pane item ){
        this.container.getChildren().add(item);
    }

    /**
     * Checks if the menu is scrollable.
     * @return Returns true if the pane is scrollable,
     * false otherwise.
     */
    public boolean isScrollable() {
        return isScrollable;
    }

    /**
     * Enables/Disables scrolling into the pane.
     * @param scrollable Enables or not the scroll in the menu.
     */
    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }
}
