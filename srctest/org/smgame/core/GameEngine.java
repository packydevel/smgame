package org.smgame.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.smgame.core.card.Card;
import org.smgame.core.card.Deck;
import org.smgame.core.card.Point;
import org.smgame.core.card.Suit;
import org.smgame.core.player.CPUPlayer;
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
        bankPlayer = null;
        currentPlayer = null;
        currentManche = 1;
    }

    public void start() {
        currentManche = 0;
        startManche();
        deck.shuffle();
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

    public void distributeFirstCard() {
        Card card;
        for (Player p : playerList.getPlayerAL()) {
            card = deck.getNextCard();
            p.getCardList().add(card);
        }
    }

    /**Richiede carta
     *
     * @param player giocatore che chiede la carta
     * @param bet puntata da effettuare
     */
    public void requestCard(Player player, double bet) throws BetOverflowException, ScoreOverflowException {
        Card card;

        if ((player.getCredit() - player.getStake()) < bet) {
            throw new BetOverflowException("Non hai sufficiente Credito per eseguire questa puntata!!!");
        } else {
            card = deck.getNextCard();
            player.getCardList().add(card);
            player.getBetList().add(bet);

            System.out.println(card.getIcon().getDescription());

            if (player.getScore() > 7.5) {
                deck.addOffGameCards(player.getCardList());
                if (player.getRole() != PlayerRole.Bank) {
                    player.setCredit(player.getCredit() - player.getStake());
                    bankPlayer.setCredit(bankPlayer.getCredit() + player.getStake());
                }
                player.setStatus(PlayerStatus.ScoreOverflow);
                throw new ScoreOverflowException("Mi spiace, Hai Sballato!!!", card);
            }
        }
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
        return bankPlayer;
    }

    private Player selectFirstRandomBankPlayer() {
        List<Player> tempList = new ArrayList<Player>(playerList.getPlayerAL());
        Collections.shuffle(tempList);
        bankPlayer = tempList.get(0);
        bankPlayer.setRole(PlayerRole.Bank);
        return bankPlayer;
    }

    private Player selectBankPlayer() {
        Player player;
        int indexList;
        if (currentManche == 1) {
            bankPlayer = selectFirstRandomBankPlayer();
        } else {
            player = playerList.firstKingSM(bankPlayer);
            if (player == null) {
                System.out.println("Mazziere nullo");
                if (deck.isIsEmptyDeck()) {
                    deck.setIsEmptyDeck(false);
                    indexList = playerList.getPlayerAL().indexOf(bankPlayer);
                    indexList = ++indexList % playerList.getPlayerAL().size();
                    player = playerList.getPlayerAL().get(indexList);
                } else {
                    player = bankPlayer;
                }
            }
            System.out.println("Mazziere attuale " + bankPlayer.getName());
            bankPlayer.setRole(PlayerRole.Normal);
            player.setRole(PlayerRole.Bank);
            System.out.println("Mazziere attuale " + bankPlayer.getName());
            bankPlayer = player;
        }
        return bankPlayer;
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
    public Player nextPlayer(Player player) {
        int indexList;
        currentPlayer = player;
        do {
            indexList = playerList.getPlayerAL().indexOf(currentPlayer);
            indexList = ++indexList % playerList.getPlayerAL().size();
            currentPlayer = playerList.getPlayerAL().get(indexList);
            if (currentPlayer instanceof CPUPlayer) {
                playCPU((CPUPlayer) currentPlayer);
            }
        } while (currentPlayer instanceof CPUPlayer && !currentPlayer.equals(bankPlayer));

        return currentPlayer;
    }

    private void playCPU(CPUPlayer player) {
        try {
            while (!player.isGoodScore()) {
                requestCard(player, player.requestBet());
            }
            declareGoodScore(player, player.requestBet());
        } catch (Exception e) {
        }
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

    public void startManche() {
        for (Player p : playerList.getPlayerAL()) {
            p.getCardList().clear();
            p.getBetList().clear();
            p.setStatus(null);
        }

        currentManche++;
        selectBankPlayer();
        distributeFirstCard();
        currentPlayer = nextPlayer(bankPlayer);
    }

    public void closeManche() {
        applyPaymentRule();
    }

    public boolean isEndManche() {
        if (currentPlayer.equals(bankPlayer) && currentPlayer.getStatus() != null) {
            return true;
        }

        return false;
    }

    public boolean isEndGame() {
        if ((gameSetting.getManches() == currentManche && isEndManche()) || playerList.existsBankruptPlayer()) {
            return true;
        }

        return false;
    }
}//end class
