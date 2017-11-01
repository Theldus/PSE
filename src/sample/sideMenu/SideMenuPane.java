package sample.sideMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import static sample.util.Appearance.setBGColor;

public class SideMenuPane extends VBox {

    private final static String FONT_NAME = "Yu Gothic";
    private final static float FONT_SIZE = 13.0f;
    private final double WIDTH = 300.f;
    private Label title;
    private Insets padding = new Insets(10,5,10,5);

    public SideMenuPane(String title){
        this.title = new Label(title);
        setMinWidth(WIDTH);
        setMinHeight(768.0f);
        setAlignment(Pos.TOP_CENTER);
        setPadding(padding);
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#000000"),null,null)));
        addTitle();
    }

    public void addTitle(){
        this.title.setTextFill(Paint.valueOf("#ffffff"));
        this.title.setFont(Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE));
        getChildren().add(this.title);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public String getTitle(){
        return this.title.getText();
    }

}
