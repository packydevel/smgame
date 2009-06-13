package org.smgame.main;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import javax.swing.UIManager;
import org.smgame.core.GUICoreMediator;
import org.smgame.frontend.MainJF;
import org.smgame.util.Common;
import org.smgame.util.Logging;

/**
 *
 * @author packyuser
 */
public class SMGameClient {

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
