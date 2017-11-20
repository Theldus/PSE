package sample;

import sample.workspace.Workspace;
import sample.nodes.NodeBox;
import sample.nodes.NodeBoxFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * MainController class.
 * @author Daniel.
 */
public class MainController {
    /* Singleton pattern. */
    private static MainController ourInstance = new MainController();
    public static MainController getInstance() {
        return ourInstance;
    }

    /* NodeBox list and Workspace. */
    private List<NodeBox> nodeBoxList;
    private static Workspace workspace;

    /**
     * Initializes the main controller.
     */
    private MainController() {
        this.nodeBoxList = new ArrayList<>();
    }

    /**
     * Sets the current workspace.
     * @param workspace Workspace.
     */
    public void setWorkspace(Workspace workspace){
        this.workspace = workspace;
    }

    /**
     * Gets the current workspace.
     * @return Returns the current workspace.
     */
    public Workspace getCurrentWorkspace(){
        return workspace;
    }
}
