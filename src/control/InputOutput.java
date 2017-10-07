package control;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

//Classe com métodos para fazer operações de entrada e saída
public class InputOutput {

    //Método para ler uma imagem em um path
    public int [][] read(String path){
        BufferedImage bfrimg = null;
        try { bfrimg = ImageIO.read(new File(path)); }
        catch (IOException e) { e.printStackTrace(); }
        int n = bfrimg.getHeight();
        int m = bfrimg.getWidth();
        int img[][] = new int[n][m];
        for(int i=0; i< n; i++){
            for(int j=0; j< m; j++){
                img[i][j] = bfrimg.getRGB(i, j);
            }
        }
        return img;
    }

    //Método para gravar uma imagem em um path
    public void print(int [][] img, String path, String format){
        int n = img.length; //Número de linhas da imagem A
        int m = img[0].length; //Número de colunas de imagem A;
        BufferedImage bfrimg = new BufferedImage(n, m, TYPE_INT_ARGB);
            for(int i=0; i<n; i++) {
                for(int j=0; j<m; j++) {
                    bfrimg.setRGB(i,j,img[i][j]);
                }
            }
        File output = new File(path);
        try { ImageIO.write(bfrimg, format, output); }
        catch (IOException e) { e.printStackTrace(); }
    }

    //Método para ler de arquivo texto em path e armazenar em uma imagem
    public int [][] readText(String path, int n, int m) {
        Scanner input = null;
        int[][] img = new int[n][m];
        try { input = new Scanner(new File(path)); }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        for(int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (input.hasNextInt()) {
                    img[i][j] = input.nextInt();
                }
            }
        }
        input.close();
        return img;
    }

    //Método para imprimir os valores de uma Imagem em texto
    public void printText(int [][] img, String path){
        PrintWriter writer = null;
        try { writer = new PrintWriter(path, "UTF-8"); }
        catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
        int n = img.length; //Número de linhas de A
        int m = img[0].length; //Número de colunas de A;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                writer.printf("%3d", img[i][j]);
            }
            writer.println();
        }
        writer.close();
    }
}

        /*
        int n = 0; //Número de linhas da imagem
        int m = 0; //Número de colunas da imagem

        try { input = new Scanner(new File(path)); }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        while(input.hasNextLine()) {
            ++n;
            Scanner colReader = new Scanner(input.nextLine());
            while(colReader.hasNextInt()) {
                ++m;
            }
        }
        int[][] img = new int[n][m];
        input.close();
        */