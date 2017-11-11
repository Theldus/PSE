package sample.util;

/**
 * Created by Daniel on 01/09/2017.
 */
public class Dimension {

    private double width;
    private double height;

    public Dimension(){
        this(.0,.0);
    }

    public Dimension( double width, double height ){
        setWidth(width);
        setHeight(height);
    }

    public Dimension( Dimension dimension ){
        this( dimension.width, dimension.height );
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("[ %.2f x %.2f ]",getWidth(),getHeight());
    }
}

