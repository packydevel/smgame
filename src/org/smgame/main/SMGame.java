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
        
        System.out.println("Sequenza iniziale di Carte");
        currentDeck.print();

        System.out.println("");

        System.out.println("Sequenza di Carte dopo mescolamento:");
        currentDeck.shuffle();
        currentDeck.print();
    }
}
