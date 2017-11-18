package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.workspace.Workspace;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Histogram class. This is a Histogram node.
 * @author Daniel
 */
public class Histogram extends NodeBox {

    protected int[] histogram = new int[256];

    /**
     * Initializes the Histogram.
     * Currently, just setup the title, workspace and icon.
     * @param title Node title.
     * @param root workspace to be added.
     * @param iconPath Histogram icon.
     */
    public Histogram(String title, Workspace root, String iconPath) {
        super(title, root, iconPath);
        getHeader().removeSupport();
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

        setImage(image);
        execute();

        if( ! (getImage() instanceof ImageFacade) ){
            super.update(getImage());
        }
    }

    /**
     * Execute the Histogram algorithm.
     * @param bi Image to be processed.
     * @return Resulting Histogram.
     */
    public BufferedImage execute(BufferedImage bi){
        Graphics2D g2d;
        BufferedImage bImg = new BufferedImage(256, 300, BufferedImage.TYPE_INT_RGB);
        g2d = bImg.createGraphics();

        /* Clear histogram. */
        histogram = new int[256];

        /* Mounts the histogram. */
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int color = bi.getRGB(i, j);
                int r = (color >> 16)  & 0xFF;
                int g = (color >>  8)  & 0xFF;
                int b = (color & 0xFF);
                color = (r + g + b) / 3;
                histogram [ color ]++;
            }
        }

        /* Gets the maximum value, to serve as threshold. */
        int top = -1;
        for (int i = 0; i < 256; i++)
            if (histogram[i] > top)
                top = histogram[i];

        /* Draws a title. TODO: Fix this title. */
        //g2d.drawString("Histograma (0-255)", 30, 30);

        /* Draws. */
        for (int i = 0; i < 256; i++)
        {
            int startY = 300 - (300 * histogram[i]) / top;
            int   endY = 300;
            g2d.drawLine(i, startY, i, endY);
        }

        return bImg;
    }

    /**
     * Execute alias: converts the input image,
     * calls the algorithm, convert back, and
     * stores.
     */
    @Override
    public void execute() {
        Image img = SwingFXUtils.toFXImage( execute( SwingFXUtils.fromFXImage(getImage(),null) ),null  );
        setImage(img);
    }
}