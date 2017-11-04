package sample.nodes;

import sample.Workspace.Workspace;

import static sample.util.Appearance.ICONS_EXT;
import static sample.util.Appearance.ICONS_PATH;

public class GaussianFilter extends NodeBox {

    public GaussianFilter(Workspace root) {
        super("Gaussian Filter", root, "DefaultFilterIcon"+ICONS_EXT);
    }

    @Override
    public void install() {
        getRoot().add(this);
    }
}
