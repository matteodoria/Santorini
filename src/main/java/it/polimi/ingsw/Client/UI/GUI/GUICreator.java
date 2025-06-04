package it.polimi.ingsw.Client.UI.GUI;

import it.polimi.ingsw.Client.ClientData.ClientGameData;
import it.polimi.ingsw.Client.ReadingThread;
import it.polimi.ingsw.Client.UI.UIDrawer;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GUICreator extends UIDrawer implements ImageLoader {

    //specific GUI creators
    BeforeStartGUICreator beforeStartGUICreator;
    InGameGuiCreator inGameGuiCreator;

    /**
     * main stage
     */
    Stage primaryStage;

    /**
     * local data structure
     */
    ClientGameData clientGameData;

    /**
     * communication socket
     */
    Socket socket;


    Scanner socketIn;
    PrintWriter socketOut;

    ReadingThread readingThread;


    /**
     * standard constructor
     * @param socket socket used for communication
     * @param primaryStage main window
     * @throws IOException when server is disconnected
     */
    public GUICreator(Stage primaryStage, Socket socket) throws IOException {
        this.primaryStage = primaryStage;
        this.socket = socket;
        socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.clientGameData = new ClientGameData(socketOut, this, socketIn);
        beforeStartGUICreator = new BeforeStartGUICreator(socketOut, clientGameData);
        inGameGuiCreator = new InGameGuiCreator(socketOut, clientGameData);
        readingThread = new ReadingThread(clientGameData, socketIn);
    }

    public ClientGameData getClientGameData() {
        return clientGameData;
    }

    public Scanner getSocketIn() {
        return socketIn;
    }

    /**
     * method for sending messages
     *
     * @param message message that needs to be sent
     */
    public void sendMessage(String message) {
        socketOut.println(message);
    }

    //methods from abstract--------------------------------------------------------------------------------------------------------------------


    /**
     * main draw method from uiDrawer
     */
    public void draw() {
        Scene scene;
        switch (clientGameData.getGamePhrase()) {
            //selezione divinità di tutti
            case ("0.0"):
            case ("0.1"):
                scene = godsForGameSelection();
                break;

            //selezione tua divinità
            case ("1"):
                scene = yourGodSelection();
                break;

            //attesa prima di iniziare
            case ("10.1"):
            case ("11.0"):
            case ("11.2"):
                scene = waitYourTurn();
                break;
            case ("10.0"):
                scene = waitForPlayers();
                break;
            case ("99"):
                clientGameData.clientDisconnected();
                scene = sceneClientDisconnected();
                break;
            case ("100"):
                scene = sceneServerDisconnected();
                break;

            //schermata in game
            default:
                scene = inGameScene();
                break;
        }
        Platform.runLater(() -> //switches to GUI Thread
        {
            primaryStage.setScene(scene);
        });
    }

    public void drawWinner() {
        Platform.runLater(() -> //switches to GUI Thread
        {
            primaryStage.setScene(sceneWin());
            primaryStage.show();
        });
    }


    public void drawLoser() {
        Platform.runLater(() -> //switches to GUI Thread
        {
            primaryStage.setScene(sceneLose());
            primaryStage.show();
        });
    }


    //BUILDER BEGIN----------------------------------------------------------------------------------------------------------------------------

    /**
     * method that draws nickname input scene
     *
     * @return complete input scene
     */
    public Scene drawNicknameScreen() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/NickScreen.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        //login fields
        final GridPane grid = new GridPane();
        grid.setPadding(new Insets(300, 10, 10, 300));
        grid.setVgap(5);
        grid.setHgap(5);


        final TextField nickfield = new TextField();
        nickfield.setPromptText("Nickname");
        nickfield.setPrefColumnCount(10);
        nickfield.getText();
        GridPane.setConstraints(nickfield, 0, 0);
        grid.getChildren().add(nickfield);

        Button send = new Button("Send");
        GridPane.setConstraints(send, 1, 0);
        grid.getChildren().add(send);
        send.setStyle("-fx-border-width:2;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: blue;" + "-fx-background-color: white;" + "-fx-text-fill: blue;");


        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                e.consume();
                String message = nickfield.getText();
                if (!message.equals("")) {
                    nickfield.setPromptText("Nickname can't be empty");
                    sendMessage(message);
                    String feedback = socketIn.nextLine();

                    if (feedback.equals("YN,NO")) {
                        nickfield.setPromptText("re-insert Nickname");
                        nickfield.clear();

                    } else {
                        if (feedback.equals("REJECTED")) {
                            Platform.runLater(() -> //switches to GUI Thread
                            {
                                primaryStage.setScene(sceneServerFull());
                            });

                        } else {

                            clientGameData.dataUpdate(feedback);
                            if (clientGameData.getYourData().getYourNumber().equals("1")) {
                                Platform.runLater(() -> //switches to GUI Thread
                                {
                                    primaryStage.setScene(selectNumPlayer());
                                });
                            } else {
                                Platform.runLater(() -> //switches to GUI Thread
                                {
                                    readingThread.start();
                                });

                            }

                        }
                    }

                }
            }
        });

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);
        root.getChildren().add(grid);

        return scene;

    }

    /**
     * method tha displays #player selection
     *
     * @return complete scene #player selection
     */
    public Scene selectNumPlayer() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/home.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(200, 10, 10, 150));
        grid.setVgap(5);
        grid.setHgap(5);

        Button send2p = new Button("2 Players");
        GridPane.setConstraints(send2p, 0, 0);
        grid.getChildren().add(send2p);
        send2p.setPrefHeight(200);
        send2p.setPrefWidth(200);
        send2p.setStyle("-fx-border-width:6;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: #624831;" + "-fx-background-color: #926E3C;");


        send2p.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();
                sendMessage("2");
                readingThread.start();
            }

        });

        Button send3p = new Button("3 Players");
        GridPane.setConstraints(send3p, 1, 0);
        grid.getChildren().add(send3p);
        send3p.setPrefHeight(200);
        send3p.setPrefWidth(200);
        send3p.setStyle("-fx-border-width:6;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: #624831;" + "-fx-background-color: #926E3C;");


        send3p.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();
                sendMessage("3");
                readingThread.start();

            }

        });

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);
        root.getChildren().add(grid);

        return scene;
    }

    //Simple game screen--------------------------------------------------------------------------------------

    /**
     * method that displays wait for your turn
     * @return wait for your turn scene
     */
    public Scene waitYourTurn() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/waitYourTurn.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);

        return scene;
    }

    /**
     * method that displays wait for your turn
     * @return wait for players scene
     */
    public Scene waitForPlayers() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/WaitPlayers.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);

        return scene;
    }

    //Scenes from specific classes-------------------------------------------------------------------

    public Scene godsForGameSelection() {
        return beforeStartGUICreator.allGodSelection();
    }

    public Scene yourGodSelection() {
        return beforeStartGUICreator.yourGodSelection();
    }

    public Scene inGameScene() {
        return inGameGuiCreator.generateInGameScene();
    }


    //win and lose-----------------------------------------------------------------

    /**
     * method that displays Winning screen
     *
     * @return win scene
     */
    public Scene sceneWin() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/winScreen.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);

        return scene;
    }

    /**
     * method that displays losing screen
     *
     * @return lose scene
     */
    public Scene sceneLose() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/loseScreen.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);

        return scene;
    }

    //Server/Client errors---------------------------------------------------------------------------------

    /**
     * method that displays server disconnection
     *
     * @return server disconnected screen
     */
    public Scene sceneServerDisconnected() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/serverDisconnected.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);

        return scene;
    }

    /**
     * method that displays client disconnection
     *
     * @return client disconnected screen
     */
    public Scene sceneClientDisconnected() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/clientDisconnected.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);

        return scene;
    }

    /**
     * method that displays server full scene
     *
     * @return client server full scene
     */
    public Scene sceneServerFull() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/ServerFullScreen.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);

        Scene scene = new Scene(root);
        root.getChildren().add(imageView);

        return scene;
    }
}
