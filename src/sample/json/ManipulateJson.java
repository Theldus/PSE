package sample.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads a NodeBoxList JSON file and creates a list
 * of NodeBox data ready to insert into the GUI.
 * @author Daniel, Davidson.
 * @since 2017-11-02
 */
public class ManipulateJson {

    JSONParser jsonParser;

    /**
     * ManipulateJson constructor.
     */
    public ManipulateJson(){
        jsonParser = new JSONParser();
    }

    /**
     * Reads a json/NodeBoxList file and fills an array of NodeBoxData.
     * @return NodeBoxData array.
     */
    public Object[] read(){

        final List<NodeBoxData> list = new ArrayList<>();

        try
        {
           JSONArray listObjs = (JSONArray) jsonParser.parse(
                   new InputStreamReader(sample.Main.class.getResourceAsStream("json/NodeBoxList"), "UTF-8"));

           for(Object obj : listObjs ){

               JSONObject nodeBox = (JSONObject) obj;

               String className = (String) nodeBox.get("className");
               String name = (String) nodeBox.get("name");
               String iconPath = (String) nodeBox.get("iconPath");
               String description = (String) nodeBox.get("description");

               list.add(new NodeBoxData(null, className, name, iconPath, description));

           }
        }
        catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list.toArray();
    }
}
