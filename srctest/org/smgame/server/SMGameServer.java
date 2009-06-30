/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import java.rmi.RMISecurityManager;
import javax.swing.UIManager;
import org.smgame.core.GUICoreMediator;
import org.smgame.server.frontend.ServerJF;
import org.smgame.util.Logging;

/**
 *
 * @author packyuser
 */
public class SMGameServer {

    public static void main(String[] args) throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        Logging.createLog("server");
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
                } catch (Exception e) {
                    Logging.logExceptionSevere(this.getClass(), e);
                }
                new ServerJF();
            }
        });

        GUICoreMediator.loadGames();
    }
}
