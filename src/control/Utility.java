package control;

//Classe com métodos auxiliares
public class Utility {

    //Método para gerar uma imagem escura
    public int [][] getBlank(int x, int y){
        int [][] blank = new int[x][y];
        for(int i=0; i<x; i++){
            for(int j=0; j<y; j++){
                blank[i][j] = 0xffffffff;
            }
        }
        return blank;
    }

    //Método para normalizar um pixel entre valores 0 e 255
    public int normalize(int i){
        if(i<0)
            i = 0;
        if(i>255)
            i = 255;
        return i;
    }

    //Método para checar um pixel se está entre 0 e 255
    public boolean check(int i){
        return i >= 0 && i <= 255;
    }

    //Método para checar se todos pixel da imagem estão entre 0 e 255
    public boolean checkMatrix(int [][] A){
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
    public int [][][] rgb(int [][] img){
        int [][] R = null;
        int [][] G = null;
        int [][] B = null;
        int [][][] rgb= {R, G, B};

        return rgb;
    }

    //Método para separar os canais HSV de uma imagem A
    public int [][][] hsv(int [][] img){
        int [][] H = null;
        int [][] S = null;
        int [][] V = null;
        int [][][] hsv= {H, S ,V};
        return hsv;
    }
}
