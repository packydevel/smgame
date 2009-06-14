/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.main;

import org.smgame.server.RMIServer;

/**
 *
 * @author packyuser
 */
public class SMGameServer {

    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("rmiregistry");
        Thread.sleep(5000);
        RMIServer.getInstance();
    }
}
