package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class KillManager {

    /**
     * players in this game
     */
    private ArrayList<Player> players;

    /**
     * standard builder
     * @param players players (from playerTurnManager)
     */
    public KillManager(ArrayList<Player> players) {
        this.players = players;
    }


    /**
     * method that kills target token
     * @param target token to kill
     */
    public void killOne(String target){
        Player targetPlayer;
        int c= Character.getNumericValue(target.toCharArray()[1]);
        targetPlayer=players.get(c-1);
        if (target.toCharArray()[0]=='m')
            targetPlayer.removeM();
        else targetPlayer.removeF();

    }

    /**
     * methof for finding player based on position
     * @param playerNum player number
     * @return player that corresponds to that number, never returns null
     */
    private Player findPlayer(int playerNum){
        for (Player player : players){
            if (player.getPlayerNumber()==playerNum)
                return player;
        }
        return null;
    }


}
