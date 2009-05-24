package org.smgame.core;

import java.util.Collections;
import org.smgame.core.card.Deck;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;

/**Classe GameEngine, motore di gioco
 *
 * @author luca
 * @author pasquale
 */
public class GameEngine {

    private static GameEngine gameEngine = null;
    private GameSetting gameSetting;
    private Deck deck;
    private PlayerList playerList;
    private Game game;
    private final double MAX_CREDIT = 64000;
    private final double MAX_SCORE = 7.5;
    private static int currentManche;
    private Player bankPlayer;
    private Player currentPlayer;

    //costruttore privato
    private GameEngine(GameSetting gameSetting, Deck deck, PlayerList playerList) {
        this.gameSetting = gameSetting;
        this.deck = deck;
        this.playerList = playerList;
    }

    /**Restituisce l'istanza della classe
     *
     * @param gameSetting settaggi del gioco
     * @param deck mazzo
     * @param playerList lista giocatori
     * @return istanza gioco
     */
    public static GameEngine getInstance(GameSetting gameSetting, Deck deck, PlayerList playerList) {
        if (gameEngine == null) {
            gameEngine = new GameEngine(gameSetting, deck, playerList);
        }

        return gameEngine;
    }

    /**Richiede carta
     *
     * @param player giocatore che chiede la carta
     * @param bet puntata da effettuare
     */
    public void requestCard(Player player, double bet) {

        player.getCardList().add(deck.getNextCard());

        if (player.getScore() > 7.5) {
            System.out.println("Hai sballato!!!");
            deck.addOffGameCards(player.getCardList());
            player.getCardList().clear();
            player.setCredit(player.getCredit() - player.getStake());
        /*
         * occorre aggiungere la somma al credito del Mazziere
         */
        }
    }

    public Player selectFirstRandomBank() {
        Collections.shuffle(playerList.getPlayerAL());
        bankPlayer = playerList.getPlayerAL().get(0);
        return bankPlayer;
    }

    public Player nextPlayer() {
        int indexList;
        if (currentPlayer == null) {
            indexList = playerList.getPlayerAL().indexOf((Player) bankPlayer);
        } else {
            indexList = playerList.getPlayerAL().indexOf((Player) currentPlayer);
        }

        indexList = indexList % playerList.getPlayerAL().size() + 1;
        currentPlayer=playerList.getPlayerAL().get(indexList);

        return currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
