package sample.plugin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.MainController;
import sample.dialogs.Toast;
import sample.json.NodeBoxData;
import sample.sideMenu.SideMenuPane;
import sample.sideMenu.VMenuItemController;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *  PluginController class. This class is responsible
 *  to load plugins into the system.
 *  @author Davidson.
 *  @version v1.0
 */
public class PluginController {
    /**
     * There's only one instance.
     */
    private static PluginController ourInstance = new PluginController();

    /**
     * It's important to bookkeeping the loaded classes.
     */
    public static Set<String> loadedClasses = new HashSet<>();

    /**
     * Gets an instance of this class, since we are using Singleton
     * Pattern.
     * @return Returns a PluginController instance.
     */
    public static PluginController getInstance() {
        return ourInstance;
    }

    /**
     * Initializes this class.
     */
    private PluginController() {
    }

    /**
     * Reads a Jar/Zip file and loads the class into
     * the memory.
     * @param file Jar or Zip file to be read.
     * @param sideMenuPane Side menu pane to added.
     */
    public void loadFileIntoPane(File file, SideMenuPane sideMenuPane){
        JarFile jf;
        ZipEntry ze;

        /* Search for the desc file. */
        try
        {
            jf = new JarFile(file);
            if ( (ze = jf.getEntry("DESC")) == null) {
                Toast.show(MainController.getInstance().getCurrentWorkspace(),
                        Toast.ERROR_MESSAGE,
                        "DESC file was not found inside Jar!",
                        "ErrorIcon",
                        1000,
                        200,
                        200,
                        "Error");

                return;
            }

            /* Reads the entire DESC file. */
            List<NodeBoxData> list = new ArrayList<>();
            JSONParser jsonParser = new JSONParser();
            JSONArray  listObjs   = (JSONArray) jsonParser.parse(new InputStreamReader(jf.getInputStream(ze),
                    "UTF-8"));

            /* Reads the file and builds a list with the values. */
            for (Object obj : listObjs ){
                JSONObject nodeBox = (JSONObject) obj;

                String className = (String) nodeBox.get("className");
                String name = (String) nodeBox.get("name");
                String iconPath = (String) nodeBox.get("iconPath");
                String description = (String) nodeBox.get("description");

                list.add(new NodeBoxData(null, className, name, iconPath, description));
            }

            /* Checks if all the classes referred by the DESC file exists. */
            for (NodeBoxData nbd : list){
                if (jf.getEntry(nbd.getClassName() + ".class") == null){
                    Toast.show(MainController.getInstance().getCurrentWorkspace(),
                            Toast.ERROR_MESSAGE,
                            "Class " + nbd.getClassName() + " not found!",
                            "ErrorIcon",
                            1000,
                            200,
                            200,
                            "Error");

                    return;
                }
            }

            /* All the classes exist =), so, let's try to load them. */

            /* Checks if we already loaded some classes before. */
            for (NodeBoxData nbd : list) {

                /* Class already loaded before. */
                if (loadedClasses.contains(nbd.getClassName())) {
                    Toast.show(MainController.getInstance().getCurrentWorkspace(),
                            Toast.ERROR_MESSAGE,
                            "Class " + nbd.getClassName() + " already loaded before!",
                            "ErrorIcon",
                            1000,
                            200,
                            200,
                            "Error");

                    return;
                }

                /* Save the class name into the HashSet. */
                loadedClasses.add(nbd.getClassName());
            }

            /* Yep, we'll have one single ClassLoader for each Jar/Zip read. */
            ClassLoader classLoader = URLClassLoader.newInstance(new URL[] { file.toURI().toURL() });

            /* Assign class loader. */
            for (NodeBoxData nbd : list)
                nbd.setClassLoader(classLoader);

            /* Adds the data into the pane. */
            VMenuItemController.getInstance().fill(sideMenuPane, list.toArray());

            /* Success message. */
            Toast.show(MainController.getInstance().getCurrentWorkspace(),
                Toast.INFORMATION_MESSAGE,
                "Plugin " + file.getName() + " successfully loaded!",
                "CheckmarkIcon",
                1000,
                200,
                200,
                "Alert");
        }
        catch (Exception ioe) { ioe.printStackTrace(); }
    }
}
