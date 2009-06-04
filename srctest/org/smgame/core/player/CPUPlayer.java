package org.smgame.core.player;

import java.io.Serializable;

/**Classe giocatore CPU
 *
 * @author luca
 * @author pasquale
 */
public class CPUPlayer extends Player implements Serializable {

    private final double MAX_CREDIT = 100.0; //massimo credito
    private final double MIN_SCORE = 4.5; //minimo punteggio

    public CPUPlayer(String name) {
        super(name);
    }

    /**Chiede un'altra carta
     *
     * @return
     */
    public boolean isGoodScore() {
        if (getScore() > MIN_SCORE) {
            return true;
        }
        return false;
    }

    /**richiede una puntata
     *
     * @return
     */
    public double requestBet() {
        return credit / 10 + 1;
    }
}