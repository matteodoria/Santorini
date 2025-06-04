package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class Map {

    /**
     * list of player in game
     */
    private ArrayList<Player> players;

    /**
     * the actual map with is 25 cell
     * 5x5 matrix of Cell elements
     */
    private Cell[][] map;

    /**
     * number of complete structures with a dome on the map
     * complete dome: level ==3 and dome == true
     */
    private Integer completeDomeNumber;

    /**
     * number of active players in game
     */
    private int activePlayers;


    /**
     * true when athena's power is active
     * when == true implies move calls moveNoUp
     */
    private boolean noUpConstraint;

    /**
     * contains the row of the cell we are currently looking at
     */
    private int lookingAtRow;

    /**
     * contains the column of the cell we are currently looking at
     */
    private int lookingAtCol;

    /**
     * kill manager, utility class
     */
    private KillManager killManager;

    /**
     * true when someone made a winning move
     */
    private boolean winner;

    /**
     * true when another God has activated the noUpConstraint
     */
    public boolean noUpByAnotherGod=false;

    /**
     * true when some token has been moved on the map
     */
    private boolean isChangedToken = false;
    /**
     * true when some level has changed on the map
     */
    private boolean isChangedBuild = false;

    //------GETTER & SETTER --------------------------------------------------------------------------------

    public boolean getNoUpConstraint() {
        return noUpConstraint;
    }
    public boolean isChangedToken() {
        return isChangedToken;
    }
    public void setChangedToken(boolean changed) {
        isChangedToken = changed;
    }
    public boolean isChangedBuild() {
        return isChangedBuild;
    }
    public void setChangedBuild(boolean changedBuild) {
        isChangedBuild = changedBuild;
    }
    public boolean isNoUpByAnotherGod() {
        return noUpByAnotherGod;
    }
    public void setNoUpByAnotherGod(boolean noUpByAnotherGod) {
        this.noUpByAnotherGod = noUpByAnotherGod;
    }
    public boolean isWinner() {
        return winner;
    }
    public void setWinner() {
        this.winner = true;
    }
    public int getActivePlayers() {
        return activePlayers;
    }
    public void setActivePlayers(int activePlayers) {
        this.activePlayers = activePlayers;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * standard builder
     * @param players list of players in the game
     */
    public Map(ArrayList<Player> players) {
        this.players=players;
        this.map= new Cell[5][5];
        this.completeDomeNumber = 0;
        this.noUpConstraint = false;
        this.killManager=new KillManager(players);
        this.winner=false;
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                map[i][j]= new Cell();
            }
        }
        this.activePlayers=players.size();
    }

    /**
     * method that adds a token
     * @param row target row
     * @param col target col
     * @param sex token sex
     * @param playerNum player number
     * @return true if the move was correct false if not
     */
    public boolean addToken(int row, int col, char sex, Integer playerNum) {
        if (map[row][col].getToken().equals("no")) {
            map[row][col].addToken(sex + playerNum.toString());
            return true;
        }
        return false;
    }


    /**
     * method that says if a cell is adjacent at the one we arw looking at
     *
     * @param row row of the cell we are checking
     * @param col column of the cell we are checking
     * @return true fi the cell is adjacent at the one we are looking at else returns false
     */
    public boolean isAdj(int row, int col) {
        if (row < 0 || col < 0 || row >= 5 || col >= 5) //out of bound
            return false;
        else if ((row == lookingAtRow || row == lookingAtRow + 1 || row == lookingAtRow - 1) && (col == lookingAtCol || col == lookingAtCol + 1 || col == lookingAtCol - 1) && !(row == lookingAtRow && col == lookingAtCol))
            return true;
        return false;
    }

    /**
     * sets winner if performing a winning move
     * @param row target cell row
     * @param col target cell col
     */
    private void checkWinningMove(int row, int col){
        if(map[row][col].getLevel()==3 && (map[row][col].getLevel() - map[lookingAtRow][lookingAtCol].getLevel() == 1))
            setWinner();
    }

    /**
     * method that moves a token in a target valid cell
     *
     * @param row target cell row
     * @param col target cell col
     */
    private void moveToken(int row, int col) {
        map[row][col].addToken(map[lookingAtRow][lookingAtCol].getToken());
        map[lookingAtRow][lookingAtCol].addToken("no");
    }

    /**
     * method for a limited movement action (athena on/prometheus on)
     *
     * @param row target cell row
     * @param col target cell col
     * @return true if the action was successful, else return false
     */
    public boolean moveNoUp(int row, int col) {
        if (isAdj(row, col) && map[row][col].getLevel() <= map[lookingAtRow][lookingAtCol].getLevel() && map[row][col].getToken().equals("no")) {
            moveToken(row, col);
            lookingAtRow = row;
            lookingAtCol = col;
            return true;
        } else return false;
    }

    /**
     * method for the standard movement action
     *
     * @param row target cell row
     * @param col target cell col
     * @return true if the action was successful, else return false
     */
    public boolean move(int row, int col) {
        if (noUpConstraint)
            return moveNoUp(row, col);
        else if (isAdj(row, col) && map[row][col].getLevel() <= map[lookingAtRow][lookingAtCol].getLevel() + 1 && map[row][col].getToken().equals("no") && !map[row][col].isDome()) {
            moveToken(row, col);
            checkWinningMove(row, col);
            lookingAtRow = row;
            lookingAtCol = col;
            return true;
        } else return false;
    }

    /**
     * standard method for building action
     *
     * @param row target cell row
     * @param col target cell col
     * @return true if the action has been completed correctly, else return false
     */
    public boolean build(int row, int col) {
        if (isAdj(row, col) && !map[row][col].isDome() && map[row][col].getToken().equals("no")) {
            map[row][col].buildHere();
            countCompleteDomes();
            return true;
        }
        return false;
    }

    /**
     * method for dome building
     *
     * @param row target cell row
     * @param col target cell col
     * @return true if the action has been completed correctly, else return false
     */
    public boolean buildDome(int row, int col) {
        if (isAdj(row, col) && !map[row][col].isDome() && map[row][col].getToken().equals("no")) {
            map[row][col].buildDome();
            countCompleteDomes();
            return true;
        }
        return false;
    }

    public int getLookingAtRow() {
        return lookingAtRow;
    }

    public void setLookingAtRow(int lookingAtRow) {
        this.lookingAtRow = lookingAtRow;
    }

    public int getLookingAtCol() {
        return lookingAtCol;
    }

    public void setLookingAtCol(int lookingAtCol) {
        this.lookingAtCol = lookingAtCol;
    }

    public Cell[][] getMap() {
        return map;
    }

    /**
     * method that activates athena on this map
     */
    public void activateNoUp() {
        this.noUpConstraint = true;
    }

    /**
     * method that deactivates athena on this map
     */
    public void deactivateNoUp() {
        this.noUpConstraint = false;
    }

    /**
     * method that sets the right position based on selected token
     * @param token selected token
     */
    public void alignPosition(String token){
        boolean stop = false;
        int i, j;
        for (i=0; i<5; i++){
            for(j=0; j<5; j++) {
                if (map[i][j].getToken().equals(token)) {
                    setLookingAtRow(i);
                    setLookingAtCol(j);
                    stop = true;
                    break;
                }
            }
            if(stop) break;
        }

    }

    /**
     * standard getter for complete domes in this game
     * @return number of complete domes
     */
    public Integer getCompleteDomeNumber() {
        return completeDomeNumber;
    }

    /**
     * counts number of complete domes
     */
    public void countCompleteDomes(){
        completeDomeNumber=0;
        for (int i=0; i<=4 ; i++){
            for(int j=0; j<=4; j++) {
                if (map[i][j].getLevel()==3 && map[i][j].isDome())
                    completeDomeNumber++;
            }
        }
        return;
    }

    /**
     * says if selected token can move basic move
     * @return true if can move, false if can't
     */
    public boolean canMoveBasic(){
        if(noUpConstraint)
            return canMoveNoUp();
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                if (isAdj(i, j) && !map[i][j].isDome() && map[i][j].getToken().equals("no") && map[i][j].getLevel()<=map[lookingAtRow][lookingAtCol].getLevel()+1){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * says if selected token can move no up movements
     * @return true if can move, false if can't
     */
    public boolean canMoveNoUp(){
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                if (isAdj(i, j) && !map[i][j].isDome() && map[i][j].getToken().equals("no") && map[i][j].getLevel()<=map[lookingAtRow][lookingAtCol].getLevel()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * says if selected token can move  with swap (don't care about token)
     * @return true if can move, false if can't
     */
    public boolean canMoveSwap(){
        String mytoken= map[lookingAtRow][lookingAtCol].getToken();
        if (noUpConstraint)
            return canMoveSwapNoUp();
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                String othertoken=map[i][j].getToken();
                if (isAdj(i, j) && !map[i][j].isDome() &&  map[i][j].getLevel()<=map[lookingAtRow][lookingAtCol].getLevel()+1 && mytoken.charAt(1)!=othertoken.charAt(1)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * says if selected token can move but not up with swap (don't care about token)
     * @return true if can move, false if can't
     */
    public boolean canMoveSwapNoUp(){
        String mytoken= map[lookingAtRow][lookingAtCol].getToken();
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                String othertoken=map[i][j].getToken();
                if (isAdj(i, j) && !map[i][j].isDome() &&  map[i][j].getLevel()<=map[lookingAtRow][lookingAtCol].getLevel()
                        && mytoken.charAt(1)!=othertoken.charAt(1)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * says if selected token can move pushing (don't care about token)
     * @return true if can move, false if can't
     */
    public boolean canMovePush(){
        String mytoken= map[lookingAtRow][lookingAtCol].getToken();
        if (noUpConstraint)
            return canMovePushNoUp();
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                String othertoken=map[i][j].getToken();
                if (isAdj(i, j) && !map[i][j].isDome() &&  map[i][j].getLevel()<=map[lookingAtRow][lookingAtCol].getLevel()+1) { // cella valida
                    if (map[i][j].getToken().equals("no"))
                        return true;
                    else if (mytoken.charAt(1)!=othertoken.charAt(1) &&  j + (j - lookingAtCol) >= 0 && j + (j - lookingAtCol) < 5 && i + (i - lookingAtCol) >= 0 && i + (i - lookingAtCol) < 5 && map[i + (i - lookingAtCol)][j + (j - lookingAtCol)].getToken().equals("no") && !map[i + (i - lookingAtCol)][j + (j - lookingAtCol)].isDome() ) //posso spingere
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * says if selected token can move but not up pushing (don't care about token)
     * @return true if can move, false if can't
     */
    public boolean canMovePushNoUp(){
        String mytoken= map[lookingAtRow][lookingAtCol].getToken();
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                String othertoken=map[i][j].getToken();
                if (isAdj(i, j) && !map[i][j].isDome() &&  map[i][j].getLevel()<=map[lookingAtRow][lookingAtCol].getLevel()) { // cella valida
                    if (map[i][j].getToken().equals("no"))
                        return true;
                    else if (mytoken.charAt(1)!=othertoken.charAt(1) && j + (j - lookingAtCol) >= 0 && j + (j - lookingAtCol) < 5 && i + (i - lookingAtCol) >= 0 && i + (i - lookingAtCol) < 5 && map[i + (i - lookingAtCol)][j + (j - lookingAtCol)].getToken().equals("no") && !map[i + (i - lookingAtCol)][j + (j - lookingAtCol)].isDome()) //posso spingere
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * method that swaps tokens on 2 cells
     * @param row target cell row
     * @param col target cell col
     * @return true if successful, false if not
     */
    public boolean swap(int row, int col){
        String mytoken= map[lookingAtRow][lookingAtCol].getToken();
        String othertoken=map[row][col].getToken();
        if (noUpConstraint)
            return swapNoUp(row, col);
        else if (isAdj(row, col) && map[row][col].getLevel() <= map[lookingAtRow][lookingAtCol].getLevel() + 1 &&  !map[row][col].isDome() &&  mytoken.charAt(1)!=othertoken.charAt(1)) {
            String tmp = map[row][col].getToken();
            moveToken(row, col);
            map[lookingAtRow][lookingAtCol].addToken(tmp);
            checkWinningMove(row, col);
            lookingAtRow = row;
            lookingAtCol = col;
            return true;
        } else return false;
    }

    /**
     * swap if can't move up
     * @param row target cell row
     * @param col target dell col
     * @return true if successful, false if not
     */
    public boolean swapNoUp(int row, int col){
        String mytoken= map[lookingAtRow][lookingAtCol].getToken();
        String othertoken=map[row][col].getToken();
        if (isAdj(row, col) && map[row][col].getLevel() <= map[lookingAtRow][lookingAtCol].getLevel() &&  !map[row][col].isDome() &&  mytoken.charAt(1)!=othertoken.charAt(1)) {
            String tmp = map[row][col].getToken();
            moveToken(row, col);
            map[lookingAtRow][lookingAtCol].addToken(tmp);
            lookingAtRow = row;
            lookingAtCol = col;
            return true;
        } else return false;
    }

    /**
     * push movement with no up constriction
     * @param row target cell row
     * @param col target cell col
     * @return true if successful, false if not
     */
    public boolean pushNoUp(int row, int col){
        String mytoken= map[lookingAtRow][lookingAtCol].getToken();
        String othertoken=map[row][col].getToken();
        if (isAdj(row, col) && map[row][col].getLevel() <= map[lookingAtRow][lookingAtCol].getLevel() &&  !map[row][col].isDome()){
            //se è libera è un movimento nu UP normale
            if (map[row][col].getToken().equals("no")){
                return moveNoUp(row, col);
            }
            //se non è libera:
            //se quella dietro è libera ed esiste spingo e libero la cella target poi muovo in quella che diventa una cella valida per un movimento normale (la cella dopo è valida se esiste, è vuota e forzabile)
            else if(mytoken.charAt(1)!=othertoken.charAt(1) && map[row+(row-lookingAtRow)][col+(col-lookingAtCol)].getToken().equals("no") && row+(row-lookingAtRow)>=0 && row+(row-lookingAtRow)<5 && col+(col-lookingAtCol)>=0 && col+(col-lookingAtCol)<5 && !map[row+(row-lookingAtRow)][col+(col-lookingAtCol)].isDome()){
                map[row+(row-lookingAtRow)][col+(col-lookingAtCol)].addToken(map[row][col].getToken());
                map[row][col].freeCell();
                return moveNoUp(row, col);
            }
            else return false;
        }
        return false;
    }

    /**
     * push movement
     * @param row target cell row
     * @param col target cell col
     * @return true if the move was successful, else if not
     */
    public boolean pushBasic(int row, int col){
        String mytoken= map[lookingAtRow][lookingAtCol].getToken();
        String othertoken=map[row][col].getToken();
        if (noUpConstraint)
            return pushNoUp(row, col);
        //se a meno del token è una mossa valida
        if (isAdj(row, col) && map[row][col].getLevel() <= map[lookingAtRow][lookingAtCol].getLevel() + 1 &&  !map[row][col].isDome()){
            //se è libera è un movimento normale
            if (map[row][col].getToken().equals("no")){
                return move(row, col);
            }
            //se non è libera:
            //se quella dietro è libera ed esiste spingo e libero la cella target poi muovo in quella che diventa una cella valida per un movimento normale (la cella dopo è valida se esiste, è vuota e forzabile)
            else if(row+(row-lookingAtRow)>=0 && row+(row-lookingAtRow)<5 && col+(col-lookingAtCol)>=0 && col+(col-lookingAtCol)<5 && mytoken.charAt(1)!=othertoken.charAt(1) && map[row+(row-lookingAtRow)][col+(col-lookingAtCol)].getToken().equals("no")  && !map[row+(row-lookingAtRow)][col+(col-lookingAtCol)].isDome()){
                map[row+(row-lookingAtRow)][col+(col-lookingAtCol)].addToken(map[row][col].getToken());
                map[row][col].freeCell();
                return move(row, col);
            }
            else return false;
        }
        return false;
    }

    /**
     * method to kill token on a target cell (you can't kill your own tokens)
     * @param row target cell row
     * @param col target cell col
     * @return true if you killed someone, false if you didn't kill someone
     */
    public boolean killHere(int row, int col){
        //se è una cella adiacente, non vuota, che contiene un token di un player diverso
        if (isAdj(row, col) && !map[row][col].getToken().equals("no") && (map[row][col].getToken().toCharArray()[1]!=map[lookingAtRow][lookingAtCol].getToken().toCharArray()[1])){
            killManager.killOne(map[row][col].getToken());
            map[row][col].freeCell();
            return true;
        }
        return false;
    }

    /**
     * method for destruction of a level
     * @param row target cell row
     * @param col target cell col
     * @return true if the action was successful, false if not
     */
    public boolean breakHere(int row, int col){
        // se è adiacente, non è senza livello e non ha un cupola ed è libera
        if (isAdj(row, col) && map[row][col].getLevel()>0 && !map[row][col].isDome() && map[row][col].getToken().equals("no")){
            map[row][col].destroyLevel();
            return true;
        }
        return false;
    }

    /**
     * method that aligns on the other token of the same player
     * @return true if found, false if not found
     */
    public boolean alignOnOtherWorker(){
        String otherToken;
        if (map[lookingAtRow][lookingAtCol].getToken().toCharArray()[0]=='m')
            otherToken="f";
        else otherToken="m";

        otherToken = otherToken + map[lookingAtRow][lookingAtCol].getToken().toCharArray()[1];

        boolean found = false;
        int i, j=0;
        for (i=0; i<5 && !found; i++){
            for(j=0; j<5 && !found; j++) {
                if (map[i][j].getToken().equals(otherToken))
                    found = true;
            }
        }
        if (found) {
            setLookingAtRow(i-1);
            setLookingAtCol(j-1);
        }

        return found;
    }

    /**
     * method that checks if selected worker can build
     * @return true if can build, false if not
     */
    public boolean canBuild(){
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                if (isAdj(i, j) && !map[i][j].isDome() &&  map[i][j].getToken().equals("no")){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method that turns adjacent enemies token to stone
     */
    public void toStone(){
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                if (map[i][j].getLevel()<map[lookingAtRow][lookingAtCol].getLevel()){
                    if (killHere(i,j)){
                        build(i,j);
                        setChangedBuild(true);
                        setChangedToken(true);
                    }
                }
            }
        }

        alignOnOtherWorker();
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                if (map[i][j].getLevel()<map[lookingAtRow][lookingAtCol].getLevel()){
                    if (killHere(i,j)){
                        build(i,j);
                        setChangedBuild(true);
                        setChangedToken(true);
                    }
                }
            }
        }

    }

    /**
     * method that controls if there is a block i can break
     * @return true if there is at least 1, false if there are no block i can break
     */
    public boolean atLeastOneBlockToBreak(){
        for (int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                if (isAdj(i,j) && map[i][j].getLevel()>=1 && !map[i][j].isDome()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method that checks Chrono condition of victory
     * @return true if successful, false if not
     */
    public boolean checkChronoWin() {
        if (completeDomeNumber == 5) {
            setWinner();
            for (Player player : players) {
                if (player.getGod().getName().equals("Chronus")) {
                    player.setHasWon(true);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method that checks number of cells where you can build
     * @return an ArrayList with all coordinates
     */
    public ArrayList<String> timesOfBuild() {
        ArrayList<String> coordinates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (isAdj(i, j) && !map[i][j].isDome() && map[i][j].getToken().equals("no")) {
                    coordinates.add(String.valueOf(i).concat(",").concat(String.valueOf(j)));
                }
            }
        } return coordinates;
    }


}





