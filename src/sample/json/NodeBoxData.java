package sample.json;

/**
 * NodeBoxData POJO. It's just an entity class
 * to map the NodeBoxList file.
 * @author Daniel, Davidson.
 * @since 2017-11-02.
 */
public class NodeBoxData {

    private ClassLoader classLoader;
    private String className;
    private String name;
    private String iconPath;
    private String description;

    /**
     * NodeBox constructor.
     * @param classLoader ClassLoader referring to a loaded a JAR file.
     * @param className Class name, without .class.
     * @param name Text description, title.
     * @param iconPath Icon path.
     * @param description NodeBox description.
     */
    public NodeBoxData(ClassLoader classLoader, String className,String name, String iconPath, String description){
        setClassName(className);
        setName(name);
        setIconPath(iconPath);
        setDescription(description);
    }

    /**
     * Gets the NodeBox name.
     * @return Returns NodeBox name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the NodeBox name.
     * @param name NodeBox name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the NodeBox icon path.
     * @return Returns NodeBox icon path.
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Sets the NodeBox iconPath.
     * @param iconPath NodeBox icon path.
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * Gets the NodeBox description.
     * @return Returns NodeBox description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the NodeBox name.
     * @param description NodeBox description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the NodeBox class name.
     * @return Returns NodeBox class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the NodeBox class name.
     * @param className NodeBox class name.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the NodeBox name.
     * @return Returns NodeBox name.
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * Sets the NodeBox classloader.
     * @param classLoader NodeBox classloader.
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
