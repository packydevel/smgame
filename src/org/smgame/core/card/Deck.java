package org.smgame.core.card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import org.smgame.util.Common;

/**Classe Mazzo
 * contiene le 40carte da gioco
 * 
 * @author pasquale
 * @author luca
 */
public class Deck implements Serializable {
    //costanti

    //TODO: ma ha senso double?
    private final double[] ALL_VALUE = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 0.5, 0.5, 0.5};//tutti i valori
    private final ArrayList<Card> CARDS = new ArrayList<Card>();
    private ArrayList<Card> onGameCardList = new ArrayList<Card>();
    private ArrayList<Card> offGameCardList = new ArrayList<Card>();
    private final int TOTAL_CARDS = 40; //carte totali
    private final int TOTAL_CARDS_PER_SUIT = 10; //carte totali per seme
    //variabili
    protected int totalRemainingCards = TOTAL_CARDS; //carte rimanenti
    private static Deck currentDeck = null; //mazzo corrente
    private transient Iterator<Card> onGameCardsIterator;
    private Card nextCard; //prossima carta
    private boolean isEmptyDeck;

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

                ImageIcon icon = new ImageIcon(Common.getResourceCards() + img);
                if (point == Point.Re && suit == Suit.Danari) {
                    c = new JollyCard(point, suit, ALL_VALUE[i], icon, icon);
                } else {
                    c = new Card(point, suit, ALL_VALUE[i], icon, icon);
                }
                CARDS.add(c);

                i++;
            }
        }

        onGameCardsIterator = CARDS.iterator();
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
        currentDeck.onGameCardList.clear();
        currentDeck.offGameCardList.addAll(currentDeck.CARDS);
        currentDeck.offGameCardList.clear();
        currentDeck.onGameCardsIterator = onGameCardList.iterator();
        currentDeck.isEmptyDeck=false;
    }

    /**
     * 
     */
    public void shuffle() {
        Collections.shuffle(CARDS);
    }

    /**Restituisce la prossima carta
     * 
     * @return prossima carta
     */
    public Card getNextCard() {
        if (!onGameCardsIterator.hasNext()) {
            onGameCardList.addAll(offGameCardList);
            Collections.shuffle(onGameCardList);
            onGameCardsIterator = onGameCardList.iterator();
            currentDeck.isEmptyDeck=false;
        }

        nextCard = onGameCardsIterator.next();
        totalRemainingCards--;

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

    public boolean isIsEmptyDeck() {
        return isEmptyDeck;
    }

    public void setIsEmptyDeck(boolean isEmptyDeck) {
        this.isEmptyDeck = isEmptyDeck;
    }
} //end class