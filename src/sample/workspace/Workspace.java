package sample.workspace;

import sample.nodes.NodeBox;
import sample.util.Dimension;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Workspace extends AnchorPane {

    private final static Paint BACKGROUND_COLOR = Paint.valueOf("#111111");
    private final static Paint DOT_COLOR = Paint.valueOf("#DDDDDD");

    List<NodeBox> nodeBoxList;

    public Workspace(Dimension dimension){

        this.nodeBoxList = new ArrayList<>();
        setMinSize(dimension.getWidth(),dimension.getHeight());
        setPrefSize(dimension.getWidth(),dimension.getHeight());
        setProperty();
        addDots(60);
    }

    public void setProperty(){
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
    }

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

    public void add(Pane nodeBox){
        getChildren().add(nodeBox);
    }

    public void remove(Pane nodeBox){
        getChildren().remove(nodeBox);
    }

    public void addNode(NodeBox nodeBox) {
        this.nodeBoxList.add(nodeBox);
    }

    public void removeNode(NodeBox nodeBox) { this.nodeBoxList.remove(nodeBox); }
}