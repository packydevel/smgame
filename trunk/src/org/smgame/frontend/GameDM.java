/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;

/**
 *
 * @author packyuser
 */
public class GameDM extends DefaultDesktopManager {

    public void dragFrame(JComponent f, int x, int y) {
        if (!(f instanceof IGameJIF)) {
            super.dragFrame(f, x, y);
        }
    }
}

