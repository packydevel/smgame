package org.smgame.main;

import org.smgame.core.card.Card;
import org.smgame.core.card.Deck;
import org.smgame.core.card.Point;
import org.smgame.core.card.Suit;

/**Classe settaggi gioco
 *
 * @author luca
 * @author pasquale
 */
public class GameSetting {

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
    private String smDAmblePaid;

    //Costruttore privato
    private GameSetting() {
        manches = 10;
        credit = MIN_CREDIT;
        //jolly = Deck.getInstance().getSelectedCard(Point.Re, Suit.Danari);
    }

    /**Restituisce l'istanza dei settaggi di gioco
     *
     * @return
     */
    public static GameSetting getInstance() {
        if (gameSetting == null) {
            gameSetting = new GameSetting();
        }
        return gameSetting;
    }

    /**Azzera/resetta l'istanza dei settaggi
     *
     */
    public void resetInstance() {
        players = 4;
        manches = 10;
        credit = MIN_CREDIT;
        jolly = Deck.getInstance().getSelectedCard(Point.Re, Suit.Danari);
        smDAmblePaid = "double";
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
     * @return
     */
    public int getManches() {
        return manches;
    }

    /**imposta il numero di manches
     *
     * @param manches
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
     * @param credit
     */
    public void setCredit(double credit) {
        if (credit < MIN_CREDIT || manches > MAX_CREDIT) {
            System.out.println("Inserire un credito corretto!!!");
        } else {
            this.credit = credit;
        }
    }

    /**Restituisce il numero giocatori
     *
     */
    public int getNumPlayers() {
        return players;
    }

}//end class
