/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.main;

import org.smgame.core.card.Deck;

/**
 *
 * @author packyuser
 */
public class SMGame {

    private static Deck currentDeck;

    public static void main(String args[]) {
        currentDeck=Deck.getInstance();
        currentDeck.shuffle();
        currentDeck.print();
    }
}
