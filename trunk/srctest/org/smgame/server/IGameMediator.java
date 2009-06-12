/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author packyuser
 */
public interface IGameMediator extends Remote {

    long getPerfectTime() throws RemoteException;
}
