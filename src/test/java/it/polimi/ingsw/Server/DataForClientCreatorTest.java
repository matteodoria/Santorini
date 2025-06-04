package it.polimi.ingsw.Server;

import it.polimi.ingsw.Model.Map;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataForClientCreatorTest {

    @Test
    void getMapTokensForClient() {
        Map map=new Map(new ArrayList<Player>());
        DataForClientCreator dataForClientCreator = new DataForClientCreator(map);

        map.addToken(0,0,'m',1);
        map.addToken(1,1,'f',1);
        map.addToken(2,2,'m',2);
        map.addToken(3,3,'f',2);
        map.addToken(4,4,'m',3);

        assertEquals(dataForClientCreator.getMapTokensForClient(), "MT,m1,no,no,no,no" +
                ",no,f1,no,no,no" +
                ",no,no,m2,no,no" +
                ",no,no,no,f2,no" +
                ",no,no,no,no,m3");
    }

    @Test
    void getMapLevelsForClient() {
        Map map=new Map(new ArrayList<Player>());
        DataForClientCreator dataForClientCreator = new DataForClientCreator(map);

        map.setLookingAtRow(1);
        map.setLookingAtCol(1);

        map.build(0,0);
        map.build(0,0);
        map.build(0,0);
        map.build(0,0);

        map.build(1,0);
        map.build(1,0);
        map.build(1,0);

        map.build(0,1);
        map.build(0,1);

        map.build(0,2);

        assertEquals(dataForClientCreator.getMapLevelsForClient(), "ML,3,2,1,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
    }

    @Test
    void getMapDomesForClient() {
        Map map=new Map(new ArrayList<Player>());
        DataForClientCreator dataForClientCreator = new DataForClientCreator(map);

        map.setLookingAtRow(1);
        map.setLookingAtCol(1);

        map.build(0,0);
        map.build(0,0);
        map.build(0,0);
        map.build(0,0);

        map.buildDome(1,0);

        map.build(0,1);
        map.build(0,1);
        map.buildDome(0,1);

        map.build(0,2);
        map.buildDome(0,2);

        assertEquals(dataForClientCreator.getMapDomesForClient(), "MD,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
    }


}