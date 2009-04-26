/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core.player;

import java.util.ArrayList;
import org.smgame.core.GameEngine;

/**
 *
 * @author packyuser
 */
public class PlayerList {

    private static PlayerList playerList = null;
    private ArrayList<Player> playerAL = new ArrayList<Player>();

    private PlayerList() {
        playerAL.add(new CPUBank());
        playerAL.add(new CPUPlayer());
        playerAL.add(new CPUPlayer());
        playerAL.add(new HumanPlayer());
    }

    public ArrayList<Player> getPlayerAL() {
        return playerAL;
    }

    public static PlayerList getInstance() {
        if (playerList == null) {
            playerList = new PlayerList();
        }

        return playerList;
    }
}