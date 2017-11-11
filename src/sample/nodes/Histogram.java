package sample.nodes;

import javafx.scene.image.Image;
import sample.Workspace.Workspace;

import static sample.util.Appearance.ICONS_EXT;
import static sample.util.Appearance.ICONS_PATH;

/**
 * Histogram class. This is a Histogram node.
 * @author Daniel
 */
public class Histogram extends NodeBox {

    /**
     * Initializes the Histogram.
     * Currently, just setup the title, workspace and icon.
     * @param title Node title.
     * @param root Workspace to be added.
     * @param iconPath Histogram icon.
     */
    public Histogram(String title, Workspace root, String iconPath) {
        super(title, root, iconPath);
    }

    /**
     * Installs the node, i.e: adds into the Workspace.
     */
    @Override
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

        /*
         * At every single change made in this node, the final image *SHOULD* be
         * send for every output line connected into this NodeBox by the update()
         * method.
         */

        System.out.println("I received an image =)");
    }
}
