package sample;

import sample.Workspace.Workspace;
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
    private Workspace workspace;

    private MainController() {
        this.nodeBoxList = new ArrayList<>();
    }

    public void installNodes(){
        NodeBoxFactory.getInstance().createGaussianFilter(getCurrentWorkspace());
        NodeBoxFactory.getInstance().createHistogram(getCurrentWorkspace());
    }

    public void setWorkspace(Workspace workspace){
        this.workspace = workspace;
    }

    public Workspace getCurrentWorkspace(){
        return this.workspace;
    }

}