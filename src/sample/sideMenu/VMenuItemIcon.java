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

/**
 * VMenuItemIcon class.
 * @author Daniel
 */
public class VMenuItemIcon extends HBox {

    /* Private data. */
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

    /**
     * VMenuItem constructor.
     * @param iconName iconName
     * @param myParent VMenuItem parent.
     * @param workspace Workspace.
     */
    public VMenuItemIcon(String iconName,VMenuItem myParent,Workspace workspace){
        this.workspace = workspace;
        setMyParent(myParent);
        setIconName(iconName);
        setItemIcon(new ImageView( sample.Main.class.getResource(PATH + iconName + EXTENSION).toString() ) );
        setItemButton(new Button());
        setPaneIndicator(new Pane());
        createComponent();
    }

    /**
     * Initializes the components as well as events.
     */
    public void createComponent(){
        getChildren().addAll(getPaneIndicator(),getItemButton());

        /* Item click. */
        getItemButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            private boolean isClicked = true;

            /* Changes the menu item. */
            private void changeMenuItem(){
                currentMenuItem.getIcon().hover(currentMenuItem);
                currentMenuItem.getIcon().isChange = true;
                workspace.remove(currentMenuItem.getSidePane());

                currentMenuItem = myParent;
                workspace.add(myParent.getSidePane());
                active(myParent);
            }

            /* Mouse click. */
            @Override
            public void handle(MouseEvent event) {

                if(isChange)
                    isClicked = true;

                if(isClicked){

                    if( currentMenuItem == null ){
                        currentMenuItem = myParent;
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
                    workspace.remove(myParent.getSidePane());
                    hover(myParent);
                    isClicked = true;
                }
            }
        });
    }

    /**
     * MenuItem onHover event.
     * @param menuItem VMenuItem.
     */
    public void hover(VMenuItem menuItem){
        changeIcon(PATH+menuItem.getIcon().getIconName()+EXTENSION);
        setBGColor(menuItem.getIcon().getItemButton(),"#00ffb2",null,null);
        setBGColor(menuItem.getIcon().getPaneIndicator(),"#00ffb2",null,null);
    }

    /**
     * MenuItem onActive event.
     * @param menuItem VMenuItem.
     */
    public void active(VMenuItem menuItem){
        changeIcon(PATH+menuItem.getIcon().getIconName()+"Light"+EXTENSION);
        setBGColor(menuItem.getIcon().getItemButton(),"#000000", null,null);
        setBGColor(menuItem.getIcon().getPaneIndicator(),"#ffffff",null,null);
    }

    /**
     * Changes the item icon.
     * @param pathName Path to the image to be loaded.
     */
    public void changeIcon(String pathName){
          getItemIcon().setImage(new Image( sample.Main.class.getResource(pathName).toString() ));
    }

    /**
     * Gets the Item icon.
     * @return Returns the icon.
     */
    public ImageView getItemIcon() {
        return itemIcon;
    }

    /**
     * Sets the Item icon.
     * @param itemIcon itemIcon.
     */
    public void setItemIcon(ImageView itemIcon) {
        this.itemIcon = itemIcon;
    }

    /**
     * Gets the pane indicator.
     * @return Returns the pane indicator.
     */
    public Pane getPaneIndicator() {
        return paneIndicator;
    }

    /**
     * Sets the pane indicator.
     * @param paneIndicator Pane to be set.
     */
    private void setPaneIndicator(Pane paneIndicator) {
        this.paneIndicator = paneIndicator;
        this.paneIndicator.setPrefSize(SPACING,SIZE.height);
    }

    /**
     * Gets the Item button.
     * @return Returns the Item button.
     */
    public Button getItemButton() {
        return itemButton;
    }

    /**
     * Sets the Item button.
     * @param itemButton button.
     */
    private void setItemButton(Button itemButton) {
        this.itemButton = itemButton;
        this.itemButton.setGraphic(getItemIcon());
        this.itemButton.setPrefSize(SIZE.width - SPACING,SIZE.height);
        this.itemButton.setMinSize(SIZE.width - SPACING, SIZE.height);
        this.itemButton.setBackground(new Background(new BackgroundFill(BUTTON_COLOR,null,null)));
    }

    /**
     * Gets the VMenuItemIcon parent.
     * @return Returns the VMenuItemIcon.
     */
    public VMenuItem getMyParent() {
        return myParent;
    }

    /**
     * Sets the VMenuItemIcon parent.
     * @param myParent Parent.
     */
    public void setMyParent(VMenuItem myParent) {
        this.myParent = myParent;
    }

    /**
     * Gets the icon path.
     * @return Returns the icon path.
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * Sets the icon name.
     * @param iconName iconName.
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
