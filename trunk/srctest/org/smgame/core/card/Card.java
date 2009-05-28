package org.smgame.core.card;

import java.io.Serializable;
import javax.swing.ImageIcon;
import org.smgame.util.Common;

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
    private final ImageIcon backImage=new ImageIcon(Common.getResourceCards() + "dorso.gif");
    private double value; //valore

    /**Costruttore con tre parametri
     * 
     * @param point punto
     * @param suit seme 
     * @param value valore
     */
    public Card(Point point, Suit suit, double value, ImageIcon image, ImageIcon icon) {
        this.point = point;
        this.suit = suit;
        this.value = value;
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

    public ImageIcon getBackImage(){
        return backImage;
    }

    public ImageIcon getFrontImage() {
        return image;
    }

    /**Restituisce il valore della carta
     * 
     * @return valore es
     */
    public double getValue() {
        return this.value;
    }

    /**Restituisce 
     * 
     */
    @Override
    public String toString() {
        return point + " di " + suit + " => " + value;
    }
}//end class