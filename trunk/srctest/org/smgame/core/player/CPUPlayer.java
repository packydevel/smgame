package org.smgame.core.player;

import java.io.Serializable;
import java.util.HashMap;
import org.smgame.core.card.Point;
import org.smgame.core.card.Suit;

/**Classe giocatore CPU
 *
 * @author luca
 * @author pasquale
 */
public class CPUPlayer extends Player implements Serializable {

    private final double MIN_SCORE = 4.5; //minimo punteggio
    private final HashMap<Double, Double> valueWeightMap = new HashMap<Double, Double>();

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

    /**Chiede un'altra carta
     *
     * @return
     */
    public boolean isGoodScore() {
        double threshold = 0.00;
        HashMap<Player, PlayerStatus> playerStatusMap = (HashMap<Player, PlayerStatus>) playerList.getPlayerStatusMap();
        HashMap<Player, Double> playerVisibleScoreMap = (HashMap<Player, Double>) playerList.getPlayerVisibleScoreMap();

        //if (role == PlayerRole.Normal) {
            if (cardList.get(0).getPoint() == Point.Re && cardList.get(0).getSuit() == Suit.Danari && cardList.size() == 1) {
                return false;
            } else if ((cardList.get(0).getPoint() == Point.Re && cardList.get(0).getSuit() == Suit.Danari && cardList.size() > 1)) {
                return true;
            } else {
                if (getScore() > MIN_SCORE) {
                    return true;
                }
            }
//        } else {
//
//            for (Player p : playerList.getPlayerAL()) {
//                if (!p.equals(this)) {
//                    if (p.getStatus() == PlayerStatus.ScoreOverflow) {
//                        threshold += p.getStake();
//                    } else {
//                        if (p.getVisibleScore() > getScore()) {
//                            threshold -= p.getStake();
//                        } else {
//                            threshold += p.getStake();
//                        }
//                    }
//                }
//            }
//
//            if (threshold >= 0.00) {
//                return true;
//            }
        //}

        return false;
    }

    /**richiede una puntata
     *
     * @return
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