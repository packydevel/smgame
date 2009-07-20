package org.smgame.client;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import java.io.File;
import javax.swing.JApplet;
import javax.swing.UIManager;

import org.smgame.client.frontend.MainJF;
import org.smgame.util.ResourceLocator;

/**Classe SetteMezzo client
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
@SuppressWarnings("serial")
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
            ResourceLocator.setWorkspace(getCodeBase().toURI().getPath());                
            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());        
            ClientProxy.getInstance().loadGames();
        } catch (Exception e) { }

        @SuppressWarnings("unused")
        MainJF frame = new MainJF();
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
        try {
            ResourceLocator.setWorkspace(new File(System.getProperty("user.dir")).toURI().getPath());
        } catch (Exception e) { }

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
                } catch (Exception e) { }

                @SuppressWarnings("unused")
                MainJF frame = new MainJF();
                
            } //end run
        }); //end invokelater

        try {
            ClientProxy.getInstance().loadGames();
        } catch (Exception e) { }
    }
}