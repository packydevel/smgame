package org.smgame.core.player;

/**Classe giocatore umano
 *
 * @author luca
 * @author pasquale
 */
public class HumanPlayer extends Player {

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
        return true;
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