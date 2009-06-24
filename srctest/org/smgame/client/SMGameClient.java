package org.smgame.client;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;

import javax.swing.JApplet;
import javax.swing.UIManager;

import org.smgame.client.frontend.MainJF;
import org.smgame.core.GameMode;
import org.smgame.util.Common;

/**Classe SetteMezzo client
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class SMGameClient extends JApplet {

    @Override
    public void init() {
    }

    @Override
    public void stop() {
    }

    /**
     *
     *
     */
    @Override
    public void start() {
        //System.setSecurityManager(new SecurityManager());
        Common.setWorkspace(getCodeBase().toString(), true);
        //Logging.createLog("smgameclient");

        try {
            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
        } catch (Exception e) {
            //Logging.logExceptionSevere(this.getClass(), e);
        }
        MainJF frame = new MainJF();
        try {
          //ClientProxy.getInstance().loadGames(GameMode.OFFLINE);
        } catch (Exception e) {
            //Logging.logExceptionSevere(this.getClass(), e);
        }
    }

    @Override
    public void destroy() {
        System.exit(0);
    }

    /**
     *
     * @param args argomenti
     */
    public static void main(String[] args) {
        Common.setWorkspace(System.getProperty("user.dir"), false);
        //Logging.createLog("smgameclient");
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
                } catch (Exception e) {
                    //Logging.logExceptionSevere(this.getClass(), e);
                }
                MainJF frame = new MainJF();
            } //end run
        }); //end invokelater

        try {
            ClientProxy.getInstance().loadGames(GameMode.OFFLINE);
        } catch (Exception e) {
        }
    }
}