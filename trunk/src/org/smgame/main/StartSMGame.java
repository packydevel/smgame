/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.main;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import javax.swing.UIManager;
import org.smgame.frontend.MainJF;

/**
 *
 * @author packyuser
 */
public class StartSMGame {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
        } catch (Exception e) {
        }
        MainJF frame = new MainJF();
    }
}
