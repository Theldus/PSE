package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

import java.awt.*;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * MSE filter.
 * @author Adhonay, Daniel.
 * @since 2017-17-11
 */
public class MSE extends NodeBox{

    private int emptyPos = 0;
    private final int INPUT_MAX = 2;
    private BufferedImage[] imagePeer = new BufferedImage[INPUT_MAX];

    /**
     * Setup the appearance: title, icon and workspace.
     *
     * @param title          NodeBox title
     * @param root           Root element that should contain this NodeBox.
     * @param actionIconName NodeBox icon path.
     */
    public MSE(String title, Workspace root, String actionIconName) {
        super(title, root, actionIconName);
        getHeader().removeSupport();
        for( int i = 0; i < imagePeer.length ; ++i )
            imagePeer[i] = SwingFXUtils.fromFXImage(auxImg,null);
    }

    /**
     * Receives an image from another node.
     * @param image Input image
     */
    @Override
    public void update(Image image) {
        imagePeer[ this.emptyPos++ % 2 ] = SwingFXUtils.fromFXImage(image,null);

        if( getInputNumber() == INPUT_MAX ){

            if (imagePeer[0] == null || imagePeer[1] == null )
                System.out.println("Image NULL!");

            String result = MSE(imagePeer[0], imagePeer[1]);

            Graphics2D g2d;
            BufferedImage bImg = new BufferedImage(80 , 60, BufferedImage.TYPE_INT_RGB);
            g2d = bImg.createGraphics();
            g2d.drawString(result,5   ,bImg.getHeight()/2);
            setImage(SwingFXUtils.toFXImage(bImg,null));
            System.out.printf("PSNR: %s\n",result);
            super.update(getImage());
        }
    }

    /**
     * MSE algorithm.
     * @param im1 Input image1.
     * @param im2 Input image2.
     * @return Returns the similarity between two images.
     */
    public static String MSE (BufferedImage im1, BufferedImage im2) {
        assert(
                im1.getType() == im2.getType()
                        && im1.getHeight() == im2.getHeight()
                        && im1.getWidth() == im2.getWidth());

        double mse = 0;
        int sum = 0;
        int width = im1.getWidth();
        int height = im1.getHeight();
        Raster r1 = im1.getRaster();
        Raster r2 = im2.getRaster();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                mse += Math.pow(r1.getSample(i, j, 0) - r2.getSample(i, j, 0), 2);
                if(r1.getSample(i, j, 0) == r2.getSample(i, j, 0)){
                    sum++;
                }
            }
        }

        mse /= (double) (width * height);
        System.err.println("MSE = " + mse);
        float x = (100*sum)/(width * height);
        System.out.println("The percentage of similarity is approximately="+x+"%");
        return String.format("%.2f",mse);
    }

    /**
     * Execute alias: converts the input image,
     * calls the algorithm, convert back, and
     * stores.
     */
    @Override
    public void execute() {
    }

    /**
     * Installs the node, i.e: adds into the workspace.
     */
    @Override
    public void install() {
    }
}
