package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.ClientData.ClientGameData;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class ClientGameDataTest {

    @Test
    void MTTest(){
        PrintWriter out = new PrintWriter(System.out);
        ClientGameData clientGameData = new ClientGameData(out);

        String update= "MT,m1,no,no,no,no" +
                ",no,f1,no,no,no" +
                ",no,no,m2,no,no" +
                ",no,no,no,f2,no" +
                ",no,no,no,no,m3";

        clientGameData.dataUpdate(update);

        assertEquals(clientGameData.getTokensOnMap()[0][0],"m1");
        assertEquals(clientGameData.getTokensOnMap()[0][1],"no");
        assertEquals(clientGameData.getTokensOnMap()[0][2],"no");
        assertEquals(clientGameData.getTokensOnMap()[0][3],"no");
        assertEquals(clientGameData.getTokensOnMap()[0][4],"no");

        assertEquals(clientGameData.getTokensOnMap()[1][1],"f1");
        assertEquals(clientGameData.getTokensOnMap()[2][2],"m2");
        assertEquals(clientGameData.getTokensOnMap()[3][3],"f2");
        assertEquals(clientGameData.getTokensOnMap()[4][4],"m3");

    }

    @Test
    void MLTest(){
        PrintWriter out=new PrintWriter (System.out);
        ClientGameData clientGameData = new ClientGameData(out);

        String update= "ML,3,2,1,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";

        clientGameData.dataUpdate(update);

        assertEquals(clientGameData.getLevelsOnMap()[0][0],"3");
        assertEquals(clientGameData.getLevelsOnMap()[0][1],"2");
        assertEquals(clientGameData.getLevelsOnMap()[0][2],"1");
        assertEquals(clientGameData.getLevelsOnMap()[0][3],"0");
        assertEquals(clientGameData.getLevelsOnMap()[0][4],"0");

        assertEquals(clientGameData.getLevelsOnMap()[1][0],"3");
        assertEquals(clientGameData.getLevelsOnMap()[2][2],"0");
        assertEquals(clientGameData.getLevelsOnMap()[3][3],"0");
        assertEquals(clientGameData.getLevelsOnMap()[4][4],"0");

    }

    @Test
    void MDTest(){
        PrintWriter out=new PrintWriter (System.out);
        ClientGameData clientGameData = new ClientGameData(out);

        String update= "MD,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";

        clientGameData.dataUpdate(update);

        assertEquals(clientGameData.getDomesOnMap()[0][0],"1");
        assertEquals(clientGameData.getDomesOnMap()[0][1],"1");
        assertEquals(clientGameData.getDomesOnMap()[0][2],"1");
        assertEquals(clientGameData.getDomesOnMap()[0][3],"0");
        assertEquals(clientGameData.getDomesOnMap()[0][4],"0");

        assertEquals(clientGameData.getDomesOnMap()[1][0],"1");
        assertEquals(clientGameData.getDomesOnMap()[2][2],"0");
        assertEquals(clientGameData.getDomesOnMap()[3][3],"0");
        assertEquals(clientGameData.getDomesOnMap()[4][4],"0");

    }

}