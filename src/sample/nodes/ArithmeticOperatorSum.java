package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.util.Edge;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

import java.awt.image.BufferedImage;

/**
 * ArithmeticOperatorSubt, does a image subtraction.
 * @author Daniel.
 * @implNote Note that this class is multi-input implementation
 * of a node.
 */
public class ArithmeticOperatorSum extends NodeBox {

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
    public ArithmeticOperatorSum(String title, Workspace root, String actionIconName) {
        super(title, root, actionIconName);
        getHeader().removeSupport();
        for( int i = 0; i < imagePeer.length ; ++i )
            imagePeer[i] = SwingFXUtils.fromFXImage(auxImg,null);
    }


    /**
     * Whenever the input is changed or a new connection
     * is made this function is called.
     * @param image Current image
     */
    @Override
    public void update(Image image) {
        imagePeer[ this.emptyPos++ % 2 ] = SwingFXUtils.fromFXImage(image,null);

        if( getInputNumber() == INPUT_MAX ){
            int mtxResult [][] = sum(ImageUtil.convertToGreyTone(imagePeer[0]), ImageUtil.convertToGreyTone(imagePeer[1]));
            setImage( ImageUtil.toImage( mtxResult ));
            System.out.println("Sum!");
            super.update(getImage());
        }
    }

    /**
     * Method to do image sum between two images.
     * @param A First image.
     * @param B Second image.
     * @return Returns the matrix representing the operation.
     */
    private int[][] sum(int [][] A, int [][] B){
        int n = A.length;    /* A lines.   */
        int m = A[0].length; /* A columns. */
        int o = B.length;    /* B lines.   */
        int p = B[0].length; /* B columns. */

        if(n != o || m != p)
            return ImageUtil.convertToGreyTone(SwingFXUtils.fromFXImage(auxImg,null));

        int [][] C = new int[n][m];

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                C[i][j] = ImageUtil.normalize(A[i][j] + B[i][j]);
            }
        }
        return C;
    }

    /**
     * Installs the NodeBox, i.e: sets everything up to work.
     */
    @Override
    public void install() {
    }

    /**
     * The algorithm goes here.
     */
    @Override
    public void execute() {
    }
}
