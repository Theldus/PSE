package sample.sideMenu;

import sample.json.NodeBoxData;

/**
 * VMenuItemController class.
 * @author Daniel
 * @since  2017-11-02
 */
public class VMenuItemController {

    /* Single pattern. */
    private static VMenuItemController ourInstance = new VMenuItemController();
    public static VMenuItemController getInstance() {
        return ourInstance;
    }

    /**
     * VMenuItemController constructor.
     */
    private VMenuItemController() {}

    /**
     * Fill all the NodeBox data into the menu bar.
     * @param sideMenuPane Target sideMenuPane
     * @param list NodeBoxData list.
     */
    public void fill( SideMenuPane sideMenuPane, Object[] list){
        NodeBoxData nodeBoxData = null;
        for (Object obj : list ){
            nodeBoxData = (NodeBoxData) obj;
            ItemView itemView = new ItemView(nodeBoxData);
            sideMenuPane.addItem(itemView);
        }
    }
}
