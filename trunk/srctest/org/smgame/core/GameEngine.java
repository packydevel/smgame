package org.smgame.core;

import java.io.Serializable;
import java.util.Iterator;

import org.smgame.core.card.Card;
import org.smgame.core.card.Deck;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.core.player.PlayerRole;
import org.smgame.core.player.PlayerStatus;
import org.smgame.util.BetOverflowException;
import org.smgame.util.ScoreOverflowException;

/**Classe GameEngine, motore di gioco
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class GameEngine implements Serializable {

    private static GameEngine gameEngine = null;
    private Deck deck;
    private PlayerList playerList;
    private final int MANCHE_NUMBER = 10;
    private final double MAX_CREDIT = 64000;
    private final double MAX_SCORE = 7.5;
    private int currentManche;
    private Player bankPlayer;
    private Player currentPlayer;
    private Player playerHasFirstKingSM;

    /**costruttore vuoto
     * 
     */
    public GameEngine() {
    }

    /**inizia la partita
     *
     */
    public void start() {
        currentManche = 0;
        deck.shuffle();
        startManche();
    }

    /**imposta il mazzo
     *
     * @param deck mazzo
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**imposta la lista dei player
     *
     * @param playerList lista giocatori
     */
    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    /**Distribuisce la prima carta
     *
     */
    public void distributeFirstCard() {
        Card card;
        Iterator<Player> playerListIterator;
        Player p;

        playerListIterator = playerList.getPlayerListIterator();
        while (playerListIterator.hasNext()) {
            p = playerListIterator.next();
            card = deck.getNextCard();
            p.getCardList().add(card);
        }
    }

    /**Richiede carta
     *
     * @param player giocatore che chiede la carta
     * @param bet puntata da effettuare
     *
     * @throws org.smgame.util.BetOverflowException
     * @throws org.smgame.util.ScoreOverflowException
     */
    public void requestCard(Player player, double bet) throws BetOverflowException, ScoreOverflowException {
        Card card;
        Double amount;

        if ((player.getCredit() - player.getStake()) < bet) {
            throw new BetOverflowException("Non hai sufficiente Credito per eseguire questa puntata!!!");
        } else {
            card = deck.getNextCard();
            player.getCardList().add(card);
            player.getBetList().add(bet);

            if (player.getScore() > 7.5) {
                deck.addOffGameCards(player.getCardList());
                if (player.getRole() != PlayerRole.Bank) {
                    amount = player.getStake();
                    player.setLastWinLoseAmount(-amount);
                    player.setCredit(player.getCredit() - amount);
                    bankPlayer.setCredit(bankPlayer.getCredit() + amount);
                }
                player.setStatus(PlayerStatus.ScoreOverflow);
                throw new ScoreOverflowException(player.getName() + " Mi spiace, Hai Sballato!!!", card);
            }
        }
    } // end requestCard

    /**Dichiarazione di stare bene
     *
     * @param player giocatore che la effettua
     * @param bet eventuale puntata
     * @throws org.smgame.util.BetOverflowException
     */
    public void declareGoodScore(Player player, double bet) throws BetOverflowException {
        if ((player.getCredit() - player.getStake()) < bet) {
            throw new BetOverflowException("Non hai sufficiente Credito per eseguire questa puntata!!!");
        } else {
            player.getBetList().add(bet);
            player.setStatus(PlayerStatus.GoodScore);
        }
    }

    /**Restituisce il mazziere
     *
     * @return mazziere
     */
    public Player getBankPlayer() {
        return bankPlayer;
    }

    /**seleziona il primo mazziere della partita
     *
     * @return mazziere
     */
    private Player selectFirstRandomBankPlayer() {
        bankPlayer = playerList.selectRandomPlayer();
        bankPlayer.setRole(PlayerRole.Bank);
        return bankPlayer;
    }

    /**seleziona il mazziere
     *
     * @return giocatore mazziere
     */
    private Player selectBankPlayer() {
        Player player;
        int indexList;
        if (currentManche == 1) {
            player = selectFirstRandomBankPlayer();
        } else {
            player = playerHasFirstKingSM;
            if (player == null) {
                if (deck.isEmptyDeck()) {
                    indexList = playerList.indexOfPlayer(bankPlayer);
                    indexList = ++indexList % playerList.size();
                    player = playerList.getPlayer(indexList);
                    deck.setEmptyDeck(false);
                } else {
                    player = bankPlayer;
                }
            } else {
                deck.resetInstance();
            }

            bankPlayer.setRole(PlayerRole.Normal);
            player.setRole(PlayerRole.Bank);
            playerHasFirstKingSM = null;
        }
        return player;
    }

    /**Valutazione tra i punteggi realizzati al 7 1/2 seondo le regole di WikiPedia
     *
     * @param player giocatore da comparare
     * @return true se il giocatore vince sul mazziere, altrimenti false
     */
    private boolean compareScore(Player player) {
        boolean compare = false;
        if (player.getStatus() == PlayerStatus.GoodScore &&
                bankPlayer.getStatus() == PlayerStatus.GoodScore) {
            if (player.getScore() > bankPlayer.getScore()) {
                compare = true;
            } else if (player.getScore() == bankPlayer.getScore()) {
                if (player.hasJollyKingSM() && !bankPlayer.hasKingSM()) {
                    compare = true;
                }
            }
        } else {
            compare = true;
        }
        return compare;
    }

    /**Determina quanto vince o perde un giocatore contro il banco secondo le regole di Wikipedia
     *
     */
    private void applyPaymentRule() {
        Double amount, bankAmountGoodScorePlayer = 0.00, bankAmountOverflowPlayer = 0.00;
        Card card;
        Iterator<Player> playerListIterator;
        Player p;

        playerListIterator = playerList.getPlayerListIterator();
        while (playerListIterator.hasNext()) {
            p = playerListIterator.next();

            if (p.getStatus() == PlayerStatus.GoodScore && !p.equals(bankPlayer)) {
                if (compareScore(p)) {
                    if (p.hasKingSM()) {
                        amount = 2 * p.getStake();
                        p.setLastWinLoseAmount(amount);
                        p.setCredit(p.getCredit() + amount);
                    } else {
                        //vincita giocatore normale
                        amount = p.getStake();
                        p.setLastWinLoseAmount(amount);
                        p.setCredit(p.getCredit() + amount);
                    }
                    bankAmountGoodScorePlayer -= amount;
                } else {
                    if (bankPlayer.hasKingSM()) {
                        amount = 2 * p.getStake();
                        p.setLastWinLoseAmount(-amount);
                        p.setCredit(p.getCredit() - amount);
                    } else {
                        amount = p.getStake();
                        p.setLastWinLoseAmount(-amount);
                        p.setCredit(p.getCredit() - amount);
                    }
                    bankAmountGoodScorePlayer += amount;
                }

            } else if (p.getStatus() == PlayerStatus.ScoreOverflow && !p.equals(bankPlayer)) {
                amount = p.getStake();
                bankAmountOverflowPlayer += amount;
            }
        }

        bankPlayer.setLastWinLoseAmount(bankAmountGoodScorePlayer + bankAmountOverflowPlayer);
        bankPlayer.setCredit(bankPlayer.getCredit() + bankAmountGoodScorePlayer);
    } //end applyPaymentRule

    /**restituisce il prossimo giocatore
     *
     * @param player giocatore attuale
     * @return prossimo giocatore
     */
    public Player nextPlayer(Player player) {
        int indexList;
        currentPlayer = player;
        do {
            indexList = playerList.indexOfPlayer(currentPlayer);
            indexList = ++indexList % playerList.size();
            currentPlayer = playerList.getPlayer(indexList);
            if (currentPlayer instanceof CPUPlayer) {
                playCPU((CPUPlayer) currentPlayer);
            }
        } while (currentPlayer instanceof CPUPlayer && !currentPlayer.equals(bankPlayer));

        return currentPlayer;
    }

    /**azioni che deve eseguire il giocatore cpu
     *
     * @param player giocatore cpu del momento
     */
    private void playCPU(CPUPlayer player) {
        try {
            while (!player.isGoodScore()) {
                if (player.getBetList().size() == 0) {
                    requestCard(player, player.requestBet());
                } else {
                    requestCard(player, 0.00);
                }
            }

            if (player.getBetList().size() == 0) {
                declareGoodScore(player, player.requestBet());
            } else {
                declareGoodScore(player, 0.00);
            }
        } catch (Exception e) {
        }
    }//end playcpu

    /**restituisce il giocatore corrente
     *
     * @return player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**Restituisce la verifica sul massimo punteggio cioè 7.5
     *
     * @param player giocatore da verificare
     * @return true se ha 7,5 altrimenti false
     */
    public boolean isMaxScore(Player player) {
        return player.hasSM();
    }

    /**inizia la manche
     *
     */
    public void startManche() {
        Card card;
        Iterator<Player> playerListIterator;
        Player p;

        playerListIterator = playerList.getPlayerListIterator();
        while (playerListIterator.hasNext()) {
            p = playerListIterator.next();
            p.getCardList().clear();
            p.getBetList().clear();
            p.setStatus(null);
        }

        currentManche++;
        bankPlayer = selectBankPlayer();
        deck.shuffle();
        distributeFirstCard();
        currentPlayer = nextPlayer(bankPlayer);
    }

    /**Restituisce manche corrente
     *
     * @return numero manche
     */
    public int getCurrentManche() {
        return currentManche;
    }

    /**chiudi la manche
     */
    public void closeManche() {
        Iterator<Player> playerListIterator;
        Player p;

        applyPaymentRule();
        playerHasFirstKingSM = playerList.firstKingSM(bankPlayer);

        playerListIterator = playerList.getPlayerListIterator();
        while (playerListIterator.hasNext()) {
            p = playerListIterator.next();
            if (p.getStatus() == PlayerStatus.GoodScore) {
                deck.addOffGameCards(p.getCardList());
            }
        }
    }

    /**controlla se è terminata la manche
     *
     * @return true = terminata
     */
    public boolean isEndManche() {
        if (currentPlayer.equals(bankPlayer) && currentPlayer.getStatus() != null) {
            return true;
        }
        return false;
    }

    /**Controlla se è terminata la partita
     *
     * @return true = terminata
     */
    public boolean isEndGame() {
        if ((MANCHE_NUMBER == currentManche && isEndManche()) ||
                playerList.existsBankruptPlayer()) {
            return true;
        }
        return false;
    }
}//end class
