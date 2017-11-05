package sample.util;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Shape;
import sample.Main;
import sample.MainController;
import sample.nodes.NodeBox;
import sample.nodes.NodeBoxController;

/**
 * Created by Daniel on 01/11/2017.
 */
public class Edge {
    public enum IO{Input, Output};

    private NodeBox nodeBoxSource;
    private NodeBox nodeBoxTarget;
    private CubicCurve line;
    private IO lastConnection;
    private EventHandler filter;

    public IO getLastConnection() {
        return lastConnection;
    }
    public void setLastConnection(IO lastConnection) {
        this.lastConnection = lastConnection;
    }

    public void setEdge(NodeBox nodeBox, IO io){
        if (io.equals(IO.Input))
            nodeBoxSource = nodeBox;
        else
            nodeBoxTarget = nodeBox;
    }

    public Edge(NodeBox nodeBox, IO io)
    {
        setEdge( nodeBox,io );
        lastConnection = io;
    }

    public NodeBox getNodeBoxSource() {
        return nodeBoxSource;
    }
    public void setNodeBoxSource(NodeBox nodeBoxSource) {
        this.nodeBoxSource = nodeBoxSource;
    }

    public NodeBox getNodeBoxTarget() {
        return nodeBoxTarget;
    }
    public void setNodeBoxTarget(NodeBox nodeBoxTarget) {
        this.nodeBoxTarget = nodeBoxTarget;
    }

    public CubicCurve getLine() {
        return line;
    }
    public void setLine(CubicCurve line) {
        this.line = line;
    }

    public CubicCurve setupLine()
    {
        Circle io = (nodeBoxSource == null) ? nodeBoxTarget.getNode().getOutput()
                : nodeBoxSource.getNode().getInput();

        Coordinates coordinates = new Coordinates();
        Bounds bounds = io.localToScene( io.getBoundsInLocal() );
        coordinates.setX( (bounds.getMinX() + (bounds.getWidth()  / 2)) - 50 );
        coordinates.setY(  bounds.getMinY() + (bounds.getHeight() / 2)       );

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

        MainController.getCurrentWorkspace().addEventFilter(MouseEvent.MOUSE_MOVED, filter);
        return line;
    }

    public void establishConnection(IO io)
    {
        Circle circle = null;

        if (io.equals(IO.Input))
            circle = nodeBoxSource.getNode().getInput();
        else
            circle = nodeBoxTarget.getNode().getOutput();

        /* Setup line coordinates. */
        Coordinates coordinates = new Coordinates();
        Bounds bounds = circle.localToScene( circle.getBoundsInLocal() );
        coordinates.setX( (bounds.getMinX() + (bounds.getWidth()  / 2)) - 50 );
        coordinates.setY(  bounds.getMinY() + (bounds.getHeight() / 2)       );

        line.setEndX(coordinates.getX());
        line.setEndY(coordinates.getY());

        /* Remove mouse move event filter. */
        MainController.getCurrentWorkspace().removeEventFilter(MouseEvent.MOUSE_MOVED, filter);
    }

    public void closeConnection()
    {
        /* Remove mouse move event filter. */
        MainController.getCurrentWorkspace().removeEventFilter(MouseEvent.MOUSE_MOVED, filter);
    }
}
