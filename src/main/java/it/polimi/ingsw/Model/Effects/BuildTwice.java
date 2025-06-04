package it.polimi.ingsw.Model.Effects;

import it.polimi.ingsw.Server.DataForClientCreator;

public class BuildTwice extends EffectOnBuild {

    public BuildTwice() {
        this.name = "BuildTwice";
    }

    @Override
    public void activateEffect() {
        DataForClientCreator messageCreator = new DataForClientCreator(map);
        int first_row, first_col, row, col;
        String s;
        String array[]= new String[2];
        do {
            handler.sendMessage("GP,4");
            s=handler.receiveMessage();
            array=s.split(",");
            first_row=Integer.parseInt(array[0]);
            first_col=Integer.parseInt(array[1]);
        }
        while(!map.build(first_row, first_col));
        handler.sendMessage(messageCreator.getMapLevelsForClient());
        handler.sendMessage(messageCreator.getMapDomesForClient());
        handler.sendMessage("UP");
        if(map.checkChronoWin()){
            return;
        }

        String check= String.valueOf(first_row).concat(",").concat(String.valueOf(first_col));
        if(map.timesOfBuild().size()!=1 || (map.timesOfBuild().size()==1 && !map.timesOfBuild().contains(check))) {
            handler.sendMessage("GP,5");
            s = handler.receiveMessage();
            if (s.equals("Y") || s.equals("y")) {
                do {
                    do {
                        handler.sendMessage("GP,4");
                        s = handler.receiveMessage();
                        array = s.split(",");
                        row = Integer.parseInt(array[0]);
                        col = Integer.parseInt(array[1]);
                    }
                    while (row == first_row && col == first_col);
                }
                while (!map.build(row, col));
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
