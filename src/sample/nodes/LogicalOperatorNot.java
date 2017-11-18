package sample.nodes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

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

    @Override
    public void update(Image image) {

        setImage(image);
        execute();

        if( ! (getImage() instanceof ImageFacade) ){
            super.update(getImage());
        }

    }

    //Método para fazer NOT em uma imagens A.
    int[][] not(int [][] A){
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int [][] B = new int[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                B[i][j] = (byte) ~A[i][j];
            }
        }
        return B;
    }


    @Override
    public void install() {

    }

    @Override
    public void execute() {
        setImage( ImageUtil.toImage( not( ImageUtil.convertToGreyTone( SwingFXUtils.fromFXImage(getImage(),null) ) ) ) );
    }
}
