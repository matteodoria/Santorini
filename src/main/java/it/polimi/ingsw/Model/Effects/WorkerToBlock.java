package it.polimi.ingsw.Model.Effects;


public class WorkerToBlock extends EffectOnEndTurn {

    public WorkerToBlock() {
        this.name = "WorkerToBlock";
    }

    @Override
    public void activateEffect() {
        map.toStone();
    }

}
