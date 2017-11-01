package sample.sideMenu;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class VMenuBar extends HBox {

   private SideBar sideBar;

   public VMenuBar(){
       sideBar = new SideBar();
       createComponent();
   }

   public void createComponent() {
       getChildren().add(sideBar);
   }

    private final class SideBar extends VBox {

       private final double WIDTH = 50.0f;
       private final Paint BACKGROUND_COLOR = Paint.valueOf("#42f49e");

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
        sideBar.addIcon(item.getIcon());
        item.setContainer(this);
    }
}
