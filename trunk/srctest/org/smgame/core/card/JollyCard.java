package org.smgame.core.card;

import javax.swing.ImageIcon;

/**Classe jollycard, Gestisce il valore della carta jolly detta anche matta
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class JollyCard extends Card {

    private static final long serialVersionUID = 6864904185042947614L;
    private static final double[] VALUES = {0.5, 1, 2, 3, 4, 5, 6, 7};

    /**Costruttore
     *
     * @param point punto
     * @param suit seme
     * @param value valore
     * @param frontImage immagine
     */
    public JollyCard(Point point, Suit suit, double value, ImageIcon frontImage) {
        super(point, suit, value, frontImage);
    }

    /**Calcola e restituisce il massimo valore della carta jolly
     *
     * @param score punteggio
     * @return valore jolly
     */
    public static double getBestValue(Double score) {
        Double value=VALUES[0];

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