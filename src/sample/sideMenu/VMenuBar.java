package sample.sideMenu;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

/**
 * VMenuBar class.
 * @author Daniel
 */
public class VMenuBar extends HBox {

    /* sideBar instance. */
    private SideBar sideBar;

    /**
     * VMenuBar constructor.
     */
    public VMenuBar(){
        setSideBar(new SideBar());
        createComponent();
    }

    /**
     * Adds the side bar into the VMenuBar.
     */
    public void createComponent() {
       getChildren().add(getSideBar());
   }

    /**
     * Gets the current side bar.
     * @return Returns the side bar.
     */
    public SideBar getSideBar() {
        return sideBar;
    }

    /**
     * Sets the current side bar.
     * @param sideBar sideBar.
     */
    public void setSideBar(SideBar sideBar) {
        this.sideBar = sideBar;
    }

    /**
     * SideBar class.
     * @author Daniel.
     */
    private final class SideBar extends VBox {

        /* Private date. */
        private final double WIDTH = 50.0f;
        private final Paint BACKGROUND_COLOR = Paint.valueOf("#00ffb2");

        /**
         * SideBar constructor.
         */
        public SideBar(){
            setMinHeight(WIDTH);
            setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
            setAlignment(Pos.CENTER);
        }

        /**
         * Adds an icon into the Sidebar.
         * @param icon Icon.
         */
        private void addIcon(VMenuItemIcon icon){
            getChildren().add(icon);
       }
    }

    /**
     * Adds an icon into the sideBar.
     * @param item VMenuItem.
     */
    public void addMenuItem( VMenuItem item ) {
        getSideBar().addIcon(item.getIcon());
        item.setContainer(this);
    }
}
