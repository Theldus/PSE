package sample.nodes;

import sample.Workspace.Workspace;

import static sample.util.Appearance.ICONS_EXT;
import static sample.util.Appearance.ICONS_PATH;

public class Histogram extends NodeBox {

    public Histogram(Workspace root) {
        super("Histogram", root, ICONS_PATH+"DefaultShowIcon"+ICONS_EXT);
    }

    @Override
    public void install() {
        getRoot().add(this);
    }
}
