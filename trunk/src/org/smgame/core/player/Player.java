package org.smgame.core.player;

import java.util.ArrayList;
import java.util.HashMap;
import org.smgame.core.card.Card;

/**Classe astratta Giocatore
 * 
 * @author luca
 * @author pasquale
 */
public abstract class Player {

    protected String name;
    protected double credit; //credito
    protected double bet; //puntata
    protected ArrayList<Card> cardList; //
    protected HashMap<Card, Double> betList;
    protected double score; //punteggio
    protected double MIN_SCORE = 4.0;
    protected double MIN_MARGIN = 0.5;
    protected PlayerRole role;
    protected PlayerList playerList;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**Restituisce il credito corrente
     * 
     * @return credito
     */
    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public HashMap<Card, Double> getBetList() {
        return betList;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public double getBet() {
        return bet;
    }

    public double getScore() {
        score = 0;

        for (Card c : cardList) {
            score += c.getValue();
        }

        return score;
    }

    public PlayerRole getRole() {
        return role;
    }

    public void setRole(PlayerRole role) {
        this.role = role;
    }

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    /**Restituisce il valore totale???
     * 
     * @return
     */
    public double getStake() {
        double stake = 0;

        for (Double b : betList.values()) {
            stake += b.doubleValue();
        }

        return stake;
    }
    /*
    protected abstract boolean askAnotherManche();

    protected abstract boolean askAnotherCard();

    protected abstract void setCredit(double amount);
     */
} //end class
