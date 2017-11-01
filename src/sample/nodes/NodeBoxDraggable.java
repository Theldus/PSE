package sample.nodes;

import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;


public class NodeBoxDraggable extends DraggedBehavior {

    public NodeBoxDraggable(Region component) {
        super(component);
    }

    @Override
    public NodeBox cast() {
        return (NodeBox) getComponent();
    }

    @Override
    public void onMouseEntered(MouseEvent event) {
        NodeBox nodeBox = cast();

        //nodeBox.getHeader().setVisible(true);
        //nodeBox.getNode().add(nodeBox.getNode().getInput());
        //nodeBox.getNode().add(nodeBox.getNode().getOutput());
    }

    @Override
    public void onMouseExited(MouseEvent event) {
        NodeBox nodeBox = cast();
        //nodeBox.getHeader().setVisible(false);
        //nodeBox.getNode().remove(nodeBox.getNode().getInput());
        //nodeBox.getNode().remove(nodeBox.getNode().getOutput());
    }
}
