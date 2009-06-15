/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import javax.swing.UIManager;
import org.smgame.server.frontend.ServerJF;
import org.smgame.util.Logging;

/**
 *
 * @author packyuser
 */
public class SMGameServer {

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
        } catch (Exception e) {
            Logging.logExceptionSevere(e);
        }
        new ServerJF();
    }
}
