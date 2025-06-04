package it.polimi.ingsw.Client.UI.GUI;

import it.polimi.ingsw.Client.ClientData.ClientGameData;
import it.polimi.ingsw.Client.ClientData.GamePhrases;

public interface PhraseGetter {

    static String retrivePhrase(String code, ClientGameData clientGameData) {
        switch (code) {
            case "2.0": //selezione worker
                return (GamePhrases.askSelectWorker);

            case "2.1": //selezione worker con no up
                return GamePhrases.askSelectWorkerNoUp;

            case "3.0": //movimento
                return GamePhrases.askMovement;

            case "3.1": //movimento ma no up
                return GamePhrases.askMovementNoUp;

            case "3.2": //movimento swap
                return GamePhrases.askMovementCanSwap;

            case "3.2.1": //swap ma no up
                return GamePhrases.askMovementCanSwapNoUp;

            case "3.3": //push
                return GamePhrases.askMovementCanPush;

            case "3.3.1": //push ma no up
                return GamePhrases.askMovementCanPushNoUp;

            case "4": //build
                return GamePhrases.askBuild;

            case "5": //usare potere
                return clientGameData.getYourData().getYourGod().toUpperCase() + ": " + clientGameData.getYourData().getYourGodPower();

            case "6": //non tuo turno
                return GamePhrases.notYourTurn;

            case "8": //piazza m
                return GamePhrases.askWorkerPlacementM;

            case "9": //piazza f
                return GamePhrases.askWorkerPlacementF;

            case "10.0": //attesa
                return GamePhrases.waitingForOtherConnection;

            case "10.1": //attesa
                return GamePhrases.waitingForGodSelection;

            case "10.2": //attesa
                return GamePhrases.waitingForWorkerPlacement;

            case "11.0": //attesa
                return GamePhrases.gameSetup;

            case "11.1": //attesa
                return GamePhrases.gameStart;

            case "11.2":
                return GamePhrases.gameGodSelection;

            case "11.3":
                return GamePhrases.gameWorkerPlacement;

            case "12":
                return GamePhrases.yourTurn;

            case "13.0":
                return GamePhrases.playerBlocked;

            case "13.1":
                return GamePhrases.maleWorkerBlocked;

            case "13.2":
                return GamePhrases.femaleWorkerBlocked;

            case "14":
                return GamePhrases.RemoveBlock;

            case "15.0":
                return GamePhrases.maleWorkerDead;

            case "15.1":
                return GamePhrases.femaleWorkerDead;

        }
        return "";
    }
}
