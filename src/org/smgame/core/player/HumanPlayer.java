package org.smgame.core.player;

import java.io.Serializable;

/**Classe giocatore umano
 *
 * @author luca
 * @author pasquale
 */
public class HumanPlayer extends Player implements Serializable {

   public HumanPlayer(String name) {
        super(name);
        MIN_SCORE = 4.0;
        MIN_MARGIN = 0.5;
    }

    /**Chiede un'altra mano
     *
     * @return
     */
    public boolean askAnotherManche() {
        return true;
    }

    /**Chiede un'altra carta
     *
     * @return
     */
    public boolean askAnotherCard() {
        //TODO: correggere o decidere cazzo fare
        if (score < MIN_SCORE || (playerList.existsWeakPlayer(score, MIN_MARGIN)))
            return true;
        else
            return false;
        //
    }

    /**Richiede una puntata
     *
     * @return
     */
    public double requestBet() {
        return 0;
    }

    /**Imposta il credito
     *
     * @param amount
     */
    public void setCredit(double amount) {
        this.credit+=amount;
    }

    /**Restituisce il credito
     *
     * @return
     */
    public double getCredit() {
        return credit;
    }
}