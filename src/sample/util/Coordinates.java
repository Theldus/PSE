package sample.util;

/**
 * Coordinates class.
 * @author Daniel
 * @since 2017-09-05
 */
public class Coordinates {

    /* Coordinates. */
    private double x;
    private double y;

    /**
     * Coordinates constructor. Initializes everything
     * with zeroes.
     */
    public Coordinates(){
        this(.0,.0);
    }

    /**
     * Coordinates constructor.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public Coordinates( double x, double y ){
        setCoordinates(x,y);
    }

    /**
     * Coordinates constructor.
     * @param coordinates Initializes with another instance.
     */
    public Coordinates( Coordinates coordinates){
        this(coordinates.x,coordinates.y);
    }

    /**
     * Set coordinates.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public void setCoordinates( double x, double y ){
        setX(x);
        setY(y);
    }

    /**
     * Get X coordinate.
     * @return Returns X coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets X coordinate.
     * @param x X coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get Y coordinate.
     * @return Returns Y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets Y coordinate.
     * @param y Y coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Does translation based in some extremity.
     * @param extremity
     */
    public void translation( Coordinates extremity ) {
        setCoordinates( getX() - extremity.x , getY() - extremity.y  );
    }

    /**
     * Formats the X/Y coordinates into a string format.
     * @return X/Y coordinates.
     */
    @Override
    public String toString() {
        return String.format("( %d : %d )",(int)getX(),(int)getY());
    }
}
