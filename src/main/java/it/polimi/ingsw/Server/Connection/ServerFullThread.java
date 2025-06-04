package it.polimi.ingsw.Server.Connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFullThread extends Thread {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    PrintWriter out;

    public ServerFullThread(ServerSocket socket) {
        this.serverSocket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                out= new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("REJECTED");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
