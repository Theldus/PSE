package sample.nodes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Settings Window to be attached into a NodeBox.
 * @author Daniel.
 */
public class ToosUINodeBoxController implements Initializable {

    @FXML
    private ComboBox cBoxMask;

    private static String mask = "3x3";
    private NodeBox root;
    private static NodeBox refRoot = null;

    /**
     * Sets the node to be attached.
     * @param nodeBox NodeBox.
     */
    public static void setRoot(NodeBox nodeBox){
            refRoot = nodeBox;
    }

    /**
     * Sets the root node with the current node.
     */
    public void setRoot(){
        root = refRoot;
    }

    /**
     * Initialize the Window.
     * @param location Placement.
     * @param resources Resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRoot(refRoot);
        fillComboBoxMask();
        setMask();
    }

    /**
     * Fills the ComboBoxMask, to be used in the
     * Gaussian Filter, for instance.
     */
    private void fillComboBoxMask(){

        cBoxMask.getEditor().setStyle("-fx-background-color: #111111; " +
                                      "-fx-text-fill: #ffffff;" +
                                      " -fx-prompt-text-fill: #ffffff; ");
        cBoxMask.setPromptText(getMask());
        cBoxMask.getItems().addAll("3x3","5x5","7x7");

    }

    /**
     * Sets the mask to be used.
     */
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

    /**
     * Sets the mask from a given mask.
     * @param arg_mask Mask.
     */
    public void setMask(String arg_mask) {
        mask = arg_mask;
    }

    /**
     * Gets the current mask.
     * @return Returns the mask.
     */
    public static String getMask(){
        return mask;
    }

    /**
     * Gets the parent node.
     * @return Returns the parent node.
     */
    public NodeBox getRoot() {
        return root;
    }
}
