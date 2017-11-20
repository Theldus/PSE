import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.workspace.Workspace;
import sample.nodes.NodeBox;
import sample.nodes.ImageFacade;

import java.awt.image.BufferedImage;

/**
 * ColorInverter class. This is a Histogram node.
 * @author Davidson
 */
public class ColorInverter extends NodeBox {

    /**
     * Initializes the Histogram.
     * Currently, just setup the title, workspace and icon.
     * @param title Node title.
     * @param root workspace to be added.
     * @param iconPath Histogram icon.
     */
    public ColorInverter(String title, Workspace root, String iconPath) {
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

        setImage(image);
        execute();

        if( ! (getImage() instanceof ImageFacade) ){
            super.update(getImage());
        }
    }

    /**
     * Execute the ColorInverter algorithm.
     * @param bi Image to be processed.
     * @return Resulting image.
     */
    public BufferedImage execute(BufferedImage bi){
        BufferedImage bImg = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);

        /* Runs through the entire Matrix. */
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int colors = bi.getRGB(i, j);
                int r = (colors >> 16) & 0xFF;
                int g = (colors >>  8) & 0xFF;
                int b = (colors & 0xFF);

                /* Invert the colors. */
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                /* Set the color. */
                bImg.setRGB(i, j, (r << 16) | (g << 8) | b);
            }
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
