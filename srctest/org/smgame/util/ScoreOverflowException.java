package org.smgame.util;

import org.smgame.core.card.Card;

/**Classe gioco
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ScoreOverflowException extends Exception {

    Card card;

    public ScoreOverflowException(String message, Card card) {
        super(message);
        this.card=card;
    }

    public Card getCardException(){
        return card;
    }
}
