package it.polimi.ingsw.Model.Effects;


public class DomeMaster extends EffectOnBuild {

    public DomeMaster() {
        this.name = "DomeMaster";
    }

    @Override
    public void activateEffect() {
        int row, col;
        String s;
        String array[] = new String[2];
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
            while (!map.buildDome(row, col));
        } else {
            do {
                handler.sendMessage("GP,4");
                s = handler.receiveMessage();
                array = s.split(",");
                row = Integer.parseInt(array[0]);
                col = Integer.parseInt(array[1]);
            }
            while (!map.build(row, col));
        }
    }

}