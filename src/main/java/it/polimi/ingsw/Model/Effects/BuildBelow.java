package it.polimi.ingsw.Model.Effects;


public class BuildBelow extends EffectOnBuild {

    public BuildBelow() {
        this.name = "BuildBelow";
    }

    public void standardBuild(){
        int row, col;
        String s;
        String array[]= new String [2];
        do {
            handler.sendMessage("GP,4");
            s = handler.receiveMessage();
            array = s.split(",");
            row = Integer.parseInt(array[0]);
            col = Integer.parseInt(array[1]);
        }
        while (!map.build(row, col));
        if(map.checkChronoWin()){
            return;
        }
    }

    @Override
    public void activateEffect() {
        String s;
        if(map.getMap()[map.getLookingAtRow()][map.getLookingAtCol()].getLevel()<3) {
            handler.sendMessage("GP,5");
            s = handler.receiveMessage();
            if (s.equals("Y") || s.equals("y")) {
                map.getMap()[map.getLookingAtRow()][map.getLookingAtCol()].buildHere();
            }else{
                standardBuild();
            }
        } else {
              standardBuild();
        }
    }

}
