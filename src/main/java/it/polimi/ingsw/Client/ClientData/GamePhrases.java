package it.polimi.ingsw.Client.ClientData;

public class GamePhrases {


    public static String godSelection2 = "SELECT 2 GODS FOR THIS GAME (1ST GOD NUMBER 2ND GOD NUMBER): ";
    public static String godSelection3 = "SELECT 3 GODS FOR THIS GAME (1ST GOD NUMBER 2ND GOD NUMBER 3RD GOD NUMBER):";
    public static String godNotAvailable = "YOU HAVE SELECTED A GOD NOT AVAILABLE FOR THIS GAME!\n";

    /**
     * number: 1
     */
    public static String yourGodSelection = "SELECT YOUR GOD FOR THIS GAME";

    /**
     * number: 2
     */
    public static String askSelectWorker = "SELECT A WORKER \n(M/F)";
    public static String askSelectWorkerNoUp = "SELECT A WORKER, BUT YOU CAN'T GO UP  \n(M/F)";

    /**
     * number: 3
     */
    public static String askMovement = "SELECT WHERE TO MOVE \n(AS ROW,COL)";
    public static String askMovementNoUp = "SELECT WHERE TO MOVE, BUT YOU CAN'T GO UP \n(AS ROW,COL)";
    public static String askMovementCanSwap = "SELECT WHERE TO MOVE (REMEMBER: YOU CAN SWAP) \n(AS ROW,COL)";
    public static String askMovementCanPush = "SELECT WHERE TO MOVE (REMEMBER: YOU CAN PUSH) \n(AS ROW,COL)";
    public static String askMovementCanSwapNoUp = "SELECT WHERE TO MOVE (REMEMBER: YOU CAN SWAP, BUT YOU CAN'T GO UP) \n(AS ROW,COL)";
    public static String askMovementCanPushNoUp = "SELECT WHERE TO MOVE (REMEMBER: YOU CAN PUSH, BUT YOU CAN'T GO UP) \n(AS ROW,COL)";

    /**
     * number: 4
     */
    public static String askBuild = "SELECT WHERE TO BUILD \n(AS ROW,COL)";

    /**
     * number: 5
     */
    public static String askActivation = "DO YOU WANT TO USE YOUR GOD'S POWER? (Y/N)";

    /**
     * number: 6
     */
    public static String notYourTurn = "PLEASE WAIT FOR YOUR TURN...";

    /**
     * number: 7
     */


    /**
     * number: 8
     */
    public static String askWorkerPlacementM = "GIVE ME COORDINATES FOR MALE WORKER";

    /**
     * number: 9
     */
    public static String askWorkerPlacementF = "GIVE ME COORDINATES FOR FEMALE WORKER";


    /**
     * number: 10
     */
    public static String waitingForGodSelection = "WAIT FOR GOD SELECTION...\n";
    public static String waitingForWorkerPlacement = "WAIT FOR OTHER PLAYERS TO PLACE THE WORKERS...";
    public static String waitingForOtherConnection = "PLEASE, WAIT FOR OTHER CONNECTIONS...";

    /**
     * number: 11
     */
    public static String gameStart = "THE GAME BEGINS!";
    public static String gameSetup = "############## GAME SETUP ###############";
    public static String gameGodSelection = "GODS SELECTION";
    public static String gameWorkerPlacement = "WORKER PLACEMENT";

    /**
     * number: 12
     */
    public static String yourTurn = "YOUR TURN";

    /**
     * number: 13
     */
    public static String playerBlocked = "YOU CAN'T MOVE ANY WORKER. YOU LOSE!\nDO YOU WANT TO KEEP FOLLOWING THE GAME?";
    public static String maleWorkerBlocked = "YOUR MALE WORKER IS BLOCKED! YOU MUST MOVE YOUR FEMALE WORKER!";
    public static String femaleWorkerBlocked = "YOUR FEMALE WORKER IS BLOCKED! YOU MUST MOVE YOUR MALE WORKER!";

    /**
     * number: 14
     */
    public static String RemoveBlock = "GIVE ME COORDINATES TO REMOVE BLOCK";

    /**
     * number: 15
     */
    public static String maleWorkerDead = "YOUR MALE WORKER HAS BEEN TURNED TO STONE!";
    public static String femaleWorkerDead = "YOUR FEMALE WORKER HAS BEEN TURNED TO STONE!";

    /**
     * number: 99
     */
    public static String ClientsError= "A PLAYER DISCONNECTED! THE GAME IS OVER!";


}
