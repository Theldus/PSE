package sample.nodes;

import javafx.scene.image.Image;
import java.io.InputStream;

/**
 * Just following a Facade pattern to Image.
 * @author Daniel.
 */
public class ImageFacade extends Image {
    /**
     * Constructor.
     * @param input Image.
     */
    public ImageFacade(InputStream input) {
        super(input);
    }
}
