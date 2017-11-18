package sample.sideMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import static sample.util.Appearance.FONT_NAME;
import static sample.util.Appearance.FONT_SIZE;
import static sample.util.Appearance.TEXT_COLOR;

public class ItemViewAdapter extends VBox {

    private String title;
    private String description;

    private Label lblTitle;
    private Text txtDescription;

    public ItemViewAdapter( String title, String description ){
        this.title = title;
        this.description = description;
        createItemView();
    }

    public VBox createText(){

        final VBox layout = new VBox(15.0f);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setMaxWidth(300.0f);

        lblTitle = new Label(this.title);
        lblTitle.setTextFill(javafx.scene.paint.Paint.valueOf("#ff0000"));
        lblTitle.setFont( javafx.scene.text.Font.font(FONT_NAME, FontWeight.EXTRA_BOLD,FONT_SIZE) );

        txtDescription = new Text(this.description);
        txtDescription.setFill(Paint.valueOf("#ffffff"));
        txtDescription.setFont( javafx.scene.text.Font.font(FONT_NAME, FontWeight.BOLD,12.0f) );
        txtDescription.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);

        layout.getChildren().add(lblTitle);
        layout.getChildren().add(txtDescription);

        return layout;

    }

    public void createItemView(){
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10.0f);
        this.setPadding(new Insets(10,10,10,10));
        this.getChildren().add( createText() );
    }

    public Text getDescription() {
        return this.txtDescription;
    }
}
