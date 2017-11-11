package sample.nodes;

import javafx.scene.image.Image;
import sample.Workspace.Workspace;

import static sample.util.Appearance.ICONS_EXT;
import static sample.util.Appearance.ICONS_PATH;

/**
 * LaplacianFilter class. This is a Laplacian node.
 * @author Daniel, Davidson and Izabela.
 */
public class LaplacianFilter extends NodeBox {

    /**
     * Initializes the Histogram.
     * Currently, just setup the title, workspace and icon.
     * @param title Node title.
     * @param root Workspace to be added.
     * @param iconPath Histogram icon.
     */
    public LaplacianFilter(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
    }

    /**
     * Installs the node, i.e: adds into the Workspace.
     */
    public void install() {
        getRoot().add(this);
    }

    /**
     * Receives an image from another node.
     * @param image Input image
     */
    @Override
    public void update(Image image)
    {
        /* TODO: The image processing goes here, and, when finished,
         * we have to propagate the final processed image for our child.
         */
    }
}
