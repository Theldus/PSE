package sample.sideMenu;

import javafx.scene.layout.*;
import sample.workspace.Workspace;

/**
 * VMenuItem class.
 * @author Daniel.
 */
public class VMenuItem extends VBox {

    /* Private data. */
    private VMenuBar container;
    private VMenuItemIcon icon;
    private Pane sidePane;
    private Workspace workspace;

    /**
     * VMenuItem constructor.
     * @param iconName Icon name.
     * @param sidePane Pane.
     * @param workspace Workspace parent.
     */
    public VMenuItem(String iconName,Pane sidePane,Workspace workspace){
        setIcon(new VMenuItemIcon(iconName,this,workspace));
        setSidePane(sidePane);
    }

    /**
     * Gets the icon.
     * @return Returns a VMenuItemIcon instance.
     */
    public VMenuItemIcon getIcon() {
        return icon;
    }

    /**
     * Sets a VMenuItemIcon instance.
     * @param icon Icon.
     */
    public void setIcon(VMenuItemIcon icon) {
        this.icon = icon;
    }

    /**
     * Gets the sidePane.
     * @return Returns the side pane.
     */
    public Pane getSidePane() {
        return sidePane;
    }

    /**
     * Sets the sidePane.
     * @param sidePane sidePane.
     */
    public void setSidePane(Pane sidePane) {
        this.sidePane = sidePane;
    }

    /**
     * Gets the VMenuBar associated to this.
     * @return Returns the VMenuBar.
     */
    public VMenuBar getContainer() {
        return container;
    }

    /**
     * Sets the VMenuBar.
     * @param container VMenuBar.
     */
    public void setContainer(VMenuBar container) {
        this.container = container;
    }
}
