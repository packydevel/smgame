/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.util;

import org.smgame.core.card.Card;

/**
 *
 * @author packyuser
 */
public class ScoreOverflowException extends Exception {

    Card card;

    public ScoreOverflowException(String message, Card card) {
        super(message);
        this.card=card;
    }

    public Card getCardException(){
        return card;
    }
}
