package algorithms;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

//Classe para executar scripts
public class Main {

    /*É necessário passar o caminho do script como argumento
      caso contrário o método joga uma exceção e o programa para.*/
    //Método para executar scripts
    public static void main(String[] args) {
        //Utiliza-se a engine chamada nashorn para executar javascript
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        File file = new File(args[0]);
        Reader read = null;
        try{ read = new FileReader(file); }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        String result;
        try { result = (String)engine.eval(read);
            System.out.println(result); }
        catch (ScriptException e) { e.printStackTrace(); }
    }
}