package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.util.Edge;
import sample.workspace.Workspace;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * LaplacianFilter class. This is a Laplacian node.
 * @author Daniel, Davidson and Izabela.
 */
public class LaplacianFilter extends NodeBox {

    /**
     * Initializes the Histogram.
     * Currently, just setup the title, workspace and icon.
     * @param title Node title.
     * @param root workspace to be added.
     * @param iconPath Histogram icon.
     */
    public LaplacianFilter(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
        getHeader().removeSupport();
    }

    /**
     * Installs the node, i.e: adds into the workspace.
     */
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

    public BufferedImage execute (BufferedImage imagemEnt){
        int[][] matrizImagem = null;
        BufferedImage image = null;
        try {
            int width  = imagemEnt.getWidth();
            int height = imagemEnt.getHeight();

            /**
             * The following code gets the RGB intensities values
             * (which are the same, since we are in greyscale).
             * The pixels coordinates are combined below, after, the variable
             * vResp receives the arithmetic operation in the obtained values.
             */
            matrizImagem = new int[width][height];
            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < height - 1; j++) {
                    Color p0 = new Color (imagemEnt.getRGB(i-1, j-1));
                    Color p1 = new Color (imagemEnt.getRGB(i-1, j));
                    Color p2 =  new Color(imagemEnt.getRGB(i-1, j+1));
                    Color p10 = new Color(imagemEnt.getRGB(i, j-1));
                    Color p11 = new Color(imagemEnt.getRGB(i, j));
                    Color p12 = new Color(imagemEnt.getRGB(i, j+1));
                    Color p20 = new Color(imagemEnt.getRGB(i+1, j-1));
                    Color p21 = new Color(imagemEnt.getRGB(i+1, j));
                    Color p22 = new Color(imagemEnt.getRGB(i+1, j+1));

                    /* Could be used the B or G component, since the image is achromatic. */
                    int vRes = -p0.getRed() -   p1.getRed() - p2.getRed() +
                            -p10.getRed() + 8*p11.getRed() - p12.getRed() +
                            -p20.getRed() -   p21.getRed() - p22.getRed();

                    vRes = Math.min(255, Math.max(0, vRes));
                    matrizImagem[i][j] = vRes;
                }
            }

            /* Builds the new image. */
            image = new BufferedImage(width,height, BufferedImage.TYPE_BYTE_GRAY);
            WritableRaster raster = image.getRaster();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    raster.setSample(i, j, 0, matrizImagem[i][j]);
                }
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

        return image;
    }

    /**
     * Executes the algorithm.
     */
    @Override
    public void execute() {
        Image img = SwingFXUtils.toFXImage( execute( SwingFXUtils.fromFXImage(getImage(),null) ),null  );
        setImage(img);
    }
}
