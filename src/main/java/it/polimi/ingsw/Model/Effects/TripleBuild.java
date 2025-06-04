package it.polimi.ingsw.Model.Effects;

import it.polimi.ingsw.Server.DataForClientCreator;

public class TripleBuild extends EffectOnEndTurn {

    public TripleBuild() {
        this.name = "TripleBuild";
    }

    @Override
    public void activateEffect() {
        DataForClientCreator messageCreator = new DataForClientCreator(map);
        int  first_row, first_col;
        String s;
        String array[]= new String [2];
        if(map.alignOnOtherWorker()) {
            if (map.getMap()[map.getLookingAtRow()][map.getLookingAtCol()].getLevel() == 0) {
                for(int i=1; i<=3;i++){
                    if (map.canBuild()) {
                        handler.sendMessage("GP,5");
                        s = handler.receiveMessage();
                        if (s.equals("Y") || s.equals("y")) {
                            do {
                                handler.sendMessage("GP,4");
                                s = handler.receiveMessage();
                                array = s.split(",");
                                first_row = Integer.parseInt(array[0]);
                                first_col = Integer.parseInt(array[1]);
                            }
                            while (!map.build(first_row, first_col));
                            if(i!=3) {
                                handler.sendMessage(messageCreator.getMapLevelsForClient());
                                handler.sendMessage(messageCreator.getMapDomesForClient());
                                handler.sendMessage("UP");
                            }
                            map.setChangedBuild(true);
                            if(map.checkChronoWin()){
                                return;
                            }
                        } else{
                            break;
                        }
                    }else{
                        break;
                    }
                }
            }
        }
    }


}
