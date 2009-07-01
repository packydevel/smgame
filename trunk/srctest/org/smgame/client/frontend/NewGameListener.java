package org.smgame.client.frontend;

import java.util.EventListener;

/**interrfaccia di ascolto nuova partita
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public interface NewGameListener extends EventListener {
    
    /**Creazione nuova partita
     * 
     * @param e evento nuova partita
     */
    public void newGameCreating(NewGameEvent e);
}