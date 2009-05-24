package org.smgame.core.player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**Classe lista giocatori
 *
 * @author luca
 * @author pasquale
 */
public class PlayerList {

    private static PlayerList playerList = null;
    private LinkedList<Player> playerAL = new LinkedList<Player>();

    //costruttore privato
    private PlayerList() { }

    
    public List<Player> getPlayerAL() {
        return playerAL;
    }

    /**Restituisce l'istanza della lista corrente dei giocatori
     *
     * @return lista giocatori
     */
    public static PlayerList getInstance() {
        if (playerList == null)
            playerList = new PlayerList();        
        return playerList;
    }

        /**Esiste giocatore debole (?)
     *
     * @param totalValue valore totale
     * @param minMargin margine minimo
     * @return booleano sul controllo del giocatore
     */
    public boolean existWeakPlayer(double totalValue, double minMargin) {
        LinkedList<Player> playerList = (LinkedList<Player>) PlayerList.getInstance().getPlayerAL();
        for (Player p : playerList) {
            if (p instanceof CPUPlayer) {
                p = (CPUPlayer) p;
            } else {
                p = (HumanPlayer) p;
            }

            if (p.getScore() <= (totalValue + minMargin)) {
                return true;
            }
        }
        return false;
    }
}