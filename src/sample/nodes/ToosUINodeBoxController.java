package sample.nodes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ToosUINodeBoxController implements Initializable {

    @FXML
    private ComboBox cBoxMask;

    private static String mask = "3x3";
    private NodeBox root;
    private static NodeBox refRoot = null;

    public static void setRoot(NodeBox nodeBox){
            refRoot = nodeBox;
    }

    public void setRoot(){
        root = refRoot;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRoot(refRoot);
        fillComboBoxMask();
        setMask();
    }

    private void fillComboBoxMask(){

        cBoxMask.getEditor().setStyle("-fx-background-color: #111111; " +
                                      "-fx-text-fill: #ffffff;" +
                                      " -fx-prompt-text-fill: #ffffff; ");
        cBoxMask.setPromptText(getMask());
        cBoxMask.getItems().addAll("3x3","5x5","7x7");

    }

    private void setMask(){

        cBoxMask.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.print("Change Mask: ");
                setRoot();
                setMask(newValue.toString());
                getRoot().update(null);
                System.out.println(newValue.toString());
            }
        });

    }

    public void setMask(String arg_mask) {
        mask = arg_mask;
    }

    public static String getMask(){
        return mask;
    }

    public NodeBox getRoot() {
        return root;
    }
}
