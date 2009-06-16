package org.smgame.core;

import java.io.Serializable;
import org.smgame.core.card.Card;

/**Classe settaggi gioco
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class GameSetting implements Serializable {

    private static GameSetting gameSetting = null;
    private final int MIN_PLAYERS = 2;
    private final int MAX_PLAYERS = 12;
    private int players;
    private final int MIN_MANCHES = 1;
    private final int MAX_MANCHES = 100;
    private int manches;
    private final double MIN_CREDIT = 1000.00;
    private final double MAX_CREDIT = 1000000.00;
    private double credit;
    private Card jolly;
    private int jollyValue;
    private String kingSMPayRule;

    /**Costruttore
     *
     */
    public GameSetting() {
        manches = 10;
        credit = MIN_CREDIT;
        //jolly = Deck.getInstance().getSelectedCard(Point.Re, Suit.Danari);
        kingSMPayRule = "double";
    }

    /**imposta il numero giocatori
     *
     * @param numPlayers numero
     */
    public void setNumPlayers(int numPlayers) {
        if (numPlayers < MIN_MANCHES || numPlayers > MAX_MANCHES) {
            System.out.println("Numero errato di giocatori!!!");
        } else {
            this.players = numPlayers;
        }
    }

    /**restituisce il numero di manches
     *
     * @return numero
     */
    public int getManches() {
        return manches;
    }

    /**imposta il numero di manches
     *
     * @param manches numero
     */
    public void setManches(int manches) {
        if (manches < MIN_PLAYERS || manches > MAX_PLAYERS) {
            System.out.println("Numero errato di manches!!!");
        } else {
            this.players = manches;
        }
    }

    /**imposta il credito
     *
     * @param credit credito
     */
    public void setCredit(double credit) {
        if (credit < MIN_CREDIT || manches > MAX_CREDIT) {
            System.out.println("Inserire un credito corretto!!!");
        } else {
            this.credit = credit;
        }
    }
}//end class