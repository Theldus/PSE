package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.dialogs.Toast;
import sample.json.ManipulateJson;
import sample.plugin.PluginController;
import sample.sideMenu.*;
import sample.util.ImageUtil;
import sample.workspace.Workspace;
import sample.util.Dimension;

import java.io.File;

/**
 * PSEMainLayout class. Defines the main layout
 * @author Daniel.
 */
public class PSEMainLayout extends BorderPane{

    /* Some important instances. */
    private Scene own;
    private static Stage root;
    private VMenuBar vMenuBar;
    private Workspace workspace;
    private FooterPane footePane;
    private Dimension prefSize = new Dimension(1024,768);
    private Delta delta = new Delta();

    /* Delta class. */
    protected class Delta{
        double x;
        double y;
    }

    /**
     * PSEMainLayout constructor.
     * @param arg_root Root stage.
     */
    public PSEMainLayout(Stage arg_root){
        root = arg_root;
        own = new Scene(this);
        setMinSize(prefSize.getWidth(),prefSize.getHeight());
        System.out.println(own.getWidth() + " " + own.getHeight());
        addWorkspace();
        addSideMenu();
    }

    /**
     * Shows the main window.
     */
    public void show(){
        getRoot().setScene(own);
        getRoot().setTitle("PSE-IMAGE");
        getRoot().show();
    }

    /**
     * Adds a title.
     */
    private void addTitle(){
    }

    /**
     * Constructs the sideMenu.
     */
    private void addSideMenu(){
        vMenuBar = new VMenuBar();

        /* Node area. */
        SideMenuPane addSideMenuPane = new SideMenuPane("Adicionar Nó");
        addSideMenuPane.setScrollable(true);
        getRoot().maximizedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                addSideMenuPane.setScrollPaneSize(new Dimension(0,768.0f));
            }
        });
        addSideMenuPane.install();
        vMenuBar.addMenuItem( new VMenuItem("Add", addSideMenuPane,this.workspace));
        ManipulateJson manipulateJson = new ManipulateJson();
        VMenuItemController.getInstance().fill(addSideMenuPane,manipulateJson.read());

        /* Plugins area. */
        ItemViewAdapter ivaConfig = new ItemViewAdapter("Plugins", "Adicionar plugin");
        ivaConfig.getDescription().setFill(Paint.valueOf("#ffffff"));
        ivaConfig.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ivaConfig.getDescription().setFill(Paint.valueOf("#00ffb2"));
                event.consume();
            }
        });

        ivaConfig.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ivaConfig.getDescription().setFill(Paint.valueOf("#ffffff"));
                event.consume();
            }
        });

        ivaConfig.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter fcef = new FileChooser.ExtensionFilter("Plugin File (.jar/.zip) file)","*.jar", "*.zip");
                fc.setTitle("Open plugin file...");
                fc.getExtensionFilters().add(fcef);

                File file = fc.showOpenDialog(PSEMainLayout.getRoot());
                if (file != null){
                    PluginController.getInstance().loadFileIntoPane(file, addSideMenuPane);
                }
                else {
                    Toast.show(MainController.getInstance().getCurrentWorkspace(),
                            Toast.ERROR_MESSAGE,
                            "Plugin não foi carregado/selecionado!",
                            "ErrorIcon",
                            1000,
                            200,
                            200,
                            "Error");
                }

                event.consume();
            }
        });

        /* Configurations area. */
        SideMenuPane configSideMenuPane = new SideMenuPane("Configurações");
        configSideMenuPane.addItem(ivaConfig);
        configSideMenuPane.install();
        vMenuBar.addMenuItem( new VMenuItem("Setting", configSideMenuPane ,this.workspace));

        /* About. */
        SideMenuPane helpSideMenuPane = new SideMenuPane("Ajuda");
        StringBuilder helpText = new StringBuilder();
        helpText.append("O projeto consiste em um PSE (Problem Solving Environment) ");
        helpText.append("para fins educacionais para área de processamento de imagens. ");
        helpText.append("O usuário não necessita saber de  alguma linguagem de programação, ");
        helpText.append("ele manipula blocos, chamados NodeBox, para criar um fluxo de ");
        helpText.append("execução, também usando linhas para conectá-los.");

        String text = "";

        int spaceCount = 0;
        for( int i = 0 ; i < helpText.length() ; ++i ){
            if( helpText.charAt(i) == ' ' )
                ++spaceCount;

            if( spaceCount == 9 ){
                text += "\n";
                spaceCount = 0;
            }
            text += helpText.charAt(i);
        }

        ItemViewAdapter helpItemView = new ItemViewAdapter("Sobre",text);
        helpSideMenuPane.addItem( helpItemView );
        helpSideMenuPane.install();
        vMenuBar.addMenuItem( new VMenuItem("Help",helpSideMenuPane,this.workspace));

        setLeft(vMenuBar);
    }

    /**
     * Adds the Workspace.
     */
    private void addWorkspace(){
        workspace = new Workspace(new Dimension(1024,768));
        MainController.getInstance().setWorkspace(this.workspace);
        setCenter(workspace);
    }

    /**
     * Adds a footer pane.
     */
    private void addFooterPane(){
    }

    /**
     * Gets the root stage.
     * @return Returns the root stage.
     */
    public static Stage getRoot() {
        return root;
    }
}
