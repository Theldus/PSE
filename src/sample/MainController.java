package sample;

import sample.workspace.Workspace;
import sample.nodes.NodeBox;
import sample.nodes.NodeBoxFactory;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    private static MainController ourInstance = new MainController();

    public static MainController getInstance() {
        return ourInstance;
    }

    private List<NodeBox> nodeBoxList;
    private static Workspace workspace;

    private MainController() {
        this.nodeBoxList = new ArrayList<>();
    }

    public void setWorkspace(Workspace workspace){
        this.workspace = workspace;
    }

    public static Workspace getCurrentWorkspace(){
        return workspace;
    }
}
