package control;

//Classe com métodos para fazer operações aritméticas e lógicas sobre imagens
public class Operation {

    private Utility util = new Utility();

    //Método para fazer soma entre imagens A e B
    public int[][] sum(int [][] A, int [][] B){
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int o = B.length; //Número de linhas de B
        int p = B[0].length; // Número de colunas B

        if(n != o || m != p){
            return null;
        }

        int [][] C = new int[n][m];

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                C[i][j] = util.normalize(A[i][j] + B[i][j]);
            }
        }
        return C;
    }

    //Método para fazer subtração entre imagens A e B
    public int[][] minus(int [][] A, int [][] B){
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int o = B.length; //Número de linhas de B
        int p = B[0].length; // Número de colunas B

        if(n != o || m != p){
            return null;
        }

        int [][] C = new int[n][m];

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                C[i][j] = util.normalize(A[i][j] - B[i][j]);
            }
        }
        return C;
    }

    //Método para fazer multiplicação entre imagens A e B
    public int[][] times(int [][] A, int [][] B)  {
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int o = B.length; //Número de linhas de B
        int p = B[0].length; // Número de colunas B

        if (m != n) {
            return null;
        }

        int [][] C = new int[n][p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < o; j++) {
                for (int k = 0; k < m; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
                util.normalize(C[i][j]);
            }
        }
        return C;
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

    //Método para fazer AND entre imagens A e B
    int[][] and(int [][] A, int [][] B){
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int o = B.length; //Número de linhas de B
        int p = B[0].length; // Número de colunas B

        if(n != o || m != p){
            return null;
        }

        int [][] C = new int[n][m];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                C[i][j] = (byte) (A[i][j] & B[i][j]);
            }
        }
        return C;
    }

    //Método para fazer OR entre imagens A e B
    int[][] or(int [][] A, int [][] B){
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int o = B.length; //Número de linhas de B
        int p = B[0].length; // Número de colunas B

        if(n != o || m != p){
            return null;
        }

        int [][] C = new int[n][m];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                C[i][j] = (byte) (A[i][j] | B[i][j]);
            }
        }
        return C;
    }

}
