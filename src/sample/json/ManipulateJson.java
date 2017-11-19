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
 * Created by Daniel on 02/11/2017.
 */
public class ManipulateJson {

    JSONParser jsonParser;

    public ManipulateJson(){
        jsonParser = new JSONParser();
    }

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
