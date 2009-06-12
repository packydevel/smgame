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
public class ServerMediator implements IGameMediator {

// Must implement constructor
// to throw RemoteException:
    public ServerMediator() throws RemoteException {
        super(); // Called automatically
    }

    public long getPerfectTime() throws RemoteException {
        return System.currentTimeMillis();
    }

// Registration for RMI serving. Throw
// exceptions out to the console.
    public static void main(String[] args) throws Exception {
        try {
            String name = "//localhost/ServerMediator";
            System.setSecurityManager(new RMISecurityManager());
            IGameMediator pt = new ServerMediator();
            Registry registry = LocateRegistry.getRegistry();
            //registry.unbind(name);


            //Naming.bind("//localhost/ServerMediator", pt);

            IGameMediator stub = (IGameMediator) UnicastRemoteObject.exportObject(pt, 0);//, 2005);
            registry.rebind(name, stub);
            System.out.println("Ready to do time");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}

