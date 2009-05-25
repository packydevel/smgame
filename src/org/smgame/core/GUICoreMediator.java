/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core;

import java.util.ArrayList;
import java.util.List;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.PlayerList;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;

/**
 *
 * @author packyuser
 */
public class GUICoreMediator {

    private static ArrayList<Game> gameList;
    private static Game currentGame;

    public static boolean askForNewGame() {
        if (currentGame != null) {
            return false;
        }
        return true;
    }

    public static boolean askForLoadGame() {
        if (currentGame != null) {
            return false;
        }
        return true;
    }

    public static void createGame(String gameName, GameSetting gamesetting, List<String> playerName, List<Boolean> playerType) {
        PlayerList playerList = PlayerList.getInstance();

        for (int i = 0; i < playerName.size(); i++) {

            if (i > 0) {
                if (playerType.get(i - 1).booleanValue()) {
                    playerList.getPlayerAL().add(new CPUPlayer(playerName.get(i)));
                } else {
                    playerList.getPlayerAL().add(new HumanPlayer(playerName.get(i)));
                }
            } else {
                playerList.getPlayerAL().add(new HumanPlayer(playerName.get(i)));
            }
        }

        currentGame = Game.create(gameName, null, playerList);
    }

    public static void closeGame() {
        currentGame=null;
    }

    public static Game getGame() {
        return currentGame;
    }

    public static void saveGame() {
        try {
            currentGame.save();
        } catch (Exception e) {
            System.out.println("Errore nel salvataggio!!!");
        }

    }

    public static void loadGame() {
        try {
            GUICoreMediator.currentGame = Game.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore nel caricamento!!!");
        }
    }
}
