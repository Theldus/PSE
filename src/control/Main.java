package control;

//Classe para executar e testar métodos
public class Main {

    public static void main(String[] args) {

        Operation Op = new Operation();
        InputOutput Io = new InputOutput();
        Utility util = new Utility();

        int [][] A = Io.readText("a.txt", 3, 3);
        int [][] B = Io.readText("b.txt", 3, 3);
        int [][] C;

        //C = Op.sum(A, B);
        //C = Op.minus(A, B);
        //C = Op.times(A, B);
        //C = Op.not(A);
        //C = Op.and(A, B);
        //C = Op.or(A, B);
        //Io.print(C);
        //C = util.getBlank(100,100);
        //Io.print(C,"blank.png","png");
        //C = Io.read("blank.png");
        //Io.print(C, "blank2.png", "png");

    }
}