package org.smgame.client.frontend;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;

/**Classe DesktopManager personalizzato
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
@SuppressWarnings("serial")
public class CustomDM extends DefaultDesktopManager {

    @Override
    public void dragFrame(JComponent f, int x, int y) {
        if (!(f instanceof ICustomDM)) {
            super.dragFrame(f, x, y);
        }
    }
}

