package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.util.Edge;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * GaussianFilter class. This is a GaussianFilter node.
 * @author Daniel, Davidson and Izabela.
 */
public class GaussianFilter extends NodeBox {

    private Image imageReserve;

    /* Gaussian masks. */
    enum Mask{

        MASK_3_X_3( new int[][]{ {1,2,1},
                                 {2,4,2},
                                 {1,2,1} } ),

        MASK_5_X_5( new int[][]{ {1,4,6,4,1},
                                 {4,16,24,16,4},
                                 {6,24,36,24,6},
                                 {4,16,24,16,4},
                                 {1,4,6,4,1} } ),

        MASK_7_X_7(new int[][]{ {0,1,4,5,4,1,0},
                                {1,9,23,29,23,9,1},
                                {4,23,61,78,61,23,4},
                                {5,29,78,100,78,29,5},
                                {4,23,61,78,61,23,4},
                                {1,9,23,29,23,9,1},
                                {0,1,4,5,4,1,0} });

        public int mask [][];

        Mask(int[][] mask) {
            this.mask = mask;
        }

        public int[][] getMask() {
            return mask;
        }
    }

    /**
     * Initializes the GaussianFilter.
     * Currently, just setup the title, workspace and icon.
     * @param title NodeBox title.
     * @param root workspace to be added.
     * @param iconPath NodeBox icon path.
     */
    public GaussianFilter(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
        ToosUINodeBoxController.setRoot(this);
        this.imageReserve = getImage();
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

        if( image != null ){
            setImage(image);
            this.imageReserve = getImage();
        }
        else{
            setImage(imageReserve);
        }

        execute();

        if( ! (getImage() instanceof ImageFacade) ){
            super.update(getImage());
        }
    }

    /**
     * GaussianFilter algorithm.
     * @param imagemEnt Input image.
     * @return Returns the resulting image.
     */
    public BufferedImage execute(BufferedImage imagemEnt) {

        BufferedImage image;
        int matrix [][] = ImageUtil.convertToGreyTone(imagemEnt);
        int width = matrix.length;
        int height = matrix[0].length;

        double somatorio = 0;

        System.out.println("Processing...");

        Mask mask = null;
        int shift = 1;
        switch ( ToosUINodeBoxController.getMask() ){

            case "3x3":
                mask = Mask.MASK_3_X_3;
                System.out.println("Mask 3x3");
                break;
            case "5x5":
                mask = Mask.MASK_5_X_5;
                shift = 2;
                System.out.println("Mask 5x5");
                break;
            default:
                mask = Mask.MASK_7_X_7;
                shift = 3;
                System.out.println("Mask 7x7");
                break;
        }

        for (int x = shift; x < width - shift; x++) {
            for (int y = shift; y < height - shift; y++) {
                somatorio = 0;
                int weight = 0;
                for (int i = 0; i < mask.getMask().length; i++) {
                    for (int j = 0; j < mask.getMask()[i].length; j++) {
                        weight += mask.getMask()[i][j];
                        somatorio += matrix[x + (i - shift)][y + (j - shift)] * mask.getMask()[i][j];
                    }
                }
                matrix[x][y] = (int) somatorio / weight;
            }
        }

        image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        WritableRaster raster = image.getRaster();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                raster.setSample(i, j, 0, matrix[i][j]);
            }
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
