package sample.nodes;

import sample.workspace.Workspace;

/**
 * NodeBoxFactory class. This class follows the Factory pattern
 * which is used to build new instances of objects.
 * @author Daniel
 * @version v1.0
 */
public class NodeBoxFactory {

    /**
     * Since this class is a Singleton, we need to keep
     * a static field to hold the instance.
     */
    private static NodeBoxFactory ourInstance = new NodeBoxFactory();

    /**
     * Gets the NodeBoxFactory instance.
     * @return Returns a NodeBoxFactory instance.
     */
    public static NodeBoxFactory getInstance() {
        return ourInstance;
    }

    /**
     * Initializes the class.
     */
    private NodeBoxFactory() {
    }

    /**
     * Creates a GaussianFilter instance.
     * @param title Title.
     * @param workspace Workspace.
     * @param iconPath Icon path.
     */
    public void createGaussianFilter(String title, Workspace workspace, String iconPath){
         new GaussianFilter(title, workspace, iconPath).install();
    }

    /**
     * Creates a Histogram instance.
     * @param title Title.
     * @param workspace Workspace.
     * @param iconPath Icon path.
     */
    public void createHistogram(String title, Workspace workspace, String iconPath){
         new Histogram(title, workspace, iconPath).install();
    }
}
