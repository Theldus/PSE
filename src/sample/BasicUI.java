package sample;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 * Created by Daniel on 02/09/2017.
 */
public interface BasicUI {

    String FONT_NAME = "Yu Gothic";
    String BACKGROUND_COLOR = "#222222";
    String FOREGROUND_COLOR = "#06bdcf";
    String TEXT_COLOR = "#ffffff";

    public void initComponents();
    public void addComponent(Node component);
    public void setProperties();


}
