package sample.nodes;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

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
     * onMouseEntered event.
     * @param event Mouse event.
     */
    @Override
    public void onMouseEntered(MouseEvent event) {
        NodeBox nodeBox = cast();

        //nodeBox.getHeader().setVisible(true);
        //nodeBox.getNode().add(nodeBox.getNode().getInput());
        //nodeBox.getNode().add(nodeBox.getNode().getOutput());
    }

    /**
     * onMouseExited event.
     * @param event Mouse event.
     */
    @Override
    public void onMouseExited(MouseEvent event) {
        NodeBox nodeBox = cast();
        //nodeBox.getHeader().setVisible(false);
        //nodeBox.getNode().remove(nodeBox.getNode().getInput());
        //nodeBox.getNode().remove(nodeBox.getNode().getOutput());
    }
}
