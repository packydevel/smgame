package org.smgame.core.card;

import java.io.Serializable;
import javax.swing.ImageIcon;
import org.smgame.util.Common;

/**Classe Carta
 * rappresenta la singola carta del mazzo
 * 
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class Card implements Serializable {
    //variabili

    private Point point; //punto
    private Suit suit; //seme
    private ImageIcon frontImage;
    private static final ImageIcon backImage = new ImageIcon(Card.class.getResource(Common.getResourceCards("napoletane") + "dorso.jpg"));
    private double value; //valore

    /**Costruttore con tre parametri
     * 
     * @param point descrizione del punto
     * @param suit seme 
     * @param value valore
     * @param frontImage immagine della carta
     */
    public Card(Point point, Suit suit, double value, ImageIcon frontImage) {
        this.point = point;
        this.suit = suit;
        this.value = value;
        this.frontImage = frontImage;
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

    /**Restituisce il dorso della carta sotto forma di imageicon
     *
     * @return dorso
     */
    public static ImageIcon getBackImage() {
        return backImage;
    }

    /**restituisce l'immagine della carta
     *
     * @return carta frontale
     */
    public ImageIcon getFrontImage() {
        return frontImage;
    }

    /**Restituisce il valore della carta
     * 
     * @return valore
     */
    public double getValue() {
        return this.value;
    }

    /**Stampa la carta
     * descrizione, seme, valore
     *
     * @return carta stampata
     */
    @Override
    public String toString() {
        return point + " di " + suit + " => " + value;
    }
}//end class
