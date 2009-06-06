/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import org.smgame.main.Game;

/**
 *
 * @author Administrator
 */
public class SingleRequestSocket extends Thread {

    private Socket socket;
    private ObjectInputStream incoming;
    private ObjectOutputStream outcoming;
    private RequestExecutor executor;

    public SingleRequestSocket(Socket pSocket) {
        try {
            socket = pSocket;
            outcoming = new ObjectOutputStream(socket.getOutputStream());
            outcoming.flush();
            incoming = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ioe) {
        }
        executor = new RequestExecutor();
    }

    public void run() {
/*
        String requestCommand;
        Object requestParameter;
        boolean exit = false;
        long gameID;
        Game game;

        try {
            while (!exit) {
                System.out.println("In attesa di richiesta ...");
                requestCommand = (String) incoming.readObject();


                if (requestCommand.equals("getgameid")) {
                    System.out.println("Richiesta di un nuovo GameID ...");
                    gameID = new Long(executor.getNewGameID());
                    outcoming.writeObject(gameID);
                    outcoming.flush();
                    System.out.println("Richiesta accolta! Nuovo GameID generato: " + gameID);
                }
                if (requestCommand.equals("getgameslist")) {
                    try {
                        outcoming.writeObject(executor.getGamesList());
                        outcoming.flush();
                        System.out.println("Partite recuperate correttamente");
                    } catch (Exception e) {
                        outcoming.writeObject(e);
                        outcoming.flush();
                    }
                }
                if (requestCommand.equals("loadgame")) {
                    requestParameter = incoming.readObject();
                    try {
                        game = executor.loadGame(((Long) requestParameter).longValue());
                        outcoming.writeObject(game);
                        outcoming.flush();
                        System.out.println("Partita caricata correttamente");
                    } catch (Exception e) {
                        outcoming.writeObject(e);
                        outcoming.flush();
                    }
                }
                if (requestCommand.equals("savegame")) {
                    requestParameter = incoming.readObject();
                    System.out.println("Richiesta di salvare la partita con ID:  ...");
                    try {
                        executor.saveGame((Game) requestParameter);
                        System.out.println("Partita salvata correttamente");
                    } catch (Exception e) {
                        outcoming.writeObject(e);
                        outcoming.flush();
                    }
                } else {
                    outcoming.writeObject(new Exception("Richiesta errata!!!"));
                    outcoming.flush();
                }
            }
        } catch (IOException ioe) {
            System.out.println("errore di I/O");
        } catch (ClassNotFoundException cnfe) {
        }
*/
    }

 
}
