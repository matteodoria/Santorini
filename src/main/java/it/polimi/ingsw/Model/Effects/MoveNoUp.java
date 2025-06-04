package it.polimi.ingsw.Model.Effects;


public class MoveNoUp extends EffectOnMovement {

    public MoveNoUp() {
        this.name = "MoveNoUp";
    }

    @Override
    public void activateEffect() {
        int row, col;
        String s;
        String array[]= new String[2];
        if(map.canMoveBasic()) {
            do {
                if (map.getNoUpConstraint())
                    handler.sendMessage("GP,3.1");
                else
                    handler.sendMessage("GP,3.0");
                s = handler.receiveMessage();
                array = s.split(",");
                row = Integer.parseInt(array[0]);
                col = Integer.parseInt(array[1]);
            }
            while (!map.move(row, col));
        }else{
            char c=map.getMap()[map.getLookingAtRow()][map.getLookingAtCol()].getToken().charAt(1);
            int num= Integer.parseInt(String.valueOf(c));
            map.getPlayers().get(num-1).setIsActive(false);
            map.setActivePlayers(map.getActivePlayers()-1);
        }
        if(!map.isNoUpByAnotherGod()){
            map.deactivateNoUp();
        }else {
            return;
        }
    }

    @Override
    public boolean checkMove() {
        return map.canMoveNoUp();
    }
}
