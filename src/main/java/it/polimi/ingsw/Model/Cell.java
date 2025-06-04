package it.polimi.ingsw.Model;

/**
 * class that represents a cell on the map
 */
public class Cell {

    /**
     * token in the cell can assume values:
     * no: if empty
     * m/f + playerNumber if contains a token; fore example m1 is male token of the 1st player
     */
    private String token;

    /**
     * level of the tower in this cell (from 0 to 3)
     */
    private Integer level;

    /**
     * is true if there's a dome in this cell
     */
    private boolean dome;

    /**
     * standard builder for an empty cell at the beginning of the game
     */
    public Cell() {
        this.token="no";
        this.level=0;
        this.dome=false;
    }

    /**
     * standard getter for token in this cell
     * @return "no" if the cell is empty or the token inside as: "m/f + playerNumber"
     */
    public String getToken() {
        return token;
    }

    /**
     * setter for adding a token in this cell
     * @param token as "m/f + playerNumber"
     */
    public void addToken(String token) {
        this.token = token;
    }

    /**
     * setter for a free cell
     */
    public void freeCell(){
        this.token="no";
    }

    /**
     * getter for the building level
     * @return an integer from 0 to 3
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * method that builds a level in this cell
     */
    public void addLevel() {
        this.level ++;
    }

    /**
     * method that destroy a level of the building
     */
    public void destroyLevel() {
        this.level --;
    }

    /**
     * method thar says if there's a dome or not
     * @return true if there's a dome, false if not
     */
    public boolean isDome() {
        return dome;
    }

    /**
     * method that builds a dome in this cell
     */
    public void buildDome() {
        this.dome = true;
    }

    /**
     * method for building in this cell
     */
    public void buildHere(){
        if (level<3)
            addLevel();
        else buildDome();
    }

}
