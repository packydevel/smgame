package org.smgame.client;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import javax.swing.JApplet;
import javax.swing.UIManager;

import org.smgame.client.frontend.MainJF;
import org.smgame.util.ResourceLocator;

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
        try {
            ResourceLocator.setWorkspace(getCodeBase().toURI().getPath(), true);

        } catch (Exception e) {
        }
        
        try {
            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
        } catch (Exception e) {
        }
        MainJF frame = new MainJF();





        try {
            ClientProxy.getInstance().loadGames();
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
        //System.setSecurityManager(new SecurityManager());
        ResourceLocator.setWorkspace(System.getProperty("user.dir"), false);

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainJF frame = new MainJF();
            } //end run
        }); //end invokelater

        try {
            ClientProxy.getInstance().loadGames();
        } catch (Exception e) {
        }
    }
}
