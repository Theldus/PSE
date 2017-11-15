package sample.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Created by Daniel on 14/11/2017.
 */
public class ImageUtil {

    public static int[][] convertToGreyTone( BufferedImage buffImg ){

        int [][] newImg = new int[buffImg.getWidth()][buffImg.getHeight()];
        Color pixelColor = null;
        int pixelColorValue = 0;
        for( int i = 0; i < newImg.length ; ++i ){
            for( int j =0; j < newImg[i].length ; ++j ){
                pixelColor = new Color( buffImg.getRGB(i,j) );
                //convert to grayTone
                pixelColorValue = ( (pixelColor.getRed() + pixelColor.getBlue() + pixelColor.getGreen())/3 );
                newImg[i][j] = pixelColorValue;
            }
        }
        return newImg;
    }

    public static javafx.scene.image.Image toImage( int[][] matrix ){

        BufferedImage buffImg = null;
        int width = matrix.length;
        int height = matrix[0].length;
        // retornando o resultado para uma nova imagem image utilizando raster
        buffImg = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = buffImg.getRaster();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                raster.setSample(i, j, 0, matrix[i][j]);
            }
        }
        return SwingFXUtils.toFXImage(buffImg,null);
    }

    //Método para gerar uma imagem escura
    public static int [][] getBlank(int x, int y){
        int [][] blank = new int[x][y];
        for(int i=0; i<x; i++){
            for(int j=0; j<y; j++){
                blank[i][j] = 0xffffffff;
            }
        }
        return blank;
    }

    //Método para normalizar um pixel entre valores 0 e 255
    public static int normalize(int i){
        if(i<0)
            i = 0;
        if(i>255)
            i = 255;
        return i;
    }

    //Método para checar um pixel se está entre 0 e 255
    public static boolean check(int i){
        return i >= 0 && i <= 255;
    }

    //Método para checar se todos pixel da imagem estão entre 0 e 255
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

    //Método para separar os canais RGB de uma imagem A
    public static int [][][] rgb(int [][] img){
        int [][] R = null;
        int [][] G = null;
        int [][] B = null;
        int [][][] rgb= {R, G, B};

        return rgb;
    }

    //Método para separar os canais HSV de uma imagem A
    public static int [][][] hsv(int [][] img){
        int [][] H = null;
        int [][] S = null;
        int [][] V = null;
        int [][][] hsv= {H, S ,V};
        return hsv;
    }



}
