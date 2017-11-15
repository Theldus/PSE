package sample.window;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.util.Dimension;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel on 13/11/2017.
 */
public class WinPreviewImageController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    private Button closeBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button configBtn;

    private Dimension dimensionImage = new Dimension(475,386);
    private static Stage rootStage;
    private static Image image;
    private Image currImg;
    private FileChooser fileChooser = new FileChooser();
    private Stage currRootStage;

    public static void setRoot( Stage stage ){
        rootStage = stage;
    }

    public static void setImage( Image img ){
        image = img;
    }

    public void setRoot(){
        currRootStage = rootStage;
    }

    public Stage getRoot(){
        return currRootStage;
    }


    public void setImage(){
        currImg = image;
    }

    public Image getImage(){
        return currImg;
    }

    public ImageView changeDimensionImage(){
        ImageView imageView = new ImageView( getImage() );
        imageView.setFitWidth(dimensionImage.getWidth());
        imageView.setFitHeight(dimensionImage.getHeight());
        return imageView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRoot();
        setImage();
        closeBtn.setGraphic(new ImageView(new Image("sample/icons/closeIcon.png")));
        saveBtn.setGraphic(new ImageView(new Image("sample/icons/saveIcon.png")));
        configBtn.setGraphic(new ImageView(new Image("sample/icons/settIcon.png")));
        mainPane.getChildren().add(changeDimensionImage());
    }

    public void saveClickedEvent(MouseEvent event) throws IOException {

        fileChooser.setTitle("Salvar Imagem");
        File file = fileChooser.showSaveDialog(rootStage);
        if (file != null){
            ImageIO.write(SwingFXUtils.fromFXImage(getImage(),null),"png",file);
        }else {

        }
    }

    public Dimension getDimensionImage() {
        return dimensionImage;
    }

    public void setDimensionImage(Dimension dimensionImage) {
        this.dimensionImage = dimensionImage;
    }

    public void saveEnteredEvent(MouseEvent event) {
        saveBtn.setGraphic(new ImageView(new Image("sample/icons/saveEnteredIcon.png")));
    }

    public void saveExitedEvent(MouseEvent event) {
        saveBtn.setGraphic(new ImageView(new Image("sample/icons/saveIcon.png")));
    }

    public void configClickedEvent(MouseEvent event) {
    }

    public void configEnteredEvent(MouseEvent event) {
        configBtn.setGraphic(new ImageView(new Image("sample/icons/settEnteredIcon.png")));
    }

    public void configExitedEvent(MouseEvent event) {
        configBtn.setGraphic(new ImageView(new Image("sample/icons/settIcon.png")));
    }

    public void closeClickedEvent(MouseEvent event) {
        getRoot().getScene().getWindow().hide();
    }

    public void closeEnteredEvent(MouseEvent event) {
        closeBtn.setGraphic(new ImageView(new Image("sample/icons/closeEnteredIcon.png")));
    }

    public void closeExitedEvent(MouseEvent event) {
        closeBtn.setGraphic(new ImageView(new Image("sample/icons/closeIcon.png")));
    }
}
