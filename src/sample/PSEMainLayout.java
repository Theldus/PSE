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
import sample.workspace.Workspace;
import sample.util.Dimension;

import java.io.File;

public class PSEMainLayout extends BorderPane{

    private Scene own;
    private static Stage root;
    private VMenuBar vMenuBar;
    private Workspace workspace;
    private FooterPane footePane;
    private Dimension prefSize = new Dimension(1024,768);
    private Delta delta = new Delta();

    protected class Delta{

        double x;
        double y;
    }

    public PSEMainLayout(Stage arg_root){
        root = arg_root;
        own = new Scene(this);
        setMinSize(prefSize.getWidth(),prefSize.getHeight());
        System.out.println(own.getWidth() + " " + own.getHeight());
        addWorkspace();
        addSideMenu();
    }

    public void show(){
        getRoot().setScene(own);
        getRoot().setTitle("PSE-IMAGE");
        //getRoot().getScene().getStylesheets().add(new File("src/sample/PSEMainStyle.css").toURI().toString());
        getRoot().show();
        //System.out.println(getRoot().getWidth() + " " + getRoot().getHeight());
    }

    private void addTitle(){
        //Empty
    }

    private void addSideMenu(){
        vMenuBar = new VMenuBar();

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

        ItemViewAdapter ivaConfig = new ItemViewAdapter("Plugins", "Adicionar plugin");
        ivaConfig.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ivaConfig.getDescription().setFill(Paint.valueOf("#5e75cd"));
                event.consume();
            }
        });

        ivaConfig.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ivaConfig.getDescription().setFill(Paint.valueOf("#AAAAAA"));
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


        SideMenuPane configSideMenuPane = new SideMenuPane("Configurações");
        configSideMenuPane.addItem(ivaConfig);
        configSideMenuPane.install();
        vMenuBar.addMenuItem( new VMenuItem("Setting", configSideMenuPane ,this.workspace));

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

            if( helpText.charAt(i) == ' ' ){
                ++spaceCount;
            }

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

    private void addWorkspace(){
        workspace = new Workspace(new Dimension(1024,768));
        MainController.getInstance().setWorkspace(this.workspace);
        //MainController.getInstance().installNodes();
        setCenter(workspace);
    }

    private void addFooterPane(){
        //Empty
    }

    public static Stage getRoot() {
        return root;
    }

}
