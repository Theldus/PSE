package sample.nodes;

import sample.Workspace.Workspace;

import static sample.util.Appearance.ICONS_EXT;

/**
 * GaussianFilter class. This is a GaussianFilter node.
 * @author Daniel
 */
public class GaussianFilter extends NodeBox {

    /**
     * Initializes the GaussianFilter.
     * Currently, just setup the title, workspace and icon.
     * @param root Workspace to be added.
     */
    public GaussianFilter(Workspace root) {
        super("Gaussian Filter", root, "DefaultFilterIcon"+ICONS_EXT);
    }

    /**
     * Installs the node, i.e: adds into the Workspace.
     */
    @Override
    public void install() {
        getRoot().add(this);
    }
}
