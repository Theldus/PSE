package sample.util;

/**
 * Dimension class.
 * @author Daniel.
 * @since 2017-09-01
 */
public class Dimension {

    /* Width and Height. */
    private double width;
    private double height;

    /**
     * Dimension constructor. Initializes everything with
     * zeroes.
     */
    public Dimension(){
        this(.0,.0);
    }

    /**
     * Dimension constructor.
     * @param width Width.
     * @param height Height.
     */
    public Dimension( double width, double height ){
        setWidth(width);
        setHeight(height);
    }

    /**
     * Dimension constructor.
     * @param dimension Initializes with another instance.
     */
    public Dimension( Dimension dimension ){
        this( dimension.width, dimension.height );
    }

    /**
     * Gets width.
     * @return Returns width.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets width.
     * @param width width.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets height.
     * @return Returns height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets height.
     * @param height height.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Formats width and height into a string.
     * @return Returns a width/height formatted into a string.
     */
    @Override
    public String toString() {
        return String.format("[ %.2f x %.2f ]",getWidth(),getHeight());
    }
}
