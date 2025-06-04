package it.polimi.ingsw.Client.UI.CLI;

import it.polimi.ingsw.Client.ClientData.ClientGameData;
import it.polimi.ingsw.Client.ClientData.EnemiesData;
import it.polimi.ingsw.Client.ClientData.GamePhrases;
import it.polimi.ingsw.Client.UI.UIDrawer;


public class CliCreator extends UIDrawer {

    private static String mapOrizLineFull = "-------"+"-------"+"-------"+"-------"+"-------"+"-";
    /**
     * game data, used to draw
     */
    private ClientGameData clientGameData;



    public CliCreator(ClientGameData clientGameData) {
        this.clientGameData = clientGameData;
    }

    /**
     * method for drawing game UI
     */
    public void draw(){
        switch (clientGameData.getFirstCommand()){
            case "UP":
                drawMap();
                break;
            case "GP":
                drawPhrase();
                break;
            case "GR":
                drawYourData();
                drawEnemiesData();
                break;
            default:
                System.out.println("Default");
                break;
        }
    }

    /**
     * method that creates the map CLI version
     */
    public void drawMap(){
        String out = mapOrizLineFull + "\n";
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                out = out + drawCellHigh(i,j);
            }
            out=out + "|\n";

            for (int j=0; j<5; j++){
                out = out + drawCellLow(i,j);
            }

            out=out + "|\n";

            out = out + mapOrizLineFull + "\n";
        }
        System.out.print(out);
    }

    /**
     * method that returns the higher part of the cell CLI version
     * @param row cell row
     * @param col cell column
     * @return string with the upper part of the cell
     */
    public String drawCellHigh(int row, int col){
        String out = "| ";

        if (clientGameData.getTokensOnMap()[row][col].equals("no"))
            out = "|    ";
        else {
            switch (clientGameData.getTokensOnMap()[row][col].charAt(1)) {
                case '1':
                    out = out + Color.ANSI_BLUE.escape() + clientGameData.getTokensOnMap()[row][col] + Color.RESET + " ";
                    break;
                case '2':
                    out = out + Color.ANSI_RED.escape() + clientGameData.getTokensOnMap()[row][col] + Color.RESET + " ";
                    break;
                case '3':
                    out = out + Color.ANSI_YELLOW.escape() + clientGameData.getTokensOnMap()[row][col] + Color.RESET + " ";
                    break;
            }
        }
        if (clientGameData.getDomesOnMap()[row][col].equals("1"))
            out = out + Color.ANSI_PURPLE.escape()+ "D " + Color.RESET;
        else
            out = out + "  ";

        return out;
    }

    /**
     * method that returns the lower part of the cell CLI version
     * @param row cell row
     * @param col cell column
     * @return string with the lower part of the cell
     */
    public String drawCellLow(int row, int col){
        return "|    " + clientGameData.getLevelsOnMap()[row][col] + " ";
    }

    /**
     * method that prints your data
     */
    public void drawYourData(){
        System.out.println("YOU ARE PLAYER #" + clientGameData.getYourData().getYourNumber());
        System.out.println("YOUR GOD IS: " + clientGameData.getYourData().getYourGod());
        System.out.println("POWER: " + clientGameData.getYourData().getYourGodPower());
    }

    /**
     * mehod that prints enemies data
     */
    public void drawEnemiesData(){
        System.out.println("YOUR OPPONENTS");
        for(EnemiesData enemiesData : clientGameData.getEnemiesData())
            System.out.println("PLAYER " + enemiesData.getEnemyNumber() + ": " + enemiesData.getEnemyNickname() + " GOD: " + enemiesData.getEnemyGod() +
                " POWER: " + enemiesData.getEnemyPowerGod());
        System.out.println();
    }


    /**
     * method that draws game phrase
     */
    public void drawPhrase(){
        switch(clientGameData.getGamePhrase()){
            case "0.0": //scelta divinità x2
                String gods2[] = clientGameData.getWildPhrase().split("\n");
                for(int i=0; i<gods2.length; i++) {
                    if(gods2[i].contains("NOT AVAILABLE"))
                        gods2[i] = gods2[i].replaceAll("\\. ", ". " + Color.ANSI_RED.escape());
                    else
                        gods2[i] = gods2[i].replaceAll("\\. ", ". " + Color.ANSI_YELLOW.escape());
                    gods2[i] = gods2[i].replaceAll(": ", Color.RESET + ": ");
                    System.out.println(gods2[i]);
                }
                System.out.println(GamePhrases.godSelection2);
                break;
            case "0.1": //scelta divinità x3
                String gods3[] = clientGameData.getWildPhrase().split("\n");
                for(int i=0; i<gods3.length; i++) {
                    if(gods3[i].contains("NOT AVAILABLE"))
                        gods3[i] = gods3[i].replaceAll("\\. ", ". " + Color.ANSI_RED.escape());
                    else
                        gods3[i] = gods3[i].replaceAll("\\. ", ". " + Color.ANSI_YELLOW.escape());
                    gods3[i] = gods3[i].replaceAll(": ", Color.RESET + ": ");
                    System.out.println(gods3[i]);
                }
                System.out.println(GamePhrases.godSelection3);
                break;
            case "0.2":
                System.out.println(GamePhrases.godNotAvailable);
                break;
            case "1": //selezione mia divinità
                System.out.println(GamePhrases.yourGodSelection);
                String gods = clientGameData.getWildPhrase();
                gods = gods.replaceAll("\\. ", ". "+Color.ANSI_YELLOW.escape()).replaceAll(": ", Color.RESET + ": ");
                System.out.println(gods);
                break;
            case "2.0": //selezione worker
                System.out.println(GamePhrases.askSelectWorker);
                break;
            case "2.1": //selezione worker con no up
                System.out.println(GamePhrases.askSelectWorkerNoUp);
                break;
            case "3.0": //movimento
                System.out.println(GamePhrases.askMovement);
                break;
            case "3.1": //movimento ma no up
                System.out.println(GamePhrases.askMovementNoUp);
                break;
            case "3.2": //movimento swap
                System.out.println(GamePhrases.askMovementCanSwap);
                break;
            case "3.2.1": //swap ma no up
                System.out.println(GamePhrases.askMovementCanSwapNoUp);
                break;
            case "3.3": //push
                System.out.println(GamePhrases.askMovementCanPush);
                break;
            case "3.3.1": //push ma no up
                System.out.println(GamePhrases.askMovementCanPushNoUp);
                break;
            case "4": //build
                System.out.println(GamePhrases.askBuild);
                break;
            case "5": //usare potere
                System.out.println(clientGameData.getYourData().getYourGod().toUpperCase()+": "+clientGameData.getYourData().getYourGodPower());
                System.out.println(GamePhrases.askActivation);
                break;
            case "6": //non tuo turno
                System.out.println(GamePhrases.notYourTurn);
                break;
            case "8": //piazza m
                System.out.println(GamePhrases.askWorkerPlacementM);
                break;
            case "9": //piazza f
                System.out.println(GamePhrases.askWorkerPlacementF);
                break;
            case "10.0": //attesa
                System.out.println(GamePhrases.waitingForOtherConnection);
                break;
            case "10.1": //attesa
                System.out.println(GamePhrases.waitingForGodSelection);
                break;
            case "10.2": //attesa
                System.out.println(GamePhrases.waitingForWorkerPlacement);
                break;
            case "11.0": //attesa
                System.out.println(GamePhrases.gameSetup);
                break;
            case "11.1": //attesa
                System.out.println(GamePhrases.gameStart);
                System.out.println("USE \"recap\" to get your info every time");
                System.out.println("USE \"enemy\" or \"enemies\" to get your enemies' info every time");
                break;
            case "11.2":
                System.out.println(GamePhrases.gameGodSelection);
                break;
            case "11.3":
                System.out.println(GamePhrases.gameWorkerPlacement);
                break;
            case "12":
                System.out.println(GamePhrases.yourTurn);
                drawMap();
                break;
            case "13.0":
                System.out.println(GamePhrases.playerBlocked);
                break;
            case "13.1":
                System.out.println(GamePhrases.maleWorkerBlocked);
                break;
            case "13.2":
                System.out.println(GamePhrases.femaleWorkerBlocked);
                break;
            case "14":
                System.out.println(GamePhrases.RemoveBlock);
                break;
            case "15.0":
                System.out.println(GamePhrases.maleWorkerDead);
                break;
            case "15.1":
                System.out.println(GamePhrases.femaleWorkerDead);
                break;
            case "99":
                System.out.println(GamePhrases.ClientsError);
                clientGameData.clientDisconnected();
                break;
            default:
                System.out.println("Messaggio fuori standard" + clientGameData.getGamePhrase());
                break;
        }
    }





    //END GAME DRAWERS--------------------------------------------------------------------------------------------------------------

    public void drawWinner(){
        System.out.println(
                        " .----------------.  .----------------.  .----------------.    .----------------.  .----------------.  .-----------------.\n" +
                        "| .--------------. || .--------------. || .--------------. |  | .--------------. || .--------------. || .--------------. |\n" +
                        "| |  ____  ____  | || |     ____     | || | _____  _____ | |  | | _____  _____ | || |     _____    | || | ____  _____  | |\n" +
                        "| | |_  _||_  _| | || |   .'    `.   | || ||_   _||_   _|| |  | ||_   _||_   _|| || |    |_   _|   | || ||_   \\|_   _| | |\n" +
                        "| |   \\ \\  / /   | || |  /  .--.  \\  | || |  | |    | |  | |  | |  | | /\\ | |  | || |      | |     | || |  |   \\ | |   | |\n" +
                        "| |    \\ \\/ /    | || |  | |    | |  | || |  | '    ' |  | |  | |  | |/  \\| |  | || |      | |     | || |  | |\\ \\| |   | |\n" +
                        "| |    _|  |_    | || |  \\  `--'  /  | || |   \\ `--' /   | |  | |  |   /\\   |  | || |     _| |_    | || | _| |_\\   |_  | |\n" +
                        "| |   |______|   | || |   `.____.'   | || |    `.__.'    | |  | |  |__/  \\__|  | || |    |_____|   | || ||_____|\\____| | |\n" +
                        "| |              | || |              | || |              | |  | |              | || |              | || |              | |\n" +
                        "| '--------------' || '--------------' || '--------------' |  | '--------------' || '--------------' || '--------------' |\n" +
                        " '----------------'  '----------------'  '----------------'    '----------------'  '----------------'  '----------------' "
        );
    }

    public void drawLoser(){
        System.out.println(
                        " .----------------.  .----------------.  .----------------.    .----------------.  .----------------.  .----------------.  .----------------. \n" +
                        "| .--------------. || .--------------. || .--------------. |  | .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                        "| |  ____  ____  | || |     ____     | || | _____  _____ | |  | |   _____      | || |     ____     | || |    _______   | || |  _________   | |\n" +
                        "| | |_  _||_  _| | || |   .'    `.   | || ||_   _||_   _|| |  | |  |_   _|     | || |   .'    `.   | || |   /  ___  |  | || | |_   ___  |  | |\n" +
                        "| |   \\ \\  / /   | || |  /  .--.  \\  | || |  | |    | |  | |  | |    | |       | || |  /  .--.  \\  | || |  |  (__ \\_|  | || |   | |_  \\_|  | |\n" +
                        "| |    \\ \\/ /    | || |  | |    | |  | || |  | '    ' |  | |  | |    | |   _   | || |  | |    | |  | || |   '.___`-.   | || |   |  _|  _   | |\n" +
                        "| |    _|  |_    | || |  \\  `--'  /  | || |   \\ `--' /   | |  | |   _| |__/ |  | || |  \\  `--'  /  | || |  |`\\____) |  | || |  _| |___/ |  | |\n" +
                        "| |   |______|   | || |   `.____.'   | || |    `.__.'    | |  | |  |________|  | || |   `.____.'   | || |  |_______.'  | || | |_________|  | |\n" +
                        "| |              | || |              | || |              | |  | |              | || |              | || |              | || |              | |\n" +
                        "| '--------------' || '--------------' || '--------------' |  | '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                        " '----------------'  '----------------'  '----------------'    '----------------'  '----------------'  '----------------'  '----------------' \n"
        );
    }

}
