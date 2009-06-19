package org.smgame.util;

import org.smgame.core.card.Card;

/**Classe eccezione di punteggio oltre il sette e mezzo
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ScoreOverflowException extends Exception {

    Card card;

    /**Costruttore
     *
     * @param message messaggio d'errore
     * @param card carta che ha generato l'overflow
     */
    public ScoreOverflowException(String message, Card card) {
        super(message);
        this.card=card;
    }

    /**restituisce la carta overflow
     *
     * @return carta
     */
    public Card getCardException(){
        return card;
    }
}