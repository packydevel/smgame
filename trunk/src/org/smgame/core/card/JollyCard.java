/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core.card;

import javax.swing.ImageIcon;

/**
 *
 * @author packyuser
 */
public class JollyCard extends Card {

    private static final double[] VALUES = {0.5, 1, 2, 3, 4, 5, 6, 7, 8};
    
    public JollyCard(Point point, Suit suit, double value, ImageIcon image, ImageIcon icon) {
        super(point, suit, value, image, icon);
    }

    public static double getBestValue(Double score) {
        Double value=VALUES[0], max = score;

        if (score > 7.5) {
            return 0.0;
        } else {
            for (int i = 0; i < VALUES.length; i++) {
                if (score + VALUES[i] <= 7.5) {
                    value = VALUES[i];
                }

            }
            return value;
        }
    }
}
