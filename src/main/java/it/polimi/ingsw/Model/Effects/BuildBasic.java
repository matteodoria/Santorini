package it.polimi.ingsw.Model.Effects;



public class BuildBasic extends EffectOnBuild {

    public BuildBasic() {
        this.name = "BuildBasic";
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
    }
}
