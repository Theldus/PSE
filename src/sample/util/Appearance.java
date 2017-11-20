package sample.util;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

/**
 * Appearance class. This class defines path, formats, fonts
 * colors and other things that defines the appearance of
 * other things.
 * @author Daniel.
 */
public class Appearance {

    /* Some constants. */
    public static String ICONS_PATH = "icons/";
    public static String ICONS_EXT = ".png";
    public static String FONT_NAME = "Yu Gothic";
    public static String BACKGROUND_COLOR = "#111111";
    public static String FOREGROUND_COLOR = "#FFFFFF";
    public static String TEXT_COLOR = "#FFFFFF";
    public static float FONT_SIZE = 13.0f;
    public static Insets padding = new Insets(10,20,10,20);

    /**
     * Sets background color.
     * @param component Component target.
     * @param hexColor Hexadecimal color.
     * @param cornerRadii Radius.
     * @param insets Insets.
     */
    public static void setBGColor(Region component, String hexColor, CornerRadii cornerRadii, Insets insets){
        component.setBackground(new Background(new BackgroundFill(Paint.valueOf(hexColor),null,null)));
    }
}
