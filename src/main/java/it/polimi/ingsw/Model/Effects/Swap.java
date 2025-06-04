package it.polimi.ingsw.Model.Effects;


public class Swap extends EffectOnMovement {

    public Swap() {
        this.name = "Swap";
    }

    @Override
    public void activateEffect() {
        int row, col;
        String s;
        String array[]= new String[2];
        do {
            if(map.getNoUpConstraint())
                handler.sendMessage("GP,3.2.1");
            else
                handler.sendMessage("GP,3.2");
            s=handler.receiveMessage();
            array=s.split(",");
            row=Integer.parseInt(array[0]);
            col=Integer.parseInt(array[1]);
        }
        while(!map.swap(row, col));
    }

    @Override
    public boolean checkMove() {
        return map.canMoveSwap();
    }

}
