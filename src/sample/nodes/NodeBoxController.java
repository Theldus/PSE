package sample.nodes;

import sample.MainController;
import sample.util.Edge;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *  NodeBoxController class. This class holds the logic to
 *  connect the lines between nodes and so on.
 *  @author Daniel, Davidson.
 *  @version v1.0
 */
public class NodeBoxController {
    /**
     * There's only one instance per NodeBox.
     */
    private static NodeBoxController ourInstance = new NodeBoxController();

    /**
     * Line stuff.
     */
    public  static List<Edge> edgeList;
    private static Edge edgeTemp = null;
    public  static boolean connAcc = false;

    /**
     * Gets an instance of this class, since we are using Singleton
     * Pattern.
     * @return Returns a NodeBoxController instance.
     */
    public static NodeBoxController getInstance() {
        return ourInstance;
    }

    /**
     * Initializes this class.
     */
    private NodeBoxController() {
        edgeList = new ArrayList<>();
    }

    /**
     * When a connection process starts, i.e: when the user clicks
     * for the first time in a output/input button, we maintain a
     * temporary edge for that, and, when the connection is successfully
     * established, we need to add the temporary edge into a edge list
     * and resets this pointer.
     * Resets the temporary edge.
     */
    private void resetEdge()
    {
        edgeTemp = null;
    }

    /**
     * This method takes care about connection between NodeBoxes
     * and is invoked whenever a user clicks in a input/output button.
     * @param nodeBox Clicked NodeBox.
     * @param io Input/Output indicator.
     */
    public void initConnection(NodeBox nodeBox, Edge.IO io){
        /* Start point. */
        if (edgeTemp == null)
        {
            connAcc = true;
            edgeTemp = new Edge(nodeBox, io);
            MainController.getCurrentWorkspace().getChildren().add(edgeTemp.setupLine());
            edgeTemp.getLine().toBack();
        }
        else
        {
            /* Invalid operations. */
            if (io.equals(edgeTemp.getLastConnection())) {
                System.err.println("> initConnection: Invalid connection");
                MainController.getCurrentWorkspace().getChildren().remove(edgeTemp.getLine());
                edgeTemp.closeConnection();
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
