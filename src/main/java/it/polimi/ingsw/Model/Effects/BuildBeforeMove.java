package it.polimi.ingsw.Model.Effects;


public class BuildBeforeMove extends EffectBeforeMovement {

    public BuildBeforeMove() {
        this.name = "BuildBeforeMove";
    }

    @Override
    public void activateEffect() {
        map.setNoUpByAnotherGod(false);
        int row, col;
        String s;
        String array[]= new String [2];
        if(map.getNoUpConstraint())
            map.setNoUpByAnotherGod(true);
        if(map.canBuild()) {
            handler.sendMessage("GP,5");
            s = handler.receiveMessage();
            if (s.equals("Y") || s.equals("y")) {
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
                if (!map.getNoUpConstraint()) {
                    map.activateNoUp();
                }
                map.setChangedBuild(true);
            } else {
                return;
            }
        }else{
            return;
        }
    }

}
