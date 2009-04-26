/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.main;

import org.smgame.core.card.Deck;
import org.smgame.core.player.PlayerList;

/**
 *
 * @author packyuser
 */
public class Game {

    private static Deck deck;
    private static PlayerList playerList;

    public Game() {

        deck = Deck.getInstance();
        playerList = PlayerList.getInstance();
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
