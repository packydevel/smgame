package org.smgame.core.player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import org.smgame.core.card.Point;
import org.smgame.core.card.Suit;

/**Classe giocatore CPU
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class CPUPlayer extends Player implements Serializable {

    private final double MIN_SCORE = 4.5; //minimo punteggio
    private final HashMap<Double, Double> valueWeightMap = new HashMap<Double, Double>();

    /**Costruttore
     *
     * @param name nome del player cpu
     */
    public CPUPlayer(String name) {
        super(name);
        valueWeightMap.put(Double.valueOf(0.50), Double.valueOf(5.00));
        valueWeightMap.put(Double.valueOf(1.00), Double.valueOf(5.00));
        valueWeightMap.put(Double.valueOf(2.00), Double.valueOf(5.00));
        valueWeightMap.put(Double.valueOf(3.00), Double.valueOf(5.00));
        valueWeightMap.put(Double.valueOf(4.00), Double.valueOf(10.00));
        valueWeightMap.put(Double.valueOf(5.00), Double.valueOf(15.00));
        valueWeightMap.put(Double.valueOf(6.00), Double.valueOf(20.00));
        valueWeightMap.put(Double.valueOf(7.00), Double.valueOf(25.00));
    }

    /**Verifica se il player cpu ha un punteggio buono
     *
     * @return true/false
     */
    public boolean isGoodScore() {
        double threshold = 0.00, hypotethicScore, i;
        int allHypotheticValues, allGoodValues;
        Iterator<Player> playerListIterator;
        Player p;
        //HashMap<Player, PlayerStatus> playerStatusMap = (HashMap<Player, PlayerStatus>) playerList.getPlayerStatusMap();
        //HashMap<Player, Double> playerVisibleScoreMap = (HashMap<Player, Double>) playerList.getPlayerVisibleScoreMap();

        if (role == PlayerRole.Normal) {
            if (cardList.get(0).getPoint() == Point.Re && cardList.get(0).getSuit() == Suit.Danari && cardList.size() == 1) {
                return false;
            } else if ((cardList.get(0).getPoint() == Point.Re && cardList.get(0).getSuit() == Suit.Danari && cardList.size() > 1)) {
                return true;
            } else {
                if (getScore() > MIN_SCORE) {
                    return true;
                }
            }
        } else {
            playerListIterator = playerList.getPlayerListIterator();
            while (playerListIterator.hasNext()) {
                p = playerListIterator.next();

                if (!p.equals(this)) {
                    if (p.getStatus() == PlayerStatus.ScoreOverflow) {
                        threshold += p.getStake();
                    } else {
                        if (p.hasSM()) {
                            if (getScore() < 7.50) {
                                threshold -= p.getStake();
                            } else {
                                threshold += p.getStake();
                            }
                        } else {
                            if (getScore() <= MIN_SCORE) {
                                return false;
                            } else {
                                i = 0.5;
                                allHypotheticValues = 0;
                                allGoodValues = 0;
                                do {
                                    if ((p.getVisibleScore() + i) <= 7) {
                                        allHypotheticValues++;
                                        if ((p.getVisibleScore() + i) <= getScore()) {
                                            allGoodValues++;
                                        }
                                    }
                                    if (i == 0.5) {
                                        i = 1;
                                    } else {
                                        i++;
                                    }
                                } while (i <= 7);

                                System.out.println(p.getName() + " - " + "Good: " + allGoodValues + " All: " + allHypotheticValues);
                                double temp = (((2 * (double) allGoodValues) / (double) allHypotheticValues) - 1) * p.getStake();
                                System.out.println("thresold " + temp);
                                threshold += temp;
                            }
                        }
                    }
                }
            }

            System.out.println(name + " - " + threshold);

            if (threshold >= 0.00) {
                return true;
            }
        }

        return false;
    }

    /**richiede una puntata
     *
     * @return il valore della puntata
     */
    public double requestBet() {
        Double bet;

        if (role == PlayerRole.Bank) {
            return 0.00;
        } else {
            if (cardList.get(0).getPoint() == Point.Re && cardList.get(0).getSuit() == Suit.Danari) {
                bet = Math.floor(((valueWeightMap.get(7.00) * 2) / 1000) * credit);
            } else {
                bet = Math.floor((valueWeightMap.get(getScore())) / 1000 * credit);
            }

            if (bet > credit || bet == 0.00) {
                return credit;
            }
            return bet;
        }
    }
}
