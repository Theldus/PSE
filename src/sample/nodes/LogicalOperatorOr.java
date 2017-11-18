package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.util.Edge;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class LogicalOperatorOr extends NodeBox {

    private int emptyPos = 0;
    private final int INPUT_MAX = 2;
    private BufferedImage[] imagePeer;

    /**
     * Setup the appearance: title, icon and workspace.
     *
     * @param title          NodeBox title
     * @param root           Root element that should contain this NodeBox.
     * @param actionIconName NodeBox icon path.
     */
    public LogicalOperatorOr(String title, Workspace root, String actionIconName) {
        super(title, root, actionIconName);
        getHeader().removeSupport();
        imagePeer = new BufferedImage[INPUT_MAX];
        for( int i = 0; i < imagePeer.length ; ++i )
            imagePeer[i] = SwingFXUtils.fromFXImage(auxImg,null);
    }


    @Override
    public void update(Image image) {

        imagePeer[ this.emptyPos++ % 2 ] = SwingFXUtils.fromFXImage(image,null);

        if( getInputNumber() == INPUT_MAX ){

            if (imagePeer[0] == null || imagePeer[1] == null )
                System.out.println("Image NULL!");

            int mtxResult [][] = or(ImageUtil.convertToGreyTone(imagePeer[0]), ImageUtil.convertToGreyTone(imagePeer[1]));
            setImage( ImageUtil.toImage( mtxResult ));
            System.out.println("OR!");
            super.update(getImage());

        }

    }

    //Método para fazer OR entre imagens A e B
    private int[][] or(int [][] A, int [][] B){
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int o = B.length; //Número de linhas de B
        int p = B[0].length; // Número de colunas B

        if(n != o || m != p){
            return ImageUtil.convertToGreyTone(SwingFXUtils.fromFXImage(auxImg,null));
        }

        int [][] C = new int[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                C[i][j] = (byte) (A[i][j] | B[i][j]);
            }
        }
        return C;
    }

    @Override
    public void install() {
    }

    @Override
    public void execute() {
    }
}
