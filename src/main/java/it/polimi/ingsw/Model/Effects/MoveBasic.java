package it.polimi.ingsw.Model.Effects;


public class MoveBasic extends EffectOnMovement{

    public MoveBasic() {
        this.name = "MoveBasic";
    }

    @Override
    public void activateEffect() {
        int row, col;
        String s;
        String array[]= new String[2];
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
        }


    @Override
    public boolean checkMove() {
        return map.canMoveBasic();
    }
}
