package org.smgame.core.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**Classe lista giocatori
 *
 * @author luca
 * @author pasquale
 */
public class PlayerList implements Serializable {

    private static PlayerList playerList = null;
    private LinkedList<Player> playerAL = new LinkedList<Player>();

    //costruttore privato
    private PlayerList() {
    }

    public List<Player> getPlayerAL() {
        return playerAL;
    }

    /**Restituisce l'istanza della lista corrente dei giocatori
     *
     * @return lista giocatori
     */
    public static PlayerList getInstance() {
        if (playerList == null) {
            playerList = new PlayerList();
        }
        return playerList;
    }

    public void resetInstance() {
        playerAL.clear();
    }

    /**Esiste giocatore debole (?)
     *
     * @param totalValue valore totale
     * @param minMargin margine minimo
     * @return booleano sul controllo del giocatore
     */
    public boolean existsWeakPlayer(double totalValue, double minMargin) {
        for (Player p : (Player[]) playerAL.toArray()) {
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

    public boolean existsBankruptPlayer() {
        for (Player p : playerAL) {
            if (p.getCredit() == 0 && p.getRole() == PlayerRole.Normal) {
                return true;
            }
        }
        return false;
    }

    public Player firstKingSM(Player player) {
        ArrayList<Player> tempList = new ArrayList(playerAL);
        int playerIndex = playerAL.indexOf(player);

        Collections.rotate(tempList, -playerIndex);

        for (Player p : tempList) {
            if (p.hasKingSM()) {
                return p;
            }
        }
        return null;
    }

    Map<Player, PlayerStatus> getPlayerStatusMap() {
        HashMap<Player, PlayerStatus> map = new HashMap<Player, PlayerStatus>();

        for (Player p : playerAL) {
            map.put(p, p.getStatus());
        }

        return map;
    }

    Map<Player, Double> getPlayerVisibleScoreMap() {
        HashMap<Player, Double> map = new HashMap<Player, Double>();

        for (Player p : playerAL) {
            map.put(p, p.getVisibleScore());
        }

        return map;
    }
}