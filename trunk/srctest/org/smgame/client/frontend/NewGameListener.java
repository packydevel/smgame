package org.smgame.client.frontend;

import java.util.EventListener;

/**
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public interface NewGameListener extends EventListener {
    
    /**
     * 
     * @param e 
     */
    public void newGameCreating(NewGameEvent e);
}


