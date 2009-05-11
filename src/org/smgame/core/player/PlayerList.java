package org.smgame.core.player;

import java.util.ArrayList;

/**Classe lista giocatori
 *
 * @author luca
 * @author pasquale
 */
public class PlayerList {

    private static PlayerList playerList = null;
    private ArrayList<Player> playerAL = new ArrayList<Player>();

    //costruttore privato
    private PlayerList() { }

    
    public ArrayList<Player> getPlayerAL() {
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
}