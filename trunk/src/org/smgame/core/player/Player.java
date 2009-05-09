package org.smgame.core.player;

import java.util.ArrayList;
import org.smgame.core.GameEngine;
import org.smgame.core.card.Card;

/**Classe astratta Giocatore
 * 
 * @author luca
 * @author pasquale
 */
public abstract class Player {

    protected double credit; //credito
    protected double score; //punteggio
    protected ArrayList<Card> CardList;
    protected GameEngine gameEngine = GameEngine.getInstance();

    /**Restituisce il credito corrente
     * 
     * @return credito
     */
    public double getCredit() {
        return credit;
    }

    /**Restituisce il valore totale???
     * 
     * @return
     */
    public double getTotalValue() {
        return score;
    }

    /*
    protected abstract boolean askAnotherManche();

    protected abstract boolean askAnotherCard();

    protected abstract double requestBet();

    protected abstract void setCredit(double amount);

    protected abstract double getCredit();
     */
} //end class