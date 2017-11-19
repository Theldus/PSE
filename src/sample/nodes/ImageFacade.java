package sample.nodes;

import javafx.scene.image.Image;

import java.awt.*;
import java.io.InputStream;

public class ImageFacade extends Image {
    public ImageFacade(InputStream input) {
        super(input);
    }
}
