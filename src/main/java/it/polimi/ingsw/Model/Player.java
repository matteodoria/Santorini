package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Gods.God;
import it.polimi.ingsw.Server.Connection.ServerClientHandler;

public class Player {

    private int playerNumber;
    private ServerClientHandler handler;
    private boolean aliveM;
    private boolean aliveF;
    private boolean isActive;
    private boolean hasWon;
    private God god;
    private Turn turn;


    public Player(int playerNumber, ServerClientHandler handler) {
        this.playerNumber = playerNumber;
        this.aliveF=true;
        this.aliveM=true;
        this.handler=handler;
        this.isActive=true;
        this.hasWon=false;
    }

    /**
     * standard getter for alive male token
     * @return true if male token is alive
     */
    public boolean isAliveM() {
        return aliveM;
    }

    /**
     * standard getter for alive female token
     * @return true if female token is alive
     */
    public boolean isAliveF() {
        return aliveF;
    }

    /**
     * method that removes female token
     */
    public void removeF(){
        handler.sendMessage("GP,15.1");
        aliveF=false;
        return;
    }

    /**
     * method that removes male token
     */
    public void removeM(){
        handler.sendMessage("GP,15.0");
        aliveM=false;
        return;
    }
    //-----------------GETTER & SETTER-----------------------------------------

    public Integer getPlayerNumber() {
        return playerNumber;
    }
    public God getGod() {
        return god;
    }
    public void setGod(God god) {
        this.god = god;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean getHasWon() {
        return hasWon;
    }
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }
    public ServerClientHandler getHandler() {
        return handler;
    }
    public Turn getTurn() {
        return turn;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
    }
}
