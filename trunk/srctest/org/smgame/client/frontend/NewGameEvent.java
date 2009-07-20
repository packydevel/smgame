package org.smgame.client.frontend;

import java.util.EventObject;

/**Evento nuovo gioco
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
@SuppressWarnings("serial")
public class NewGameEvent extends EventObject {

    /**Costruttore
     *
     * @param source provenienza
     */
    public NewGameEvent(Object source) {
        super(source);
    }
}
