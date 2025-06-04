package it.polimi.ingsw.Model.Gods;

import it.polimi.ingsw.Model.Effect;
import it.polimi.ingsw.Model.Effects.EffectBeforeMovement;
import it.polimi.ingsw.Model.Effects.EffectOnBuild;
import it.polimi.ingsw.Model.Effects.EffectOnEndTurn;
import it.polimi.ingsw.Model.Effects.EffectOnMovement;
import it.polimi.ingsw.Server.Connection.ServerClientHandler;

import java.util.ArrayList;

public class God {
    /**
     *name of God
     */
    private String name;

    /**
     *Description of God
     */
    private String description;

    /**
     *list of God's effects
     */
    private EffectBeforeMovement effectBeforeMovement;
    private EffectOnMovement effectOnMovement;
    private EffectOnBuild effectOnBuild;
    private EffectOnEndTurn effectOnEndTurn;

    /**
     * number of players god can be used with
     */
    private ArrayList<Integer> playersInGame = new ArrayList<Integer>();

    public God(String name, String description, ArrayList<Effect> effect, ArrayList<Integer> players) {
        this.name=name;
        this.description=description;
        effectBeforeMovement = (EffectBeforeMovement) effect.get(0);
        effectOnMovement = (EffectOnMovement) effect.get(1);
        effectOnBuild = (EffectOnBuild) effect.get(2);
        effectOnEndTurn = (EffectOnEndTurn) effect.get(3);
        playersInGame=players;
    }

    //----------------GETTER--------------------------------------------------------------
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public EffectBeforeMovement getEffectBeforeMovement() {
        return effectBeforeMovement;
    }
    public EffectOnMovement getEffectOnMovement() {
        return effectOnMovement;
    }
    public EffectOnBuild getEffectOnBuild() {
        return effectOnBuild;
    }
    public EffectOnEndTurn getEffectOnEndTurn() {
        return effectOnEndTurn;
    }
    public ArrayList<Integer> getPlayersInGame() {
        return playersInGame;
    }
}
