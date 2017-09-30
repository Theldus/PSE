package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

/**
 * Created by Daniel on 06/09/2017.
 */
public class FlowBoxPreview  extends FlowBox{
    public FlowBoxPreview(String title, Dimension dimension) {
        super(title, dimension);
        addComponent();
    }

    public void addComponent() {

        addComponent(new Component("Control 01") {
            @Override
            public Control createComponent(HBox layoutManager) {
                return new Slider();
            }
        });

        addComponent(new Component("Control 02") {
            @Override
            public Control createComponent(HBox layoutManager) {
                layoutManager.setAlignment(Pos.BOTTOM_RIGHT);
                Button btn = new Button("Apply");
                btn.setBackground(new Background(new BackgroundFill(Paint.valueOf("#666666"), null, null)));
                btn.setTextFill(Paint.valueOf("D1D1D1"));
                return btn;
            }
        });

        addComponent(new Component("Botao novo inserido") {
            @Override
            public Control createComponent(HBox layoutManager) {
                return new Button("Novo botao");
            }
        });
    }








}
