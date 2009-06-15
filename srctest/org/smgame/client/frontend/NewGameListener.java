/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.client.frontend;

import java.util.EventListener;

/**
 *
 * @author packyuser
 */
public interface NewGameListener extends EventListener {

    public void newGameCreating(NewGameEvent e);
}


