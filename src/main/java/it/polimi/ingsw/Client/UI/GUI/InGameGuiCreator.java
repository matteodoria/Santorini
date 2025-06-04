package it.polimi.ingsw.Client.UI.GUI;

import it.polimi.ingsw.Client.ClientData.ClientGameData;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

import java.io.PrintWriter;

public class InGameGuiCreator implements ImageLoader, CodeToGod, PhraseGetter {

    private PrintWriter printWriter;
    private ClientGameData clientGameData;


    public InGameGuiCreator(PrintWriter printWriter, ClientGameData clientGameData) {
        this.printWriter = printWriter;
        this.clientGameData = clientGameData;
    }

    public Scene generateInGameScene() {
        final StackPane root = new StackPane();
        GridPane biggestGrid = new GridPane();
        GridPane leftGrid = new GridPane();

        //map
        StackPane map = generateMap();
        GridPane.setConstraints(map, 0, 0);
        leftGrid.getChildren().add(map);

        //map
        StackPane phrase = generatePhrase();
        GridPane.setConstraints(phrase, 0, 1);
        leftGrid.getChildren().add(phrase);

        GridPane.setConstraints(leftGrid, 0, 0);
        biggestGrid.getChildren().add(leftGrid);

        //gods and button
        StackPane rightSide = generateRightSide();
        GridPane.setConstraints(rightSide, 1, 0);
        biggestGrid.getChildren().add(rightSide);

        root.getChildren().add(biggestGrid);
        return new Scene(root);
    }

    /**
     * method that generates map
     * @return map with tokens levels and domes
     */
    private StackPane generateMap() {
        //stab(); //<------------------------------------------TEST
        StackPane root = new StackPane();

        ImageView map = ImageLoader.imageLoader("Graphic/Map.png");
        map.setFitWidth(600);
        map.setFitHeight(600);

        GridPane mapGrid = new GridPane();
        mapGrid.setPadding(new Insets(19, 0, 0, 27));
        mapGrid.setVgap(12);
        mapGrid.setHgap(12);

        //row 1---------------------------------------------------------
        StackPane stackPane00 = fillCell(0, 0);
        GridPane.setConstraints(stackPane00, 0, 0);
        mapGrid.getChildren().add(stackPane00);

        StackPane stackPane01 = fillCell(0, 1);
        GridPane.setConstraints(stackPane01, 1, 0);
        mapGrid.getChildren().add(stackPane01);

        StackPane stackPane02 = fillCell(0, 2);
        GridPane.setConstraints(stackPane02, 2, 0);
        mapGrid.getChildren().add(stackPane02);

        StackPane stackPane03 = fillCell(0, 3);
        GridPane.setConstraints(stackPane03, 3, 0);
        mapGrid.getChildren().add(stackPane03);

        StackPane stackPane04 = fillCell(0, 4);
        GridPane.setConstraints(stackPane04, 4, 0);
        mapGrid.getChildren().add(stackPane04);

        //row2-----------------------------------------------------------

        StackPane stackPane10 = fillCell(1, 0);
        GridPane.setConstraints(stackPane10, 0, 1);
        mapGrid.getChildren().add(stackPane10);

        StackPane stackPane11 = fillCell(1, 1);
        GridPane.setConstraints(stackPane11, 1, 1);
        mapGrid.getChildren().add(stackPane11);

        StackPane stackPane12 = fillCell(1, 2);
        GridPane.setConstraints(stackPane12, 2, 1);
        mapGrid.getChildren().add(stackPane12);

        StackPane stackPane13 = fillCell(1, 3);
        GridPane.setConstraints(stackPane13, 3, 1);
        mapGrid.getChildren().add(stackPane13);

        StackPane stackPane14 = fillCell(1, 4);
        GridPane.setConstraints(stackPane14, 4, 1);
        mapGrid.getChildren().add(stackPane14);

        //row3---------------------------------------------------------

        StackPane stackPane20 = fillCell(2, 0);
        GridPane.setConstraints(stackPane20, 0, 2);
        mapGrid.getChildren().add(stackPane20);

        StackPane stackPane21 = fillCell(2, 1);
        GridPane.setConstraints(stackPane21, 1, 2);
        mapGrid.getChildren().add(stackPane21);

        StackPane stackPane22 = fillCell(2, 2);
        GridPane.setConstraints(stackPane22, 2, 2);
        mapGrid.getChildren().add(stackPane22);

        StackPane stackPane23 = fillCell(2, 3);
        GridPane.setConstraints(stackPane23, 3, 2);
        mapGrid.getChildren().add(stackPane23);

        StackPane stackPane24 = fillCell(2, 4);
        GridPane.setConstraints(stackPane24, 4, 2);
        mapGrid.getChildren().add(stackPane24);

        //row4---------------------------------------------------------

        StackPane stackPane30 = fillCell(3, 0);
        GridPane.setConstraints(stackPane30, 0, 3);
        mapGrid.getChildren().add(stackPane30);

        StackPane stackPane31 = fillCell(3, 1);
        GridPane.setConstraints(stackPane31, 1, 3);
        mapGrid.getChildren().add(stackPane31);

        StackPane stackPane32 = fillCell(3, 2);
        GridPane.setConstraints(stackPane32, 2, 3);
        mapGrid.getChildren().add(stackPane32);

        StackPane stackPane33 = fillCell(3, 3);
        GridPane.setConstraints(stackPane33, 3, 3);
        mapGrid.getChildren().add(stackPane33);

        StackPane stackPane34 = fillCell(3, 4);
        GridPane.setConstraints(stackPane34, 4, 3);
        mapGrid.getChildren().add(stackPane34);

        //row5---------------------------------------------------------

        StackPane stackPane40 = fillCell(4, 0);
        GridPane.setConstraints(stackPane40, 0, 4);
        mapGrid.getChildren().add(stackPane40);

        StackPane stackPane41 = fillCell(4, 1);
        GridPane.setConstraints(stackPane41, 1, 4);
        mapGrid.getChildren().add(stackPane41);

        StackPane stackPane42 = fillCell(4, 2);
        GridPane.setConstraints(stackPane42, 2, 4);
        mapGrid.getChildren().add(stackPane42);

        StackPane stackPane43 = fillCell(4, 3);
        GridPane.setConstraints(stackPane43, 3, 4);
        mapGrid.getChildren().add(stackPane43);

        StackPane stackPane44 = fillCell(4, 4);
        GridPane.setConstraints(stackPane44, 4, 4);
        mapGrid.getChildren().add(stackPane44);

        root.getChildren().add(map);
        root.getChildren().add(mapGrid);
        return root;
    }

    /**
     * create the content of a single cell
     * @param row cell row
     * @param col cell col
     * @return content ofa cell
     *
     */
    private StackPane fillCell(int row, int col) {
        StackPane root = new StackPane();
        root.setMinWidth(102);
        root.setMinHeight(102);
        ImageView dome, token;
        GridPane inCellGrid = new GridPane();
        //complete dome
        if (clientGameData.getDomesOnMap()[row][col].equals("1") && clientGameData.getLevelsOnMap()[row][col].equals("3")) {
            dome = ImageLoader.imageLoader("Graphic/Buildings/completeDome.png");
            dome.setFitWidth(102);
            dome.setFitHeight(102);
            root.getChildren().add(dome);
            return root;
        }
        //incomplete dome
        else {
            if (clientGameData.getDomesOnMap()[row][col].equals("1")) {
                dome = ImageLoader.imageLoader("Graphic/Buildings/dome.png");
                dome.setFitWidth(102);
                dome.setFitHeight(102);
                root.getChildren().add(dome);
                return root;
            }
            //no dome
            else {
                if (!clientGameData.getLevelsOnMap()[row][col].equals("0")) {
                    dome = ImageLoader.imageLoader("Graphic/Buildings/LV" + clientGameData.getLevelsOnMap()[row][col] + ".png");
                    dome.setFitWidth(52);
                    switch (clientGameData.getLevelsOnMap()[row][col]) {
                        case "1":
                            dome.setFitHeight(51);
                            break;
                        case "2":
                            dome.setFitHeight(72);
                            break;
                        case "3":
                            dome.setFitHeight(102);
                            break;
                    }
                    GridPane.setConstraints(dome, 0, 0);
                    inCellGrid.getChildren().add(dome);
                }
                if (!clientGameData.getTokensOnMap()[row][col].equals("no")) {
                    token = ImageLoader.imageLoader("Graphic/Token/" + clientGameData.getTokensOnMap()[row][col] + ".png");
                    token.setFitHeight(50);
                    token.setFitWidth(50);
                    GridPane.setConstraints(token, 1, 0);
                    inCellGrid.getChildren().add(token);
                }
                root.getChildren().add(inCellGrid);
                return root;

            }
        }

    }

    //RIGHT SIDE-------------------------------------------------------------------------------------------------------

    private StackPane generateRightSide(){
        StackPane root = new StackPane();


        GridPane rightGrid = new GridPane();

        TabPane gods=new TabPane();


        //your data tab------------------------------------------------------------------------------
        Tab myGod = new Tab("Your God");
        myGod.setClosable(false);
        StackPane bg = new StackPane();
        ImageView back = ImageLoader.imageLoader("Graphic/background.jpg");
        back.setFitHeight(321);
        back.setFitWidth(450);
        bg.getChildren().add(back);


        GridPane yourDataGrid = new GridPane();
        yourDataGrid.setPadding(new Insets(25, 0, 0, 100));

        GridPane godcard = new GridPane();
        godcard.setPadding(new Insets(0, 0, 0, 40));

        ImageView yourGodCard = ImageLoader.imageLoader("Graphic/Cards/" + clientGameData.getYourData().getYourGod() + ".png");
        yourGodCard.setFitWidth(175);
        yourGodCard.setFitHeight(250);
        GridPane.setConstraints(yourGodCard,0,0);
        godcard.getChildren().add(yourGodCard);

        GridPane.setConstraints(godcard,0,0);
        yourDataGrid.getChildren().add(godcard);

        Label power = new Label("YOU ARE PLAYER "+ clientGameData.getYourData().getYourNumber() + "\n");
        power.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 30;");
        GridPane.setConstraints(power,0,1);
        yourDataGrid.getChildren().add(power);

        //from stackoverflow
        StackPane stickyNotesPane = createPowerPopup(CodeToGod.getIndexFromName(clientGameData.getYourData().getYourGod()));

        Popup popup = new Popup();
        popup.getContent().add(stickyNotesPane);

        yourGodCard.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                Bounds bnds = yourGodCard.localToScreen(yourGodCard.getLayoutBounds());
                double x = bnds.getMinX() - (stickyNotesPane.getWidth() / 2) + (yourGodCard.getFitWidth() / 2);
                double y = bnds.getMinY() - stickyNotesPane.getHeight();
                popup.show(yourGodCard, x, y);
            } else {
                popup.hide();
            }
        });

        bg.getChildren().add(yourDataGrid);
        myGod.setContent(bg);
        gods.getTabs().add(myGod);

        //enemies tab---------------------------------------------------------------

        Tab enemiesTab = createEnemiesTab();
        gods.getTabs().add(enemiesTab);

        //buttons ------------------------------------------------------------------
        StackPane buttonSide = new StackPane();
        ImageView back2 = ImageLoader.imageLoader("Graphic/background.jpg");
        back2.setFitHeight(300);
        back2.setFitWidth(450);
        buttonSide.getChildren().add(back2);
        GridPane buttons = ButtonsCreator.generateButtons(clientGameData.getGamePhrase(),printWriter);
        buttons.setPadding(new Insets(25, 0, 0, 75));
        buttonSide.getChildren().add(buttons);


        GridPane.setConstraints(gods,0,0);
        rightGrid.getChildren().add(gods);
        GridPane.setConstraints(buttonSide,0,1);
        rightGrid.getChildren().add(buttonSide);

        root.getChildren().add(rightGrid);
        return root;
    }

    /**
     * method that creates enemies tab
     * @return enemies tab
     */
    private Tab createEnemiesTab(){
        Tab tab = new Tab("Enemies");
        tab.setClosable(false);
        StackPane total = new StackPane();
        ImageView back = ImageLoader.imageLoader("Graphic/background.jpg");
        back.setFitHeight(321);
        back.setFitWidth(450);
        total.getChildren().add(back);

        GridPane enemiesGrid = new GridPane();
        enemiesGrid.setPadding(new Insets(25, 25, 0, 35));
        enemiesGrid.setHgap(10);
        enemiesGrid.setVgap(25);

        //first enemy---------------------------------------------------------
        ImageView enemy1im=ImageLoader.imageLoader("Graphic/Cards/" + clientGameData.getEnemiesData().get(0).getEnemyGod() + ".png");
        enemy1im.setFitWidth(84);
        enemy1im.setFitHeight(120);
        Label enemy11=new Label(clientGameData.getEnemiesData().get(0).getEnemyNickname() + "\n" + "IS PLAYER: " + clientGameData.getEnemiesData().get(0).getEnemyNumber());
        enemy11.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 30;");

        //from stackoverflow
        StackPane stickyNotesPane = createPowerPopup(CodeToGod.getIndexFromName(clientGameData.getEnemiesData().get(0).getEnemyGod()));

        Popup popup = new Popup();
        popup.getContent().add(stickyNotesPane);

        enemy1im.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                Bounds bnds = enemy1im.localToScreen(enemy1im.getLayoutBounds());
                double x = bnds.getMinX() - (stickyNotesPane.getWidth() / 2) + (enemy1im.getFitWidth() / 2);
                double y = bnds.getMinY() - stickyNotesPane.getHeight();
                popup.show(enemy1im, x, y);
            } else {
                popup.hide();
            }
        });

        GridPane.setConstraints(enemy1im,0,0);
        GridPane.setConstraints(enemy11,1,0);

        enemiesGrid.getChildren().add(enemy1im);
        enemiesGrid.getChildren().add(enemy11);

        //second enemy
        if(clientGameData.getEnemiesData().size()==2){
            ImageView enemy2im=ImageLoader.imageLoader("Graphic/Cards/" + clientGameData.getEnemiesData().get(1).getEnemyGod() + ".png");
            enemy2im.setFitWidth(84);
            enemy2im.setFitHeight(120);
            Label enemy22=new Label(clientGameData.getEnemiesData().get(1).getEnemyNickname() + "\n" + "IS PLAYER: " + clientGameData.getEnemiesData().get(1).getEnemyNumber());
            enemy22.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 30;");

            GridPane.setConstraints(enemy2im,0,1);
            GridPane.setConstraints(enemy22,1,1);

            enemiesGrid.getChildren().add(enemy2im);
            enemiesGrid.getChildren().add(enemy22);

            //from stackoverflow
            StackPane stickyNotesPane2 = createPowerPopup(CodeToGod.getIndexFromName(clientGameData.getEnemiesData().get(1).getEnemyGod()));

            Popup popup2 = new Popup();
            popup2.getContent().add(stickyNotesPane2);

            enemy2im.hoverProperty().addListener((obs, oldVal, newValue) -> {
                if (newValue) {
                    Bounds bnds = enemy2im.localToScreen(enemy2im.getLayoutBounds());
                    double x = bnds.getMinX() - (stickyNotesPane2.getWidth() / 2) + (enemy2im.getFitWidth() / 2);
                    double y = bnds.getMinY() - stickyNotesPane2.getHeight();
                    popup2.show(enemy2im, x, y);
                } else {
                    popup2.hide();
                }
            });
        }

        total.getChildren().add(enemiesGrid);
        tab.setContent(total);
        return tab;
    }

    private StackPane generatePhrase(){
        StackPane pane = new StackPane();
        pane.setMinHeight(50);
        pane.setMinWidth(600);

        Label phrase= new Label(PhraseGetter.retrivePhrase(clientGameData.getGamePhrase(), clientGameData).replaceAll("\n", " "));
        phrase.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20;" + "-fx-text-fill: blue;");
        pane.setStyle("-fx-border-color: blue;" + "-fx-border-style: dashed;" + "-fx-border-width: 3;");
        if (PhraseGetter.retrivePhrase(clientGameData.getGamePhrase(), clientGameData).length() >= 55)
            phrase.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 13;" + "-fx-text-fill: blue;");

        pane.getChildren().add(phrase);

        return pane;
    }

    /**
     * method that creates pane for power popup
     * @param godnum god id
     * @return power popup pane
     */
    private StackPane createPowerPopup(int godnum){
        StackPane popup = new StackPane();

        Label power= new Label(CodeToGod.assignPower(godnum));
        power.setPadding(new Insets(5,5,5,5));
        power.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 15;" + "-fx-text-fill: #a90d0d;");
        popup.setStyle("-fx-border-color: #624831;" + "-fx-border-style: solid;" + "-fx-border-width: 2;" + "-fx-background-color: #926E3C;");

        popup.getChildren().add(power);

        return popup;
    }

}


