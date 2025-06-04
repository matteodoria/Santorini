package it.polimi.ingsw.Model;


import it.polimi.ingsw.Model.Map;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Server.Connection.ServerClientHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class MapTest {

    @Test
    void isAdj() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.setLookingAtRow(0);
        m1.setLookingAtCol(0);

        assertFalse(m1.isAdj(-1, 0));
        assertFalse(m1.isAdj(3,3));
        assertTrue(m1.isAdj(1,1));

        m1.setLookingAtCol(4);
        m1.setLookingAtRow(4);
        assertFalse(m1.isAdj(5,5));
        assertFalse(m1.isAdj(4,4));
    }

    @Test
    void moveNoUp() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);
        m1.addToken(0,0, 'm', 1);
        m1.addToken(1,0, 'm', 1);

        m1.setLookingAtRow(0);
        m1.setLookingAtCol(0);
        m1.build(1,1);

        assertFalse(m1.moveNoUp(1,1)); //non vado su

        assertTrue(m1.moveNoUp(0,1)); //corretto
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

        assertFalse(m1.moveNoUp(3,3)); //troppo lontano
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

        assertFalse(m1.moveNoUp(1,0)); //no dove è occupato
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

    }

    @Test
    void move() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.setLookingAtRow(0);
        m1.setLookingAtCol(0);
        m1.addToken(0,0, 'm', 1);
        m1.addToken(0,2, 'm', 1);
        m1.build(1,1);

        assertTrue(m1.move(1,1)); //mi posso muover su di 1
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(1,m1.getLookingAtRow());

        assertTrue(m1.move(0,1)); //posso scendere di 1
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

        assertFalse(m1.move(-1,1)); //no in celle non esistenti
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

        m1.build(1,1);

        assertFalse(m1.move(1,1)); //no salire di + di 1
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

        m1.buildDome(0, 0); //no dove c'è la cupola
        assertFalse(m1.move(0,0));
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

        assertFalse(m1.move(0,1)); //no dove sono già
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

        assertFalse(m1.move(0,2)); //no dove è occupato
        assertEquals(1, m1.getLookingAtCol());
        assertEquals(0,m1.getLookingAtRow());

        m1.build(1,2);
        m1.activateNoUp();
        assertFalse(m1.move(1,2)); //non su con atena attiva


    }

    @Test
    void build() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.setLookingAtRow(0);
        m1.setLookingAtCol(0);
        m1.addToken(0,0, 'm', 1);
        m1.addToken(0,1, 'm', 1);

        assertFalse(m1.build(0,0)); // non dove sono

        assertTrue(m1.build(1,1));
        assertTrue(m1.build(1,1));
        assertTrue(m1.build(1,1));
        assertTrue(m1.build(1,1));

        assertFalse(m1.build(1,1)); //non dove è completo

        assertFalse(m1.build(0,1)); //non dove è occupato

    }


    @Test
    void addToken() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.setLookingAtRow(0);
        m1.setLookingAtCol(0);
        assertTrue(m1.addToken(0,0, 'm', 1));

        assertFalse(m1.addToken(0,0,'m',1));
    }

    @Test
    void buildDome() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.setLookingAtRow(0);
        m1.setLookingAtCol(0);
        m1.addToken(0,0, 'm', 1);
        m1.addToken(0,1, 'm', 1);

        assertFalse(m1.buildDome(0,0));
        assertFalse(m1.buildDome(0,1));
        assertTrue(m1.buildDome(1,1));

    }

    @Test
    void canMoveBasic() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(1,1,'m', 1);
        m1.setLookingAtCol(1);
        m1.setLookingAtRow(1);
        assertTrue(m1.canMoveBasic());

            m1.build(0,0);
            m1.build(0,1);
            m1.build(0,2);
            m1.build(1,0);
            m1.build(1,2);
            m1.build(2,0);
            m1.build(2,1);
            m1.build(2,2);

        assertTrue(m1.canMoveBasic());
        m1.activateNoUp();
        assertFalse(m1.canMoveBasic());
        m1.deactivateNoUp();

            m1.build(0,0);
            m1.build(0,1);
            m1.build(0,2);
            m1.build(1,0);
            m1.build(1,2);

        assertTrue(m1.canMoveBasic());

            m1.build(2,0);
            m1.build(2,1);
            m1.build(2,2);

            assertFalse(m1.canMoveBasic());
        m1.activateNoUp();
        assertFalse(m1.canMoveBasic());
        m1.deactivateNoUp();
    }

    @Test
    void canMoveBasic2() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(1,1,'m', 1);
        m1.setLookingAtCol(1);
        m1.setLookingAtRow(1);
        assertTrue(m1.canMoveBasic());

        m1.addToken(0,0,'m', 1);
        m1.addToken(0,1,'m', 1);
        m1.addToken(0,2,'m', 1);
        m1.addToken(1,0,'m', 1);
        m1.addToken(1,2,'m', 1);
        m1.addToken(2,0,'m', 1);
        m1.addToken(2,1,'m', 1);
        m1.addToken(2,2,'m', 1);

        assertFalse(m1.canMoveBasic());

    }

    @Test
    void canMoveNoUp() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(1,1,'m', 1);
        m1.setLookingAtCol(1);
        m1.setLookingAtRow(1);
        assertTrue(m1.canMoveNoUp());

        m1.build(0,0);
        m1.build(0,1);
        m1.build(0,2);
        m1.build(1,0);

        assertTrue(m1.canMoveNoUp());

        m1.build(1,2);
        m1.build(2,0);
        m1.build(2,1);
        m1.build(2,2);

        assertFalse(m1.canMoveNoUp());
    }

    @Test
    void canMoveSwap() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(1,1,'m', 1);
        m1.setLookingAtCol(1);
        m1.setLookingAtRow(1);
        assertTrue(m1.canMoveSwap());

        m1.build(0,0);
        m1.build(0,1);
        m1.build(0,2);
        m1.build(1,0);
        m1.build(1,2);
        m1.build(2,0);
        m1.build(2,1);
        m1.build(2,2);

        m1.build(0,0);
        m1.build(0,1);
        m1.build(0,2);
        m1.build(1,0);
        m1.build(1,2);
        m1.build(2,0);
        m1.build(2,1);

        m1.addToken(2,2,'m', 2);

        assertTrue(m1.canMoveSwap());
    }

    @Test
    void swap() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);
        

        m1.addToken(1,1,'m', 1);
        m1.setLookingAtCol(1);
        m1.setLookingAtRow(1);

        assertTrue(m1.swap(1,2));
        assertTrue(m1.getMap()[1][1].getToken().equals("no"));
        assertTrue(m1.getMap()[1][2].getToken().equals("m1"));

        m1.build(1,1);
        m1.addToken(1,1,'f',2);
        assertTrue(m1.swap(1,1));
        assertTrue(m1.getMap()[1][1].getToken().equals("m1"));
        assertTrue(m1.getMap()[1][2].getToken().equals("f2"));

        m1.build(2,1);
        m1.build(2,1);
        m1.addToken(2,1,'f',1);
        assertFalse(m1.swap(1,1));


    }

    @Test
    void swapNoUp() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(1,1,'m', 1);
        m1.setLookingAtCol(1);
        m1.setLookingAtRow(1);

        assertTrue(m1.swapNoUp(1,2));
        assertTrue(m1.getMap()[1][1].getToken().equals("no"));
        assertTrue(m1.getMap()[1][2].getToken().equals("m1"));

        m1.build(1,1);
        m1.addToken(1,1,'f',1);
        assertFalse(m1.swapNoUp(1,1));
    }


    @Test
    void canMovePush() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(0,0,'m', 1);
        m1.setLookingAtCol(0);
        m1.setLookingAtRow(0);
        assertTrue(m1.canMovePush());


        m1.buildDome(1,0);
        m1.buildDome(1,1);


        m1.addToken(0,1,'m', 2);

        assertTrue(m1.canMovePush());

        m1.setLookingAtCol(1);

        m1.buildDome(0,2);

        m1.setLookingAtCol(0);

        assertFalse(m1.canMovePush());

        //------------------------------------------

        Map m2 = new Map(players);

        m2.addToken(0,0,'m', 1);
        m2.setLookingAtCol(0);
        m2.setLookingAtRow(0);
        assertTrue(m2.canMovePush());

        m2.buildDome(1,0);
        m2.buildDome(1,1);

        m2.build(0,1);
        m2.build(0,1);

        m1.addToken(0,1,'m', 1);

        assertFalse(m2.canMovePush());

        //-----------------------------------------------

        Map m3 = new Map(players);

        m3.addToken(0,0,'m', 1);
        m3.setLookingAtCol(0);
        m3.setLookingAtRow(0);
        assertTrue(m3.canMovePush());

        m3.buildDome(1,0);
        m3.buildDome(1,1);

        m3.build(0,1);

        m1.addToken(0,1,'m', 1);

        assertTrue(m3.canMovePush());
    }

    @Test
    void canMovePushNoUp() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(0,0,'m', 1);
        m1.setLookingAtCol(0);
        m1.setLookingAtRow(0);
        assertTrue(m1.canMovePushNoUp());


        m1.buildDome(1,0);
        m1.buildDome(1,1);


        m1.addToken(0,1,'m', 2);

        assertTrue(m1.canMovePushNoUp());

        m1.setLookingAtCol(1);

        m1.buildDome(0,2);

        m1.setLookingAtCol(0);

        assertFalse(m1.canMovePushNoUp());

        //------------------------------------------

        Map m2 = new Map(players);

        m2.addToken(0,0,'m', 1);
        m2.setLookingAtCol(0);
        m2.setLookingAtRow(0);
        assertTrue(m2.canMovePushNoUp());

        m2.buildDome(1,0);
        m2.buildDome(1,1);

        m2.build(0,1);
        m2.build(0,1);

        m1.addToken(0,1,'m', 1);

        assertFalse(m2.canMovePushNoUp());

        //-----------------------------------------------

        Map m3 = new Map(players);

        m3.addToken(0,0,'m', 1);
        m3.setLookingAtCol(0);
        m3.setLookingAtRow(0);
        assertTrue(m3.canMovePushNoUp());

        m3.buildDome(1,0);
        m3.buildDome(1,1);

        m3.build(0,1);

        m1.addToken(0,1,'m', 1);

        assertFalse(m3.canMovePushNoUp());
    }

    @Test
    void killHere() {
        ArrayList<Player> players = new ArrayList();
        ServerClientHandler serverClientHandler;
        try {
            serverClientHandler=new ServerClientHandler(new Socket());
            Player p2=new Player(2, serverClientHandler);
            players.add(p2);

            Map m1 = new Map(players);

            m1.addToken(0,0,'m', 1);
            m1.setLookingAtCol(0);
            m1.setLookingAtRow(0);

            m1.addToken(0,1,'f', 1);

            assertFalse(m1.killHere(0,1));

            m1.build(1,0);
            m1.addToken(1,0,'m', 2);
            assertTrue(m1.killHere(1,0));

            m1.addToken(3,3,'f', 2);
            assertFalse(m1.killHere(3,3));

            assertFalse(p2.isAliveM());
            assertTrue(p2.isAliveF());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void breakHere() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(0,0,'m', 1);
        m1.setLookingAtCol(0);
        m1.setLookingAtRow(0);

        m1.build(0,1);
        m1.build(0,1);

        m1.build(1,0);
        m1.buildDome(1,0);

        assertFalse(m1.breakHere(1,0));
        assertFalse(m1.breakHere(1,1));

        assertTrue(m1.breakHere(0,1));

        assertEquals(m1.getMap()[0][1].getLevel().intValue(), 1);
        assertEquals(m1.getMap()[1][0].getLevel().intValue(), 1);
    }

    @Test
    void pushNoUp() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(0,0,'m', 1);
        m1.setLookingAtCol(0);
        m1.setLookingAtRow(0);

        assertTrue(m1.pushNoUp(0,1));

        m1.addToken(0,2,'f', 2);
        assertTrue(m1.pushNoUp(0,2));
        assertEquals(m1.getMap()[0][2].getToken(), "m1");
        assertEquals(m1.getMap()[0][3].getToken(), "f2");

        m1.setLookingAtCol(3);
        m1.buildDome(0,4);
        m1.setLookingAtCol(2);
        assertFalse(m1.pushNoUp(0,3));

        m1.build(1,2);
        m1.addToken(0,2,'f', 1);
        assertFalse(m1.pushNoUp(1,2));
    }

    @Test
    void pushBasic() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(0,0,'m', 1);
        m1.setLookingAtCol(0);
        m1.setLookingAtRow(0);

        assertTrue(m1.pushBasic(0,1));

        m1.addToken(0,2,'f', 2);
        assertTrue(m1.pushBasic(0,2));
        assertEquals(m1.getMap()[0][2].getToken(), "m1");
        assertEquals(m1.getMap()[0][3].getToken(), "f2");

        m1.setLookingAtCol(3);
        m1.buildDome(0,4);
        m1.setLookingAtCol(2);
        assertFalse(m1.pushBasic(0,3));

        m1.build(1,2);
        m1.addToken(0,2,'f', 1);
        assertTrue(m1.pushBasic(1,2));
    }

    @Test
    void alignOnOtherWorker(){
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(0,0,'m', 1);
        m1.setLookingAtCol(0);
        m1.setLookingAtRow(0);

        m1.addToken(2,1,'m', 1);
        m1.addToken(2,2,'m', 2);
        m1.addToken(2,3,'f', 2);

        assertFalse(m1.alignOnOtherWorker());
        assertEquals(m1.getLookingAtRow(), 0);
        assertEquals(m1.getLookingAtCol(), 0);

        m1.addToken(3,3,'f', 1);
        assertTrue(m1.alignOnOtherWorker());
        assertEquals(m1.getLookingAtRow(), 3);
        assertEquals(m1.getLookingAtCol(), 3);

        assertTrue(m1.alignOnOtherWorker());
        assertEquals(m1.getLookingAtRow(), 0);
        assertEquals(m1.getLookingAtCol(), 0);
    }

    @Test
    void canBuild() {
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(0,0,'m', 1);
        m1.setLookingAtCol(0);
        m1.setLookingAtRow(0);

        assertTrue(m1.canBuild());
        m1.addToken(0,1,'m', 1);
        m1.addToken(1,0,'m', 2);
        m1.addToken(1,1,'f', 2);
        assertFalse(m1.canBuild());

        m1.addToken(4,4,'m', 1);
        m1.setLookingAtCol(4);
        m1.setLookingAtRow(4);

        m1.buildDome(4,3);
        m1.buildDome(3,4);
        assertTrue(m1.canBuild());
        m1.build(3,3);
        assertTrue(m1.canBuild());
        m1.build(3,3);
        assertTrue(m1.canBuild());
        m1.build(3,3);
        assertTrue(m1.canBuild());
        m1.build(3,3);
        assertFalse(m1.canBuild());
    }

    @Test
    void toStone() {
        ArrayList<Player> players = new ArrayList();
        ServerClientHandler serverClientHandler;
        try {
            serverClientHandler=new ServerClientHandler(new Socket());
            Player p2=new Player(2, serverClientHandler);
            players.add(p2);

            Map m1 = new Map(players);

            m1.addToken(0,0,'m', 1);
            m1.setLookingAtCol(0);
            m1.setLookingAtRow(0);
            m1.addToken(3,3,'f', 1);

            m1.build(1,1);
            m1.build(0,1);
            m1.build(0,1);
            m1.addToken(1,0,'m',2);
            m1.addToken(0,1,'m',2);
            m1.addToken(1,1,'m',2);

            m1.addToken(3,2,'m',2);
            m1.addToken(3,4,'m',1);

            m1.toStone();

            assertEquals("m2", m1.getMap()[0][1].getToken());
            assertEquals("m2", m1.getMap()[1][1].getToken());
            assertEquals("no", m1.getMap()[1][0].getToken());
            assertEquals(1, m1.getMap()[1][0].getLevel().intValue());

            assertEquals("no", m1.getMap()[3][2].getToken());
            assertEquals(1, m1.getMap()[3][2].getLevel().intValue());

            assertEquals("m1", m1.getMap()[3][4].getToken());
            assertEquals(0, m1.getMap()[3][4].getLevel().intValue());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void atLeastOneBlockToBreak(){
        ArrayList<Player> players = new ArrayList();
        Map m1 = new Map(players);

        m1.addToken(0,0,'m', 1);
        m1.alignPosition("m1");
        assertEquals(m1.getLookingAtCol(),0);
        assertEquals(m1.getLookingAtRow(),0);

        assertFalse(m1.atLeastOneBlockToBreak());

        m1.build(0,1);
        assertTrue(m1.atLeastOneBlockToBreak());

        m1.build(0,1);
        assertTrue(m1.atLeastOneBlockToBreak());

        m1.build(0,1);
        assertTrue(m1.atLeastOneBlockToBreak());

        m1.build(0,1);
        assertFalse(m1.atLeastOneBlockToBreak());

        assertTrue(m1.build(1,1));
        //m1.addToken(1,1,'f',2);
        assertTrue(m1.atLeastOneBlockToBreak());

    }
}