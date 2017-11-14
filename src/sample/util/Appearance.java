package sample.util;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class Appearance {

    public static String ICONS_PATH = "src/sample/icons/";
    public static String ICONS_EXT = ".png";
    public static String FONT_NAME = "Yu Gothic";
    public static String BACKGROUND_COLOR = "#111111";
    public static String FOREGROUND_COLOR = "#FFFFFF";
    public static String TEXT_COLOR = "#FFFFFF";
    public static float FONT_SIZE = 13.0f;
    public static Insets padding = new Insets(10,20,10,20);

    public static void setBGColor(Region component, String hexColor, CornerRadii cornerRadii, Insets insets){
        component.setBackground(new Background(new BackgroundFill(Paint.valueOf(hexColor),null,null)));
    }



}
