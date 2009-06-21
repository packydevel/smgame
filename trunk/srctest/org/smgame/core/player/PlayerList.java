package org.smgame.core.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**Classe lista giocatori
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class PlayerList implements Serializable {

    private LinkedList<Player> playerAL = new LinkedList<Player>();

    /**Costruttore vuoto
     *
     */
    public PlayerList() {
    }

    public void addPlayer(Player player) {
        playerAL.add(player);
    }

    public Player getPlayer(int order) {
        return playerAL.get(order);
    }

    public int indexOfPlayer(Player player) {
        return playerAL.indexOf(player);
    }

    public int size() {
        return playerAL.size();
    }

    public Iterator<Player> getPlayerListIterator() {
        return playerAL.iterator();
    }

    public Player selectRandomPlayer() {
        List<Player> tempList = new ArrayList<Player>(playerAL);
        Collections.shuffle(tempList, new Random(System.currentTimeMillis()));
        return tempList.get(0);
    }

    /**Verifica se esiste almeno un player in bancarotta
     *
     * @return true = player bancarotta
     */
    public boolean existsBankruptPlayer() {
        for (Player p : playerAL) {
            if (p.getCredit() <= 0.00) {
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

    public List<Player> maxPlayerScoreList() {
        int count;
        ArrayList<Player> playerSubList = new ArrayList();
        ArrayList<Player> tempList = new ArrayList(playerAL);

        Collections.sort(tempList, new PlayerCreditComparator());

        count = Collections.frequency(tempList, tempList.get(0));

        for (int i = 0; i < count; i++) {
            playerSubList.add(tempList.get(i));
        }

        return playerSubList;
    }
//
//    /**restituisce gli stati dei giocatori
//     *
//     * @return hashmap giocatore stato
//     */
//    Map<Player, PlayerStatus> getPlayerStatusMap() {
//        HashMap<Player, PlayerStatus> map = new HashMap<Player, PlayerStatus>();
//
//        for (Player p : playerAL) {
//            map.put(p, p.getStatus());
//        }
//
//        return map;
//    }
//
//    /**Restituisce il punteggio visibile di ciascun giocatore
//     *
//     * @return hashmap giocatore punteggio
//     */
//    Map<Player, Double> getPlayerVisibleScoreMap() {
//        HashMap<Player, Double> map = new HashMap<Player, Double>();
//
//        for (Player p : playerAL) {
//            map.put(p, p.getVisibleScore());
//        }
//
//        return map;
//    }
}
