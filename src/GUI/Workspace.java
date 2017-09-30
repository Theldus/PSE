package GUI;

import javafx.scene.Node;
import javafx.scene.effect.Light;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import org.omg.CORBA.PRIVATE_MEMBER;
import sample.Dimension;

import java.awt.*;

/**
 * Created by Daniel on 10/09/2017.
 */
public class Workspace extends AnchorPane {

    public final static Paint BACKGROUND_COLOR = Paint.valueOf("#111111");

    public Workspace(Dimension dimension){
        setMinSize(dimension.getWidth(),dimension.getHeight());
        setPrefSize(dimension.getWidth(),dimension.getHeight());
        setProperty();
        addDots(60);
    }

    public void setProperty(){
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
    }


    private Line createHLine(double width, double strokeWidth, double space, String color){
        Line line = new Line(0.0f,space,width,space);
        line.setLayoutX(0.0f);
        line.setLayoutY(0.0f);
        line.setStroke(Paint.valueOf(color));
        line.setStrokeWidth(strokeWidth);
        return line;
    }
    private Line createVLine(double width, double strokeWidth, double space, String color){
        Line line = new Line(space,0.0f,space,width);
        line.setLayoutX(0.0f);
        line.setLayoutY(0.0f);
        line.setStroke(Paint.valueOf(color));
        line.setStrokeWidth(strokeWidth);
        //line.setStrokeType(StrokeType.OUTSIDE);
        return line;
    }

    public void addDots( int space ){

        Line line = null;
        int infinity = 0xfff;
        for( int i = space/2; i < infinity ; i+= space ){
            for( int j = space/2; j < infinity; j+= space ){
                line = new Line(i, j,i+1,j+1 );
                line.setStroke(Paint.valueOf("#DDDDDD"));
                line.setStrokeWidth(0.5f);
                getChildren().add(line);
            }
        }

        /*
        for( int i = 40 ; i < maxSize ; i+= 40 ){
            getChildren().add( createHLine( maxSize,1.0f,i,"#303030") );
            getChildren().add( createVLine( maxSize,1.0f,i,"#303030") );
        }

        for( int i = 20 ; i < maxSize ; i+= 20 ){
            getChildren().add( createHLine( maxSize,0.5f,i,"#111111"));
            getChildren().add( createVLine( maxSize,0.5f,i,"#111111"));
        }
        */
    }

}
