import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.workspace.Workspace;
import sample.nodes.NodeBox;
import sample.nodes.ImageFacade;
import sample.util.ImageUtil;

import java.awt.image.BufferedImage;

/**
 * Sepia class. This is a Sepia node.
 * @author Daniel
 */
public class Sepia extends NodeBox {

    /**
     * Initializes the Histogram.
     * Currently, just setup the title, workspace and icon.
     * @param title Node title.
     * @param root workspace to be added.
     * @param iconPath Histogram icon.
     */
    public Sepia(String title, Workspace root, String iconPath) {
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
                
                /* Set the weights. */
                int tr = (int)( (0.393*r) + (0.769*g) + (0.189*b) );
                int tg = (int)( (0.349*r) + (0.686*g) + (0.168*b) );
                int tb = (int)( (0.272*r) + (0.534*g) + (0.131*b) );                
                
                /* Normalize. */
                tr = ImageUtil.normalize(tr);
                tg = ImageUtil.normalize(tg);
                tb = ImageUtil.normalize(tb);                                
                
                /* RGB Image. */
                colors = (tr << 16) | (tg << 8) | tb;

                /* Set the color. */
                bImg.setRGB(i, j, colors);
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
