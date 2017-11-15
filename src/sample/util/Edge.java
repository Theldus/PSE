package sample.util;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import sample.MainController;
import sample.nodes.NodeBox;
import sample.nodes.NodeBoxController;

/**
 *  Edge class. Deals with the line between NodeBox.
 *  @author Daniel, Davidson.
 *  @version v1.0
 */
public class Edge {
    /**
     * Input/Output indicator.
     */
    public enum IO{Input, Output};

    /**
     * Edge stuff.
     */
    private NodeBox nodeBoxSource;
    private NodeBox nodeBoxTarget;
    private CubicCurve line;
    private IO lastConnection;
    private EventHandler filter;

    /**
     * Sets the NodeBox and IO indicator.
     * @param nodeBox NodeBox
     * @param io IO indicator.
     */
    public Edge(NodeBox nodeBox, IO io)
    {
        setEdge(nodeBox, io);
        lastConnection = io;
    }

    /**
     * Gets the type of the last connection. It's useful
     * since this helps to identify input/input and output/output
     * connections.
     * @return Returns IO indicator.
     */
    public IO getLastConnection() {
        return lastConnection;
    }

    /**
     * Configures the last connection.
     * @param lastConnection Last connection into this edge.
     */
    public void setLastConnection(IO lastConnection) {
        this.lastConnection = lastConnection;
    }

    /**
     * Sets the NodeBox accordingly with his type.
     * @param nodeBox NodeBox.
     * @param io IO indicator.
     */
    public void setEdge(NodeBox nodeBox, IO io){
        if (io.equals(IO.Input))
            nodeBoxTarget = nodeBox;
        else
            nodeBoxSource = nodeBox;
    }

    /**
     * Gets the NodeBox source.
     * @return Returns NodeBox source.
     */
    public NodeBox getNodeBoxSource() {
        return nodeBoxSource;
    }

    /**
     * Sets the NodeBox source.
     * @param nodeBoxSource NodeBox source.
     */
    public void setNodeBoxSource(NodeBox nodeBoxSource) {
        this.nodeBoxSource = nodeBoxSource;
    }

    /**
     * Gets the NodeBox target.
     * @return Returns the NodeBox target.
     */
    public NodeBox getNodeBoxTarget() {
        return nodeBoxTarget;
    }

    /**
     * Sets the NodeBox target.
     * @param nodeBoxTarget NodeBox target.
     */
    public void setNodeBoxTarget(NodeBox nodeBoxTarget) {
        this.nodeBoxTarget = nodeBoxTarget;
    }

    /**
     * Gets the current line used to illustrate the connection
     * between two NodeBox.
     * @return Returns the line.
     */
    public CubicCurve getLine() {
        return line;
    }

    /**
     * Sets the line to be used to connect two NodeBoxes.
     * @param line Line.
     */
    public void setLine(CubicCurve line) {
        this.line = line;
    }

    /**
     * setupLine. This method is invoked whenever a line is being
     * created. Both the coordinates and the event are set here.
     * @return Returns the line created.
     */
    public CubicCurve setupLine()
    {
        /* Appropriate circle. */
        Circle io = (nodeBoxSource == null) ? nodeBoxTarget.getNode().getInput()
                : nodeBoxSource.getNode().getOutput();

        /* Calculate the coordinates. */
        Coordinates coordinates = new Coordinates();
        Bounds bounds = io.localToScene( io.getBoundsInLocal() );
        coordinates.setX( (bounds.getMinX() + (bounds.getWidth()  / 2)) - 50 );
        coordinates.setY(  bounds.getMinY() + (bounds.getHeight() / 2)       );

        /* Create the line and setup the properties. */
        line = new CubicCurve();
        line.setFill(Color.TRANSPARENT);
        line.setStartX   ( coordinates.getX() );
        line.setStartY   ( coordinates.getY() );
        line.setControlX1( coordinates.getX() );
        line.setControlX2( coordinates.getX() );
        line.setControlY1( coordinates.getY() );
        line.setControlY2( coordinates.getY() );
        line.setEndX     ( coordinates.getX() );
        line.setEndY     ( coordinates.getY() );
        line.setStroke(Color.GREENYELLOW);
        line.setStrokeWidth(2);

        /*
         * Setup the mouse move event for the line, note
         * that we are using the workspace to handle the
         * event, since we need track the workspace.
         */
        filter = new EventHandler<MouseEvent>() {
            private Edge.IO io = Edge.this.lastConnection;

            @Override
            public void handle(MouseEvent event) {
                /* There's a line being created. */
                if (NodeBoxController.connAcc){
                    double diff = Math.abs (Edge.this.line.getEndX() - Edge.this.line.getStartX());
                    diff = (diff * 0.4);

                    if (io.equals(IO.Input))
                        diff *= -1;

                    Edge.this.line.setControlX1( Edge.this.line.getStartX() + diff );
                    Edge.this.line.setControlY1( Edge.this.line.getStartY() );
                    Edge.this.line.setControlX2( Edge.this.line.getEndX  () - diff );
                    Edge.this.line.setControlY2( Edge.this.line.getEndY  () );

                    Edge.this.line.setEndX( event.getX() );
                    Edge.this.line.setEndY( event.getY() );
                }

                /* Consume event. */
                event.consume();
            }
        };

        /* Adds the event filter. */
        MainController.getInstance().getCurrentWorkspace().addEventFilter(MouseEvent.MOUSE_MOVED, filter);
        return line;
    }

    /**
     * establishConnection. This method as the name, establishes
     * a connection between two nodes when the user clicks an
     * input/output button the second time.
     * @param io IO indicator.
     */
    public void establishConnection(IO io)
    {
        Circle circle = null;

        if (io.equals(IO.Input))
            circle = nodeBoxTarget.getNode().getInput();
        else
            circle = nodeBoxSource.getNode().getOutput();

        /* Setup line coordinates. */
        Coordinates coordinates = new Coordinates();
        Bounds bounds = circle.localToScene( circle.getBoundsInLocal() );
        coordinates.setX( (bounds.getMinX() + (bounds.getWidth()  / 2)) - 50 );
        coordinates.setY(  bounds.getMinY() + (bounds.getHeight() / 2)       );

        line.setEndX(coordinates.getX());
        line.setEndY(coordinates.getY());

        /* Remove mouse move event filter. */
        MainController.getInstance().getCurrentWorkspace().removeEventFilter(MouseEvent.MOUSE_MOVED, filter);

        /* Add the line click, to remove the line. */
        line.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MainController.getInstance().getCurrentWorkspace().getChildren().remove(line);

                if ( Edge.this.getNodeBoxSource().removeEdge(Edge.this) != true ||
                        Edge.this.getNodeBoxTarget().removeEdge(Edge.this) != true){
                    System.err.println("> lineRemove: One of the edges was not removed!");
                }

                /* TODO: Send a stub image to NodeBox target (input node). */
            }
        });

        /* Adds mouse enter. */
        line.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                line.setCursor(Cursor.HAND);
            }
        });

        /* Adds mouse exit. */
        line.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                line.setCursor(Cursor.DEFAULT);
            }
        });

        /* Toggles the last connection. */
        if (lastConnection.equals(IO.Input))
            lastConnection = IO.Output;
        else
            lastConnection = IO.Input;

        /* Sends a stub image to the input(target) node. */
        /* TODO: Send a real image object to the target node. */

        nodeBoxTarget.update(getNodeBoxSource().getImage());
    }

    /**
     * closeConnection. This method is invoke when the user does
     * an invalid connection between two NodeBoxes.
     */
    public void closeConnection()
    {
        /* Remove mouse move event filter. */
        MainController.getInstance().getCurrentWorkspace().removeEventFilter(MouseEvent.MOUSE_MOVED, filter);
    }
}
