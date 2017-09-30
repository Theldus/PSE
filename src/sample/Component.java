package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by Daniel on 31/08/2017.
 */
public abstract class Component extends VBox implements BasicUI{

    protected final String FONT_NAME = "Yu Gothic";
    protected final String BACKGROUND_COLOR = "#222222";
    protected final String FOREGROUND_COLOR = "#FFFFFF";
    protected final String TEXT_COLOR = "#FFFFFF";
    protected final double FONT_SIZE = 13.0;
    protected final Insets padding = new Insets(12,10,5,10);
    private Label title;
    private HBox layout;

    public Component( String name ){
        setProperties();
        initComponents();
        setName(name);
    }

    public String getName() {
        return this.title.getText();
    }

    public void setName(String name) {
        this.title.setText(name);
    }

    @Override
    public void initComponents() {
        addComponent((Node) createTitle());
        addComponent(createBody());
        addComponent(createComponent(this.getLayout()));
        //setBackground(new Background(new BackgroundFill(Paint.valueOf("#444444"),null,null)));
    }

    private Label createTitle(){
        final Label title = new Label();
        this.title = title;
        title.setFont( Font.font(FONT_NAME, FontWeight.MEDIUM,FONT_SIZE) );
        title.setTextFill(Paint.valueOf(TEXT_COLOR));
        title.setPadding(padding);
        //title.setBackground(new Background(new BackgroundFill(Paint.valueOf("#222222"),null,null)));
        return title;
    }

    private HBox createBody(){
        setLayout(new HBox(5));
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(padding);
        return layout;
    }

    public abstract Control createComponent( HBox layoutManager );

    @Override
    public void addComponent(Node component) {
        getChildren().add(component);
    }

    private void addComponent( Control control ){
        getLayout().getChildren().add(control);
    }

    @Override
    public void setProperties() {
        setSpacing(5);
        //setPadding(padding);
        setAlignment(Pos.CENTER_LEFT);

        //setBorder(new Border(new BorderStroke(Paint.valueOf("#999999"),BorderStrokeStyle.DASHED,new CornerRadii(10.0),BorderWidths.FULL)));
        //setBackground(new Background(new BackgroundFill(Paint.valueOf("#292929"),null,null)));
        //setEffect(new DropShadow());
    }

    public HBox getLayout() {
        return layout;
    }

    public void setLayout(HBox layout) {
        this.layout = layout;
    }
}