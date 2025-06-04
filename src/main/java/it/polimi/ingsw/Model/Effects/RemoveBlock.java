package it.polimi.ingsw.Model.Effects;


public class RemoveBlock extends EffectOnEndTurn {

    public RemoveBlock() {
        this.name = "RemoveBlock";
    }

    @Override
    public void activateEffect() {
        int row, col;
        String s;
        String array[] = new String[2];
            if (map.alignOnOtherWorker()) {
                if(map.atLeastOneBlockToBreak()) {
                handler.sendMessage("GP,5");
                s = handler.receiveMessage();
                if (s.equals("Y") || s.equals("y")) {
                    do {
                        handler.sendMessage("GP,14");
                        s = handler.receiveMessage();
                        array = s.split(",");
                        row = Integer.parseInt(array[0]);
                        col = Integer.parseInt(array[1]);
                        map.setChangedBuild(true);
                    }
                    while (!map.breakHere(row, col));
                } else {
                    return;
                }
            }else{
                return ;
            }
        }else{
            return;
        }
    }

}
