/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.server;

/**
 *
 * @author Administrator
 */
public class MonopolyServerMediator {
    private static SMGameServer server;
    private static Thread serverThread;
    
    public MonopolyServerMediator(SMGameServer pMonopolyServer) {
        server=pMonopolyServer;
    }
    
    public static void requestStartServer() {
        Thread t = new Thread(server);
        serverThread=t;
        t.start();
    }
    
    public static void requestStopServer() {
        serverThread.interrupt();
    }
    
    public static void requestPlayers(int pPlayersNumbers) {
        
    }
}
