/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author packyuser
 */
public class RMIServer implements IGameMediator {

// Must implement constructor
// to throw RemoteException:
    public RMIServer() throws RemoteException {

        try {
            String name = "//localhost/ServerMediator";
            System.setSecurityManager(new RMISecurityManager());
            Registry registry = LocateRegistry.getRegistry();
            IGameMediator stub = (IGameMediator) UnicastRemoteObject.exportObject(this, 0);//, 2005);
            registry.rebind(name, stub);
            System.out.println("Ready to do time");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public long getPerfectTime() throws RemoteException {
        return System.currentTimeMillis();
    }
}

