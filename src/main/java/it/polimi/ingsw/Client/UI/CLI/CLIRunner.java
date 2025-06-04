package it.polimi.ingsw.Client.UI.CLI;

import com.google.gson.JsonObject;
import it.polimi.ingsw.Client.ClientData.ClientGameData;
import it.polimi.ingsw.Model.ParserManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CLIRunner {
    private boolean connectionStablished = false;
    private Socket socket;
    private Scanner socketIn;
    private PrintWriter socketOut;
    private Scanner stdin = new Scanner(System.in);
    private ClientGameData clientGameData;
    private String ip;
    private int port;

    public void cliStart() {
        String in = "";
        do {
            do {
                System.out.println("Do you want to use default settings? (Y/N)");
                in = stdin.nextLine();
            } while (!in.toUpperCase().equals("N") && !in.toUpperCase().equals("Y"));
            ParserManager pm = null;
            try {
                pm = new ParserManager("Connection.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            JsonObject jObj = pm.getJsonObj();
            port = jObj.get("port").getAsInt();
            if (in.toUpperCase().equals("N")) {
                do {
                    System.out.println("Select ip");
                    in = stdin.nextLine();
                    if (in.equals("")) {
                        System.out.println("IP CAN'T BE EMPTY");
                    } else {
                        ip = in;
                        try {
                            socket = new Socket(ip, port);
                            connectionStablished = true;
                        } catch (IOException e) {
                            System.out.println("THERE IS NO SERVER LISTENING AT THIS ADDRESS");
                        }
                    }
                }
                while (!connectionStablished);
            } else {
                try {
                    socket = new Socket(jObj.get("host").getAsString(), port);
                    connectionStablished = true;
                } catch (IOException e) {
                    System.out.println("THERE IS NO SERVER LISTENING AT THIS ADDRESS");
                }
            }
        }
        while (!connectionStablished);
        try {
            socketIn = new Scanner(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientGameData = new ClientGameData(socketOut);
        clientStart();
    }

    public void clientStart() {
        System.out.print(Color.ANSI_BLUE_BOLD_BRIGHT.escape());
        System.out.print("                                                                          \n" +
                " ,---.    ,---.  ,--.  ,--.,--------. ,-----. ,------. ,--.,--.  ,--.,--. \n" +
                "/   .-'  /  O  \\ |  ,'.|  |'--.  .--''  .-.  '|  .--. '|  ||  ,'.|  ||  | \n" +
                "`.  `-. |  .-.  ||  |' '  |   |  |   |  | |  ||  '--'.'|  ||  |' '  ||  | \n" +
                ".-'    ||  | |  ||  | `   |   |  |   '  '-'  '|  |\\  \\ |  ||  | `   ||  | \n" +
                "`-----' `--' `--'`--'  `--'   `--'    `-----' `--' '--'`--'`--'  `--'`--' \n" +
                "                                                                          \n");
        System.out.print(Color.RESET);
        String yourNumberArrives = "";
        String nickname = "";
        do {
            System.out.print(!yourNumberArrives.equals("") ? "Nickname already used!\n" : "");
            System.out.print("Insert nickname: ");
            try {
                do {
                    nickname = stdin.nextLine();
                    if (nickname.equals("")) {
                        System.out.println("YOU CAN'T INSERT A NICKNAME EMPTY");
                        System.out.print("Insert nickname: ");
                    }
                } while (nickname.equals(""));
                socketOut.println(nickname);
                yourNumberArrives = socketIn.nextLine();
                if (yourNumberArrives.equals("REJECTED")) {
                    System.out.println("YOU CAN'T JOIN THE GAME SESSION!");
                    return;
                }
            } catch (NoSuchElementException e) {
                System.out.println("SERVER CONNECTION ERROR!");
                return;
            }
        }
        while (yourNumberArrives.split(",")[1].equals("NO"));
        clientGameData.dataUpdate(yourNumberArrives);

        //se sei il primo decidi il numero dei giocatori
        if (clientGameData.getYourData().getYourNumber().equals("1")) {
            String playersNum;
            do {
                System.out.println("How many players? (2/3)");
                try {
                    playersNum = stdin.nextLine();
                } catch (NoSuchElementException e) {
                    System.out.println("SERVER CONNECTION ERROR!");
                    return;
                }
            }
            while (!(playersNum.equals("2") || playersNum.equals("3")));
            socketOut.println(playersNum);
        }
        KeyboardThread keyboard = new KeyboardThread(clientGameData, socketOut);
        keyboard.start();
        String message;
        try {
            while (!clientGameData.getYourData().isYouLose() && !clientGameData.getYourData().isYouWon() && !clientGameData.isaClientHasDisconnected()) {
                message = socketIn.nextLine();
                clientGameData.dataUpdate(message);
            }
        } catch (NoSuchElementException e) {
            System.out.println("SERVER CONNECTION ERROR!");
        }
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

