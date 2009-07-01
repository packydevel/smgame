package org.smgame.server;

import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;
import java.rmi.RMISecurityManager;
import javax.swing.UIManager;
import org.smgame.core.GUICoreMediator;
import org.smgame.server.frontend.ServerJF;
import org.smgame.util.Logging;

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
