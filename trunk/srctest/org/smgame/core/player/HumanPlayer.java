package org.smgame.core.player;

import java.io.Serializable;

/**Classe giocatore umano
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class HumanPlayer extends Player implements Serializable {

    /**Costruttore
     *
     * @param name nome giocatore
     */
    public HumanPlayer(String name) {
        super(name);
        MIN_MARGIN = 0.5;
    }
}