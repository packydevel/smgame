package org.smgame.client.frontend;

import java.util.EventObject;

/**
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class NewGameEvent extends EventObject {

    /**
     *
     * @param source provenienza
     */
    public NewGameEvent(Object source) {
        super(source);
    }
}
