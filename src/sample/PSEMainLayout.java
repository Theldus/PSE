package sample;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Workspace.Workspace;
import sample.sideMenu.SideMenuPane;
import sample.sideMenu.VMenuBar;
import sample.sideMenu.VMenuItem;
import sample.util.Dimension;

public class PSEMainLayout extends BorderPane{

    private Stage root;
    private TitlePane titlePane;
    private VMenuBar vMenuBar;
    private Workspace workspace;
    private FooterPane footePane;
    private Dimension prefSize = new Dimension(1024,768);

    public PSEMainLayout(Stage root){
        setRoot(root);
        //setMinSize(prefSize.width,prefSize.height);
        addWorkspace();
        addSideMenu();
    }

    public void show(){

        MainController.getInstance().setWorkspace(this.workspace);
        MainController.getInstance().installNodes();
        Scene scene = new Scene(this);
        getRoot().setScene(scene);
        getRoot().show();
    }

    private void addTitle(){
        //Empty
    }

    private void addSideMenu(){
        vMenuBar = new VMenuBar();
        vMenuBar.addMenuItem( new VMenuItem("Add",new SideMenuPane("Panel 01"),this.workspace));
        vMenuBar.addMenuItem( new VMenuItem("Cursor",new SideMenuPane("Panel 02"),this.workspace));
        vMenuBar.addMenuItem( new VMenuItem("Redo",new SideMenuPane("Panel 03"),this.workspace));
        vMenuBar.addMenuItem( new VMenuItem("Undo",new SideMenuPane("Panel 04"),this.workspace));
        vMenuBar.addMenuItem( new VMenuItem("Setting",new SideMenuPane("Panel 05"),this.workspace));
        vMenuBar.addMenuItem( new VMenuItem("Help",new SideMenuPane("Panel 06"),this.workspace));
        setLeft(vMenuBar);
    }

    private void addWorkspace(){
        workspace = new Workspace(new Dimension(1024,768));
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
