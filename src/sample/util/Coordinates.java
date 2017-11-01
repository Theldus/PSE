package sample.util;

/**
 * Created by Daniel on 05/09/2017.
 */
public class Coordinates {

    private double x;
    private double y;

    public Coordinates(){
        this(.0,.0);
    }

    public Coordinates( double x, double y ){
        setCoordinates(x,y);
    }

    public Coordinates( Coordinates coordinates){
        this(coordinates.x,coordinates.y);
    }

    public void setCoordinates( double x, double y ){
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void translation( Coordinates extremity ) {
        setCoordinates( getX() - extremity.x , getY() - extremity.y  );
    }

    @Override
    public String toString() {
        return String.format("( %d : %d )",(int)getX(),(int)getY());
    }
}

