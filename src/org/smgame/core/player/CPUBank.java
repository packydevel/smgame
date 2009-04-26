/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core.player;

/**
 *
 * @author packyuser
 */
public class CPUBank extends Player {

    private final double MIN_TOTAL_VALUE = 4.0;
    private final double MIN_MARGIN = 0.5;

    public boolean askAnotherCard() {
        if (totalValue < MIN_TOTAL_VALUE || (gameEngine.existWeakPlayer(totalValue, MIN_MARGIN))) {
            return true;
        } else {
            return false;
        }
    }
}
