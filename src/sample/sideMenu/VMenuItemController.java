package sample.sideMenu;

import javafx.event.Event;
import javafx.event.EventHandler;
import sample.Json.NodeBoxData;
import sample.MainController;
import sample.Workspace.Workspace;
import sample.nodes.NodeBox;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Daniel on 02/11/2017.
 */
public class VMenuItemController {
    private static VMenuItemController ourInstance = new VMenuItemController();

    public static VMenuItemController getInstance() {
        return ourInstance;
    }

    private VMenuItemController() {}

    public void fill( SideMenuPane sideMenuPane, Object[] list){
        NodeBoxData nodeBoxData = null;
        for (Object obj : list ){
            nodeBoxData = (NodeBoxData) obj;
            ItemView itemView = new ItemView(nodeBoxData);
            sideMenuPane.addItem(itemView);
        }
    }
}
