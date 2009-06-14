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
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class PlayerList implements Serializable {

    private static PlayerList playerList = null;
    private LinkedList<Player> playerAL = new LinkedList<Player>();

    /**Costruttore vuoto
     *
     */
    public PlayerList() { }

    /**REstituisce la lista di giocatori
     *
     * @return lista
     */
    public List<Player> getPlayerAL() {
        return playerAL;
    }

    /**Esiste giocatore debole
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

    /**Verifica se esiste almeno un player in bancarotta
     *
     * @return true = player bancarotta
     */
    public boolean existsBankruptPlayer() {
        for (Player p : playerAL) {
            if (p.getCredit() == 0 && p.getRole() == PlayerRole.Normal) {
                return true;
            }
        }
        return false;
    }

    /**Restituisce il primo giocatore ad aver fatto SMreale per il cambio di mazziere
     *
     * @param player vecchio mazziere
     * @return nuovo mazziere
     */
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

    /**restituisce gli stati dei giocatori
     *
     * @return hashmap giocatore stato
     */
    Map<Player, PlayerStatus> getPlayerStatusMap() {
        HashMap<Player, PlayerStatus> map = new HashMap<Player, PlayerStatus>();

        for (Player p : playerAL) {
            map.put(p, p.getStatus());
        }

        return map;
    }

    /**Restituisce il punteggio visibile di ciascun giocatore
     *
     * @return hashmap giocatore punteggio
     */
    Map<Player, Double> getPlayerVisibleScoreMap() {
        HashMap<Player, Double> map = new HashMap<Player, Double>();

        for (Player p : playerAL) {
            map.put(p, p.getVisibleScore());
        }

        return map;
    }
}
