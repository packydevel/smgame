package org.smgame.main;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.UIManager;
import org.smgame.core.GUICoreMediator;
import org.smgame.frontend.MainJF;
import org.smgame.util.Logging;

/**
 *
 * @author packyuser
 */
public class StartSMGame {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
                } catch (Exception e) {
                    Logging.logExceptionSevere(e);
                }
                MainJF frame = new MainJF();
            }
        });

        try {
            GUICoreMediator.loadGames();
        } catch (Exception e) {
            Logging.logExceptionSevere(e);
            System.out.println("Errore nel caricamento!!!");
        }
    }
}
