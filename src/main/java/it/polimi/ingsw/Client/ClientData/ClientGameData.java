package it.polimi.ingsw.Client.ClientData;

import it.polimi.ingsw.Client.ClientData.EnemiesData;
import it.polimi.ingsw.Client.ClientData.YourData;
import it.polimi.ingsw.Client.ReadingThread;
import it.polimi.ingsw.Client.UI.CLI.CliCreator;
import it.polimi.ingsw.Client.UI.UIDrawer;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientGameData {

    private UIDrawer uiCreator;
    private PrintWriter socketOut;
    private boolean useGUI;

    private String[][] tokensOnMap;
    private String[][] levelsOnMap;
    private String[][] domesOnMap;

    private YourData yourData;

    private String firstCommand;
    private String gamePhrase;
    private String wildPhrase;

    private ArrayList<EnemiesData> enemiesData;

    private ArrayList<String> availableGods = new ArrayList<>();

    private boolean upToDate;

    /**
     * utility variable, true when a client disconnects, used to maintain gui up after server shutdown
     */
    private boolean aClientHasDisconnected= false;

    /**
     * builder with CLI
     * @param socketOut output socket
     */
    public ClientGameData(PrintWriter socketOut) {
        this.socketOut = socketOut;
        uiCreator = new CliCreator(this);
        this.tokensOnMap= new String[5][5];
        this.levelsOnMap= new String[5][5];
        this.domesOnMap= new String[5][5];
        this.upToDate=false;

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                tokensOnMap[i][j]="no";
                levelsOnMap[i][j]="0";
                domesOnMap[i][j]="0";
            }
        }

        this.yourData=new YourData();
        this.enemiesData=new ArrayList<>();
        this.gamePhrase="";
    }

    /**
     * builder with GUI
     * @param socketOut output socket
     * @param GuiCreator gui Creator
     * @param socketIn is used to pass Object ClientGameData
     */
    public ClientGameData(PrintWriter socketOut, UIDrawer GuiCreator, Scanner socketIn) {
        this.socketOut = socketOut;
        this.uiCreator = GuiCreator;
        this.tokensOnMap= new String[5][5];
        this.levelsOnMap= new String[5][5];
        this.domesOnMap= new String[5][5];
        this.upToDate=false;

        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                tokensOnMap[i][j]="no";
                levelsOnMap[i][j]="0";
                domesOnMap[i][j]="0";
            }
        }

        this.yourData=new YourData();
        this.enemiesData=new ArrayList<>();
    }

    /**
     * method which update Client data given the string received
     * @param command string received
     */
    public void dataUpdate(String command){
        String[] splittedCommand = command.split(",");
        firstCommand = splittedCommand[0];
        switch (firstCommand){
            case "MT":
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        tokensOnMap[i][j] = splittedCommand[(i * 5) + j + 1];
                    }
                }
                break;

            case "ML":
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        levelsOnMap[i][j] = splittedCommand[(i * 5) + j + 1];
                    }
                }
                break;

            case "MD":
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        domesOnMap[i][j] = splittedCommand[(i * 5) + j + 1];
                    }
                }
                break;

            case "NP":
                if(!splittedCommand[1].equals(yourData.getYourNumber())) {
                    enemiesData.add(new EnemiesData(splittedCommand[1], splittedCommand[2]));
                }
                break;

            case "PG":
                if (yourData.getYourNumber().equals(splittedCommand[1])){
                    yourData.setYourGod(splittedCommand[2]);
                    String power = "";
                    power += splittedCommand[3];
                    for (int i=4; i<splittedCommand.length; i++)
                        power += "," + splittedCommand[i];
                    yourData.setYourGodPower(power);
                }
                else {
                    for (EnemiesData enemy : enemiesData) {
                        if (enemy.getEnemyNumber().equals(splittedCommand[1])) {
                            enemy.setEnemyGod(splittedCommand[2]);
                            String power = "";
                            power += splittedCommand[3];
                            for (int i=4; i<splittedCommand.length; i++)
                                power += "," + splittedCommand[i];
                            enemy.setEnemyPowerGod(power);
                        }
                    }
                }
                break;

            case "GR":
                uiCreator.draw();
                break;

            case "GP":
                gamePhrase = splittedCommand[1];
                uiCreator.draw();
                break;

            case "YN":
                yourData.setYourNumber(splittedCommand[1]);
                break;

            case "WL":
                yourData.winLoseSetter(splittedCommand[1]);
                if(yourData.isYouWon())
                    uiCreator.drawWinner();
                else if(yourData.isYouLose())
                    uiCreator.drawLoser();
                break;

            case "UP":
                uiCreator.draw();
                break;

            case "GL":
                for(int i =1; i<splittedCommand.length; i++ ){
                    availableGods.add(splittedCommand[i]);
                }
                break;

            case "WP":
                wildPhrase=splittedCommand[1];
                for (int i=2; i<splittedCommand.length; i++)
                    wildPhrase =wildPhrase + "," + splittedCommand[i];
                wildPhrase = wildPhrase.replace('*', '\n');
                break;

            default:
                System.out.println("CAN'T RESOLVE THIS MESSAGE:"+ " "+command);
        }

        return;
    }

    //------------GETTERS---------------------------------------------------------------
    public String[][] getTokensOnMap() {
        return tokensOnMap;
    }
    public String[][] getLevelsOnMap() {
        return levelsOnMap;
    }
    public String[][] getDomesOnMap() {
        return domesOnMap;
    }
    public YourData getYourData() {
        return yourData;
    }
    public String getFirstCommand() {
        return firstCommand;
    }
    public String getGamePhrase() {
        return gamePhrase;
    }
    public ArrayList<EnemiesData> getEnemiesData() {
        return enemiesData;
    }
    public boolean isUseGUI() {
        return useGUI;
    }
    public void setUseGUI(boolean useGUI) {
        this.useGUI = useGUI;
    }
    public ArrayList<String> getAvailableGods() {
        return availableGods;
    }
    public String getWildPhrase() {
        return wildPhrase;
    }
    public boolean isUpToDate() {
        return upToDate;
    }
    public PrintWriter getSocketOut() {
        return socketOut;
    }
    public void setSocketOut(PrintWriter socketOut) {
        this.socketOut = socketOut;
    }
    public boolean isaClientHasDisconnected() {
        return aClientHasDisconnected;
    }

    //------------------------------------------------------------------------------------------------------------------------

    /**
     * method that means i used all updates since now and i'm waiting for others
     */
    public void usedUpdate(){
        upToDate=false;
    }

    /**
     * method that activates client disconnected variable
     */
    public void clientDisconnected(){
        aClientHasDisconnected=true;
    }


}
