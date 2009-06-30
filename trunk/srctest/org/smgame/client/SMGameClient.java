package org.smgame.client;

import de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel;

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

    @Override
    public void start() {
        //System.setSecurityManager(new SecurityManager());
        Common.setWorkspace(getCodeBase().toString(), true);        
        try {
            UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
        } catch (Exception e) {
        }
        MainJF frame = new MainJF();
        try {
          //ClientProxy.getInstance().loadGames(GameMode.OFFLINE);
        } catch (Exception e) {
        }
    }

    @Override
    public void destroy() {
        System.exit(0);
    }

    /**Lancia l'applicazione
     *
     * @param args argomenti
     */
    public static void main(String[] args) {
        Common.setWorkspace(System.getProperty("user.dir"), false);
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
                } catch (Exception e) {
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
