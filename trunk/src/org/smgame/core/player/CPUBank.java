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

    private final double MIN_SCORE = 4.0;
    private final double MIN_MARGIN = 0.5;

    public boolean askAnotherCard() {
        /*TODO: correggere o decidere cazzo fare
        if (score < MIN_SCORE || (gameEngine.existWeakPlayer(score, MIN_MARGIN)))
            return true;
        else
            return false;
        */
        return false;
    }
}
