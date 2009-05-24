package org.smgame.core.card;

import java.io.Serializable;

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
    private double value; //valore

    /**Costruttore con tre parametri
     * 
     * @param point punto
     * @param suit seme 
     * @param value valore
     */
    public Card(Point point, Suit suit) {
        this.point = point;
        this.suit = suit;
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