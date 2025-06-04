package it.polimi.ingsw.Model.Effects;


public class BuildUp extends EffectOnBuild {

    public BuildUp() {
        this.name = "BuildUp";
    }

    @Override
    public void activateEffect() {
        int row, col;
        String s;
        String array[]= new String[2];
        do {
            handler.sendMessage("GP,4");
            s=handler.receiveMessage();
            array=s.split(",");
            row=Integer.parseInt(array[0]);
            col=Integer.parseInt(array[1]);
        }
        while(!map.build(row, col));
        if(map.checkChronoWin()){
            return;
        }
        if( map.getMap()[row][col].getLevel()<=2) {
            handler.sendMessage("GP,5");
            s = handler.receiveMessage();
            if (s.equals("Y") || s.equals("y")) {
                map.build(row, col);
                if(map.checkChronoWin()){
                    return;
                }
            } else {
                return;
            }
        }else{
            return;
        }
    }

}
