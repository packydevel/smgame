package org.smgame.core.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.smgame.util.EmptyDeckException;

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
    private final ArrayList<Card> CARDS = new ArrayList<Card>();
    private final int TOTAL_CARDS = 40; //carte totali
    private final int TOTAL_CARDS_PER_SUIT = 10; //carte totali per seme
    //variabili
    protected int totalRemainingCards = TOTAL_CARDS; //carte rimanenti
    private static Deck currentDeck = null; //mazzo corrente
    private Iterator<Card> iCard;
    protected Card nextCard; //prossima carta

    //costruttore privato
    private Deck() {
        int i;
        for (Suit suit : Suit.values()) {
            i = 0;
            for (Point point : Point.values()) {
                CARDS.add(new Card(point, suit, ALL_VALUE[i]));
                i++;
            }
        }

        iCard = CARDS.iterator();
    } //end costruttore

    /**Restituisce l'istanza corrente di mazzo
     * 
     * @return Deck mazzo
     */
    public static Deck getInstance() {
        if (currentDeck == null) {
            currentDeck = new Deck();
        }
        return currentDeck;
    } //end getInstance

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
    public Card getNextCard() throws EmptyDeckException {
        if (iCard.hasNext()) {
            nextCard = iCard.next();
            totalRemainingCards--;
        } else {
            throw new EmptyDeckException();
        }
        return nextCard;
    }//end getNextCard

    /**Stampa le carte
     * 
     */
    public void print() {
        for (Card c : CARDS) {
            System.out.println(c.toString());
        }
    }//end print
} //end class