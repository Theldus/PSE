package sample.window;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.util.Dimension;
import sample.util.ImageUtil;

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

    @FXML
    private Pane settingPane;

    @FXML
    private ComboBox cBoxImgSize;


    private Dimension dimPreviewImage = new Dimension(475,386);
    private Dimension dimensionImage;
    private static Stage rootStage;
    private static Image image;
    private Image currImg;
    private FileChooser fileChooser = new FileChooser();
    private Stage currRootStage;
    private boolean clicked = false;
    private String formatImg = "png";

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
        imageView.setFitWidth(dimPreviewImage.getWidth());
        imageView.setFitHeight(dimPreviewImage.getHeight());
        return imageView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRoot();
        setImage();

        setFileChooser();

        //set default size image
        dimensionImage = new Dimension(getImage().getWidth(),getImage().getHeight());

        closeBtn.setGraphic(new ImageView(new Image("sample/icons/closeIcon.png")));
        saveBtn.setGraphic(new ImageView(new Image("sample/icons/saveIcon.png")));
        configBtn.setGraphic(new ImageView(new Image("sample/icons/settIcon.png")));

        settingPane.setVisible(false);

        setComboBoxImgSize();
        setValuePropertyComboBoxSize();

        mainPane.getChildren().add(changeDimensionImage());
    }

    private void setFileChooser(){
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todas imagens", "*.*"),
                new FileChooser.ExtensionFilter("png", "*.png"),
                new FileChooser.ExtensionFilter("jpg","*.jpg","*.jpeg"),
                new FileChooser.ExtensionFilter("pbm","*.pbm"),
                new FileChooser.ExtensionFilter("raw","*.raw")
        );
    }

    public void saveClickedEvent(MouseEvent event) throws IOException {

        fileChooser.setTitle("Salvar Imagem");
        File file = fileChooser.showSaveDialog(rootStage);
        if (file != null){

            Image img = ImageUtil.scale(getImage(),dimensionImage.getWidth(),dimensionImage.getHeight(),false);
            ImageIO.write(SwingFXUtils.fromFXImage(img,null),
                                                   formatImg,
                                                    file);
        }else {
            //Empty
        }
    }

    public void saveEnteredEvent(MouseEvent event) {
        saveBtn.setGraphic(new ImageView(new Image("sample/icons/saveEnteredIcon.png")));
    }

    public void saveExitedEvent(MouseEvent event) {
        saveBtn.setGraphic(new ImageView(new Image("sample/icons/saveIcon.png")));
    }

    public void configClickedEvent(MouseEvent event) {

        if( clicked ){
            //add controlPanel
            settingPane.setVisible(true);
            clicked = false;
        }
        else{
            settingPane.setVisible(false);
            clicked = true;
        }
    }

    private void setComboBoxImgSize(){

        cBoxImgSize.setPromptText("Tamanho imagem");
        cBoxImgSize.getItems().addAll("tamanho original","600x400","800x600","1024x768","1280x1024");

    }

    private void setValuePropertyComboBoxSize(){

        cBoxImgSize.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if(newValue.toString().equals("tamanho original")){
                    dimensionImage.setWidth(getImage().getWidth());
                    dimensionImage.setHeight(getImage().getHeight());
                }
                else {
                    String size [] = newValue.toString().split("x");
                    dimensionImage.setWidth(Double.parseDouble(size[0]));
                    dimensionImage.setHeight(Double.parseDouble(size[1]));
                }

                System.out.println("Dim: "+ dimensionImage.getWidth() + " " + dimensionImage.getHeight());

            }
        });
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
