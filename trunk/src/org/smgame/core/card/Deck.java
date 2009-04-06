/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core.card;

/**
 *
 * @author packyuser
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Deck {

    final String[] ALL_SUIT = {"Danari", "Spade", "Coppe", "Bastoni"};
    final String[] ALL_POINT = {"Asso", "Due", "Tre", "Quattro", "Cinque",
        "Sei", "Sette", "Donna", "Fante", "Re"};
    final double[] ALL_VALUE = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 0.5, 0.5, 0.5};
    private final ArrayList<Card> CARDS=new ArrayList<Card>();
    final int TOTAL_CARDS = 40;
    final int TOTAL_CARDS_PER_SUIT = 10;
    int TOTAL_REMAINING_CARDS=TOTAL_CARDS;
    private static Deck currentDeck = null;
    private Iterator<Card> iCard;
    private Card nextCard;

    private Deck() {

        for (int i = 0; i < TOTAL_CARDS_PER_SUIT; i++) {
            CARDS.add(new Card(ALL_POINT[i], ALL_SUIT[0], ALL_VALUE[i]));
            CARDS.add(new Card(ALL_POINT[i], ALL_SUIT[1], ALL_VALUE[i]));
            CARDS.add(new Card(ALL_POINT[i], ALL_SUIT[2], ALL_VALUE[i]));
            CARDS.add(new Card(ALL_POINT[i], ALL_SUIT[3], ALL_VALUE[i]));

        }

        iCard =CARDS.iterator();
    }

    public static Deck getInstance() {
        if (currentDeck == null) {
            currentDeck = new Deck();
        }
        return currentDeck;
    }

    public void shuffle() {
        Collections.shuffle(CARDS);
    }

    public Card getNextCard() {
        if (iCard.hasNext()) {
            nextCard=iCard.next();
            TOTAL_REMAINING_CARDS--;
        } else {
            nextCard=null;
        }

        return nextCard;
    }

    public void print() {
       for (Card c: CARDS) {
           System.out.println(c.toString());
       }
    }
}
