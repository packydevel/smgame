package org.smgame.server;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;

import java.io.File;
import java.rmi.RMISecurityManager;

import javax.swing.UIManager;

import org.smgame.server.frontend.ServerJF;
import org.smgame.util.Logging;
import org.smgame.util.ResourceLocator;

/**Server smgame
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class SMGameServer {

    /**esegue il main
     *
     * @param args argomenti
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        ResourceLocator.setWorkspace(new File(System.getProperty("user.dir")).toURI().getPath());
        Logging.createLog("server");
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
                } catch (Exception e) {
                    Logging.logExceptionSevere(this.getClass(), e);
                }
                new ServerJF();
            }
        });
    }
}