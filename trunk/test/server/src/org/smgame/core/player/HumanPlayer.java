package org.smgame.core.player;

import java.io.Serializable;

/**Classe giocatore umano
 *
 * @author luca
 * @author pasquale
 */
public class HumanPlayer extends Player implements Serializable {

   public HumanPlayer(String name) {
        super(name);
        MIN_MARGIN = 0.5;
    }
}