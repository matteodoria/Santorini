package it.polimi.ingsw.Model.Effects;


public class Push extends EffectOnMovement {

    public Push() {
        this.name = "Push";
    }

    @Override
    public void activateEffect() {
        int row, col;
        String s;
        String array[]= new String[2];
        do {
            if(map.getNoUpConstraint())
                handler.sendMessage("GP,3.3.1");
            else
                handler.sendMessage("GP,3.3");
            s=handler.receiveMessage();
            array=s.split(",");
            row=Integer.parseInt(array[0]);
            col=Integer.parseInt(array[1]);
        }
        while(!map.pushBasic(row, col));


    }

    @Override
    public boolean checkMove() {
        return map.canMovePush();
    }
}
