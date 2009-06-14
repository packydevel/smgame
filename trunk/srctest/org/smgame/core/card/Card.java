package org.smgame.core.card;

import java.awt.Toolkit;
import java.io.Serializable;
import javax.swing.ImageIcon;

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
    private ImageIcon image;
    private ImageIcon icon;
//    private transient final ImageIcon backImage=new ImageIcon(Common.getResourceCards("napoletane") + "dorso.jpg");
    private transient final ImageIcon backImage=new ImageIcon(Toolkit.getDefaultToolkit().createImage("org/smgame/resource/cardimage/napoletane/dorso.jpg"));
//        private transient final ImageIcon backImage=new ImageIcon(Card.class.getResource("org/smgame/resource/cardimage/napoletane/dorso.jpg"));
    private double value; //valore

    /**Costruttore con tre parametri
     * 
     * @param point descrizione del punto
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

    /**Restituisce l'icona come imageicon
     * 
     * @return icona
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**Restituisce il dorso della carta sotto forma di imageicon
     *
     * @return dorso
     */
    public ImageIcon getBackImage(){
        return backImage;
    }

    /**restituisce l'immagine della carta
     *
     * @return carta frontale
     */
    public ImageIcon getFrontImage() {
        return image;
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