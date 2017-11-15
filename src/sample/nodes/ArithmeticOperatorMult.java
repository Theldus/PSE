package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.util.Edge;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

import java.awt.image.BufferedImage;

public class ArithmeticOperatorMult extends NodeBox {

    private int countInput = 0;
    private final int INPUT_MAX = 2;
    private BufferedImage[] imagePeer = new BufferedImage[INPUT_MAX];

    /**
     * Setup the appearance: title, icon and workspace.
     *
     * @param title          NodeBox title
     * @param root           Root element that should contain this NodeBox.
     * @param actionIconName NodeBox icon path.
     */
    public ArithmeticOperatorMult(String title, Workspace root, String actionIconName) {
        super(title, root, actionIconName);
    }


    @Override
    public void update(Image image) {

        int countInput = 0;

        for(Edge e : getEdgeList() ){
            if( e.getNodeBoxTarget() == this )
                ++countInput;
        }

        if( countInput <= 1 )
            imagePeer[ countInput ] = SwingFXUtils.fromFXImage(image,null);

        if( countInput == 2 )
            imagePeer[ this.countInput++ % 2 ] = SwingFXUtils.fromFXImage(image,null);

        System.out.println(countInput);

        if( countInput > 0 ){

            int mtxResult [][] = times(ImageUtil.convertToGreyTone(imagePeer[0]),ImageUtil.convertToGreyTone(imagePeer[1]));
            setImage( ImageUtil.toImage( mtxResult ));
            System.out.println("Times!");
            super.update(image);
        }

    }

    //Método para fazer soma entre imagens A e B
    private int[][] times(int [][] A, int [][] B){
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int o = B.length; //Número de linhas de B
        int p = B[0].length; // Número de colunas B

        if(n != o || m != p){
            return ImageUtil.convertToGreyTone(SwingFXUtils.fromFXImage(auxImg,null));
        }

        int [][] C = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                C[i][j] = ImageUtil.normalize( A[i][j] * B[i][j] );
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
