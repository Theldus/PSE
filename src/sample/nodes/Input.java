package sample.nodes;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.MainController;
import sample.PSEMainLayout;
import sample.dialogs.Toast;
import sample.util.Edge;
import sample.util.ImageUtil;
import sample.workspace.Workspace;

import java.io.File;

/**
 * Input class, let the user to choose an image from a file system.
 * @since 2017-11-02
 */
public class Input extends NodeBox {

    private final FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilter = new FileChooser.
            ExtensionFilter("Image File","*.png","*.jpg","*.jpeg","*.raw","*.bpm");

    /**
     * Input constructor.
     * @param title Title.
     * @param root Parent root.
     * @param iconPath Icon path.
     */
    public Input(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
        getHeader().removeSupport();
        getNode().getChildren().remove(getNode().getInput());
        execute();
    }

    /**
     * Installs the node inside the parent.
     */
    public void install() {
        getRoot().add(this);
    }

    /**
     * Setup the fileChooser.
     */
    public void configFileChooser(){
        this.fileChooser.setTitle("Abrir Imagem");
        this.fileChooser.getExtensionFilters().add(extensionFilter);
    }

    /**
     * Whenever the input is changed or a new connection
     * is made this function is called.
     * @param image Current image
     */
    @Override
    public void update(Image image) {

        if( ! (getImage() instanceof ImageFacade) ){
            super.update(getImage());
        }
    }

    /**
     * Open the fileChooser the choose the image.
     */
    @Override
    public void execute() {
        configFileChooser();
        getNode().getActionBtn().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() > 1) {
                    File file = fileChooser.showOpenDialog(PSEMainLayout.getRoot());
                    if (file != null) {
                        saveImg(file);
                        fileChooser.setInitialDirectory(file.getParentFile());
                    } else {
                        Toast.show(getRoot(),
                                Toast.ERROR_MESSAGE,
                                "Imagem não foi carregada/selecionada!",
                                "ErrorIcon",
                                1000,
                                200,
                                200,
                                 "Error");
                    }
                }
            }
        });
    }

    /**
     * Saves the image given a file.
     * @param file Target file.
     */
    private void saveImg(File file){
        setImage(new Image(file.toURI().toString()));
        update(null);

        Toast.show(getRoot(),
                Toast.INFORMATION_MESSAGE,
                String.format("Imagem: %s adicionada!",file.getName()),
                "InformationIcon",
                1000,
                200,
                200,
                "Alert");
    }
}
