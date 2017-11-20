package sample.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * ImageUtil class. Here contains some functions
 * which can help another classes and packages
 * to do image processing.
 * @author Daniel, Pertence
 * @since 2017-11-14
 */
public class ImageUtil {

    /**
     * Convert a given image to greyscale.
     * @param buffImg Image
     * @return Returns a matrix in greyscale.
     */
    public static int[][] convertToGreyTone( BufferedImage buffImg ){
        int [][] newImg = new int[buffImg.getWidth()][buffImg.getHeight()];
        Color pixelColor = null;
        int pixelColorValue = 0;
        for( int i = 0; i < newImg.length ; ++i ){
            for( int j =0; j < newImg[i].length ; ++j ){
                pixelColor = new Color( buffImg.getRGB(i,j) );
                pixelColorValue = ( (pixelColor.getRed() + pixelColor.getBlue() + pixelColor.getGreen())/3 );
                newImg[i][j] = pixelColorValue;
            }
        }
        return newImg;
    }

    /**
     * Given a matrix, converts into a JavaFX image in RGB.
     * @param matrix Input matrix.
     * @return Returns image.
     */
    public static javafx.scene.image.Image toImage( int[][] matrix ){
        BufferedImage buffImg = null;
        int width = matrix.length;
        int height = matrix[0].length;

        buffImg = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = buffImg.getRaster();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                raster.setSample(i, j, 0, matrix[i][j]);
            }
        }

        return SwingFXUtils.toFXImage(buffImg,null);
    }

    /**
     * Given an image, converts into a RGB matrix.
     * @param buffImg Input image.
     * @return Returns image matrix.
     */
    public static int[][] fromImage(BufferedImage buffImg) {
        int [][] newImg = new int[buffImg.getWidth()][buffImg.getHeight()];
        for (int i = 0; i < buffImg.getWidth(); i++)
            for (int j = 0; j < buffImg.getHeight(); j++)
                newImg[i][j] = buffImg.getRGB(i, j);
        return newImg;
    }

    /**
     * Generates a blank image.
     * @param x Width.
     * @param y Height.
     * @return Returns a white matrix.
     */
    public static int [][] getBlank(int x, int y){
        int [][] blank = new int[x][y];
        for(int i=0; i<x; i++){
            for(int j=0; j<y; j++){
                blank[i][j] = 0xffffffff;
            }
        }
        return blank;
    }

    /**
     * Normalizes a pixel between 0 and 255.
     * @param i Pixel value.
     * @return Normalized pixel.
     */
    public static int normalize(int i){
        if(i<0)
            i = 0;
        if(i>255)
            i = 255;
        return i;
    }

    /**
     * Method to checks if a pixel is between 0 and 255
     * @param i Pixel value.
     * @return Returns true if the pixel is between 0 and 255
     * and false, otherwise.
     */
    public static boolean check(int i){
        return i >= 0 && i <= 255;
    }

    /**
     * Method to check if all the pixels are between 0 and 255
     * @param A Image matrix.
     * @return True if the image is already normalized.
     */
    public static boolean checkMatrix(int [][] A){
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(check(A[i][j]))
                    return false;
            }
        }
        return true;
    }

    /**
     * Splits the RGB channels into a 3D cube, each depth
     * is a specific channel.
     * @param img Image matrix.
     * @return Cube containing the RGB channels.
     */
    public static int [][][] rgb(int [][] img){
        int [][] R = null;
        int [][] G = null;
        int [][] B = null;
        int [][][] rgb= {R, G, B};

        return rgb;
    }

    /**
     * Splits the HSV channels into a 3D cube, each
     * depth is a specific channel.
     * @param img Image matrix.
     * @return Cube containing the HSV channels.
     */
    public static int [][][] hsv(int [][] img){
        int [][] H = null;
        int [][] S = null;
        int [][] V = null;
        int [][][] hsv= {H, S ,V};
        return hsv;
    }

    /**
     * Scales up an image.
     * @param img Image.
     * @param width Width.
     * @param height Height.
     * @param preserveRatio Preserve ratio?
     * @return Returns the scaled image.
     */
    public static Image scale(Image img, double width, double height, boolean preserveRatio ){
        ImageView imageView = new ImageView(img);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView.snapshot(null,null);
    }
}
