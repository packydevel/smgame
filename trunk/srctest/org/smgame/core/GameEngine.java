package org.smgame.core;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Random;
import org.smgame.core.card.Card;
import org.smgame.core.card.Deck;
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
    private Player playerHasFirstKingSM;

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

    /**Azzera/resetta l'istanza di gameEngine
     *
     */
    public void resetInstance() {
        gameSetting.resetInstance();
        deck.resetInstance();
        playerList.resetInstance();
        bankPlayer = null;
        currentPlayer = null;
        playerHasFirstKingSM = null;
        currentManche = 1;
    }

    public void start() {
        currentManche = 0;
        deck.shuffle();
        startManche();
    }

    /**imposta il mazzo
     *
     * @param deck
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**imposta i settaggi di gioco
     *
     * @param gameSetting
     */
    public void setGameSetting(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    /**imposta la lista dei player
     *
     * @param playerList
     */
    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    /**Distribuisce la prima carta
     *
     */
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
     *
     * @throws org.smgame.util.BetOverflowException
     * @throws org.smgame.util.ScoreOverflowException
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
     * @return
     */
    public Player getBankPlayer() {
        return bankPlayer;
    }

    //seleziona il primo mazziere
    private Player selectFirstRandomBankPlayer() {
        List<Player> tempList = new ArrayList<Player>(playerList.getPlayerAL());
        Collections.shuffle(tempList, new Random(System.currentTimeMillis()));
        bankPlayer = tempList.get(0);
        bankPlayer.setRole(PlayerRole.Bank);
        return bankPlayer;
    }

    //seleziona il mazziere
    private Player selectBankPlayer() {
        Player player;
        int indexList;
        if (currentManche == 1) {
            bankPlayer = selectFirstRandomBankPlayer();
        } else {
            player = playerHasFirstKingSM;
            if (player == null) {
                if (deck.isEmptyDeck()) {
                    indexList = playerList.getPlayerAL().indexOf(bankPlayer);
                    indexList = ++indexList % playerList.getPlayerAL().size();
                    player = playerList.getPlayerAL().get(indexList);
                    deck.setEmptyDeck(false);
                } else {
                    player = bankPlayer;
                }
            } else {
                deck.resetInstance();
            }

            bankPlayer.setRole(PlayerRole.Normal);
            player.setRole(PlayerRole.Bank);
            bankPlayer = player;
            playerHasFirstKingSM = null;
        }
        return bankPlayer;
    }

    //Valutazione tra i punteggi realizzati al 7 1/2 seondo le regole di WikiPedia
    //TODO: integrare caso 7mezzo reale con matta  che batte 7mezzo illegittimo mazziere
    //p.s. rendere + leggibile?
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
                        //vincita giocatore doppia x effetto sette e mezzo reale
                        p.setCredit(p.getCredit() + 2 * p.getStake());
                        bankPlayer.setCredit(bankPlayer.getCredit() - 2 * p.getStake());
                    } else {
                        //vincita giocatore normale
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
     * @return prossimo giocatore
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

    //
    private void playCPU(CPUPlayer player) {
        try {
            while (!player.isGoodScore()) {
                
                System.out.println(player.getName() + " - "+ player.isGoodScore());
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
    }

    /**restituisce il giocatore corrente
     *
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**Restituisce la verifica sul massimo punteggio cioè 7.5
     *
     * @param player
     * @return
     */
    public boolean isMaxScore(Player player) {
        return player.hasSM();
    }

    /**inizia la manche
     *
     */
    public void startManche() {
        for (Player p : playerList.getPlayerAL()) {
            p.getCardList().clear();
            p.getBetList().clear();
            p.setStatus(null);
        }

        currentManche++;
        selectBankPlayer();
        deck.shuffle();
        distributeFirstCard();
        currentPlayer = nextPlayer(bankPlayer);

        for (Player p : playerList.getPlayerAL()) {
            System.out.println(p.getName() +" - "+ p.getRole());
        }
    }

    /**chiudi la manche
     */
    public void closeManche() {
        applyPaymentRule();
        playerHasFirstKingSM = playerList.firstKingSM(bankPlayer);
        for (Player p : playerList.getPlayerAL()) {
            if (p.getStatus() == PlayerStatus.GoodScore) {
                deck.addOffGameCards(p.getCardList());
            }
        }
    }

    /**controlla se è terminata la manche
     *
     * @return
     */
    public boolean isEndManche() {
        if (currentPlayer.equals(bankPlayer) && currentPlayer.getStatus() != null) {
            return true;
        }

        return false;
    }

    /**Controlla se è terminata la partita
     *
     * @return
     */
    public boolean isEndGame() {
        if ((gameSetting.getManches() == currentManche && isEndManche()) || playerList.existsBankruptPlayer()) {
            return true;
        }

        return false;
    }
}//end class