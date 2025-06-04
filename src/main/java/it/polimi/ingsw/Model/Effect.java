package it.polimi.ingsw.Model;


import it.polimi.ingsw.Server.Connection.ServerClientHandler;

import java.util.NoSuchElementException;

public abstract class Effect {
    public Map map;
    public String name;
    public ServerClientHandler handler;
    /**
     * method that actives God power
     * @throws NoSuchElementException when a client is disconnected
     */
    public abstract void activateEffect () throws NoSuchElementException;
    public String getName() {return name;}
    public void setServerClientHandler (ServerClientHandler handler){ this.handler=handler;}
    public void setMap (Map map){ this.map=map;}
}

