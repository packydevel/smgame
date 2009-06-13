/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import java.util.EventListener;

/**
 *
 * @author packyuser
 */
public interface NewOnLineGameListener extends EventListener {

    public void newOnLineGameCreating(NewOnLineGameEvent e);
}


