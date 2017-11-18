package sample.sideMenu;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class VMenuBar extends HBox {

   private SideBar sideBar;

   public VMenuBar(){
       setSideBar(new SideBar());
       createComponent();
   }

   public void createComponent() {
       getChildren().add(getSideBar());
   }

    public SideBar getSideBar() {
        return sideBar;
    }

    public void setSideBar(SideBar sideBar) {
        this.sideBar = sideBar;
    }

    private final class SideBar extends VBox {

       private final double WIDTH = 50.0f;
       private final Paint BACKGROUND_COLOR = Paint.valueOf("#00ffb2");

       public SideBar(){
           setMinHeight(WIDTH);
           setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR,null,null)));
           setAlignment(Pos.CENTER);
       }
       private void addIcon(VMenuItemIcon icon){
            getChildren().add(icon);
       }
   }

    public void addMenuItem( VMenuItem item ) {
        getSideBar().addIcon(item.getIcon());
        item.setContainer(this);
    }
}
