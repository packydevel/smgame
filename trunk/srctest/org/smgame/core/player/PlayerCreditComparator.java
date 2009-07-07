package org.smgame.core.player;

import java.util.Comparator;

/**Classe Comparatore di crediti giocatori
 * lo scopo è quello di trovare il massimo credito
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class PlayerCreditComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.getCredit() > p2.getCredit()) {
            return -1;
        } else if (p1.getCredit() < p2.getCredit()) {
            return +1;
        } else {
            return 0;
        }
    }
}
