package sample.nodes;

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
     * @param root Workspace to be added.
     */
    public Histogram(Workspace root) {
        super("Histogram", root, "DefaultShowIcon"+ICONS_EXT);
    }

    /**
     * Installs the node, i.e: adds into the Workspace.
     */
    @Override
    public void install() {
        getRoot().add(this);
    }
}
