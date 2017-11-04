package sample.nodes;

import javafx.scene.shape.CubicCurve;
import sample.MainController;
import sample.Workspace.Workspace;
import sample.util.Coordinates;
import sample.util.Edge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 01/11/2017.
 */
public class NodeBoxController {
    private static NodeBoxController ourInstance = new NodeBoxController();

    List<Edge> edgeList;
    private Edge edgeTemp = null;
    public static boolean connAcc = false;

    public static NodeBoxController getInstance() {
        return ourInstance;
    }

    private NodeBoxController() {
        edgeList = new ArrayList<>();
    }
    private void resetEdge()
    {
        edgeTemp = null;
    }

    public void initConnection(NodeBox nodeBox, Edge.IO io){
        /* Start point. */
        if (edgeTemp == null )
        {
            connAcc = true;
            edgeTemp = new Edge(nodeBox, io);
            MainController.getCurrentWorkspace().getChildren().add(edgeTemp.setupLine());
            edgeTemp.getLine().toBack();
        }
        else
        {
            /* Invalid operation. */
            if (io.equals(edgeTemp.getLastConnection())) {
                System.err.println("> initConnection: Invalid connection");
                MainController.getCurrentWorkspace().getChildren().remove(edgeTemp.getLine());
                resetEdge();
                connAcc = false;
            }

            /* End point. */
            else
            {
                edgeTemp.setEdge(nodeBox,io);
                edgeTemp.establishConnection(io);
                connAcc = false;
                edgeList.add(edgeTemp);
                resetEdge();
            }
        }
    }

}
