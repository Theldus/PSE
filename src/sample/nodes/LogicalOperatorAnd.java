package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.util.Edge;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

import java.awt.image.BufferedImage;

/**
 * LogicalOperatorAnd class. This is a AND node.
 * @author Pertence, Daniel.
 */
public class LogicalOperatorAnd extends NodeBox {

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
    public LogicalOperatorAnd(String title, Workspace root, String actionIconName) {
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

            int mtxResult [][] = and(ImageUtil.convertToGreyTone(imagePeer[0]), ImageUtil.convertToGreyTone(imagePeer[1]));
            setImage( ImageUtil.toImage( mtxResult ));
            System.out.println("AND!");
            super.update(getImage());
        }
    }

    /**
     * AND two images.
     * @param A First image.
     * @param B Second image.
     * @return Returns a matrix representing the operation.
     */
    int[][] and(int [][] A, int [][] B){
        int n = A.length;    /* A lines.   */
        int m = A[0].length; /* A columns  */
        int o = B.length;    /* B lines.   */
        int p = B[0].length; /* B columns. */

        if(n != o || m != p)
            return ImageUtil.convertToGreyTone(SwingFXUtils.fromFXImage(auxImg,null));

        int [][] C = new int[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                C[i][j] = (byte) (A[i][j] & B[i][j]);
            }
        }
        return C;
    }

    /**
     * Installs the node, i.e: adds into the workspace.
     */
    @Override
    public void install() {
    }

    /**
     * Execute alias: converts the input image,
     * calls the algorithm, convert back, and
     * stores.
     */
    @Override
    public void execute() {
    }
}
