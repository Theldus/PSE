package sample.nodes;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import sample.workspace.Workspace;

import javax.imageio.ImageReader;

public class ConversionRGB extends NodeBox {
    /**
     * Setup the appearance: title, icon and workspace.
     *
     * @param title          NodeBox title
     * @param root           Root element that should contain this NodeBox.
     * @param actionIconName NodeBox icon path.
     */
    public ConversionRGB(String title, Workspace root, String actionIconName) {
        super(title, root, actionIconName);
    }

    @Override
    public void update(Image image) {

    }

    /**
     * Conversao de imagem do HSV para o RGB
     * @param h hue - matiz
     * @param s saturation
     * @param v value
     * @return valores correspondentes ao RGB
     */
    private static double[] hsvToRgb(double h,double s,double v){
        double r, g, b;
        double f,p,q,t;
        int auxH;

        auxH = (int) (Math.floor(h/60) % 6); //sempre arredonda para baixo

        f = (h/60)-auxH;
        p = v*(1-s);
        q = v*(1-f*s);
        t = v*(1-(1-f)*s);

        switch (auxH) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
                r = v;
                g = p;
                b = q;
                break;
            default:
                r = v;
                g = p;
                b = q;
                break;
        }

        return new double[]{r,g,b};
    }

    @Override
    public void install() {

    }

    @Override
    public void execute() {

        Image auxImage = getImage();
        PixelReader pixelReader = getImage().getPixelReader();
        Color pxColor = null;
        double rgb [];

        /*
        for( int i = 0 ; i < auxImage.getWidth() ; ++i ){
            for( int j = 0 ; j < auxImage.getHeight() ; ++j ){
                pxColor = pixelReader.getColor(i,j);
                rgb =  hsvToRgb(pxColor.getRed(), pxColor.getGreen(),pxColor.getBlue());


            }


        }
        */



    }
}
