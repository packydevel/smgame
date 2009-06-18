/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core.player;

import java.util.Comparator;

/**
 *
 * @author packyuser
 */
public class PlayerCreditComparator implements Comparator<Player> {

    public int compare(Player p1, Player p2) {
        if (p1.getCredit() > p2.getCredit()) {
            return +1;
        } else if (p1.getCredit() < p2.getCredit()) {
            return -1;
        } else {
            return 0;
        }
    }
}
