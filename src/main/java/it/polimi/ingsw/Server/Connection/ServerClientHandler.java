package it.polimi.ingsw.Server.Connection;

import it.polimi.ingsw.Model.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerClientHandler implements Runnable {

    private Socket clientSocket;
    private boolean active;
    private Scanner in;
    private PrintWriter out;

    public ServerClientHandler(Socket socket) throws IOException {
        this.clientSocket=socket;
        in = new Scanner (clientSocket.getInputStream());
        out= new PrintWriter(clientSocket.getOutputStream(), true);
        active = true;
    }

    @Override
    public void run() {
        try {
            while(active){
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * method that send a message to the client
     * @param s message
     */
    public void sendMessage(String s){
        out.println(s);
        System.out.println(s);
    }

    /**
     * method that receive a message from the client
     * @return the message
     * @throws NoSuchElementException when a client is disconnected
     */
    public String receiveMessage () throws NoSuchElementException {
            String s= in.nextLine();
            System.out.println("Ho ricevuto: "+ s);
            return s;
    }

}

