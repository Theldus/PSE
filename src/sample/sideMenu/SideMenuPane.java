package sample.sideMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SideMenuPane extends VBox {

    private final static String FONT_NAME = "Yu Gothic";
    private final static float FONT_SIZE = 15.0f;
    private final double WIDTH = 300.f;
    private Label title;
    private Insets padding = new Insets(15,5,15,15);
    private ScrollPane scrollPane;
    private VBox container;

    public SideMenuPane(String title){
        this.title = new Label(title);
        this.scrollPane = new ScrollPane();
        this.container = new VBox(15.0f);
        this.container.setBackground(new Background(new BackgroundFill(Paint.valueOf("#000000"),null,null)));
        this.container.setMinHeight(2048.0f);
        setMinWidth(WIDTH);
        setMinHeight(1024);
        setAlignment(Pos.TOP_LEFT);
        //setPadding(padding);
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#000000"),null,null)));
        addTitle();
        addScrollPane();
    }

    public void addTitle(){
        this.title.setTextFill(Paint.valueOf("#FFFFFF"));
        this.title.setFont(Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE));
        this.title.setPadding(new Insets(30,10,10,10));
        this.getChildren().add(this.title);
    }

    public void addScrollPane(){

        this.scrollPane.getStylesheets().add("sample/sideMenu/ScrollPaneStyle.css");
        this.scrollPane.setMinHeight(768);
        this.scrollPane.setContent(container);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.getChildren().add(scrollPane);

    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public String getTitle(){
        return this.title.getText();
    }

    public void addItem( ItemView item ){
        this.container.getChildren().add(item);
    }
}
