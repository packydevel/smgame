package org.smgame.core.card;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;

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
    private final HashMap<Card, Float> CARDS_VALUE = new HashMap<Card, Float>();
    private final ArrayList<Card> CARDS = new ArrayList<Card>();
    private ArrayList<Card> onGameCardList = new ArrayList<Card>();
    private ArrayList<Card> offGameCardList = new ArrayList<Card>();
    private final int TOTAL_CARDS = 40; //carte totali
    private final int TOTAL_CARDS_PER_SUIT = 10; //carte totali per seme
    //variabili
    protected int totalRemainingCards = TOTAL_CARDS; //carte rimanenti
    private static Deck currentDeck = null; //mazzo corrente
    private transient Iterator<Card> onGameCardsIterator;
    protected Card nextCard; //prossima carta

    //costruttore privato
    private Deck() {
        String curDir = System.getProperty("user.dir");
        String separ = File.separator;
        String img = "";

        /*TODO: aggiustare il path x le carte in merito alla scelta delle carte*/
        curDir += separ + "src" + separ + "org" + separ + "smgame" + separ +
                "resource" + separ + "cartemini" + separ + "napoletane" + separ;
        int i;
        int value;
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

                ImageIcon icon = new ImageIcon(curDir + img);
                c = new Card(point, suit, icon, icon);
                CARDS.add(c);
                CARDS_VALUE.put(c, new Float(ALL_VALUE[i]));

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
        //throw new EmptyDeckException();
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
} //end class