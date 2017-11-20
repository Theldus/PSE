package sample.workspace;

import sample.nodes.NodeBox;
import sample.util.Dimension;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Workspace class. All the components goes here.
 * @author Daniel
 */
public class Workspace extends AnchorPane {

    /* Colors. */
    private final static Paint BACKGROUND_COLOR = Paint.valueOf("#111111");
    private final static Paint DOT_COLOR = Paint.valueOf("#DDDDDD");

    /* NodeBox list. */
    List<NodeBox> nodeBoxList;

    /**
     * Workspace constructor.
     * @param dimension Desired dimension.
     */
    public Workspace(Dimension dimension){
        this.nodeBoxList = new ArrayList<>();
        setMinSize(dimension.getWidth(),dimension.getHeight());
        setPrefSize(dimension.getWidth(),dimension.getHeight());
        setProperty();
        addDots(60);
    }

    /**
     * Sets the workspace properties.
     */
    public void setProperty(){
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
    }

    /**
     * Add dots into the workspace.
     * @param space Space between dots.
     */
    private void addDots(int space){
        Line line = null;
        int infinity = 0xfff;
        for( int i = space/2; i < infinity ; i+= space ){
            for( int j = space/2; j < infinity; j+= space ){
                line = new Line(i, j,i+1,j+1 );
                line.setStroke(DOT_COLOR);
                line.setStrokeWidth(0.5f);
                this.getChildren().add(line);
            }
        }
    }

    /**
     * Adds a pane into to the worskpace.
     * @param nodeBox NodeBox or other component, to be added.
     */
    public void add(Pane nodeBox){
        getChildren().add(nodeBox);
    }

    /**
     * Removes a given component.
     * @param nodeBox Component to be removed.
     */
    public void remove(Pane nodeBox){
        getChildren().remove(nodeBox);
    }

    /**
     * Adds a NodeBox into the workspace.
     * @param nodeBox NodeBox to be added.
     */
    public void addNode(NodeBox nodeBox) {
        this.nodeBoxList.add(nodeBox);
    }

    /**
     * Removes a given NodeBox from workspace.
     * @param nodeBox NodeBox to be removed.
     */
    public void removeNode(NodeBox nodeBox) { this.nodeBoxList.remove(nodeBox); }
}
