package org.smgame.core.player;

import java.io.Serializable;

import java.util.ArrayList;

import org.smgame.core.card.Card;
import org.smgame.core.card.JollyCard;
import org.smgame.core.card.Point;
import org.smgame.core.card.Suit;

/**Classe astratta Giocatore
 * 
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public abstract class Player implements Serializable {
    
    protected String name; //nome giocatore
    protected double credit; //credito
    protected double bet; //puntata
    protected double lastWinLoseAmount;
    //protected boolean hasJollyCard = false;
    protected ArrayList<Card> cardList = new ArrayList<Card>(); //
    protected ArrayList<Double> betList = new ArrayList<Double>(12);
    protected double MIN_MARGIN = 0.5;
    protected PlayerRole role; //ruolo giocatore
    protected PlayerStatus status; //status del giocatore dopo la sua giocata
    protected PlayerList playerList;

    /**Costruttore
     *
     * @param name
     */
    public Player(String name) {
        this.name = name;
        this.role = PlayerRole.Normal;
    }

    /**Restituisce il nome giocatore
     *
     * @return nome
     */
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

    /**imposta il credito corrente
     *
     * @param credit credito
     */
    public void setCredit(double credit) {
        this.credit = credit;
    }

    /**Restituisce  la lista delle puntate
     *
     * @return lista puntate
     */
    public ArrayList<Double> getBetList() {
        return betList;
    }

    /**Restituisce la lista delle carte
     *
     * @return lista di card
     */
    public ArrayList<Card> getCardList() {
        return cardList;
    }

    /**imposta la puntata
     *
     * @param bet puntata
     */
    public void setBet(double bet) {
        this.bet = bet;
    }

    /**Restituisce il punteggio
     *
     * @return punteggio
     */
    public double getScore() {
        double bestValue;
        double score = 0.00;
        boolean hasJollyCard = false;

        for (Card c : cardList) {
            if (c instanceof JollyCard) {
                hasJollyCard = true;
            } else {
                score += c.getValue();
            }
        }

        if (hasJollyCard) {
            bestValue = JollyCard.getBestValue(score);
            score += bestValue;
        }

        return score;
    }

    /**restituisce il punteggio visibile
     *
     * @return punteggio
     */
    double getVisibleScore() {
        Card firstCard = cardList.get(0);
        double score = 0.00;

        if (status == PlayerStatus.ScoreOverflow || hasSM()) {
            return getScore();
        } else {
            if (firstCard instanceof JollyCard) {
                for (Card c : cardList) {
                    if (!c.equals(firstCard)) {
                        score += c.getValue();
                    }
                }
                return score;
            } else {
                return getScore() - firstCard.getValue();
            }
        }
    }
//
//    private boolean hasJollyCard() {
//        for (Card c : cardList) {
//            if (c instanceof JollyCard) {
//                return true;
//            }
//        }
//        return false;
//    }
//

    /**verifica se c'è sette e mezzo
     *
     * @return true = sette e mezzo
     */
    public boolean hasSM() {
        if (getScore() == 7.5) {
            return true;
        }
        return false;
    }

    /**verifica se c'è sette e mezzo reale
     *
     * @return true = sm reale
     */
    public boolean hasKingSM() {
        if (hasSM() && cardList.size() == 2) {
            return true;
        }
        return false;
    }

    /**Verifica se c'è sette e mezzo reale con matta
     *
     * @return true = sm reale matta
     */
    public boolean hasJollyKingSM() {
        if (hasKingSM() &&
                ((getCardList().get(0).getPoint() == Point.Re && getCardList().get(0).getSuit() == Suit.Danari) ||
                (getCardList().get(1).getPoint() == Point.Re && getCardList().get(1).getSuit() == Suit.Danari))) {
            return true;
        }
        return false;
    }

    /**Restituisce il ruolo corrente
     * se mazziere o giocatore
     *
     * @return ruolo
     */
    public PlayerRole getRole() {
        return role;
    }

    /**imposta il ruolo corrente
     *
     * @param role ruolo
     */
    public void setRole(PlayerRole role) {
        this.role = role;
    }

    /**imposta la lista dei giocatori
     *
     * @param playerList lista
     */
    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    /**Restituisce la puntata
     * 
     * @return puntata
     */
    public double getStake() {
        double stake = 0;

        for (Double b : betList) {
            stake += b.doubleValue();
        }

        return stake;
    }

    /**restituisce lo stato
     *
     * @return stato
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**imposta lo stato
     *
     * @param status stato
     */
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    /**Restituisce l'ammontare dell'ultima vincita/perdita
     *
     * @return vincita/perdita
     */
    public double getLastWinLoseAmount() {
        return lastWinLoseAmount;
    }

    /**Imposta l'ammontare dell'ultima vincita/perdita
     *
     * @param lastWinLoseAmount ultimo ammontare
     */
    public void setLastWinLoseAmount(double lastWinLoseAmount) {
        this.lastWinLoseAmount = lastWinLoseAmount;
    }

} //end class
