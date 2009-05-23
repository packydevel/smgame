package org.smgame.main;

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

    /**imposta il numero giocatori
     *
     * @param numPlayers numero
     */
    public void setNumPlayers(int numPlayers) {
        if (numPlayers < MIN_MANCHES || numPlayers > MAX_MANCHES)
            System.out.println("Numero errato di giocatori!!!");
        else 
            this.players = numPlayers;
    }

    public void setManches(int manches) {
        if (manches < MIN_PLAYERS || manches > MAX_PLAYERS)
            System.out.println("Numero errato di manches!!!");
        else
            this.players = manches;

    }

    /**Costruttore
     *
     */
    public GameSetting() { }

    /**Restituisce il numero giocatori
     *
     */
    public int getNumPlayers() {
        return players;
    }
}