package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Effect;
import it.polimi.ingsw.Model.Effects.BuildBeforeMove;
import it.polimi.ingsw.Model.Map;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Server.Connection.ServerClientHandler;
import it.polimi.ingsw.Server.DataForClientCreator;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Turn {

    private Map map;
    private ArrayList<ServerClientHandler> handlers;
    private DataForClientCreator messageCreator;
    private Player p;

    /**
     * constructor of the class
     * @param map game Map
     * @param handlers communication handlers with players
     * @param messageCreator message creator
     * @param player player
     */
    public Turn(Map map, ArrayList<ServerClientHandler> handlers, DataForClientCreator messageCreator, Player player){
        this.handlers=handlers;
        this.map = map;
        this.messageCreator= messageCreator;
        this.p = player;
    }

    /**
     * method that control the worker selection to move and to build with
     * @throws NoSuchElementException when a client is disconnected
     */
    private void selectWorker() throws NoSuchElementException {
        String s;
        if(p.isAliveF() && p.isAliveM() ){                          //if both workers are alive
            map.alignPosition("m"+(p.getPlayerNumber() + 1));
            if(!p.getGod().getEffectOnMovement().checkMove()) {     //if male worker can't move
                map.alignOnOtherWorker();
                if (p.getGod().getEffectOnMovement().checkMove()) { //if female worker can move
                    p.getHandler().sendMessage("GP,13.1");
                    map.alignPosition( "f"+(p.getPlayerNumber() + 1));
                } else {                                            //if female worker can't move
                    p.setIsActive(false);
                    map.setActivePlayers(map.getActivePlayers()-1);
                }
            }else{                                                  //if male worker can move
                map.alignOnOtherWorker();
                if(!p.getGod().getEffectOnMovement().checkMove()){  //if female worker can't move
                    p.getHandler().sendMessage("GP,13.2");
                    map.alignPosition( "m"+ (p.getPlayerNumber() + 1));
                }else{                                              //if female worker can move
                    if(map.getNoUpConstraint())
                        p.getHandler().sendMessage("GP,2.1");
                    else
                        p.getHandler().sendMessage("GP,2.0");
                    s=p.getHandler().receiveMessage();
                    int tmp = p.getPlayerNumber()+1;
                    String pos = s.toLowerCase() + String.valueOf(tmp);
                    map.alignPosition(pos);
                }
            }
        } else if(p.isAliveF()){                                    //if only female worker is alive
            map.alignPosition("f"+ (p.getPlayerNumber() + 1));
            if(!p.getGod().getEffectOnMovement().checkMove()) {     //if female worker can't move
                p.setIsActive(false);
                map.setActivePlayers(map.getActivePlayers()-1);
            }
        } else if(p.isAliveM()){                                    //if only male worker is alive
            map.alignPosition("m"+ (p.getPlayerNumber() + 1));
            if(!p.getGod().getEffectOnMovement().checkMove()) {     //if male worker can't move
                p.setIsActive(false);
                map.setActivePlayers(map.getActivePlayers()-1);
            }
        } else {                                                    //if both are not alive
            p.setIsActive(false);
            map.setActivePlayers(map.getActivePlayers()-1);
        }
    }

    /**
     * method that allows to communicate with all the clients
     * @param s string to be sent
     */
    private void sendBroadcast(String s){
        for(ServerClientHandler handler : handlers)
            handler.sendMessage(s);
    }

    /**
     * method that control the turn play
     * @throws NoSuchElementException when a client is disconnected
     */
    public void play() throws NoSuchElementException{
        if(p.getGod().getEffectOnMovement().getName().equals("DenyUp"))
            map.deactivateNoUp();       //DenyUp lasts a hand
        selectWorker();
        if (p.getIsActive()) {          //if player is active can play his turn
            p.getGod().getEffectBeforeMovement().activateEffect();
            if (map.isChangedToken()) {
                sendBroadcast(messageCreator.getMapTokensForClient());
                if(!map.isChangedBuild())
                    sendBroadcast("UP");
                map.setChangedToken(false);
            }
            if (map.isChangedBuild()) {
                sendBroadcast(messageCreator.getMapLevelsForClient());
                sendBroadcast(messageCreator.getMapDomesForClient());
                sendBroadcast("UP");
                map.setChangedBuild(false);
            }
            if(map.isWinner()){
                return;
            }

            p.getGod().getEffectOnMovement().activateEffect();
            sendBroadcast(messageCreator.getMapTokensForClient());
            sendBroadcast("UP");
            if (map.isWinner()) {
                p.setHasWon(true);
                return;
            }
            if(!p.getIsActive()){
                return;
            }

            p.getGod().getEffectOnBuild().activateEffect();
            sendBroadcast(messageCreator.getMapLevelsForClient());
            sendBroadcast(messageCreator.getMapDomesForClient());
            sendBroadcast("UP");
            if(map.isWinner()){
                return;
            }

            p.getGod().getEffectOnEndTurn().activateEffect();
            if (map.isChangedToken()) {
                sendBroadcast(messageCreator.getMapTokensForClient());
                if(!map.isChangedBuild())
                    sendBroadcast("UP");
                map.setChangedToken(false);
            }
            if (map.isChangedBuild()) {
                sendBroadcast(messageCreator.getMapLevelsForClient());
                sendBroadcast(messageCreator.getMapDomesForClient());
                sendBroadcast("UP");
                map.setChangedBuild(false);
            }
        }else{                              //if player has been deactivated
            if(map.getActivePlayers()>1){   //if there are more than one active players
                p.getHandler().sendMessage("GP,13.0"); //asks if he wants to keep following the game
                String answer = p.getHandler().receiveMessage();
                if(answer.equals("n") || answer.equals("N")) {
                    p.getHandler().sendMessage("WL,0");
                    p.getHandler().sendMessage("EXIT");
                    handlers.remove(p.getHandler());
                }
            }
        }
    }
}
