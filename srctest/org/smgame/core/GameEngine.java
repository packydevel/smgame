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
        player.setStatus(PlayerStatus.GoodScore);
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

        if ((player.getCredit() - player.getStake()) < bet) {
            throw new BetOverflowException("Non hai sufficiente Credito per eseguire questa puntata!!!");
        } else {
            card = deck.getNextCard();
            player.getCardList().add(card);
            player.getBetList().add(bet);

            System.out.println(card.getIcon().getDescription());

            if (player.getScore() > 7.5) {
                System.out.println("Credito attuale: " + player.getCredit());
                System.out.println("Puntata singola: " + bet);
                System.out.println("Puntata complessiva: " + player.getStake());
                deck.addOffGameCards(player.getCardList());
                //player.getCardList().clear();
                //player.getBetList().clear();
                if (player.getRole() != PlayerRole.Bank) {
                    player.setCredit(player.getCredit() - player.getStake());
                    bankPlayer.setCredit(bankPlayer.getCredit() + player.getStake());
                }
                player.setStatus(PlayerStatus.ScoreOverflow);
                throw new ScoreOverflowException("Mi spiace, Hai Sballato!!!", card);
            }
        }

        return card;
    }

    public void declareGoodScore(Player player, double bet) throws BetOverflowException {
        if ((player.getCredit() - player.getStake()) < bet) {
            throw new BetOverflowException("Non hai sufficiente Credito per eseguire questa puntata!!!");
        } else {
            player.getBetList().add(bet);
            player.setStatus(PlayerStatus.GoodScore);
        }
    }

    public Player getBankPlayer() {
        if (currentManche == 0) {
            return selectFirstRandomBankPlayer();
        } else {
            return bankPlayer;
        }
    }

    private Player selectFirstRandomBankPlayer() {
        List<Player> tempList = new ArrayList<Player>(playerList.getPlayerAL());
        Collections.shuffle(tempList);
        bankPlayer = tempList.get(0);
        bankPlayer.setRole(PlayerRole.Bank);
        return bankPlayer;
    }

    private Player selectNextBankPlayer() {
        Player player;
        int indexList;
        if (currentManche == 0) {
            bankPlayer = selectFirstRandomBankPlayer();
        } else {
            player = playerList.firstKingSM(bankPlayer);
            if (player == null) {
                if (deck.isIsEmptyDeck()) {
                    deck.setIsEmptyDeck(false);
                    indexList = playerList.getPlayerAL().indexOf(bankPlayer);
                    indexList = ++indexList % playerList.getPlayerAL().size();
                    player = playerList.getPlayerAL().get(indexList);
                } else {
                    player = bankPlayer;
                }
            }
            bankPlayer.setRole(PlayerRole.Normal);
            player.setRole(PlayerRole.Bank);
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
    private boolean compareScore(Player player) {
        if (player.getStatus() == PlayerStatus.GoodScore && bankPlayer.getStatus() == PlayerStatus.GoodScore) {
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
            return true;
        }
    }

    /*
     * Determina quanto vince o perde un giocatore contro il banco
     * secondo le regole di Wikipedia
     * Luka puoi verificare???
     */
    private void applyPaymentRule() {
        for (Player p : gameEngine.playerList.getPlayerAL()) {
            if (p.getStatus() == PlayerStatus.GoodScore) {
                if (compareScore(p)) {
                    if (p.getScore() == 7.5 && p.getCardList().size() == 2) {
                        p.setCredit(p.getCredit() + 2 * p.getStake());
                        bankPlayer.setCredit(bankPlayer.getCredit() - 2 * p.getStake());
                    } else {
                        p.setCredit(p.getCredit() + p.getStake());
                        bankPlayer.setCredit(bankPlayer.getCredit() - p.getStake());
                    }
                } else {
                    if (bankPlayer.getScore() == 7.5 && bankPlayer.getCardList().size() == 2) {
                        p.setCredit(p.getCredit() - 2 * p.getStake());
                        bankPlayer.setCredit(bankPlayer.getCredit() + 2 * p.getStake());
                    } else {
                        p.setCredit(p.getCredit() - p.getStake());
                        bankPlayer.setCredit(bankPlayer.getCredit() + p.getStake());
                    }
                }
            }
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

    public boolean isMaxScore(Player player) {
        if (player.getScore() == 7.5) {
            return true;
        }
        return false;
    }

    public boolean isEndGame() {
        System.out.println("Numero di manches totali: " + gameSetting.getManches());
        System.out.println("Numero di manches totali: " + currentManche);
        System.out.println("Giacatori in rosso: " + playerList.existsBankruptPlayer());
        if (gameSetting.getManches() == currentManche || playerList.existsBankruptPlayer()) {
            return true;
        }
        return false;
    }

    public void closeManche() {
        applyPaymentRule();
        selectNextBankPlayer();
        for (Player p : playerList.getPlayerAL()) {
            p.getCardList().clear();
            p.getBetList().clear();
        }
        currentPlayer = null;
        currentManche++;
    }

    public boolean isEndManche() {
        if (currentPlayer.equals(bankPlayer)) {
            return true;
        }
        return false;
    }
}//end class
