package it.polimi.ingsw.Client;


import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.UI.CLI.CLIRunner;
import it.polimi.ingsw.Client.UI.GUI.GUICreator;
import it.polimi.ingsw.Client.UI.GUI.ImageLoader;
import it.polimi.ingsw.Model.ParserManager;
import it.polimi.ingsw.Server.Connection.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;


public class Client extends Application {

    private static CLIRunner cliRunner;
    private GUICreator guiCreator;
    private String ip;
    private static int port;

    public static void main(String[] args) {
        cliRunner = new CLIRunner();
        if (args.length == 0) {
            launch(args);
        } else if (args[0].equalsIgnoreCase("CLI")) {
            cliRunner.cliStart();
        } else if (args[0].equalsIgnoreCase("SERVER")) {
            try {
                ParserManager pm = new ParserManager("Connection.json");
                JsonObject obj = pm.getJsonObj();
                port = obj.get("port").getAsInt();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Server server = new Server(port);
            server.serverStart();
        }
        System.exit(0);
    }

    //---- GUI ---------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setTitle("Santorini");
        setupLogin(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * settles up all login scene
     *
     * @param primaryStage where to display the scene
     */

    private void setupLogin(Stage primaryStage) {

        //JSON---------------

        ParserManager pm = null;
        try {
            pm = new ParserManager("Connection.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonObject jObj = pm.getJsonObj();
        port = jObj.get("port").getAsInt();

        //GRAPHIC------------

        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/IPScreen.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        //login fields
        final GridPane grid = new GridPane();
        grid.setPadding(new Insets(300, 10, 10, 300));
        grid.setVgap(5);
        grid.setHgap(5);


        final TextField ipfield = new TextField();
        ipfield.setPromptText("Ip address");
        ipfield.setPrefColumnCount(10);
        ipfield.getText();
        GridPane.setConstraints(ipfield, 0, 0);
        grid.getChildren().add(ipfield);

        Button login = new Button("Log in");
        GridPane.setConstraints(login, 1, 0);
        grid.getChildren().add(login);
        login.setStyle("-fx-border-width:2;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: blue;" + "-fx-background-color: white;" + "-fx-text-fill: blue;");


        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();
                String ipAddress;
                ipAddress = ipfield.getText();
                if(ipAddress.equals("")){
                    ipfield.setPromptText("IP can't be empty");
                    ipfield.clear();
                }
                    else{
                    try {
                        Socket socket = new Socket(ipAddress, port);
                        guiCreator = new GUICreator(primaryStage, socket);
                        Platform.runLater(() -> //switches to GUI Thread
                        {
                            primaryStage.setScene(guiCreator.drawNicknameScreen()); //<--------------------------------------
                        });
                    } catch (IOException e1) {
                        ipfield.setPromptText("Unable to connect");
                        ipfield.clear();
                    }
                }
            }

        });

        Button deafultButton = new Button("Use default IP");
        deafultButton.setPrefWidth(150);
        deafultButton.setStyle("-fx-border-width:2;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: blue;" + "-fx-background-color: white;" + "-fx-text-fill: blue;");
        GridPane.setConstraints(deafultButton, 0, 1);
        grid.getChildren().add(deafultButton);

        deafultButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();
                try {
                    Socket socket = new Socket(jObj.get("host").getAsString(), port);
                    guiCreator = new GUICreator(primaryStage, socket);
                    Platform.runLater(() -> //switches to GUI Thread
                    {
                        primaryStage.setScene(guiCreator.drawNicknameScreen()); //<--------------------------------------
                    });
                } catch (IOException e1) {
                    ipfield.setPromptText("Unable to connect");
                    ipfield.clear();
                }
            }

        });

        primaryStage.setScene(new Scene(root));
        root.getChildren().add(imageView);
        root.getChildren().add(grid);

    }


}



