package sample.util;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Shape;
import sample.MainController;
import sample.nodes.NodeBox;
import sample.nodes.NodeBoxController;

/**
 * Created by Daniel on 01/11/2017.
 */
public class Edge {

    private NodeBox nodeBoxSource;
    private NodeBox nodeBoxTarget;
    private CubicCurve line;
    private IO lastConnection;

    public IO getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(IO lastConnection) {
        this.lastConnection = lastConnection;
    }

    public enum IO{Input, Output};

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

    public CubicCurve setupLine(Coordinates coordinates)
    {
        Circle io = (nodeBoxSource == null) ? nodeBoxTarget.getNode().getOutput()
                : nodeBoxSource.getNode().getInput();

        line = new CubicCurve();
        line.setFill(Color.TRANSPARENT);
        line.setStartX   ( coordinates.getX() );
        line.setStartY   ( coordinates.getY() );
        line.setControlX1(  coordinates.getX() );
        line.setControlX2(  coordinates.getX() );
        line.setControlY1( coordinates.getY() );
        line.setControlY2( coordinates.getY() );
        line.setEndX     ( coordinates.getX() );
        line.setEndY     ( coordinates.getY() );
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);

        System.out.printf("IO X: %f | Y: %f\n", coordinates.getX(), coordinates.getY());

        MainController.getCurrentWorkspace().setOnMouseMoved(new EventHandler<MouseEvent>() {

            private Edge.IO io = Edge.this.lastConnection;

            @Override
            public void handle(MouseEvent event) {
                //Existe linha sendo criada
                if (NodeBoxController.connAcc){

                    //System.out.println("MOVED WORKSPACE!!!");

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
            }
        });

        return line;

    }
}