/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.client;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.smgame.server.IGameMediator;

/**
 *
 * @author packyuser
 */
public class ClientMediator {

    public static void main(String[] args)
            throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry = LocateRegistry.getRegistry("localhost");


        IGameMediator t = (IGameMediator) registry.lookup("//localhost/ServerMediator");
        //for (int i = 0; i < 10; i++) {
        while (true) {
            System.out.println("Perfect time=" + t.getPerfectTime());}
        //}
    }
}
