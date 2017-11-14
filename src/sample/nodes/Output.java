package sample.nodes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sample.util.Dimension;
import sample.workspace.Workspace;

/**
 * Created by Daniel on 02/11/2017.
 */
public class Output extends NodeBox {

    public Output(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
        getNode().getChildren().remove(getNode().getOutput());
        //execute();
    }

    public void install() {
        getRoot().add(this);
    }



    @Override
    public void update(Image image) {
        setImage(image);
        execute();
    }

    @Override
    public void execute() {

        getNode().getChildren().remove(getNode().getContainer());

        getNode().setMinSize(180,180);
        ImageView img = new ImageView(getImage());
        img.setFitWidth(140.0f);
        img.setFitHeight(140.0f);
        getNode().setIcon(img);
        getNode().getChildren().add( getNode().createContainer() );


    }
}
