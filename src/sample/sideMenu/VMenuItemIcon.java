package sample.sideMenu;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import sample.workspace.Workspace;

import java.awt.*;
import static sample.util.Appearance.setBGColor;

public class VMenuItemIcon extends HBox {

    private VMenuItem myParent;
    private ImageView itemIcon;
    private Button itemButton;
    private Pane paneIndicator;
    private String iconName;
    private static final String PATH = "icons/";
    private static final String EXTENSION = "Icon.png";
    private Workspace workspace;

    private static VMenuItem currentMenuItem = null;
    private boolean isChange = false;
    
    private static final Paint BUTTON_COLOR = Paint.valueOf("#00ffb2");
    private static final Dimension SIZE = new Dimension(50,50);
    private static final double SPACING = 2.0f;

    public VMenuItemIcon(String iconName,VMenuItem myParent,Workspace workspace){
        this.workspace = workspace;
        setMyParent(myParent);
        setIconName(iconName);
        setItemIcon(new ImageView( sample.Main.class.getResource(PATH + iconName + EXTENSION).toString() ) );
        setItemButton(new Button());
        setPaneIndicator(new Pane());
        createComponent();
    }

    public void createComponent(){
        getChildren().addAll(getPaneIndicator(),getItemButton());

        getItemButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            private boolean isClicked = true;

            private void changeMenuItem(){

                currentMenuItem.getIcon().hover(currentMenuItem);
                currentMenuItem.getIcon().isChange = true;
                workspace.remove(currentMenuItem.getSidePane());
                //myParent.getContainer().getChildren().remove(currentMenuItem.getSidePane());

                currentMenuItem = myParent;
                //myParent.getContainer().getChildren().add(myParent.getSidePane());
                workspace.add(myParent.getSidePane());
                active(myParent);
                
            }

            @Override
            public void handle(MouseEvent event) {

                if(isChange)
                    isClicked = true;

                if(isClicked){

                    if( currentMenuItem == null ){
                        currentMenuItem = myParent;
                        //myParent.getContainer().getChildren().add(myParent.getSidePane());
                        workspace.add(myParent.getSidePane());
                        active(myParent);
                    }
                    else {
                        changeMenuItem();
                    }
                    isClicked = isChange = false;
                }
                else{

                    hover(currentMenuItem);
                    currentMenuItem = null;
                    //myParent.getContainer().getChildren().remove(myParent.getSidePane());
                    workspace.remove(myParent.getSidePane());
                    hover(myParent);
                    isClicked = true;

                }
            }
        });
    }

    public void hover(VMenuItem menuItem){
        changeIcon(PATH+menuItem.getIcon().getIconName()+EXTENSION);
        setBGColor(menuItem.getIcon().getItemButton(),"#00ffb2",null,null);
        setBGColor(menuItem.getIcon().getPaneIndicator(),"#00ffb2",null,null);
    }

    public void active(VMenuItem menuItem){
        changeIcon(PATH+menuItem.getIcon().getIconName()+"Light"+EXTENSION);
        setBGColor(menuItem.getIcon().getItemButton(),"#000000", null,null);
        setBGColor(menuItem.getIcon().getPaneIndicator(),"#ffffff",null,null);
    }

    public void changeIcon(String pathName){
          getItemIcon().setImage(new Image( sample.Main.class.getResource(pathName).toString() ));
    }

    public ImageView getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(ImageView itemIcon) {
        this.itemIcon = itemIcon;
    }

    public Pane getPaneIndicator() {
        return paneIndicator;
    }

    private void setPaneIndicator(Pane paneIndicator) {
        this.paneIndicator = paneIndicator;
        this.paneIndicator.setPrefSize(SPACING,SIZE.height);
        //this.paneIndicator.setBackground(new Background(new BackgroundFill(INDICATOR_COLOR,null,null)));
    }

    public Button getItemButton() {
        return itemButton;
    }

    private void setItemButton(Button itemButton) {
        this.itemButton = itemButton;
        this.itemButton.setGraphic(getItemIcon());
        this.itemButton.setPrefSize(SIZE.width - SPACING,SIZE.height);
        this.itemButton.setMinSize(SIZE.width - SPACING, SIZE.height);
        this.itemButton.setBackground(new Background(new BackgroundFill(BUTTON_COLOR,null,null)));
    }

    public VMenuItem getMyParent() {
        return myParent;
    }

    public void setMyParent(VMenuItem myParent) {
        this.myParent = myParent;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
