package org.smgame.main;

/**Classe settaggi gioco
 *
 * @author luca
 * @author pasquale
 */
public class GameSetting {

    private final int MIN_PLAYERS = 2;
    private final int MAX_PLAYERS = 12;
    private int numPlayers = 4;

    /**imposta il numero giocatori
     *
     * @param numPlayers numero
     */
    public void setNumPlayers(int numPlayers) {
        if (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS)
            System.out.println("Numero errato di giocatori!!!");
        else 
            this.numPlayers = numPlayers;        
    }

    /**Costruttore
     *
     */
    public GameSetting() { }

    /**Restituisce il numero giocatori
     *
     */
    public int getNumPlayers() {
        return numPlayers;
    }
}