package org.smgame.core.card;

import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**Classe Carta
 * rappresenta la singola carta del mazzo
 * 
 * @author pasquale
 * @author luca
 */
public class Card implements Serializable {
    //variabili

    private Point point; //punto
    private Suit suit; //seme
    private ImageIcon image;
    private ImageIcon icon;
    private double value; //valore

    /**Costruttore con tre parametri
     * 
     * @param point punto
     * @param suit seme 
     * @param value valore
     */
    public Card(Point point, Suit suit, ImageIcon image, ImageIcon icon) {
        this.point = point;
        this.suit = suit;
        this.image = image;
        this.icon = icon;
    }

    /**Restituisce il punto
     * 
     * @return stringa punto
     */
    public Point getPoint() {
        return this.point;
    }

    /**Restituisce il seme
     * 
     * @return stringa seme
     */
    public Suit getSuit() {
        return this.suit;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public ImageIcon getImage() {
        return image;
    }

    /**Restituisce il valore della carta
     * 
     * @return valore es
     */
    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /**Restituisce 
     * 
     */
    @Override
    public String toString() {
        return point + " di " + suit + " => " + value;
    }
}//end class