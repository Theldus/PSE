package sample.nodes;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.MainController;
import sample.PSEMainLayout;
import sample.dialogs.Toast;
import sample.util.Edge;
import sample.workspace.Workspace;

import java.io.File;

/**
 * Created by Daniel on 02/11/2017.
 */
public class Input extends NodeBox {

    private final FileChooser fileChooser = new FileChooser();
    private FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image File","*.png","*.jpg","*.raw");
    public static Image imageAux;
    public Input(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
        getNode().getChildren().remove(getNode().getInput());
        execute();
    }

    public void install() {
        getRoot().add(this);
    }

    public void configFileChooser(){
        this.fileChooser.setTitle("Abrir Imagem");
        this.fileChooser.getExtensionFilters().add(extensionFilter);
    }

    @Override
    public void update(Image image) {

        if( ! (getImage() instanceof ImageFacade) ){
            super.update(getImage());
        }
    }

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
                    } else {
                        Toast.show(MainController.getCurrentWorkspace(),
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

    private void saveImg(File file){

        setImage(new Image(file.toURI().toString()));
        //imageAux = getImage();
        update(null);

        Toast.show(MainController.getCurrentWorkspace(),
                Toast.INFORMATION_MESSAGE,
                String.format("Imagem: %s adicionada!",file.getName()),
                "InformationIcon",
                1000,
                200,
                200,
                "Alert");

    }

}

