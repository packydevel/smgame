package org.smgame.main;

import org.smgame.core.card.Card;
import org.smgame.core.card.Point;
import org.smgame.core.card.Suit;

/**Classe settaggi gioco
 *
 * @author luca
 * @author pasquale
 */
public class GameSetting {

    private final int MIN_PLAYERS = 2;
    private final int MAX_PLAYERS = 12;
    private int players;
    private final int MIN_MANCHES = 1;
    private final int MAX_MANCHES = 100;
    private int manches;
    private Card jolly;
    private int jollyValue;
    private String smDAmblePaid;


    /**Costruttore
     *
     */
    public GameSetting() {
        players=4;
        manches=10;
        jolly = new Card(Point.Re, Suit.Danari);
        smDAmblePaid="double";
        
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

    public void setManches(int manches) {
        if (manches < MIN_PLAYERS || manches > MAX_PLAYERS) {
            System.out.println("Numero errato di manches!!!");
        } else {
            this.players = manches;
        }

    }

    /**Restituisce il numero giocatori
     *
     */
    public int getNumPlayers() {
        return players;
    }
}