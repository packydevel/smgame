package org.smgame.core.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**Classe Mazzo
 * contiene le 40carte da gioco
 * 
 * @author pasquale
 * @author luca
 */
public class Deck {
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
    private Iterator<Card> onGameCardsIterator;
    protected Card nextCard; //prossima carta

    //costruttore privato
    private Deck() {
        int i;
        int value;
        Card c;
        for (Suit suit : Suit.values()) {
            i = 0;
            for (Point point : Point.values()) {
                c = new Card(point, suit);
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

    private void resetInstance() {
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
} //end class