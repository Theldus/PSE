package sample.nodes;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import sample.Behavior;
import sample.util.Coordinates;

public abstract class DraggedBehavior implements Behavior{

    private Region component;
    protected Coordinates origin = new Coordinates();
    protected Coordinates cursor = new Coordinates();
    protected Coordinates destiny = new Coordinates();

    public DraggedBehavior( Region component ){
        setComponent(component);
    }

    public abstract Object cast();

    public void onMouseDragged(MouseEvent event){

        destiny.setCoordinates( event.getSceneX() - getComponent().getParent().getLayoutX(),
                                event.getSceneY() - getComponent().getParent().getLayoutY());
        destiny.translation( origin );
        destiny.setCoordinates( cursor.getX() + destiny.getX() ,cursor.getY() + destiny.getY() );
        getComponent().setLayoutX(destiny.getX());
        getComponent().setLayoutY(destiny.getY());
    }

    public void onMouseEntered(MouseEvent event){
        //Empty
    }

    public void onMouseExited(MouseEvent event){
        //Empty
    }

    public void onMousePressed(MouseEvent event){

        origin.setCoordinates(event.getSceneX() - getComponent().getParent().getLayoutX(),
                              event.getSceneY() - getComponent().getParent().getLayoutY());
        cursor.setCoordinates(  origin.getX() - event.getX(),
                origin.getY() - event.getY());
        getComponent().setCursor(Cursor.MOVE);
    }

    public void onMouseReleased(MouseEvent event){
        getComponent().setCursor(Cursor.DEFAULT);
    }

    public Region getComponent() {
        return component;
    }

    public void setComponent(Region component) {
        this.component = component;
    }

    @Override
    public void make() {
        getComponent().setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onMouseEntered(event);
            }
        });

        getComponent().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              onMouseDragged(event);
            }
        });

        getComponent().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onMouseExited(event);
            }
        });

        getComponent().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onMousePressed(event);
            }
        });

        getComponent().setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onMouseReleased(event);
            }
        });
    }
}
