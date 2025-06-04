package it.polimi.ingsw.Client.UI.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.PrintWriter;

public interface ButtonsCreator extends ImageLoader {

    static GridPane generateButtons(String gamePhrase, PrintWriter printWriter){
        switch (gamePhrase){
            case "8":
            case "9":
            case "3.0":
            case "3.1":
            case "3.2":
            case "3.2.1":
            case "3.3":
            case "3.3.1":
            case "4":
            case "14":
                return generateCellSelection(printWriter);
            case "5":
            case"13.0":
                return generateYN(printWriter);
            case "2.0":
            case "2.1":
                return generateWorkerSel(printWriter);
            default:
                return generateNotYourTurn();


        }
    }

    /**
     * method that sends messages
     * @param msg message to send
     * @param printWriter where to send
     */
    static void sendMsg(String msg, PrintWriter printWriter){
        printWriter.println(msg);
        System.out.println("invio" + msg);
    }

    static GridPane generateCellSelection(PrintWriter printWriter){
        GridPane gridPane = new GridPane();

        //row 0----------------------------------------------------------
        Button button00 = generateMapButton(0,0, printWriter);
        GridPane.setConstraints(button00,0,0);
        gridPane.getChildren().add(button00);

        Button button01 = generateMapButton(0,1, printWriter);
        GridPane.setConstraints(button01,1,0);
        gridPane.getChildren().add(button01);

        Button button02 = generateMapButton(0,2, printWriter);
        GridPane.setConstraints(button02,2,0);
        gridPane.getChildren().add(button02);

        Button button03 = generateMapButton(0,3, printWriter);
        GridPane.setConstraints(button03,3,0);
        gridPane.getChildren().add(button03);

        Button button04 = generateMapButton(0,4, printWriter);
        GridPane.setConstraints(button04,4,0);
        gridPane.getChildren().add(button04);

        //row 1----------------------------------------------------------
        Button button10 = generateMapButton(1,0, printWriter);
        GridPane.setConstraints(button10,0,1);
        gridPane.getChildren().add(button10);

        Button button11 = generateMapButton(1,1, printWriter);
        GridPane.setConstraints(button11,1,1);
        gridPane.getChildren().add(button11);

        Button button12 = generateMapButton(1,2, printWriter);
        GridPane.setConstraints(button12,2,1);
        gridPane.getChildren().add(button12);

        Button button13 = generateMapButton(1,3, printWriter);
        GridPane.setConstraints(button13,3,1);
        gridPane.getChildren().add(button13);

        Button button14 = generateMapButton(1,4, printWriter);
        GridPane.setConstraints(button14,4,1);
        gridPane.getChildren().add(button14);

        //row 2----------------------------------------------------------
        Button button20 = generateMapButton(2,0, printWriter);
        GridPane.setConstraints(button20,0,2);
        gridPane.getChildren().add(button20);

        Button button21 = generateMapButton(2,1, printWriter);
        GridPane.setConstraints(button21,1,2);
        gridPane.getChildren().add(button21);

        Button button22 = generateMapButton(2,2, printWriter);
        GridPane.setConstraints(button22,2,2);
        gridPane.getChildren().add(button22);

        Button button23 = generateMapButton(2,3, printWriter);
        GridPane.setConstraints(button23,3,2);
        gridPane.getChildren().add(button23);

        Button button24 = generateMapButton(2,4, printWriter);
        GridPane.setConstraints(button24,4,2);
        gridPane.getChildren().add(button24);

        //row 3----------------------------------------------------------
        Button button30 = generateMapButton(3,0, printWriter);
        GridPane.setConstraints(button30,0,3);
        gridPane.getChildren().add(button30);

        Button button31 = generateMapButton(3,1, printWriter);
        GridPane.setConstraints(button31,1,3);
        gridPane.getChildren().add(button31);

        Button button32 = generateMapButton(3,2, printWriter);
        GridPane.setConstraints(button32,2,3);
        gridPane.getChildren().add(button32);

        Button button33 = generateMapButton(3,3, printWriter);
        GridPane.setConstraints(button33,3,3);
        gridPane.getChildren().add(button33);

        Button button34 = generateMapButton(3,4, printWriter);
        GridPane.setConstraints(button34,4,3);
        gridPane.getChildren().add(button34);

        //row 4----------------------------------------------------------
        Button button40 = generateMapButton(4,0, printWriter);
        GridPane.setConstraints(button40,0,4);
        gridPane.getChildren().add(button40);

        Button button41 = generateMapButton(4,1, printWriter);
        GridPane.setConstraints(button41,1,4);
        gridPane.getChildren().add(button41);

        Button button42 = generateMapButton(4,2, printWriter);
        GridPane.setConstraints(button42,2,4);
        gridPane.getChildren().add(button42);

        Button button43 = generateMapButton(4,3, printWriter);
        GridPane.setConstraints(button43,3,4);
        gridPane.getChildren().add(button43);

        Button button44 = generateMapButton(4,4, printWriter);
        GridPane.setConstraints(button44,4,4);
        gridPane.getChildren().add(button44);


        return gridPane;
    }

    static Button generateMapButton(int row, int col, PrintWriter printWriter){
        Button button = new Button(row + "," + col);
        button.setMaxWidth(60);
        button.setMinWidth(60);
        button.setMaxHeight(50);
        button.setMinHeight(50);

        button.setStyle("-fx-border-width:1;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: #624831;" + "-fx-background-color: #926E3C;");


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();
                sendMsg(row + "," + col, printWriter);
            }

        });

        return button;
    }


    static GridPane generateYN(PrintWriter printWriter){
        GridPane gridPane = new GridPane();

        Button yes=generatorYNMF("YES", printWriter);
        GridPane.setConstraints(yes, 0,0);
        gridPane.getChildren().add(yes);

        Button no=generatorYNMF("NO", printWriter);
        GridPane.setConstraints(no, 1,0);
        gridPane.getChildren().add(no);

        return gridPane;
    }

    static GridPane generateWorkerSel(PrintWriter printWriter){
        GridPane gridPane = new GridPane();

        Button male=generatorYNMF("M", printWriter);
        GridPane.setConstraints(male, 0,0);
        gridPane.getChildren().add(male);

        Button female=generatorYNMF("F", printWriter);
        GridPane.setConstraints(female, 1,0);
        gridPane.getChildren().add(female);

        return gridPane;
    }

    static Button generatorYNMF(String content, PrintWriter printWriter){
        Button button = new Button(content);
        button.setMaxWidth(150);
        button.setMinWidth(150);
        button.setMaxHeight(250);
        button.setMinHeight(250);

        button.setStyle("-fx-border-width:1;" + "-fx-font-weight: bold;" + "-fx-text-alignment: center;" + "-fx-border-color: #624831;" + "-fx-background-color: #926E3C;");


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                e.consume();
                sendMsg(String.valueOf(content.toLowerCase().toCharArray()[0]), printWriter);
            }

        });
        return button;
    }

    static GridPane generateNotYourTurn(){
        GridPane gridPane = new GridPane();
        ImageView imageView = ImageLoader.imageLoader("Graphic/Phrases/notYT.png");
        imageView.setFitWidth(300);
        imageView.setFitHeight(250);

        GridPane.setConstraints(imageView,0,0);
        gridPane.getChildren().add(imageView);
        return gridPane;
    }
}
