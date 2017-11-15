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

    private int[][] matMasc = new int[3][3];

    /**
     * Initializes the GaussianFilter.
     * Currently, just setup the title, workspace and icon.
     * @param title NodeBox title.
     * @param root workspace to be added.
     * @param iconPath NodeBox icon path.
     */
    public GaussianFilter(String title,Workspace root,String iconPath) {
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

    public BufferedImage execute(BufferedImage imagemEnt) {

        BufferedImage image = null;
        int matrix [][] = ImageUtil.convertToGreyTone(imagemEnt); // instanciando matriz de trabalho
        int width = matrix.length;
        int height = matrix[0].length;

        double somatorio = 0;

        matMasc[0][0] = 1;
        matMasc[0][1] = 2;
        matMasc[0][2] = 1;
        matMasc[1][0] = 2;
        matMasc[1][1] = 4;
        matMasc[1][2] = 2;
        matMasc[2][0] = 1;
        matMasc[2][1] = 2;
        matMasc[2][2] = 1;


        for (int x = 2; x < width - 2; x++) {
            for (int y = 2; y < height - 2; y++) {

                somatorio = 0;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        somatorio += matrix[x + (i - 1)][y + (j - 1)] * matMasc[i][j]; // convoluindo masc e imagem
                    }
                }
                matrix[x][y] = (int) somatorio / 16;
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

    @Override
    public void execute() {
        Image img = SwingFXUtils.toFXImage( execute( SwingFXUtils.fromFXImage(getImage(),null) ),null  );
        setImage(img);
    }
}
