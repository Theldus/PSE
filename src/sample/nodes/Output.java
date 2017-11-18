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
public class Output extends NodeBox {

    private class Delta{
        double x;
        double y;
    }

    public Output(String title,Workspace root,String iconPath) {
        super(title, root, iconPath);
        getHeader().removeSupport();
        getNode().getChildren().remove(getNode().getOutput());
        setOpenWinEvent();
    }

    public void install() {
        getRoot().add(this);
    }

    private void setOpenWinEvent(){

        getNode().getContainer().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(event.getClickCount() > 1 ) {

                    Delta delta = new Delta();

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

                        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                delta.x = newWinPreview.getX() - event.getScreenX();
                                delta.y = newWinPreview.getY() - event.getScreenY();
                            }
                        });

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

    @Override
    public void update(Image image) {
        setImage(image);
        execute();
    }

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
