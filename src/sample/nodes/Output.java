package sample.nodes;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.window.WinPreviewImageController;
import sample.workspace.Workspace;
import java.io.IOException;

/**
 * Created by Daniel on 02/11/2017.
 */

/**
 * Output NodeBox. Shows the input image into the screen.
 */
public class Output extends NodeBox {

    /* Delta. */
    private class Delta{
        double x;
        double y;
    }

    /**
     * Output constructor.
     * @param title Title.
     * @param root Workspace root.
     * @param iconPath iconPath.
     */
    public Output(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
        getHeader().removeSupport();
        getNode().getChildren().remove(getNode().getOutput());
        setOpenWinEvent();
    }

    /**
     * Installs the node, i.e: adds into the workspace.
     */
    public void install() {
        getRoot().add(this);
    }

    /**
     * Configures the event that shows the preview window.
     */
    private void setOpenWinEvent(){

        getNode().getContainer().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(event.getClickCount() > 1 ) {

                    Delta delta = new Delta();

                    /* Attributes. */
                    System.out.println("Show IMG!!!");
                    Stage newWinPreview = new Stage();
                    newWinPreview.setMaximized(false);
                    newWinPreview.setResizable(false);
                    newWinPreview.setFullScreen(false);
                    newWinPreview.setTitle("Visualizar Imagem");
                    newWinPreview.initStyle(StageStyle.TRANSPARENT);

                    WinPreviewImageController.setRoot(newWinPreview);
                    WinPreviewImageController.setImage(getImage());

                    try {

                        Parent root = FXMLLoader.load(getClass().getResource("/sample/window/WinPreviewImage.fxml"));

                        Scene scene = new Scene(root);

                        /* Get the coordinates. */
                        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                delta.x = newWinPreview.getX() - event.getScreenX();
                                delta.y = newWinPreview.getY() - event.getScreenY();
                            }
                        });

                        /* Drag the window. */
                        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                newWinPreview.setX(event.getScreenX() + delta.x);
                                newWinPreview.setY(event.getScreenY() + delta.y);
                            }
                        });

                        newWinPreview.setScene(scene);
                        newWinPreview.show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                event.consume();

            }
        });
    }

    /**
     * Receives an image from another node.
     * @param image Input image
     */
    @Override
    public void update(Image image) {
        setImage(image);
        execute();
    }

    /**
     * Shows the current image into the screen.
     */
    @Override
    public void execute() {
        getNode().getChildren().remove(getNode().getContainer());
        getNode().setMinSize(180,160);
        ImageView img = new ImageView(getImage());
        img.setFitWidth(140.0f);
        img.setFitHeight(120.0f);
        img.setEffect(new DropShadow());
        getNode().setActionIcon(img);
        getNode().getChildren().add( getNode().createContainer() );
        setOpenWinEvent();
    }
}
