package sample;

import com.sun.org.apache.xerces.internal.impl.dv.xs.AbstractDateTimeDV;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Json.ManipulateJson;
import sample.Workspace.Workspace;
import sample.sideMenu.SideMenuPane;
import sample.sideMenu.VMenuBar;
import sample.sideMenu.VMenuItem;
import sample.sideMenu.VMenuItemController;
import sample.util.Coordinates;
import sample.util.Dimension;

public class PSEMainLayout extends BorderPane{

    private static Stage root;
    private TitlePane titlePane;
    private VMenuBar vMenuBar;
    private Workspace workspace;
    private FooterPane footePane;
    private Dimension prefSize = new Dimension(1024,768);

    public PSEMainLayout(Stage root){
        setRoot(root);
        setMinSize(prefSize.getWidth(),prefSize.getHeight());
        addWorkspace();
        addSideMenu();
    }

    public void show(){
        Scene scene = new Scene(this);
        getRoot().setScene(scene);
        getRoot().show();
        System.out.println(getRoot().getWidth() + " " + getRoot().getHeight());
    }

    private void addTitle(){
        //Empty
    }

    private void addSideMenu(){
        vMenuBar = new VMenuBar();

        SideMenuPane addSideMenuPane = new SideMenuPane("Adicionar Nó");
        vMenuBar.addMenuItem( new VMenuItem("Add", addSideMenuPane,this.workspace));
        ManipulateJson manipulateJson = new ManipulateJson();
        VMenuItemController.getInstance().fill(addSideMenuPane,manipulateJson.read());
        vMenuBar.addMenuItem( new VMenuItem("Setting",new SideMenuPane("Panel 05"),this.workspace));
        vMenuBar.addMenuItem( new VMenuItem("Help",new SideMenuPane("Panel 06"),this.workspace));

        setLeft(vMenuBar);
    }

    private void addWorkspace(){
        workspace = new Workspace(new Dimension(1024,768));
        MainController.getInstance().setWorkspace(this.workspace);
        //MainController.getInstance().installNodes();
        setCenter(workspace);
    }

    private void addFooterPane(){
        //Empty
    }


    public Stage getRoot() {
        return root;
    }

    public void setRoot(Stage root) {
        this.root = root;
        //root.initStyle(StageStyle.TRANSPARENT);
    }
}
