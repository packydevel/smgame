/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class SMGameServer implements Runnable {

    private static final int PORT = 1355;

    public static void main(String[] args) {
        SMGameServer server = new SMGameServer();
        MonopolyServerMediator serverMediator = new MonopolyServerMediator(server);
        Thread t = new Thread(server);
        t.start();
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            
            while (!Thread.currentThread().isInterrupted()) {
                //System.out.println(!Thread.currentThread().isInterrupted());
                System.out.println("In attesa di una connessione ...");
                Socket s = ss.accept();
                System.out.println("Connessione stabilita");
                Thread t = new SingleRequestSocket(s);
                t.start();
            }

        } catch (IOException ioe) {
        }
    }
}

