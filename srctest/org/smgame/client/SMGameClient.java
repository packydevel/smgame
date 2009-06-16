package org.smgame.client;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;

import javax.swing.JApplet;
import javax.swing.UIManager;

import org.smgame.core.GUICoreMediator;
import org.smgame.client.frontend.MainJF;
import org.smgame.util.Common;
import org.smgame.util.Logging;

/**Classe SetteMezzo client
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class SMGameClient extends JApplet{

    @Override
    public void init() {}

    @Override
    public void stop() {}

    /**
     *
     *
     */
    @Override
    public void start (){
        //System.setSecurityManager(new SecurityManager());
        Logging.createLog(Common.getCurrentDateTime());

        try {
            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
        } catch (Exception e) {
            Logging.logExceptionSevere(e);
        }
        MainJF frame = new MainJF();
        try {
            //GUICoreMediator.loadGames();
        } catch (Exception e) {
            Logging.logExceptionSevere(e);
        }
    }

    @Override
    public void destroy(){
        System.exit(0);
    }

    /**
     *
     * @param args argomenti
     */
    public static void main(String[] args) {
        Logging.createLog(Common.getCurrentDateTime());
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
                } catch (Exception e) {
                    Logging.logExceptionSevere(e);
                }
                MainJF frame = new MainJF();
            } //end run
        }); //end invokelater

        try {
            GUICoreMediator.loadGames();
        } catch (Exception e) {
            Logging.logExceptionSevere(e);
        }
    }
}