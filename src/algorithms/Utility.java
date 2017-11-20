package algorithms;

/**
 * Class with auxiliary methods.
 * @author Pertence.
 */
public class Utility {

    /**
     * Generates an blank image.
     * @param x Image width.
     * @param y Image height.
     * @return Image.
     */
    public int [][] getBlank(int x, int y){
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
    public int normalize(int i){
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
    public boolean check(int i){
        return i >= 0 && i <= 255;
    }

    /**
     * Method to check if all the pixels are between 0 and 255
     * @param A Image matrix.
     * @return True if the image is already normalized.
     */
    public boolean checkMatrix(int [][] A){
        int n = A.length;    /* A lines.   */
        int m = A[0].length; /* A columns. */
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
    public int [][][] rgb(int [][] img){
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
    public int [][][] hsv(int [][] img){
        int [][] H = null;
        int [][] S = null;
        int [][] V = null;
        int [][][] hsv= {H, S ,V};
        return hsv;
    }
}
