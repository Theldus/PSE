package sample.nodes;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import sample.Behavior;
import sample.util.Coordinates;

/**
 * DraggedBehaviour class. This class deals with a generic
 * implementation of mouse events, such as drag, press, enter...
 * @author Daniel
 * @version v1.0
 */
public abstract class DraggedBehavior implements Behavior{

    /**
     * Region component.
     */
    private Region component;

    /**
     * Mouse coordinates.
     */
    protected Coordinates origin = new Coordinates();
    protected Coordinates cursor = new Coordinates();
    protected Coordinates destiny = new Coordinates();

    /**
     * Initializes the class.
     * @param component Component region.
     */
    public DraggedBehavior( Region component ){
        setComponent(component);
    }

    /**
     * Generic method to gets the parent object.
     * @return Parent object.
     */
    public abstract Object cast();

    /**
     * Generic implementation of what would be a mouseDragged event.
     * @param event Mouse event.
     */
    public void onMouseDragged(MouseEvent event){

        destiny.setCoordinates( event.getSceneX() - getComponent().getParent().getLayoutX(),
                                event.getSceneY() - getComponent().getParent().getLayoutY());
        destiny.translation( origin );
        destiny.setCoordinates( cursor.getX() + destiny.getX() ,cursor.getY() + destiny.getY() );
        getComponent().setLayoutX(destiny.getX());
        getComponent().setLayoutY(destiny.getY());
    }

    /**
     * Generic implementation of what would be a mouseEntered event.
     * @param event Mouse event.
     */
    public void onMouseEntered(MouseEvent event){
    }

    /**
     * Generic implementation of what would be a mouseExited event.
     * @param event Mouse event.
     */
    public void onMouseExited(MouseEvent event){
    }

    /**
     * Generic implementation of what would be a mousePressed event.
     * @param event Mouse event.
     */
    public void onMousePressed(MouseEvent event){

        origin.setCoordinates(event.getSceneX() - getComponent().getParent().getLayoutX(),
                              event.getSceneY() - getComponent().getParent().getLayoutY());
        cursor.setCoordinates(  origin.getX() - event.getX(),
                origin.getY() - event.getY());
        getComponent().setCursor(Cursor.MOVE);
    }

    /**
     * Generic implementation of what would be a mouseReleased event.
     * @param event Mouse event.
     */
    public void onMouseReleased(MouseEvent event){
        getComponent().setCursor(Cursor.DEFAULT);
    }

    /**
     * Gets the region component.
     * @return Returns the region component.
     */
    public Region getComponent() {
        return component;
    }

    /**
     * Sets the region component.
     * @param component Region component.
     */
    public void setComponent(Region component) {
        this.component = component;
    }

    /**
     * Setup all the events.
     */
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
