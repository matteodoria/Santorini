package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Map;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Server.Connection.Server;
import it.polimi.ingsw.Server.Connection.ServerClientHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    ServerClientHandler handler0 = null;
    ServerClientHandler handler1 = null;
    ServerClientHandler handler2 = null;
    ServerSocket server;

    {
        try {
            server = new ServerSocket(9100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ArrayList<ServerClientHandler> client = new ArrayList<>();
    ArrayList<ServerClientHandler> client_one = new ArrayList<>();

    @BeforeEach
    void setUp() {
        try {
            handler0 = new ServerClientHandler(new Socket("localhost", 9100));
            handler1 = new ServerClientHandler(new Socket("localhost", 9100));
            handler2 = new ServerClientHandler(new Socket("localhost", 9100));
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.add(handler0);
        client.add(handler1);
        client.add(handler2);
        client_one.add(handler0);
        client_one.add(handler1);

    }


    @Test
    void setWinnerAndLoser() {
        GameController controller = new GameController(client);
        controller.nextPlayer();
        controller.getPlayers().get(0).setIsActive(false);
        controller.getMap().setActivePlayers(controller.getMap().getActivePlayers()-1);
        controller.nextPlayer();
        controller.getPlayers().get(1).setIsActive(false);
        controller.getMap().setActivePlayers(controller.getMap().getActivePlayers()-1);
        controller.nextPlayer();
        assertEquals(false, controller.getPlayers().get(0).getHasWon());
        assertEquals(false, controller.getPlayers().get(1).getHasWon());
        assertEquals(true, controller.getPlayers().get(2).getHasWon());

        GameController controller_one = new GameController(client_one);
        controller_one.nextPlayer();
        controller_one.getPlayers().get(0).setIsActive(false);
        controller_one.getMap().setActivePlayers(controller_one.getMap().getActivePlayers()-1);
        controller_one.nextPlayer();
        assertEquals(false, controller_one.getPlayers().get(0).getHasWon());
        assertEquals(true, controller_one.getPlayers().get(1).getHasWon());


    }

    @Test
    void nextPlayer() {
        GameController controller = new GameController(client);
        assertEquals(0, controller.getCurrentPlayer());
        controller.nextPlayer();
        assertEquals(1, controller.getCurrentPlayer());
        controller.nextPlayer();
        assertEquals(2, controller.getCurrentPlayer());
        controller.getPlayers().get(0).setIsActive(false);
        controller.getMap().setActivePlayers(controller.getMap().getActivePlayers()-1);
        controller.nextPlayer();
        assertEquals(1, controller.getCurrentPlayer());
        }
}