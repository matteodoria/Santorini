package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.ClientData.ClientGameData;
import it.polimi.ingsw.Client.UI.UIDrawer;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ReadingThread extends Thread {

    private ClientGameData clientGameData;

    private Scanner socketIn;

    /**
     * standard builder
     *
     * @param clientGameData local data
     * @param socketIn       input stream
     */
    public ReadingThread(ClientGameData clientGameData, Scanner socketIn) {
        this.clientGameData = clientGameData;
        this.socketIn = socketIn;

    }

    @Override
    public void run() {

        String message;
        try {
            while (!clientGameData.getYourData().isYouLose() && !clientGameData.getYourData().isYouWon() && !clientGameData.isaClientHasDisconnected()) {
                message = socketIn.nextLine();
                System.out.println(message);
                clientGameData.dataUpdate(message);
            }
        }
        catch (NoSuchElementException e){
            clientGameData.dataUpdate("GP,100");
        }
    }
}




