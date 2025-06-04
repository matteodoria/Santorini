package it.polimi.ingsw.Client.UI.CLI;

import it.polimi.ingsw.Client.ClientData.ClientGameData;
import it.polimi.ingsw.Client.ClientData.EnemiesData;

import java.io.PrintWriter;
import java.util.Scanner;

import static it.polimi.ingsw.Client.UI.CLI.Color.ANSI_YELLOW;
import static it.polimi.ingsw.Client.UI.CLI.Color.RESET;

public class KeyboardThread extends Thread {

    private ClientGameData clientGameData;
    private PrintWriter socketOut;
    private Scanner keyIn;

    public KeyboardThread(ClientGameData clientGameData, PrintWriter socketOut) {
        this.clientGameData = clientGameData;
        this.socketOut = socketOut;
        keyIn = new Scanner(System.in);
    }

    private boolean checkYourGodSelection(String s) {
        s = s.trim();
        if(s.matches("^[0-9]{1,2}$"))
            return true;
        else{
            System.out.println("YOU MUST SELECT ONE GOD!");
            return false;
        }
    }

    private boolean checkGodSelection2(String s) {
        boolean check;
        s = s.trim();
        if (s.matches("^[0-9]+\\D{1}[0-9]+$")) {
            s = s.replaceAll("\\D", ",");
            String g[] = s.split(",");
            if (g.length != 2) {
                check = false;
                System.out.println("YOU MUST SELECT 2 GODS!");
                return check;
            }
            Integer[] intGods = new Integer[2];
            for (int i = 0; i < g.length; i++)
                intGods[i] = Integer.parseInt(g[i]);
            check = intGods[0] != intGods[1];
            if (!check)
                System.out.println("YOU MUST SELECT 2 DIFFERENT GODS!");
            return check;
        } else {
            System.out.println("PLEASE, INSERT JUST THE GOD'S NUMBERS!");
            check = false;
            return check;
        }
    }

    private boolean checkGodSelection3(String s) {
        boolean check;
        s = s.trim();
        if (s.matches("^[0-9]+\\D{1}[0-9]+\\D{1}[0-9]+$")) {
            s = s.replaceAll("\\D", ",");
            String g[] = s.split(",");
            if (g.length != 3) {
                check = false;
                System.out.println("YOU MUST SELECT 3 GODS!\n");
                return check;
            }
            Integer[] intGods = new Integer[3];
            for (int i = 0; i < g.length; i++)
                intGods[i] = Integer.parseInt(g[i]);
            check = intGods[0] != intGods[1] && intGods[0] != intGods[2] && intGods[1] != intGods[2];
            if (!check)
                System.out.println("YOU MUST SELECT 3 DIFFERENT GODS!");
            return check;
        } else {
            System.out.println("PLEASE, INSERT JUST THE GOD'S NUMBERS!");
            check = false;
            return check;
        }
    }

    private boolean checkWorker(String s) {
        s = s.trim();
        if(s.length() == 1 && (s.equals("M") || s.equals("m") || s.equals("F") || s.equals("f")))
            return true;
        System.out.println("(M/F)");
        return false;
    }

    private boolean checkAnswer(String s) {
        s = s.trim();
        if(s.length() == 1 && (s.equals("y") || s.equals("Y") || s.equals("N") || s.equals("n")))
            return true;
        System.out.println("(Y/N)");
        return false;
    }

    private boolean checkCoordinates(String xy) {
        boolean check;
        xy = xy.trim();
        if (xy.matches("^[0-9]+\\D{1}[0-9]+$")) {
            xy = xy.replaceAll("\\D", ",");
            String coord[] = xy.split(",");
            if (coord.length != 2) {
                check = false;
                System.out.println("YOU MUST INSERT 2 COORDINATES (x,y)!\n");
                return check;
            }
            Integer[] intCoord = new Integer[2];
            for (int i = 0; i < coord.length; i++)
                intCoord[i] = Integer.parseInt(coord[i]);
            check = intCoord[0] >= 0 && intCoord[0] <= 4 && intCoord[1] >= 0 && intCoord[1] <= 4;
            if (!check)
                System.out.println("COORDINATES OUT OF BOUND [0-4]");
            return check;
        } else {
            System.out.println("PLEASE, INSERT JUST THE COORDINATES!");
            check = false;
            return check;
        }
    }

    @Override
    public void run() {

        String input="";
        try {
            while (!clientGameData.getYourData().isYouLose() && !clientGameData.getYourData().isYouWon() && !clientGameData.isaClientHasDisconnected()) {
                input = keyIn.nextLine();
                if (input.equalsIgnoreCase("recap")) {
                    System.out.println("YOU ARE PLAYER " + clientGameData.getYourData().getYourNumber());
                    System.out.println("YOUR GOD IS: " + ANSI_YELLOW.escape() + clientGameData.getYourData().getYourGod() + RESET + "\n" + clientGameData.getYourData().getYourGodPower());
                } else {
                    if (input.equalsIgnoreCase("enemies") || input.equalsIgnoreCase("enemy")) {
                        for (EnemiesData enemy : clientGameData.getEnemiesData()){
                            System.out.println(enemy.getEnemyNickname() + ":");
                            System.out.println("NUMBER: " + enemy.getEnemyNumber());
                            System.out.println("GOD: " + ANSI_YELLOW.escape() + enemy.getEnemyGod() + RESET + "\n" + enemy.getEnemyPowerGod());
                        }

                    } else {

                        switch (clientGameData.getGamePhrase()) {
                            case "0.0": //scelta divinità x2
                                if (checkGodSelection2(input)) {
                                    input = input.trim().replaceAll("\\D", ",");
                                    socketOut.println(input);
                                }
                                break;
                            case "0.1": //scelta divinità x3
                                if (checkGodSelection3(input)) {
                                    input = input.trim().replaceAll("\\D", ",");
                                    socketOut.println(input);
                                }
                                break;
                            case "1": //selezione mia divinità
                                if (checkYourGodSelection(input))
                                    socketOut.println(input.trim());
                                break;
                            case "2.0":
                            case "2.1": //selezione worker
                                if (checkWorker(input))
                                    socketOut.println(input.trim());
                                break;
                            case "3.0":   //movement
                            case "3.1":
                            case "3.2":
                            case "3.2.1":
                            case "3.3":
                            case "3.3.1":
                            case "4":     //build
                            case "8":
                            case "9":     //worker placement
                            case "14":    //remove block
                                if (checkCoordinates(input)) {
                                    input = input.trim().replaceAll("\\D", ",");
                                    socketOut.println(input);
                                }
                                break;
                            case "5":
                            case "13.0":  //usare potere
                                if (checkAnswer(input))
                                    socketOut.println(input.trim());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR! CANT'T REACH THE SERVER");
        }

    }

}
