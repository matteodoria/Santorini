package it.polimi.ingsw.Server.Connection;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.ParserManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    private static ArrayList<String> nicknames = new ArrayList<>();
    private static int num_players = 0;
    private static int num_connection=0;
    private static int port;
    private static ArrayList<ServerClientHandler> clients = new ArrayList<>();
    private boolean active;

    public Server(int port) {
        this.port = port;
    }

    /**
     * method that start the server execution, waiting and accepting client connections
     */
    public void serverStart() {
        active = true;

        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Server is ready on " + port);
        ServerFullThread serverFullThread = new ServerFullThread(serverSocket);
        while (active) {
            try {
                Socket first_socket = serverSocket.accept();
                num_connection++;
                ServerClientHandler first_clientHandler = new ServerClientHandler(first_socket);
                clients.add(first_clientHandler);
                executor.submit(first_clientHandler);
                nicknames.add(first_clientHandler.receiveMessage());
                first_clientHandler.sendMessage("YN,1");
                String num_player_string = first_clientHandler.receiveMessage();
                num_players = Integer.parseInt(num_player_string);
                first_clientHandler.sendMessage("GP,10.0");
                while(num_connection<num_players){
                    Socket socket = serverSocket.accept();
                    num_connection++;
                    ServerClientHandler clientHandler = new ServerClientHandler(socket);
                    clients.add(clientHandler);
                    executor.submit(clientHandler);
                    boolean check;
                    do {
                        String nickname = clientHandler.receiveMessage();
                        check = nicknames.contains(nickname);
                        System.out.println(check);
                        if (!check){
                            nicknames.add(nickname);
                            clientHandler.sendMessage("YN," + num_connection);
                        }
                        else
                            clientHandler.sendMessage("YN,NO");
                    } while (check);

                    if(num_connection<num_players)
                        clientHandler.sendMessage("GP,10.0");
                }
                for(int i=0; i<clients.size(); i++){
                    for(int j=0; j<clients.size(); j++)
                        clients.get(i).sendMessage("NP,"+(j+1)+","+nicknames.get(j));
                }
                GameController gameController = new GameController(clients);
                System.out.println("STARTING GAME");
                serverFullThread.start();
                gameController.startGame();
                TimeUnit.MILLISECONDS.sleep(3000);
                serverFullThread.interrupt();
                serverSocket.close();
                active = false;
            }
            catch (NoSuchElementException | IOException | InterruptedException e) {
                serverFullThread.interrupt();
                for(int i=0; i<clients.size(); i++){
                    clients.get(i).sendMessage("GP,99");
                }
                System.exit(0);
            }
        }
        executor.shutdown();
    }

    public static void main(String[] args)  {
        try {
            ParserManager pm= new ParserManager("Connection.json");
            JsonObject obj=pm.getJsonObj();
            port=obj.get("port").getAsInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Server server1 = new Server(port);
        server1.serverStart();
        System.exit(0);
    }
}
