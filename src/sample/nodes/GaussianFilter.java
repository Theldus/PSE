package sample.nodes;

import sample.Workspace.Workspace;

import static sample.util.Appearance.ICONS_EXT;
import static sample.util.Appearance.ICONS_PATH;

/**
 * GaussianFilter class. This is a GaussianFilter node.
 * @author Daniel
 */
public class GaussianFilter extends NodeBox {

    /**
     * Initializes the GaussianFilter.
     * Currently, just setup the title, workspace and icon.
     * @param title NodeBox title.
     * @param root Workspace to be added.
     * @param iconPath NodeBox icon path.
     */
    public GaussianFilter(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
    }

    /**
     * Installs the node, i.e: adds into the Workspace.
     */
    @Override
    public void install() {
        getRoot().add(this);
    }
}
