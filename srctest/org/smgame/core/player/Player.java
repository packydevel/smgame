package org.smgame.core.player;

import java.io.Serializable;
import java.util.ArrayList;
import org.smgame.core.card.Card;
import org.smgame.core.card.JollyCard;
import org.smgame.core.card.Point;
import org.smgame.core.card.Suit;

/**Classe astratta Giocatore
 * 
 * @author luca
 * @author pasquale
 */
public abstract class Player implements Serializable {

    protected String name; //nome giocatore
    protected double credit; //credito
    protected double bet; //puntata
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
     * @return
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
     * @param credit
     */
    public void setCredit(double credit) {
        this.credit = credit;
    }

    public ArrayList<Double> getBetList() {
        return betList;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    /**Restituisce il punteggio
     *
     * @return
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
    public boolean hasSM() {
        if (getScore() == 7.5) {
            return true;
        }

        return false;
    }

    public boolean hasKingSM() {
        if (hasSM() && cardList.size() == 2) {
            return true;
        }

        return false;
    }

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
     * @return
     */
    public PlayerRole getRole() {
        return role;
    }

    /**imposta il ruolo corrente
     *
     * @param role
     */
    public void setRole(PlayerRole role) {
        this.role = role;
    }

    /**imposta la lista dei giocatori
     *
     * @param playerList
     */
    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    /**Restituisce il valore totale???
     * 
     * @return
     */
    public double getStake() {
        double stake = 0;

        for (Double b : betList) {
            stake += b.doubleValue();
        }

        return stake;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
} //end class
