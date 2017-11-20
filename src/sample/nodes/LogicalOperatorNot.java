package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

/**
 * LogicalOperatorNot class. This is a NOT node.
 * @author Pertence, Daniel.
 */
public class LogicalOperatorNot extends NodeBox {
    /**
     * Setup the appearance: title, icon and workspace.
     *
     * @param title          NodeBox title
     * @param root           Root element that should contain this NodeBox.
     * @param actionIconName NodeBox icon path.
     */
    public LogicalOperatorNot(String title, Workspace root, String actionIconName) {
        super(title, root, actionIconName);
        getHeader().removeSupport();
    }

    /**
     * Receives an image from another node.
     * @param image Input image
     */
    @Override
    public void update(Image image) {
        setImage(image);
        execute();

        if( ! (getImage() instanceof ImageFacade) ){
            super.update(getImage());
        }
    }

    /**
     * Negate an image.
     * @param A Input image.
     * @return Returns a matrix representing the operation.
     */
    int[][] not(int [][] A){
        int n = A.length;    /* A lines.  */
        int m = A[0].length; /* A columns */
        int [][] B = new int[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                B[i][j] = (byte) ~A[i][j];
            }
        }
        return B;
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
        setImage( ImageUtil.toImage( not( ImageUtil.convertToGreyTone( SwingFXUtils.fromFXImage(getImage(),null) ) ) ) );
    }
}
