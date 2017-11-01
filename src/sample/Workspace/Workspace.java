package sample.Workspace;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
;
import javafx.scene.shape.Line;
import sample.NodeBoxObserver;
import sample.WokspaceSubject;

import sample.util.Coordinates;
import sample.util.Dimension;


import java.util.ArrayList;
import java.util.List;

public class Workspace extends AnchorPane implements WokspaceSubject {

    private final static Paint BACKGROUND_COLOR = Paint.valueOf("#111111");
    private final static Paint DOT_COLOR = Paint.valueOf("#DDDDDD");

    List<NodeBoxObserver> nodeBoxList;

    public Workspace(Dimension dimension){



        this.nodeBoxList = new ArrayList<>();
        setMinSize(dimension.getWidth(),dimension.getHeight());
        setPrefSize(dimension.getWidth(),dimension.getHeight());
        setProperty();
        addDots(60);
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getSceneX()+" "+event.getSceneY());
            }
        });
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

    @Override
    public void addObserver(NodeBoxObserver nodeBoxObserver) {
        this.nodeBoxList.add(nodeBoxObserver);
    }

    @Override
    public void removeObserver(NodeBoxObserver nodeBoxObserver) {

        int indexObj = this.nodeBoxList.indexOf(nodeBoxObserver);
        if(  indexObj != -1 ){
            nodeBoxList.remove(indexObj);
        }
    }

    @Override
    public void notifyAllObservers(Coordinates coordinates) {
        for( NodeBoxObserver observers : nodeBoxList ){
            observers.update(coordinates);
        }
    }

    @Override
    public void setChange() {
        //Empty
    }



}