/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core.player;

import java.util.ArrayList;
import org.smgame.core.GameEngine;
import org.smgame.core.card.Card;

/**
 *
 * @author packyuser
 */
public abstract class Player {

    protected double credit;
    protected double totalValue;
    protected ArrayList<Card> CardList;
    protected GameEngine gameEngine = GameEngine.getInstance();

    public double getCredit() {
        return credit;
    }

    public double getTotalValue() {
        return totalValue;
    }

    /*
    protected abstract boolean askAnotherManche();

    protected abstract boolean askAnotherCard();

    protected abstract double requestBet();

    protected abstract void setCredit(double amount);

    protected abstract double getCredit();
     */
}
