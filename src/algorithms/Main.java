package algorithms;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

//Classe para executar scripts

/**
 * Test-bench class, reads a JavaScript file
 * and execute some tests.
 * @author Pertence.
 */
public class Main {

    /**
     * Main function, just execute the scripts.
     * @param args Arguments list.
     */
    public static void main(String[] args) {
        /* Nashorn engine. */
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
