package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import sample.workspace.Workspace;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Histogram class. This is a Histogram node.
 * @author Daniel
 */
public class Histogram extends NodeBox {

    private final static int MAX = 256;

    /**
     * Initializes the Histogram.
     * Currently, just setup the title, workspace and icon.
     * @param title Node title.
     * @param root workspace to be added.
     * @param iconPath Histogram icon.
     */
    public Histogram(String title, Workspace root, String iconPath) {
        super(title, root, iconPath);
    }

    /**
     * Installs the node, i.e: adds into the workspace.
     */
    @Override
    public void install() {
        getRoot().add(this);
    }

    /**
     * Receives an image from another node.
     * @param image Input image
     */
    @Override
    public void update(Image image)
    {
        /* TODO: The image processing goes here, and, when finished,
         * we have to propagate the final processed image for our child.
         */

        /*
         * At every single change made in this node, the final image *SHOULD* be
         * send for every output line connected into this NodeBox by the update()
         * method.
         */

        //System.out.println("I received an image =)");

        setImage(image);
        execute();

        if( ! (getImage() instanceof ImageFacade) ){
            super.update(getImage());
        }

    }

    private BufferedImage generateHistogram( BufferedImage buffImg ){

        int [][] grayscaleImg = convertToGreyTone(buffImg);
        int [] histogram = new int[MAX];

        for( int i = 0; i < grayscaleImg.length ; ++i ){
            for( int j = 0; j < grayscaleImg[0].length; ++j ){
                ++histogram[grayscaleImg[i][j]];
            }
        }

        int maxValue = histogram[0];
        for( int i = 1; i < histogram.length ; ++i ){
            if( histogram[i] > maxValue )
                maxValue = histogram[i];
        }

        int minValue = histogram[0];
        for( int i = 1; i < histogram.length ; ++i ){
            if( histogram[i] < minValue )
                minValue = histogram[i];
        }

        //normalize
        for( int i = 1; i < histogram.length ; ++i ){
            histogram[i] = (((histogram[i] - minValue)/( maxValue - minValue ))*MAX);
        }

        WritableImage histogramUI = new WritableImage(maxValue,MAX);
        for( int i = 0; i < grayscaleImg.length ; ++i ){
            for( int j = 0; j < grayscaleImg[0].length; ++j ){
                histogramUI.getPixelWriter().setColor(i,j,new javafx.scene.paint.Color(0,0,0,1));
            }
        }

        for( int j = 0; j < MAX; ++j ){
            for( int i = 0; i < histogram[j]; ++i  ){
                histogramUI.getPixelWriter().setColor((MAX-1) - i, j,new javafx.scene.paint.Color(1,1,1,1));
            }
        }

        return SwingFXUtils.fromFXImage(histogramUI,null);
    }


    private int[][] convertToGreyTone( BufferedImage buffImg ){

        int [][] newImg = new int[buffImg.getWidth()][buffImg.getHeight()];
        Color pixelColor = null;
        int pixelColorValue = 0;
        for( int i = 0; i < newImg.length ; ++i ){
            for( int j =0; j < newImg[i].length ; ++j ){
                pixelColor = new Color( buffImg.getRGB(i,j) );
                pixelColorValue = ( (pixelColor.getRed() + pixelColor.getBlue() + pixelColor.getGreen())/3 );
                newImg[i][j] = pixelColorValue;
                System.out.print(pixelColorValue + " ");
            }
            System.out.println();
        }
        return newImg;
    }



    @Override
    public void execute() {
        //Image img = SwingFXUtils.toFXImage( generateHistogram( SwingFXUtils.fromFXImage(getImage(),null) ),null  );
        //setImage(img);
    }
}