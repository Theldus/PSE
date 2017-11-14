package sample.json;

/**
 * Created by Daniel on 02/11/2017.
 */
public class NodeBoxData {

    private String className;
    private String name;
    private String iconPath;
    private String description;

    public NodeBoxData(String className,String name, String iconPath, String description){
        setClassName(className);
        setName(name);
        setIconPath(iconPath);
        setDescription(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
