package sample.nodes;

import sample.Workspace.Workspace;

import static sample.util.Appearance.ICONS_EXT;
import static sample.util.Appearance.ICONS_PATH;

/**
 * Created by Daniel on 02/11/2017.
 */
public class LaplacianFilter extends NodeBox {


    public LaplacianFilter(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
    }

    public void install() {
        getRoot().add(this);
    }
}
