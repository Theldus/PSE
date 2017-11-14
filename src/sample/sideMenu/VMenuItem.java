package sample.sideMenu;

import javafx.scene.layout.*;
import sample.workspace.Workspace;

public class VMenuItem extends VBox {

    private VMenuBar container;
    private VMenuItemIcon icon;
    private Pane sidePane;
    private Workspace workspace;

    public VMenuItem(String iconName,Pane sidePane,Workspace workspace){
        setIcon(new VMenuItemIcon(iconName,this,workspace));
        setSidePane(sidePane);
    }

    public VMenuItemIcon getIcon() {
        return icon;
    }

    public void setIcon(VMenuItemIcon icon) {
        this.icon = icon;
    }

    public Pane getSidePane() {
        return sidePane;
    }

    public void setSidePane(Pane sidePane) {
        this.sidePane = sidePane;
    }

    public VMenuBar getContainer() {
        return container;
    }

    public void setContainer(VMenuBar container) {
        this.container = container;
    }
}
