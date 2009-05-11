package org.smgame.core;

import java.util.ArrayList;
import org.smgame.core.card.Card;
import org.smgame.core.card.Deck;
import org.smgame.core.player.CPUBank;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;
import org.smgame.util.EmptyDeckException;

/**
 *
 * @author packyuser
 */
public class GameEngine {

    private static GameEngine gameEngine = null;
    private GameSetting gameSetting;
    private Deck deck;
    private ArrayList<Card> remainingCards = new ArrayList<Card>();
    private PlayerList playerList;
    private Game game;
    private final double MAX_CREDIT = 64000;
    private final double MAX_SCORE = 7.5;

    private GameEngine(GameSetting gameSetting, Deck deck, PlayerList playerList) {
        this.gameSetting=gameSetting;
        this.deck=deck;
        this.playerList=playerList;
    }

    public static GameEngine getInstance(GameSetting gameSetting, Deck deck, PlayerList playerList) {
        if (gameEngine == null) {
            gameEngine = new GameEngine(gameSetting, deck, playerList);
        }

        return gameEngine;
    }

    public void requestCard(Player player, double bet) {
        try {
            player.getCardList().add(deck.getNextCard());
        }
        catch (EmptyDeckException ede) {

        }

        if (player.getScore() > 7.5) {
            System.out.println("Hai sballato!!!");
            remainingCards.addAll(player.getCardList());
            player.getCardList().clear();
            player.setCredit(player.getCredit()-player.getStake());
        }
    }

    public boolean existWeakPlayer(double totalValue, double minMargin) {
        ArrayList<Player> playerList = PlayerList.getInstance().getPlayerAL();
        for (Player p : playerList) {
            if (p instanceof CPUBank) {
                p = (CPUBank) p;
            } else if (p instanceof CPUPlayer) {
                p = (CPUPlayer) p;
            } else {
                p = (HumanPlayer) p;
            }

            if (p.getScore() <= (totalValue + minMargin)) {
                return true;
            }
        }
        return false;
    }
}
