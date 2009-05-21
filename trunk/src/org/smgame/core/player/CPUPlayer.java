package org.smgame.core.player;

/**Classe giocatore CPU
 *
 * @author luca
 * @author pasquale
 */
public class CPUPlayer extends Player {

    private final double MAX_CREDIT = 100.0; //massimo credito
    private final double MIN_SCORE = 4.5; //minimo punteggio

    public CPUPlayer(String name) {
        super(name);
    }

    /**Chiede un'altra carta
     *
     * @return
     */
    public boolean askAnotherCard() {
        if (score < MIN_SCORE) {
            return true;
        } else {
            return false;
        }
    }

    /**richiede una puntata
     *
     * @return
     */
    public double requestBet() {
        return credit / 10 + 1;
    }
}