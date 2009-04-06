/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.core.card;

/**
 *
 * @author packyuser
 */
public class Card {

    private String point;
    private String suit;
    private double value;

    public Card(String point, String suit, double value) {
        this.point = point;
        this.suit = suit;
        this.value = value;
    }

    public String getPoint() {
        return this.point;
    }

    public String getSuit() {
        return this.suit;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return point + " di " + suit + " => "+ value;
    }

}
