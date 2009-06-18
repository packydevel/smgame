package org.smgame.core.card;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import org.smgame.util.Common;

/**Classe Mazzo
 * contiene le 40carte da gioco
 * 
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class Deck implements Serializable {

    private final double[] ALL_VALUE = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 0.5, 0.5, 0.5};//tutti i valori
    private final ArrayList<Card> CARDS = new ArrayList<Card>();
    
    private ArrayList<Card> onGameCardList = new ArrayList<Card>();
    private ArrayList<Card> offGameCardList = new ArrayList<Card>();   
    private transient Iterator<Card> onGameCardsIterator;
    private Card nextCard; //prossima carta
    private boolean emptyDeck;

    /**Costruttore
     *
     */
    public Deck() {
        String img = "";
        ImageIcon frontImage;
        int i;
        Card c;

        for (Suit suit : Suit.values()) {
            i = 0;
            for (Point point : Point.values()) {
                if (suit == Suit.Bastoni) {
                    img = "B";
                } else if (suit == Suit.Coppe) {
                    img = "C";
                } else if (suit == Suit.Danari) {
                    img = "D";
                } else if (suit == Suit.Spade) {
                    img = "S";
                }

                if (i < 9) {
                    img += "0";
                }
                img += i + 1 + ".jpg";

                String resource = Common.getResourceCards("napoletane") + img;
                URL url = Card.class.getResource(resource);
                frontImage = new ImageIcon(url);
                if (point == Point.Re && suit == Suit.Danari) {
                    c = new JollyCard(point, suit, ALL_VALUE[i], frontImage);
                } else {
                    c = new Card(point, suit, ALL_VALUE[i], frontImage);
                }
                CARDS.add(c);

                i++;
            }
        }

        onGameCardList.addAll(CARDS);
        onGameCardsIterator = onGameCardList.iterator();
    } //end costruttore

    /**Resetta l'istanza del mazzo
     * 
     */
    public void resetInstance() {
        onGameCardList.clear();
        offGameCardList.clear();
        onGameCardList.addAll(CARDS);
        onGameCardsIterator = onGameCardList.iterator();
        emptyDeck = false;
    }

    /**Mischia il mazzo
     * 
     */
    public void shuffle() {
        Collections.shuffle(onGameCardList, new Random(System.currentTimeMillis()));
    }

    /**Restituisce la prossima carta
     * 
     * @return prossima carta
     */
    public Card getNextCard() {

        if (onGameCardsIterator == null) {
            onGameCardsIterator = onGameCardList.iterator();
        }

        if (!onGameCardsIterator.hasNext()) {
            onGameCardList.addAll(offGameCardList);
            offGameCardList.clear();
            Collections.shuffle(onGameCardList, new Random(System.currentTimeMillis()));
            onGameCardsIterator = onGameCardList.iterator();
            emptyDeck = true;
        }

        nextCard = (Card) onGameCardsIterator.next();
        onGameCardsIterator.remove();
        return nextCard;
    }//end getNextCard

    /**Aggiunge la lista di carte alla lista di carte fuori gioco
     *
     * @param cardList lista di carte
     */
    public void addOffGameCards(List<Card> cardList) {
        offGameCardList.addAll(cardList);
    }

    /**Stampa le carte
     * 
     */
    public void print() {
        for (Card c : CARDS) {
            System.out.println(c.toString());
        }
    }//end print

    /**Restituisce la carta selezionata
     *
     * @param point punto
     * @param suit seme
     * @return carta selezionata
     */
    public Card getSelectedCard(Point point, Suit suit) {
        for (Card c : CARDS) {
            if (c.getPoint() == point && c.getSuit() == suit) {
                return c;
            }
        }
        return null;
    }

    /**Restituisce lo stato del mazzo vuoto
     *
     * @return booleano di risposta
     */
    public boolean isEmptyDeck() {
        return emptyDeck;
    }

    /**Imposta lo stato del mazzo vuoto
     *
     * @param isEmptyDeck booleano
     */
    public void setEmptyDeck(boolean isEmptyDeck) {
        this.emptyDeck = isEmptyDeck;
    }
} //end class