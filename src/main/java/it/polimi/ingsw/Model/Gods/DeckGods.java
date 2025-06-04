package it.polimi.ingsw.Model.Gods;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Model.Effect;
import it.polimi.ingsw.Model.Effects.*;
import it.polimi.ingsw.Model.Gods.God;
import it.polimi.ingsw.Model.ParserManager;

import java.io.*;
import java.util.ArrayList;

public class DeckGods {

    ArrayList<God> gods = new ArrayList<>();
    ParserManager pm;

    public int size(){
        return gods.size();
    }

    public God getGod(int pos){
        return gods.get(pos-1);
    }

    private Effect selectEffect(String eff){
        switch (eff){
            case "BeforeMoveBasic": return new BeforeMoveBasic();
            case "BuildBeforeMove": return new BuildBeforeMove();
            case "MoveBasic": return new MoveBasic();
            case "Swap": return new Swap();
            case "MoveTwice": return new MoveTwice();
            case "DenyUp": return new DenyUp();
            case "MoveNoUp": return new MoveNoUp();
            case "Push": return new Push();
            case "WinByFallingDown": return new WinByFallingDown();
            case "BuildBasic": return new BuildBasic();
            case "DomeMaster": return new DomeMaster();
            case "BuildTwice": return new BuildTwice();
            case "BuildUp": return new BuildUp();
            case "BuildBelow": return new BuildBelow();
            case "EndTurnBasic": return new EndTurnBasic();
            case "RemoveBlock": return new RemoveBlock();
            case "WorkerToBlock": return new WorkerToBlock();
            case "TripleBuild": return new TripleBuild();
            default: return null;
        }
    }

    public DeckGods() {

        try {
            pm = new ParserManager("gods.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonObject jSonObj = pm.getJsonObj();

        JsonArray jSonArr = jSonObj.get("DeckGods").getAsJsonArray();
        for (int i = 0; i < jSonArr.size(); i++) {
            ArrayList<String> effectsString = new ArrayList<>();
            for (int j = 0; j < jSonArr.get(i).getAsJsonObject().get("effect").getAsJsonArray().size(); j++) {
                effectsString.add(jSonArr.get(i).getAsJsonObject().get("effect").getAsJsonArray().get(j).getAsString());
            }
            ArrayList<Effect> effects=new ArrayList<>();
            effects.add((EffectBeforeMovement) selectEffect(effectsString.get(0)));
            effects.add((EffectOnMovement) selectEffect(effectsString.get(1)));
            effects.add((EffectOnBuild) selectEffect(effectsString.get(2)));
            effects.add((EffectOnEndTurn) selectEffect(effectsString.get(3)));
            ArrayList<Integer> players = new ArrayList<>();
            for(int j=0; j<jSonArr.get(i).getAsJsonObject().get("players_in_game").getAsJsonArray().size(); j++){
                players.add(jSonArr.get(i).getAsJsonObject().get("players_in_game").getAsJsonArray().get(j).getAsInt());
            }
            gods.add(new God(jSonArr.get(i).getAsJsonObject().get("name").getAsString(),
                   jSonArr.get(i).getAsJsonObject().get("description").getAsString(),
                    effects, players));

        }
    }
}