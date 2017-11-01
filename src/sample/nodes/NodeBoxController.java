package sample.nodes;

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

    public void initConnection(NodeBox nodeBox, Edge.IO io, Coordinates coordinates){
        if (edgeTemp == null )
        {
            edgeTemp = new Edge(nodeBox, io);
            connAcc = true;
            MainController.getCurrentWorkspace().getChildren().add(edgeTemp.setupLine(coordinates));
        }
        else
        {
            // Loop, operacao invalida
            if (io.equals(edgeTemp.getLastConnection())) {
                resetEdge();
                connAcc = false;
            }

            else
            {
                edgeTemp.setEdge(nodeBox,io);
                connAcc = false;
                edgeList.add(edgeTemp);
                resetEdge();
            }
        }
    }

}
