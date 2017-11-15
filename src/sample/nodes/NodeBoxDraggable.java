package sample.nodes;

import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import sample.util.Coordinates;
import sample.util.Edge;

/**
 * NodeBoxDraggable class. This class inherits from
 * DraggedBehaviour and overrides the appropriate
 * methods to work properly.
 * @implNote Unlike the name, this class handles all
 * mouse events, not just drag events.
 * @author Daniel
 * @version v1.0
 */
public class NodeBoxDraggable extends DraggedBehavior {

    /**
     * Initializes the class.
     * @param component Region component.
     */
    public NodeBoxDraggable(Region component) {
        super(component);
    }

    /**
     * Gets the NodeBox attached to this class.
     * @return Returns NodeBox component.
     */
    @Override
    public NodeBox cast() {
        return (NodeBox) getComponent();
    }

    /**
     * onMouseDragged event.
     * @param event Mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent event){
        NodeBox node = (NodeBox) getComponent();

        destiny.setCoordinates( event.getSceneX() - getComponent().getParent().getLayoutX(),
                event.getSceneY() - getComponent().getParent().getLayoutY());
        destiny.translation( origin );
        destiny.setCoordinates( cursor.getX() + destiny.getX() ,cursor.getY() + destiny.getY() );
        getComponent().setLayoutX(destiny.getX());
        getComponent().setLayoutY(destiny.getY());

        /* Mouse all the lines connected into it =). */
        for (Edge edge : node.getEdgeList()) {
            Edge.IO io;

            /* Checks if we are in a input/output line. */
            if (edge.getNodeBoxSource() == node)
                io = Edge.IO.Output;
            else
                io = Edge.IO.Input;

            /* Appropriate circle. */
            Circle circle = (io.equals(Edge.IO.Output) ? node.getNode().getOutput() : node.getNode().getInput());

            /* Calculate the coordinates. */
            Coordinates coordinates = new Coordinates();
            Bounds bounds = circle.localToScene( circle.getBoundsInLocal() );
            coordinates.setX( (bounds.getMinX() + (bounds.getWidth()  / 2)) - 50 );
            coordinates.setY(  bounds.getMinY() + (bounds.getHeight() / 2)       );

            /* Calculates the module of position difference. */
            double diff = Math.abs (edge.getLine().getEndX() - edge.getLine().getStartX());
            diff = (diff * 0.4);

            /* Input node. */
            if (io.equals(Edge.IO.Input)) {
                if (edge.getLastConnection().equals(Edge.IO.Input)) {
                    edge.getLine().setEndX(coordinates.getX());
                    edge.getLine().setEndY(coordinates.getY());
                }
                else{
                    diff *= -1;
                    edge.getLine().setStartX(coordinates.getX());
                    edge.getLine().setStartY(coordinates.getY());
                }
            }

            /* Output node. */
            else{
                if (edge.getLastConnection().equals(Edge.IO.Input)) {
                    edge.getLine().setStartX(coordinates.getX());
                    edge.getLine().setStartY(coordinates.getY());
                }
                else{
                    diff *= -1;
                    edge.getLine().setEndX(coordinates.getX());
                    edge.getLine().setEndY(coordinates.getY());
                }
            }

            /* Setup new coordinates. */
            edge.getLine().setControlX1( edge.getLine().getStartX() + diff );
            edge.getLine().setControlY1( edge.getLine().getStartY() );
            edge.getLine().setControlX2( edge.getLine().getEndX  () - diff );
            edge.getLine().setControlY2( edge.getLine().getEndY  () );
        }
    }

    /**
     * onMouseEntered event.
     * @param event Mouse event.
     */
    @Override
    public void onMouseEntered(MouseEvent event) {
        NodeBox nodeBox = cast();

        nodeBox.getHeader().setVisible(true);
        /*
        if( nodeBox instanceof Input ) {
            nodeBox.getNode().add(nodeBox.getNode().getOutput());
        }
        else if( nodeBox instanceof Output ) {
            nodeBox.getNode().add(nodeBox.getNode().getInput());
        }
        else{
            nodeBox.getNode().add(nodeBox.getNode().getInput());
            nodeBox.getNode().add(nodeBox.getNode().getOutput());
        }
        */
    }

    /**
     * onMouseExited event.
     * @param event Mouse event.
     */
    @Override
    public void onMouseExited(MouseEvent event) {
        NodeBox nodeBox = cast();

        nodeBox.getHeader().setVisible(false);

        /*
        if( nodeBox instanceof Input ) {
            nodeBox.getNode().remove(nodeBox.getNode().getOutput());
        }
        else if( nodeBox instanceof Output ) {
            nodeBox.getNode().remove(nodeBox.getNode().getInput());
        }
        else{
            nodeBox.getNode().remove(nodeBox.getNode().getInput());
            nodeBox.getNode().remove(nodeBox.getNode().getOutput());
        }
        */
    }
}
