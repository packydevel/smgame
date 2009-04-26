/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core;

import java.util.ArrayList;
import org.smgame.core.player.CPUBank;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.main.Game;

/**
 *
 * @author packyuser
 */
public class GameEngine {

    private static GameEngine gameEngine = null;
    private Game game;
    private final double MAX_CREDIT = 64000;
    private final double MAX_SCORE = 7.5;

    private GameEngine() {
    }

    public static GameEngine getInstance() {
        if (gameEngine == null) {
            gameEngine = new GameEngine();
        }

        return gameEngine;
    }

    public boolean existWeakPlayer(double totalValue, double minMargin) {
        ArrayList<Player> playerList = PlayerList.getInstance().getPlayerAL();
        for (Player p : playerList) {
            if (p instanceof CPUBank) {
                p = (CPUBank) p;
            } else if (p instanceof CPUPlayer) {
                p = (CPUPlayer) p;
            } else {
                p = (HumanPlayer) p;
            }

            if (p.getTotalValue() <= (totalValue + minMargin)) {
                return true;
            }
        }
        return false;
    }
}
