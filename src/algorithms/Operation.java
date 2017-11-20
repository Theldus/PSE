package algorithms;

//Classe com métodos para fazer operações aritméticas e lógicas sobre imagens

/**
 * Class with methods to do math and logic operations
 * in images.
 * @author Pertence.
 */
public class Operation {

    private Utility util = new Utility();

    //Método para fazer soma entre imagens A e B

    /**
     * Sum two images.
     * @param A First image.
     * @param B Second image.
     * @return Returns a matrix representing the sum.
     */
    public int[][] sum(int [][] A, int [][] B){
        int n = A.length;    /* A lines.   */
        int m = A[0].length; /* A columns. */
        int o = B.length;    /* B lines.   */
        int p = B[0].length; /* B columns. */

        if(n != o || m != p)
            return null;

        int [][] C = new int[n][m];

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                C[i][j] = util.normalize(A[i][j] + B[i][j]);
            }
        }
        return C;
    }

    /**
     * Subtracts two images.
     * @param A First image.
     * @param B Second image.
     * @return Returns a matrix representing the operation.
     */
    public int[][] minus(int [][] A, int [][] B){
        int n = A.length;    /* A lines.   */
        int m = A[0].length; /* A columns. */
        int o = B.length;    /* B lines.   */
        int p = B[0].length; /* B columns. */

        if(n != o || m != p)
            return null;

        int [][] C = new int[n][m];

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                C[i][j] = util.normalize(A[i][j] - B[i][j]);
            }
        }
        return C;
    }

    /**
     * Multiply two images.
     * @param A First image.
     * @param B Second image.
     * @return Returns a matrix representing the multiplication.
     */
    public int[][] times(int [][] A, int [][] B)  {
        int n = A.length; //Número de linhas de A
        int m = A[0].length; //Número de colunas de A
        int o = B.length; //Número de linhas de B
        int p = B[0].length; // Número de colunas B

        if (m != n)
            return null;

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

    /**
     * Negate an image.
     * @param A Input image.
     * @return Returns a matrix representing the operation.
     */
    int[][] not(int [][] A){
        int n = A.length;    /* A lines.  */
        int m = A[0].length; /* A columns */
        int [][] B = new int[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                B[i][j] = (byte) ~A[i][j];
            }
        }
        return B;
    }

    /**
     * AND two images.
     * @param A First image.
     * @param B Second image.
     * @return Returns a matrix representing the operation.
     */
    int[][] and(int [][] A, int [][] B){
        int n = A.length;    /* A lines.   */
        int m = A[0].length; /* A columns  */
        int o = B.length;    /* B lines.   */
        int p = B[0].length; /* B columns. */

        if(n != o || m != p)
            return null;

        int [][] C = new int[n][m];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                C[i][j] = (byte) (A[i][j] & B[i][j]);
            }
        }
        return C;
    }

    /**
     * OR two images.
     * @param A First image.
     * @param B Second image.
     * @return Returns a matrix representing the operation.
     */
    int[][] or(int [][] A, int [][] B){
        int n = A.length;    /* A lines.   */
        int m = A[0].length; /* A columns. */
        int o = B.length;    /* B lines.   */
        int p = B[0].length; /* B columns. */

        if(n != o || m != p)
            return null;

        int [][] C = new int[n][m];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                C[i][j] = (byte) (A[i][j] | B[i][j]);
            }
        }
        return C;
    }
}
