package it.polimi.ingsw.Model.Effects;

import it.polimi.ingsw.Server.DataForClientCreator;

public class MoveTwice extends EffectOnMovement {

    public MoveTwice() {
        this.name = "MoveTwice";
    }

    @Override
    public void activateEffect() {
        DataForClientCreator messageCreator = new DataForClientCreator(map);
        int curr_row, curr_col, row, col;
        String s;
        String array[]= new String[2];
        curr_row=map.getLookingAtRow();
        curr_col=map.getLookingAtCol();
        do {
            if(map.getNoUpConstraint())
                handler.sendMessage("GP,3.1");
            else
                handler.sendMessage("GP,3.0");
            s=handler.receiveMessage();
            array=s.split(",");
            row=Integer.parseInt(array[0]);
            col=Integer.parseInt(array[1]);
        }
        while(!map.move(row, col));
        if(map.canMoveBasic()) {
            handler.sendMessage(messageCreator.getMapTokensForClient());
            handler.sendMessage("UP");
            handler.sendMessage("GP,5");
            s = handler.receiveMessage();
            if (s.equals("Y") || s.equals("y")) {
                do {
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
                    while (row == curr_row && col == curr_col);
                }
                while (!map.move(row, col));
            } else {
                return;
            }
        }else{
            return;
        }
    }

    @Override
    public boolean checkMove() {
        return map.canMoveBasic();
    }
}
