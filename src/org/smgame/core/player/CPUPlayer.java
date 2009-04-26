/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.core.player;

/**
 *
 * @author packyuser
 */
public class CPUPlayer extends Player {

    private final double MAX_CREDIT=100.0;

    private final double MIN_SCORE=4.5;

    public boolean askAnotherCard() {
        if (score < MIN_SCORE) {
            return true;
        } else {
            return false;
        }
    }
}
