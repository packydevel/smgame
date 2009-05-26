package org.smgame.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import org.smgame.core.card.Card;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;
import org.smgame.util.NoGamesException;

/**
 *
 * @author 
 */
public class GUICoreMediator {

    private static ArrayList<Game> gameList = new ArrayList<Game>();
    private static Game currentGame = null;
    private static List<String> playerNameList;
    private static List<Boolean> playerTypeList;
    private static final String FILENAME = System.getProperty("user.dir") +
            File.separator + "games.dat";

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
            if (playerTypeList.get(i).booleanValue()) {
                playerList.getPlayerAL().add(new CPUPlayer(playerNameList.get(i)));
            } else {
                playerList.getPlayerAL().add(new HumanPlayer(playerNameList.get(i)));
            }

        }

        currentGame = Game.create(gameName, null, playerList);
        gameList.add(currentGame);
    }

    public static void closeGame() {
        currentGame = null;
        playerNameList = null;
        playerTypeList = null;
    }

    public static void saveGame() throws FileNotFoundException, IOException {
        saveGames();
    }

    public static void saveGames() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(FILENAME);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(gameList);
        oos.flush();
        oos.close();
        fos.close();
    }

    public static void loadGame()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        //
    }

    public static void loadGames()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(FILENAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        gameList = (ArrayList<Game>) ois.readObject();
        ois.close();
        fis.close();
    }

    /**Restituisce la lista dei nomi dei giocatori
     *
     * @return lista stringa nomi
     */
    public static List<String> getPlayerNameList() {
        return playerNameList;
    }

    /**restituisce la lista dei tipi dei giocatori
     * human = Boolean.false
     *
     * @return lista Boolean cpu/human
     */
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

    /**restituisce la posizione del mazziere nella lista dei giocatori
     *
     * @return posizione
     */
    public static int getBankPlayer() {
        return currentGame.getPlayerList().getPlayerAL().indexOf(currentGame.getGameEngine().selectFirstRandomBankPlayer());
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
    } // end getPlayerCards

    public static List<String> getGameNameList() throws NoGamesException {
        List<String> gameNameList = new ArrayList<String>();
        if (gameList.size() != 0) {
            for (Game g : gameList) {
                gameNameList.add(g.getGameName());
            }
            return gameNameList;
        } else {
            throw new NoGamesException("Non ci sono partite da caricare");
        }

    }

    public static List<Date> getGameCreationDateList() throws NoGamesException {
        List<Date> gameCreationDateList = new ArrayList<Date>();
        if (gameList.size() != 0) {
            for (Game g : gameList) {
                gameCreationDateList.add(g.getCreationDate());
            }

            return gameCreationDateList;
        } else {
            throw new NoGamesException("Non ci sono partite da caricare");
        }
    }

    public static List<Date> getGameLastDateList() throws NoGamesException {
        List<Date> gameLastDateList = new ArrayList<Date>();
        if (gameList.size() != 0) {
            for (Game g : gameList) {
                gameLastDateList.add(g.getCreationDate());
            }

            return gameLastDateList;
        } else {
            throw new NoGamesException("Non ci sono partite da caricare");
        }
    }

    public static String getGameName() {
        return currentGame.getGameName();
    }
}