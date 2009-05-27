package org.smgame.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.smgame.core.card.Card;
import org.smgame.core.card.Deck;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.main.GameSetting;
import org.smgame.util.BetOverflowException;
import org.smgame.util.ScoreOverflowException;

/**Classe GameEngine, motore di gioco
 *
 * @author luca
 * @author pasquale
 */
public class GameEngine implements Serializable {

    private static GameEngine gameEngine = null;
    private GameSetting gameSetting;
    private Deck deck;
    private PlayerList playerList;
    private final double MAX_CREDIT = 64000;
    private final double MAX_SCORE = 7.5;
    private int currentManche;
    private Player bankPlayer;
    private Player currentPlayer;

    //costruttore privato
    private GameEngine() {
    }

    /**Restituisce l'istanza della classe
     *
     * @param gameSetting settaggi del gioco
     * @param deck mazzo
     * @param playerList lista giocatori
     * @return istanza gioco
     */
    public static GameEngine getInstance() {
        if (gameEngine == null) {
            gameEngine = new GameEngine();
        }

        return gameEngine;
    }

    public void resetInstance() {
        gameSetting.resetInstance();
        deck.resetInstance();
        playerList.resetInstance();
        bankPlayer=null;
        currentPlayer=null;
        currentManche=0;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setGameSetting(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    public Card getFirstCard(Player player) {
        Card card;
        card = deck.getNextCard();
        player.getCardList().add(card);

        return card;
    }

    /**Richiede carta
     *
     * @param player giocatore che chiede la carta
     * @param bet puntata da effettuare
     */
    public Card requestCard(Player player, double bet) throws BetOverflowException, ScoreOverflowException {
        Card card;

        if (player.getCredit() < bet) {
            throw new BetOverflowException("Non hai sufficiente Credito per eseguire questa puntata!!!");
        } else {
            card = deck.getNextCard();
            player.getCardList().add(card);
            System.out.println(card.getIcon().getDescription());

            if (player.getScore() > 7.5) {
                deck.addOffGameCards(player.getCardList());
                player.getCardList().clear();
                player.getBetList().clear();
                player.setCredit(player.getCredit() - player.getStake());
                bankPlayer.setCredit(bankPlayer.getCredit() + player.getStake());
                throw new ScoreOverflowException("Mi spiace, Hai Sballato!!!");
            } else {
                player.getBetList().add(bet);
                player.setCredit(player.getCredit() - bet);
            }
        }

        return card;
    }

    public void declareGoodScore(Player player, double bet) throws BetOverflowException {
        if (player.getCredit() < bet) {
            throw new BetOverflowException("Non hai sufficiente Credito per eseguire questa puntata!!!");
        } else {
            player.getBetList().add(bet);
            player.setCredit(player.getCredit() - bet);
        }
    }

    public Player selectFirstRandomBankPlayer() {
        List<Player> tempList = new ArrayList<Player>(playerList.getPlayerAL());
        Collections.shuffle(tempList);
        bankPlayer = tempList.get(0);
        return bankPlayer;
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    /**restituisce il prossimo giocatore
     *
     * @return
     */
    public Player nextPlayer() {
        int indexList;
        if (currentPlayer == null) {
            indexList = playerList.getPlayerAL().indexOf((Player) bankPlayer);
        } else {
            indexList = playerList.getPlayerAL().indexOf((Player) currentPlayer);
        }

        indexList = ++indexList % playerList.getPlayerAL().size();
        currentPlayer = playerList.getPlayerAL().get(indexList);

        return currentPlayer;
    }

    /**restituisce il giocatore corrente
     *
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isEndGame() {
        if (gameSetting.getManches()==currentManche) {
            return true;
        } else if (playerList.existsBankruptPlayer()) {
            return true;
        } else {
            return false;
        }
    }
}//end class
