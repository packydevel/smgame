package org.smgame.core.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**Classe Mazzo
 * contiene le 40carte da gioco
 * 
 * @author pasquale
 * @author luca
 */
public class Deck {
    //costanti
    private final String[] ALL_SUIT = {"Danari", "Spade", "Coppe", "Bastoni"}; //tutti i semi
    private final String[] ALL_POINT = {"Asso", "Due", "Tre", "Quattro", "Cinque",
        "Sei", "Sette", "Donna", "Fante", "Re"};//tutti i punti
    //TODO: ma ha senso double?
    private final double[] ALL_VALUE = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 0.5, 0.5, 0.5};//tutti i valori
    private final ArrayList<Card> CARDS=new ArrayList<Card>();
    private final int TOTAL_CARDS = 40; //carte totali
    private final int TOTAL_CARDS_PER_SUIT = 10; //carte totali per seme
    
    //variabili
    protected int totalRemainingCards = TOTAL_CARDS; //carte rimanenti
    private static Deck currentDeck = null; //mazzo corrente
    private Iterator<Card> iCard; 
    protected Card nextCard; //prossima carta

    //costruttore privato
    private Deck() {
        for (int i = 0; i < TOTAL_CARDS_PER_SUIT; i++) {
            CARDS.add(new Card(ALL_POINT[i], ALL_SUIT[0], ALL_VALUE[i]));
            CARDS.add(new Card(ALL_POINT[i], ALL_SUIT[1], ALL_VALUE[i]));
            CARDS.add(new Card(ALL_POINT[i], ALL_SUIT[2], ALL_VALUE[i]));
            CARDS.add(new Card(ALL_POINT[i], ALL_SUIT[3], ALL_VALUE[i]));
        }
        iCard =CARDS.iterator();
    } //end costruttore

    /**Restituisce l'istanza corrente di mazzo
     * 
     * @return Deck mazzo
     */
    public static Deck getInstance() {
        if (currentDeck == null)
            currentDeck = new Deck();       
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
    public Card getNextCard() {
        if (iCard.hasNext()) {
            nextCard=iCard.next();
            totalRemainingCards--;
        } else
            nextCard=null;
        return nextCard;
    }//end getNextCard

    /**Stampa le carte
     * 
     */
    public void print() {
       for (Card c: CARDS) {
           System.out.println(c.toString());
       }
    }//end print
    
} //end class