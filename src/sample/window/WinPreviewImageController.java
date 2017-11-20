package sample.window;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.util.Dimension;
import sample.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.xml.ws.Action;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.util.Appearance.ICONS_PATH;

/**
 * WinPreviewImageController class. Controller class for the
 * WinPreviewImage.fxml, that manages the preview image window.
 */
public class WinPreviewImageController implements Initializable {

    /* FXML components. */
    @FXML
    private Pane mainPane;

    @FXML
    private ImageView closeBtn;

    @FXML
    private ImageView minimizeBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button configBtn;

    @FXML
    private Pane settingPane;

    @FXML
    private ComboBox cBoxImgSize;

    /* Private data. */
    private Dimension dimPreviewImage = new Dimension(475,386);
    private Dimension dimensionImage;
    private static Stage rootStage;
    private static Image image;
    private Image currImg;
    private FileChooser fileChooser = new FileChooser();
    private Stage currRootStage;
    private boolean clicked = false;
    private String formatImg = "png";

    /**
     * Sets root component.
     * @param stage Stage component.
     */
    public static void setRoot( Stage stage ){
        rootStage = stage;
    }

    /**
     * Sets the current root component as the rootStage.
     */
    public void setRoot(){
        currRootStage = rootStage;
    }

    /**
     * Gets the root component.
     * @return Returns the root component.
     */
    public Stage getRoot(){
        return currRootStage;
    }

    /**
     * Sets the preview image.
     * @param img Input image.
     */
    public static void setImage( Image img ){
        image = img;
    }

    /**
     * Sets the current preview image as the image.
     */
    public void setImage(){
        currImg = image;
    }

    /**
     * Gets the current image.
     * @return Returns the image.
     */
    public Image getImage(){
        return currImg;
    }

    /**
     * Resize the image.
     * @return Resized image.
     */
    public ImageView changeDimensionImage(){
        ImageView imageView = new ImageView( getImage() );
        imageView.setFitWidth(dimPreviewImage.getWidth());
        imageView.setFitHeight(dimPreviewImage.getHeight());
        return imageView;
    }

    /**
     * Initializes the components.
     * @param location location.
     * @param resources resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRoot();
        setImage();

        setFileChooser();

        /* Set default size image. */
        dimensionImage = new Dimension(getImage().getWidth(),getImage().getHeight());

        closeBtn.setImage( new Image( sample.Main.class.getResource(ICONS_PATH + "closeIcon.png").toString() ) );
        closeBtn.setFitWidth(20.0f);
        closeBtn.setFitHeight(20.f);

        minimizeBtn.setImage(new Image(sample.Main.class.getResource(ICONS_PATH + "minimizeIcon.png").toString() ));
        minimizeBtn.setFitWidth(20.0f);
        minimizeBtn.setFitHeight(20.f);

        saveBtn.setGraphic(new ImageView(new Image(sample.Main.class.getResource(ICONS_PATH + "saveIcon.png").toString() )));
        configBtn.setGraphic(new ImageView(new Image(sample.Main.class.getResource(ICONS_PATH + "settIcon.png").toString() )));

        mainPane.setVisible(true);
        settingPane.setVisible(false);

        setComboBoxImgSize();
        setValuePropertyComboBoxSize();

        mainPane.getChildren().add(changeDimensionImage());
    }

    /**
     * Sets the fileChooser.
     */
    private void setFileChooser(){
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png", "*.png"),
                new FileChooser.ExtensionFilter("jpg","*.jpg","*.jpeg")
        );
    }

    /**
     * Image save button.
     * @param event Event.
     * @throws IOException
     */
    public void saveClickedEvent(MouseEvent event) throws IOException {

        fileChooser.setTitle("Salvar Imagem");
        File file = fileChooser.showSaveDialog(rootStage);
        if (file != null){
            fileChooser.setInitialDirectory(file.getParentFile());
            Image img = ImageUtil.scale(getImage(),dimensionImage.getWidth(),dimensionImage.getHeight(),false);
            ImageIO.write(SwingFXUtils.fromFXImage(img,null),
                                                   fileChooser.getSelectedExtensionFilter().getDescription(),
                                                    file);
        }
    }

    /**
     * OnMosueEntered event.
     * @param event Event.
     */
    public void saveEnteredEvent(MouseEvent event) {
        saveBtn.setGraphic(new ImageView(new Image(sample.Main.class.getResource(ICONS_PATH + "saveEnteredIcon.png").toString() )));
    }

    /**
     * OnMouseExited event.
     * @param event Event.
     */
    public void saveExitedEvent(MouseEvent event) {
        saveBtn.setGraphic(new ImageView(new Image(sample.Main.class.getResource(ICONS_PATH + "saveIcon.png").toString() )));
    }

    /**
     * Configures the click event.
     * @param event Event.
     */
    public void configClickedEvent(MouseEvent event) {
        if( clicked ){
            /* Add controlPanel. */
            settingPane.setVisible(true);
            mainPane.setVisible(false);
            clicked = false;
        }
        else{
            settingPane.setVisible(false);
            mainPane.setVisible(true);
            clicked = true;
        }
    }

    /**
     * Sets the image sizes.
     */
    private void setComboBoxImgSize(){
        cBoxImgSize.setPromptText("Tamanho imagem");
        cBoxImgSize.getItems().addAll("tamanho original","600x400","800x600","1024x768","1280x1024");
    }

    /**
     * Watch for the dimension value changes
     */
    @Action
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

    /**
     * Configure OnMouseEntered event.
     * @param event Event.
     */
    public void configEnteredEvent(MouseEvent event) {
        configBtn.setGraphic(new ImageView(new Image(sample.Main.class.getResource(ICONS_PATH + "settEnteredIcon.png").toString() )));
    }

    /**
     * Configure OnMouseExited event.
     * @param event Event.
     */
    public void configExitedEvent(MouseEvent event) {
        configBtn.setGraphic(new ImageView(new Image(sample.Main.class.getResource(ICONS_PATH + "settIcon.png").toString() )));
    }

    /**
     * Close OnMouseClicked event.
     * @param event Event.
     */
    public void closeClickedEvent(MouseEvent event) {
        getRoot().getScene().getWindow().hide();
    }

    /**
     * Close OnMouseEntered event.
     * @param event Event.
     */
    public void closeEnteredEvent(MouseEvent event) {
        closeBtn.setImage(new Image(sample.Main.class.getResource(ICONS_PATH + "closeEnteredIcon.png").toString() ));
    }

    /**
     * Close OnMouseExited event.
     * @param event Event.
     */
    public void closeExitedEvent(MouseEvent event) {
        closeBtn.setImage(new Image(sample.Main.class.getResource(ICONS_PATH + "closeIcon.png").toString() ));
    }

    /**
     * Minimize OnMouseClicked event.
     * @param mouseEvent Event.
     */
    public void minimizeClickedEvent(MouseEvent mouseEvent) {
        getRoot().setIconified(true);
    }

    /**
     * Minimize OnMouseEntered event.
     * @param mouseEvent Event.
     */
    public void minimizeEnteredEvent(MouseEvent mouseEvent) {
        minimizeBtn.setImage(new Image(sample.Main.class.getResource(ICONS_PATH + "minimizeEnteredIcon.png").toString() ));
    }

    /**
     * Minimize OnMouseExited event.
     * @param mouseEvent Event.
     */
    public void minimizeExitedEvent(MouseEvent mouseEvent) {
        minimizeBtn.setImage(new Image(sample.Main.class.getResource(ICONS_PATH + "minimizeIcon.png").toString() ));
    }
}
