package it.polimi.ingsw.Model.Effects;


public class WinByFallingDown extends EffectOnMovement {

    public WinByFallingDown() {
        this.name = "WinByFallingDown";
    }

    @Override
    public void activateEffect() {
        int row, col, curr_row, curr_col;
        String s;
        String array[] = new String[2];
        curr_row = map.getLookingAtRow();
        curr_col = map.getLookingAtCol();
        do {
            if(map.getNoUpConstraint())
                handler.sendMessage("GP,3.1");
            else
                handler.sendMessage("GP,3.0");
            s = handler.receiveMessage();
            array = s.split(",");
            row = Integer.parseInt(array[0]);
            col = Integer.parseInt(array[1]);
        }
        while (!map.move(row, col));
        if ((map.getMap()[curr_row][curr_col].getLevel() - map.getMap()[row][col].getLevel()) >= 2)
            map.setWinner();

    }

    @Override
    public boolean checkMove() {
        return map.canMoveBasic();
    }


}
