package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Gods.DeckGods;
import it.polimi.ingsw.Model.Gods.God;
import it.polimi.ingsw.Model.Map;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Turn;
import it.polimi.ingsw.Server.Connection.ServerClientHandler;
import it.polimi.ingsw.Server.DataForClientCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class GameController {

    /**
     * players in this game
     */
    private ArrayList<Player> players = new ArrayList<>();

    private int currentPlayer;

    /**
     * list of gods in this game
     */
    private DeckGods deckGods;

    /**
     * map of this game
     */
    private Map map;

    private static Integer noWinner = 100;
    /**
     * winner of this game
     */
    private Integer winner = noWinner;

    /**
     * message creator for clients
     */
    private DataForClientCreator messageCreator;

    /**
     * server-client handlers to send and receive message
     */
    private ArrayList<ServerClientHandler> clientHandlers;

    /**
     * Constructor
     * @param clients list of client handlers used for communication
     */
    public GameController(ArrayList<ServerClientHandler> clients) {
        clientHandlers = clients;
        for(int i=0; i<clients.size(); i++)
            players.add(new Player(i, clients.get(i)));
        this.map=new Map(players);
        this.messageCreator= new DataForClientCreator(map);
        for(int i=0; i<clients.size(); i++)
            players.get(i).setTurn(new Turn(map, clientHandlers, messageCreator, players.get(i)));
        deckGods = new DeckGods();
    }


    /**
     * method that checks the presence of a String in a String Array
     * @param s string used for check
     * @param arr array used for check
     * @return true if it is contained
     */
    private boolean isInArray(String s, String arr[]){
        for(int i=0; i<arr.length; i++)
            if(s.equals(arr[i]))
                return true;
        return false;
    }

    /**
     * method that check whether there is a god which is not available for the game
     * @param arr array of gods to check
     * @param deckGods containing the number of players a god can be used
     * @return true if there is god not available
     */
    private boolean checkGodNotAvailable(String arr[], DeckGods deckGods){
        for(int i=0; i<arr.length; i++){
            if(!deckGods.getGod(Integer.valueOf(arr[i])).getPlayersInGame().contains(players.size())) {
                clientHandlers.get(0).sendMessage("GP,0.2");
                return true;
            }
        }
        return false;
    }

    /**
     * method that checks whether the array has all different values and
     * all values inside the bound
     * @param arr array used for check
     * @param bound number of Gods available
     * @return false if the array contains all different values inside the boundaries
     */
    private boolean checkArray(String arr[], int bound){
        for(int i=0; i<arr.length; i++){
            if(Integer.parseInt(arr[i])<=0 || Integer.parseInt(arr[i])>bound) {
                return true;
            }
            for(int j=i+1; j<arr.length; j++)
                if(arr[i]==arr[j]) {
                    return true;
                }
        }
        return false;
    }

    /**
     * method that control the gods selection for the game
     * @throws NoSuchElementException when a client is disconnected
     */
    private void godsSelection() throws NoSuchElementException {
        sendBroadcast("GP,11.2");
        String gods[];
        String selection;

        //creating the message to be sent
        String deckGodsMessage = "";
        for(int i=1; i<=deckGods.size(); i++){
            deckGodsMessage += i + ". "
                            + deckGods.getGod(i).getName().toUpperCase()
                            + (deckGods.getGod(i).getPlayersInGame().contains(players.size()) ? "": " (NOT AVAILABLE FOR THIS GAME!)")
                            + ": " + deckGods.getGod(i).getDescription() + "*";
        }
        clientHandlers.get(0).sendMessage("WP,"+deckGodsMessage);

        do {
            if (players.size() == 2) {
                clientHandlers.get(0).sendMessage("GP,0.0");
            } else if (players.size() == 3) {
                clientHandlers.get(0).sendMessage("GP,0.1");
            }
            selection = clientHandlers.get(0).receiveMessage();
            gods = selection.split(",");
        } while(checkArray(gods, deckGods.size()) || checkGodNotAvailable(gods, deckGods));

        clientHandlers.get(0).sendMessage("GP,10.1");

        ArrayList<String> godsList= new ArrayList<>();
        ArrayList<God> godsInGame = new ArrayList<>();
        for(int i=0; i<gods.length; i++){
            godsInGame.add(deckGods.getGod(Integer.parseInt(gods[i])));
            godsList.add(gods[i]);
        }
        for(int i=1; i<clientHandlers.size(); i++){
            String message = "";
            for(int j=0; j<godsInGame.size(); j++){
                message += godsList.get(j) + ". " + godsInGame.get(j).getName().toUpperCase() +
                        ": " + godsInGame.get(j).getDescription() + "*";
            }
            do {
                clientHandlers.get(i).sendMessage("WP," + message);
                clientHandlers.get(i).sendMessage("GP,1");
                selection = clientHandlers.get(i).receiveMessage();
            } while(!isInArray(selection, Arrays.copyOf(godsList.toArray(),godsList.size(), String[].class)));
            players.get(i).setGod(deckGods.getGod(Integer.parseInt(selection)));
            {
                players.get(i).getGod().getEffectBeforeMovement().setMap(this.map);
                players.get(i).getGod().getEffectBeforeMovement().setServerClientHandler(clientHandlers.get(i));
                players.get(i).getGod().getEffectOnMovement().setMap(this.map);
                players.get(i).getGod().getEffectOnMovement().setServerClientHandler(clientHandlers.get(i));
                players.get(i).getGod().getEffectOnBuild().setMap(this.map);
                players.get(i).getGod().getEffectOnBuild().setServerClientHandler(clientHandlers.get(i));
                players.get(i).getGod().getEffectOnEndTurn().setMap(this.map);
                players.get(i).getGod().getEffectOnEndTurn().setServerClientHandler(clientHandlers.get(i));
            }
            godsInGame.remove(deckGods.getGod(Integer.parseInt(selection)));
            godsList.remove(selection);
            if(i!=clientHandlers.size()-1)
                clientHandlers.get(i).sendMessage("GP,10.1");
        }
        players.get(0).setGod(godsInGame.get(0));
        players.get(0).getGod().getEffectBeforeMovement().setMap(this.map);
        players.get(0).getGod().getEffectBeforeMovement().setServerClientHandler(clientHandlers.get(0));
        players.get(0).getGod().getEffectOnMovement().setMap(this.map);
        players.get(0).getGod().getEffectOnMovement().setServerClientHandler(clientHandlers.get(0));
        players.get(0).getGod().getEffectOnBuild().setMap(this.map);
        players.get(0).getGod().getEffectOnBuild().setServerClientHandler(clientHandlers.get(0));
        players.get(0).getGod().getEffectOnEndTurn().setMap(this.map);
        players.get(0).getGod().getEffectOnEndTurn().setServerClientHandler(clientHandlers.get(0));
        for(int i=0; i<clientHandlers.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                clientHandlers.get(i).sendMessage("PG," + (j+1) + "," + players.get(j).getGod().getName().toUpperCase()
                                                    + "," + players.get(j).getGod().getDescription());
            }
        }
        sendBroadcast("GR");
    }

    /**
     * method that control the first workers placement
     * @throws NoSuchElementException when a client is disconnected
     */
    private void workersOnMap() throws NoSuchElementException{
        sendBroadcast("GP,11.3");
        String coordinate;
        String xy[];
        for(int j=1; j<clientHandlers.size(); j++)
            clientHandlers.get(j).sendMessage("GP,10.2");
        clientHandlers.get(0).sendMessage(messageCreator.getMapTokensForClient());
        clientHandlers.get(0).sendMessage("UP");
        for (int i = 0; i < clientHandlers.size(); i++) {
            do {
                clientHandlers.get(i).sendMessage("GP,8");
                coordinate = clientHandlers.get(i).receiveMessage();
                xy = coordinate.split(",");
            } while (!map.addToken(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]), 'm', i + 1));
            do {
                clientHandlers.get(i).sendMessage("GP,9");
                coordinate = clientHandlers.get(i).receiveMessage();
                xy = coordinate.split(",");
            } while (!map.addToken(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]), 'f', i + 1));
            for (int j = 0; j < clientHandlers.size(); j++) {
                clientHandlers.get(j).sendMessage(messageCreator.getMapTokensForClient());
                clientHandlers.get(j).sendMessage("UP");
            }
            if (i != clientHandlers.size() - 1)
                clientHandlers.get(i).sendMessage("GP,10.2");
        }
    }

    /**
     * method that allows to communicate with all the clients
     * @param s string to be sent
     */
    private void sendBroadcast(String s){
        for(ServerClientHandler handler : clientHandlers)
            handler.sendMessage(s);
    }

    /**
     * method that manage the winning/losing communication
     */
    private void setWinnerAndLoser(){
        for(Player player: players){
            if(player.getHasWon()){
                player.getHandler().sendMessage("WL,1");
            }else{
                player.getHandler().sendMessage("WL,0");
            }
        }
    }

    /**
     * method that control the game
     */
    public void startGame(){
        sendBroadcast("GP,11.0");
        godsSelection();
        workersOnMap();
        sendBroadcast("GP,11.1");
        while(!map.isWinner()){
            if(players.get(currentPlayer).getIsActive()) {
                System.out.println("Sta giocando il #"+currentPlayer);
                for (int i = 0; i < players.size(); i++) {
                    if (i == currentPlayer)
                        players.get(i).getHandler().sendMessage("GP,12");
                    else
                        players.get(i).getHandler().sendMessage("GP,6");
                }
                players.get(currentPlayer).getTurn().play();
                if (map.isWinner()) {
                    setWinnerAndLoser();
                    sendBroadcast("EXIT");
                    break;
                }
            }
            nextPlayer();
        }
    }

    /**
     * method that finds next player and if only one is active
     * sets him as the winner of the game
     */
    public void nextPlayer(){
        System.out.println("Num giocatori attivi: "+map.getActivePlayers());
        if(map.getActivePlayers()==1){
            map.setWinner();
            for(Player p:players){
                if(p.getIsActive()){
                    p.setHasWon(true);
                    break;
                }
            }
            setWinnerAndLoser();
            sendBroadcast("EXIT");
        } else {
            if (currentPlayer == players.size() - 1)
                currentPlayer = 0;
            else {
                currentPlayer++;
            }
            if (!players.get(currentPlayer).getIsActive()) {
                nextPlayer();
            }
        }
    }

    //-----------GETTER-----------------------------------------
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public Map getMap() {
        return map;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

}

