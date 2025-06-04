package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CellTest {


    /**
     * test for correct building (white box)
     */
    @Test
    public void buildHereTest(){
        Cell c1 = new Cell();
        assertEquals(0, (int) c1.getLevel());
        assertFalse(c1.isDome());
        c1.buildHere();
        assertEquals(1, (int) c1.getLevel());
        assertFalse(c1.isDome());
        c1.buildHere();
        assertEquals(2, (int) c1.getLevel());
        assertFalse(c1.isDome());
        c1.buildHere();
        assertEquals(3, (int) c1.getLevel());
        assertFalse(c1.isDome());
        c1.buildHere();
        assertEquals(3, (int) c1.getLevel());
        assertTrue(c1.isDome());
    }


    /**
     * test: building a dome in every level
     */
    @Test
    void buildDomeTest() {
        Cell c0 = new Cell();
        Cell c1 = new Cell();
        Cell c2 = new Cell();
        Cell c3 = new Cell();

        c1.buildHere();

        c2.buildHere();
        c2.buildHere();

        c3.buildHere();
        c3.buildHere();
        c3.buildHere();

        assertFalse(c0.isDome());
        c0.buildDome();
        assertTrue(c0.isDome());

        assertFalse(c1.isDome());
        c1.buildDome();
        assertTrue(c1.isDome());

        assertFalse(c2.isDome());
        c2.buildDome();
        assertTrue(c2.isDome());

        assertFalse(c3.isDome());
        c3.buildDome();
        assertTrue(c3.isDome());

    }

}