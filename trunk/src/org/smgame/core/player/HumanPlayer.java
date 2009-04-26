/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.core.player;

/**
 *
 * @author packyuser
 */
public class HumanPlayer extends Player {

    public boolean askAnotherManche() {
        return true;
    }

    public boolean askAnotherCard() {

        return true;
    }

    public double requestBet() {
        return 0;
    }

    public void setCredit(double amount) {
        this.credit+=amount;
    }

    public double getCredit() {
        return credit;
    }
}
