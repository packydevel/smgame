package org.smgame.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.smgame.core.card.Card;
import org.smgame.core.card.Deck;
import org.smgame.core.card.Point;
import org.smgame.core.card.Suit;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.core.player.PlayerRole;
import org.smgame.core.player.PlayerStatus;
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
    private int currentManche = 0;
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
        bankPlayer = null;
        currentPlayer = null;
        currentManche = 0;
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
        currentManche++;
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
                player.setStatus(PlayerStatus.ScoreOverflow);
                bankPlayer.setCredit(bankPlayer.getCredit() + player.getStake());
                throw new ScoreOverflowException("Mi spiace, Hai Sballato!!!", card);
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
            player.setStatus(PlayerStatus.GoodScore);
        }
    }

    public Player selectFirstRandomBankPlayer() {
        List<Player> tempList = new ArrayList<Player>(playerList.getPlayerAL());
        Collections.shuffle(tempList);
        bankPlayer = tempList.get(0);
        bankPlayer.setRole(PlayerRole.Bank);
        return bankPlayer;
    }

    public Player selectNextBankPlayer() {
        Player player = playerList.firstKingSM(bankPlayer);
        int indexList;

        if (player == null) {
            if (deck.isIsEmptyDeck()) {
                deck.setIsEmptyDeck(false);
                indexList = playerList.getPlayerAL().indexOf(bankPlayer);
                indexList = ++indexList % playerList.getPlayerAL().size();
                bankPlayer = playerList.getPlayerAL().get(indexList);
            }
        } else {
            bankPlayer = player;
        }
        return bankPlayer;
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    /*
     * Valutazione tra i punteggi realizzati al 7 1/2 seondo le regle di WikiPedia
     * Luka verifichi anche tu???
     */
    public boolean compareScore(Player player) {
        if (player.getStatus() == PlayerStatus.GoodScore) {
            if (player.getScore() > bankPlayer.getScore()) {
                return true;
            } else if (player.getScore() < bankPlayer.getScore()) {
                return false;
            } else {
                if (player.getScore() == 7.5) {
                    if ((player.getCardList().size() == 2) &&
                            ((player.getCardList().get(0).getPoint() == Point.Re && player.getCardList().get(0).getSuit() == Suit.Danari) ||
                            (player.getCardList().get(1).getPoint() == Point.Re && player.getCardList().get(1).getSuit() == Suit.Danari))) {
                        if (bankPlayer.getCardList().size() == 2) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /*
     * Determina quanto vince o perde un giocatore contro il banco
     * secondo le regole di Wikipedia
     * Luka puoi verificare???
     */
    public double paymentRule(Player player) {

        if (player.getStatus() == PlayerStatus.GoodScore) {
            if (compareScore(player)) {
                if (player.getScore() == 7.5 && player.getCardList().size() == 2) {
                    return player.getCredit() + 2 * player.getStake();
                } else {
                    return player.getCredit() + player.getStake();
                }
            } else {
                if (bankPlayer.getScore() == 7.5 && bankPlayer.getCardList().size() == 2) {
                    return player.getCredit() - player.getStake();
                } else {
                    return player.getCredit() - 2 * player.getStake();
                }
            }
        } else {
            return player.getCredit();
        }
    }

    /**restituisce il prossimo giocatore
     *
     * @return
     */
    public Player nextPlayer() {
        int indexList;
        if (currentPlayer == null) {
            indexList = playerList.getPlayerAL().indexOf(bankPlayer);
        } else {
            indexList = playerList.getPlayerAL().indexOf(currentPlayer);
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
        if (gameSetting.getManches() == currentManche) {
            return true;
        } else if (playerList.existsBankruptPlayer()) {
            return true;
        } else {
            return false;
        }
    }
}//end class
