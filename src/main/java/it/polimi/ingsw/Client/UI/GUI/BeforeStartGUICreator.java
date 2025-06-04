package it.polimi.ingsw.Client.UI.GUI;

import it.polimi.ingsw.Client.ClientData.ClientGameData;
import it.polimi.ingsw.Client.ClientData.GamePhrases;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

import java.io.PrintWriter;

public class BeforeStartGUICreator implements ImageLoader, CodeToGod, ButtonsCreator {

    private PrintWriter printWriter;
    private ClientGameData clientGameData;
    private String output = "";

    public BeforeStartGUICreator(PrintWriter printWriter, ClientGameData clientGameData) {
        this.printWriter = printWriter;
        this.clientGameData = clientGameData;
    }

    //-------------------------------------------------------------------------------------------------------------
    public void sendMsg(String msg) {
        printWriter.println(msg);
    }

    private String godsNumberGetter(String s) {
        String out = "";
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.toCharArray()[i] >= '0' && s.toCharArray()[i] <= '9') {
                out = out + s.toCharArray()[i];
                if (!(s.toCharArray()[i + 1] >= '0' && s.toCharArray()[i + 1] <= '9')) {
                    out = out + ',';
                }
            }
        }
        return out;
    }

    /**
     * create scene for selection of gods
     *
     * @return complete scene
     */
    public Scene allGodSelection() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/home.png");
        imageView.setFitWidth(800);
        imageView.setFitHeight(800);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 10, 10, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        //row 1-------------------------------------------------
        StackPane apolloPane = createSelectionAll(1);
        GridPane.setConstraints(apolloPane, 0, 0);
        grid.getChildren().add(apolloPane);

        StackPane artemisPane = createSelectionAll(2);
        GridPane.setConstraints(artemisPane, 1, 0);
        grid.getChildren().add(artemisPane);

        StackPane athenaPane = createSelectionAll(3);
        GridPane.setConstraints(athenaPane, 2, 0);
        grid.getChildren().add(athenaPane);

        StackPane atlasPane = createSelectionAll(4);
        GridPane.setConstraints(atlasPane, 3, 0);
        grid.getChildren().add(atlasPane);

        StackPane demeterPane = createSelectionAll(5);
        GridPane.setConstraints(demeterPane, 4, 0);
        grid.getChildren().add(demeterPane);

        //row 2---------------------------------------------------------
        StackPane prometheusPane = createSelectionAll(6);
        GridPane.setConstraints(prometheusPane, 0, 1);
        grid.getChildren().add(prometheusPane);

        StackPane ephaestusPane = createSelectionAll(7);
        GridPane.setConstraints(ephaestusPane, 1, 1);
        grid.getChildren().add(ephaestusPane);

        StackPane minotaurPane = createSelectionAll(8);
        GridPane.setConstraints(minotaurPane, 2, 1);
        grid.getChildren().add(minotaurPane);

        StackPane panPane = createSelectionAll(9);
        GridPane.setConstraints(panPane, 3, 1);
        grid.getChildren().add(panPane);

        StackPane aresPane = createSelectionAll(10);
        GridPane.setConstraints(aresPane, 4, 1);
        grid.getChildren().add(aresPane);

        //row 3----------------------------------------------------

        StackPane chronoPane = createCondSelectionAll(11);
        GridPane.setConstraints(chronoPane, 0, 2);
        grid.getChildren().add(chronoPane);

        StackPane medusaPane = createSelectionAll(12);
        GridPane.setConstraints(medusaPane, 1, 2);
        grid.getChildren().add(medusaPane);

        StackPane poseidonPane = createSelectionAll(13);
        GridPane.setConstraints(poseidonPane, 2, 2);
        grid.getChildren().add(poseidonPane);

        StackPane zeusPane = createSelectionAll(14);
        GridPane.setConstraints(zeusPane, 3, 2);
        grid.getChildren().add(zeusPane);


        ImageView num;
        if (clientGameData.getGamePhrase().equals("0.0"))
            num = ImageLoader.imageLoader("Graphic/Screen/2.png");
        else {
            num = ImageLoader.imageLoader("Graphic/Screen/3.png");
        }
        num.setFitHeight(200);
        num.setFitWidth(140);
        GridPane.setConstraints(num, 4, 2);
        grid.getChildren().add(num);


        root.getChildren().add(imageView);
        root.getChildren().add(grid);


        return new Scene(root);
    }

    /**
     * button creator for all gods selection
     *
     * @param godnum god number
     * @return button for that god
     */
    private StackPane createSelectionAll(int godnum) {
        StackPane pane = new StackPane();

        ImageView imageView = ImageLoader.imageLoader("Graphic/Cards/" + CodeToGod.getNameFromIndex(godnum) + ".png");
        imageView.setFitHeight(200);
        imageView.setFitWidth(140);


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0, 0, 50, 30));

        Button button = new Button(CodeToGod.getNameFromIndex(godnum));
        button.setPrefHeight(20);
        button.setPrefWidth(80);
        button.setStyle("-fx-border-width:2;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: #624831;" + "-fx-background-color: #926E3C;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();
                if ((clientGameData.getGamePhrase().equals("0.0") && output.contains(",") && output.split(",").length == 1) || (clientGameData.getGamePhrase().equals("0.1") && output.contains(",") && output.split(",").length == 2)) {
                    output = output + godnum;
                    sendMsg(output);
                } else {
                    output = output + godnum + ",";
                    System.out.println(output);
                }
                button.setDisable(true);
                button.setVisible(false);
            }

        });

        //from stackoverflow
        StackPane stickyNotesPane = createPowerPopup(godnum);

        Popup popup = new Popup();
        popup.getContent().add(stickyNotesPane);

        pane.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                Bounds bnds = pane.localToScreen(pane.getLayoutBounds());
                double x = bnds.getMinX() - (stickyNotesPane.getWidth() / 2) + (pane.getWidth() / 2);
                double y = bnds.getMinY() - stickyNotesPane.getHeight();
                popup.show(pane, x, y);
            } else {
                popup.hide();
            }
        });

        GridPane.setConstraints(button, 0, 0);
        gridPane.getChildren().add(button);

        pane.getChildren().add(imageView);
        pane.getChildren().add(gridPane);

        return pane;
    }

    /**
     * button creator for all gods selection, for gods with specia conmditions on selection
     *
     * @param godnum god number
     * @return button for that god
     */
    private StackPane createCondSelectionAll(int godnum) {
        StackPane pane = new StackPane();

        ImageView imageView = ImageLoader.imageLoader("Graphic/Cards/" + CodeToGod.getNameFromIndex(godnum) + ".png");
        imageView.setFitHeight(200);
        imageView.setFitWidth(140);


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0, 0, 50, 30));


        Button button = new Button(CodeToGod.getNameFromIndex(godnum));
        button.setPrefHeight(20);
        button.setPrefWidth(80);
        button.setStyle("-fx-border-width:2;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: #624831;" + "-fx-background-color: #926E3C;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();
                if ((clientGameData.getGamePhrase().equals("0.0") && output.contains(",") && output.split(",").length == 1) || (clientGameData.getGamePhrase().equals("0.1") && output.contains(",") && output.split(",").length == 2)) {
                    output = output + godnum;
                    sendMsg(output);
                } else {
                    output = output + godnum + ",";
                    System.out.println(output);
                }
                button.setDisable(true);
                button.setVisible(false);
            }

        });

        //from stackoverflow
        StackPane stickyNotesPane;
        if (clientGameData.getGamePhrase().equals("0.0")) //if not blocked -> standard creation
            stickyNotesPane=createPowerPopup(godnum);

        else { //if blocked
            stickyNotesPane = new StackPane();

            Label error = new Label("UNAVAILABLE FOR 3 PLAYERS");
            error.setPadding(new Insets(5, 5, 5, 5));
            error.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20;" + "-fx-text-fill: #ff1015;");
            stickyNotesPane.setStyle("-fx-border-color: #ff1015;" + "-fx-border-style: solid;" + "-fx-border-width: 2;" + "-fx-background-color: black;");

            stickyNotesPane.getChildren().add(error);
        }

        Popup popup = new Popup();
        popup.getContent().add(stickyNotesPane);

        pane.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                Bounds bnds = pane.localToScreen(pane.getLayoutBounds());
                double x = bnds.getMinX() - (stickyNotesPane.getWidth() / 2) + (pane.getWidth() / 2);
                double y = bnds.getMinY() - stickyNotesPane.getHeight();
                popup.show(pane, x, y);
            } else {
                popup.hide();
            }
        });

        GridPane.setConstraints(button, 0, 0);
        gridPane.getChildren().add(button);

        if (!clientGameData.getGamePhrase().equals("0.0")) {
            button.setDisable(true);
            button.setVisible(false);
        }

        pane.getChildren().add(imageView);
        pane.getChildren().add(gridPane);

        return pane;
    }

    /**
     * method that creates your god selection based on content on wild phrases
     * @return your God selection scene
     */
    public Scene yourGodSelection() {
        final StackPane root = new StackPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Screen/GodSelScreen.png");
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(200, 0, 0, 73));
        grid.setVgap(15);
        grid.setHgap(15);

        String splitted[] = godsNumberGetter(clientGameData.getWildPhrase()).split(",");

        StackPane firstPane = createSelectionYour(Integer.parseInt(splitted[0]));
        GridPane.setConstraints(firstPane, 0, 0);
        grid.getChildren().add(firstPane);

        StackPane secondPane = createSelectionYour(Integer.parseInt(splitted[1]));
        GridPane.setConstraints(secondPane, 1, 0);
        grid.getChildren().add(secondPane);

        if (splitted.length == 3) {
            StackPane thirdPane = createSelectionYour(Integer.parseInt(splitted[2]));
            GridPane.setConstraints(thirdPane, 2, 0);
            grid.getChildren().add(thirdPane);
        }


        root.getChildren().add(imageView);
        root.getChildren().add(grid);


        return new Scene(root);
    }

    /**
     * creator of god selection for your god
     *
     * @param godnum god id
     * @return scene for your god selection
     */
    private StackPane createSelectionYour(Integer godnum) {
        StackPane pane = new StackPane();

        ImageView imageView = ImageLoader.imageLoader("Graphic/Cards/" + CodeToGod.getNameFromIndex(godnum) + ".png");
        imageView.setFitHeight(250);
        imageView.setFitWidth(175);


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0, 0, 50, 40));

        Button button = new Button(CodeToGod.getNameFromIndex(godnum));
        button.setPrefHeight(20);
        button.setPrefWidth(100);
        button.setStyle("-fx-border-width:2;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: #624831;" + "-fx-background-color: #926E3C;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();

                sendMsg(godnum.toString());

            }

        });

        //from stackoverflow
        StackPane stickyNotesPane = createPowerPopup(godnum);

        Popup popup = new Popup();
        popup.getContent().add(stickyNotesPane);

        pane.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                Bounds bnds = pane.localToScreen(pane.getLayoutBounds());
                double x = bnds.getMinX() - (stickyNotesPane.getWidth() / 2) + (pane.getWidth() / 2);
                double y = bnds.getMinY() - stickyNotesPane.getHeight();
                popup.show(pane, x, y);
            } else {
                popup.hide();
            }
        });

        GridPane.setConstraints(button, 0, 0);
        gridPane.getChildren().add(button);

        pane.getChildren().add(imageView);
        pane.getChildren().add(gridPane);

        return pane;
    }

    /**
     * method that creates pane for power popup
     *
     * @param godnum god id
     * @return power popup pane
     */
    private StackPane createPowerPopup(int godnum) {
        StackPane popup = new StackPane();

        Label power = new Label(CodeToGod.assignPower(godnum));
        power.setPadding(new Insets(5, 5, 5, 5));
        power.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20;" + "-fx-text-fill: blue;");
        popup.setStyle("-fx-border-color: blue;" + "-fx-border-style: solid;" + "-fx-border-width: 2;" + "-fx-background-color: white;");

        popup.getChildren().add(power);

        return popup;
    }


}
