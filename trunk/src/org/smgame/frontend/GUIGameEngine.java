/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import org.smgame.core.player.PlayerList;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;

/**
 *
 * @author packyuser
 */
public class GUIGameEngine {

    private static Game game;

    static void createGame(String gameName, GameSetting gamesetting, PlayerList playerList) {
        game.create(gameName, gamesetting, playerList);
    }

    static Game getGame() {
        return game;
    }

    static void saveGame() {
        try {
            game.save();
        } catch (Exception e) {
            System.out.println("Errore nel salvataggio!!!");
        }
    }
}
