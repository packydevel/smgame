/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.main;

import org.smgame.core.GameEngine;
import org.smgame.core.card.Deck;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.PlayerList;

/**
 *
 * @author packyuser
 */
public class Game {

    private static GameSetting gameSetting;
    private static GameEngine gameEngine;
    private static Deck deck;
    private static PlayerList playerList;

    public Game(GameSetting gameSetting) {

        char playerType='H';

        this.gameSetting = gameSetting;
        deck = Deck.getInstance();
        playerList = PlayerList.getInstance();

        for (int i = 1; i <= gameSetting.getNumPlayers(); ++i) {
            if (playerType=='H')
                playerList.getPlayerAL().add(new HumanPlayer());
            else
                playerList.getPlayerAL().add(new CPUPlayer());
        }

        gameEngine = GameEngine.getInstance(gameSetting, deck, playerList);
    }

    public void printTest() {
        System.out.println("Sequenza iniziale di Carte");
        deck.print();

        System.out.println("");

        System.out.println("Sequenza di Carte dopo mescolamento:");
        deck.shuffle();
        deck.print();
    }
}
