package org.smgame.core.card;

import java.io.Serializable;
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
 * @author pasquale
 * @author luca
 */
public class Deck implements Serializable {

    private static Deck currentDeck = null; //mazzo corrente

    //costanti
    private final int TOTAL_CARDS = 40; //carte totali
    private final int TOTAL_CARDS_PER_SUIT = 10; //carte totali per seme
    private final double[] ALL_VALUE = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 0.5, 0.5, 0.5};//tutti i valori
    private final ArrayList<Card> CARDS = new ArrayList<Card>();

    //variabili
    private ArrayList<Card> onGameCardList = new ArrayList<Card>();
    private ArrayList<Card> offGameCardList = new ArrayList<Card>();
    private int totalRemainingCards = TOTAL_CARDS; //carte rimanenti
    private transient Iterator<Card> onGameCardsIterator;
    private Card nextCard; //prossima carta
    private boolean emptyDeck;

    //costruttore privato
    private Deck() {
        String img = "";
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

                ImageIcon icon = new ImageIcon(Common.getResourceCards("napoletane") + img);
                if (point == Point.Re && suit == Suit.Danari) {
                    c = new JollyCard(point, suit, ALL_VALUE[i], icon, icon);
                } else {
                    c = new Card(point, suit, ALL_VALUE[i], icon, icon);
                }
                CARDS.add(c);

                i++;
            }
        }

        onGameCardList.addAll(CARDS);
        onGameCardsIterator = onGameCardList.iterator();
    } //end costruttore

    /**Restituisce l'istanza corrente di mazzo
     * 
     * @return Deck mazzo
     */
    public static Deck getInstance() {
        if (currentDeck == null) {
            currentDeck = new Deck();
        } else {
            currentDeck.resetInstance();
        }

        return currentDeck;
    } //end getInstance

    public void resetInstance() {
        onGameCardList.clear();
        offGameCardList.clear();
        onGameCardList.addAll(currentDeck.CARDS);
        onGameCardsIterator = onGameCardList.iterator();
        emptyDeck = false;
    }

    /**
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

        if (onGameCardList == null) {
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
        System.out.println("Il numero di carte rimaste è: " + onGameCardList.size());
        //totalRemainingCards--;

        return nextCard;
    }//end getNextCard

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

    public Card getSelectedCard(Point point, Suit suit) {
        for (Card c : CARDS) {
            if (c.getPoint() == point && c.getSuit() == suit) {
                return c;
            }
        }
        return null;
    }

    public boolean isEmptyDeck() {
        return emptyDeck;
    }

    public void setEmptyDeck(boolean isEmptyDeck) {
        this.emptyDeck = isEmptyDeck;
    }
} //end class