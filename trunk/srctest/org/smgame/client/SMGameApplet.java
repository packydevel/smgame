package org.smgame.client;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import javax.swing.JApplet;
import javax.swing.UIManager;
import org.smgame.client.frontend.MainJF;
import org.smgame.util.Common;
import org.smgame.util.Logging;

/**
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class SMGameApplet extends JApplet{

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

}
