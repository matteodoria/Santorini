package it.polimi.ingsw.Server;

import it.polimi.ingsw.Model.Map;

public class DataForClientCreator {

    /**
     * game map
     */
    private Map map;


    public DataForClientCreator(Map map) {
        this.map = map;
    }

    /**
     * method that creates map data on token update for client, separated by ",", starts with MT
     * @return string as MT,m1,no,no,f2,f1,no...
     */
    public String getMapTokensForClient(){
        String out= "MT";
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                out = out + "," + map.getMap()[i][j].getToken();
            }
        }
        return out;
    }

    /**
     * method that creates map data on levels update for client, separated by ",", starts with ML, domes not included, values:{0,1,2,3}
     * @return string as ML,0,0,2,3,1,0,..
     */
    public String getMapLevelsForClient(){
        String out= "ML";
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                out = out + "," + map.getMap()[i][j].getLevel().toString();
            }
        }
        return out;
    }

    /**
     * method that creates map data on domes update for client, separated by ",", starts with MD, values{0,1}
     * @return string as MD,0,0,1,0...
     */
    public String getMapDomesForClient(){
        String out= "MD";
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(map.getMap()[i][j].isDome()) {
                    out = out + ",1";
                }
                else
                    out = out + ",0";
            }
        }
        return out;
    }

}
