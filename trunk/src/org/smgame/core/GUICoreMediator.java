/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.core;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import org.smgame.core.card.Card;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;

/**
 *
 * @author packyuser
 */
public class GUICoreMediator {

    private static ArrayList<Game> gameList;
    private static Game currentGame;
    private static List<String> playerNameList;
    private static List<Boolean> playerTypeList;

    public static boolean askForNewGame() {
        if (currentGame != null) {
            return false;
        }
        return true;
    }

    public static boolean askForLoadGame() {
        if (currentGame != null) {
            return false;
        }
        return true;
    }

    public static void createGame(String gameName, GameSetting gamesetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        PlayerList playerList = PlayerList.getInstance();
        GUICoreMediator.playerNameList = playerNameList;
        GUICoreMediator.playerTypeList = playerTypeList;

        for (int i = 0; i < playerNameList.size(); i++) {

            if (i > 0) {
                if (playerTypeList.get(i - 1).booleanValue()) {
                    playerList.getPlayerAL().add(new CPUPlayer(playerNameList.get(i)));
                } else {
                    playerList.getPlayerAL().add(new HumanPlayer(playerNameList.get(i)));
                }
            } else {
                playerList.getPlayerAL().add(new HumanPlayer(playerNameList.get(i)));
            }
        }

        currentGame = Game.create(gameName, null, playerList);
    }

    public static void closeGame() {
        currentGame = null;
        playerNameList = null;
        playerTypeList = null;
    }

    public static void saveGame() {
        try {
            currentGame.save();
        } catch (Exception e) {
            System.out.println("Errore nel salvataggio!!!");
        }

    }

    public static void loadGame() {
        try {
            GUICoreMediator.currentGame = Game.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore nel caricamento!!!");
        }
    }

    public static List<String> getPlayerNameList() {
        return playerNameList;
    }

    public static List<Boolean> getPlayerTypeList() {
        return playerTypeList;
    }

    public static List<Double> getPlayerCreditList() {
        List<Double> playerCreditList = new ArrayList<Double>();
        for (Player p : currentGame.getPlayerList().getPlayerAL()) {
            playerCreditList.add(new Double(p.getCredit()));
        }
        return playerCreditList;
    }

    public static List<Double> getPlayerStakeList() {
        List<Double> playerStakeList = new ArrayList<Double>();
        for (Player p : currentGame.getPlayerList().getPlayerAL()) {
            playerStakeList.add(new Double(p.getStake()));
        }
        return playerStakeList;
    }

    public static List<ImageIcon> getPlayerCards(String playerName) {
        List<ImageIcon> playerCards = new ArrayList<ImageIcon>();
        Player player = null;
        for (Player p : currentGame.getPlayerList().getPlayerAL()) {
            if (p.getName().equals(playerName)) {
                player = p;
                break;
            }
        }

        for (Card c : player.getCardList()) {
            playerCards.add(c.getImage());
        }
        return playerCards;
    }
}
