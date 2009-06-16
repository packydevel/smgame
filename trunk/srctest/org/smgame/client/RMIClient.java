/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.client;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.smgame.server.IGameMediator;

/**
 *
 * @author packyuser
 */
public class RMIClient {

    private static IGameMediator stub=null;

    private RMIClient() throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry = LocateRegistry.getRegistry("localhost");
        stub = (IGameMediator) registry.lookup("rmi://localhost/ServerMediator");
    }

    public static IGameMediator getStub() throws Exception {
        if (stub==null) {
            new RMIClient();
        }

        return stub;
    }
}
