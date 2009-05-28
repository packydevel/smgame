package org.smgame.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.smgame.util.Common;
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
    private static final String FILENAME = Common.getWorkspace() + "games.dat";
    private static final NumberFormat formatter = new DecimalFormat("#0.00");

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

    public static void createGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        PlayerList.getInstance().resetInstance();
        PlayerList playerList = PlayerList.getInstance();
        GUICoreMediator.playerNameList = playerNameList;
        GUICoreMediator.playerTypeList = playerTypeList;

        for (int i = 0; i < playerNameList.size(); i++) {
            if (playerTypeList.get(i).booleanValue()) {
                playerList.getPlayerAL().add(new CPUPlayer(playerNameList.get(i)));
            } else {
                playerList.getPlayerAL().add(new HumanPlayer(playerNameList.get(i)));
            }
            playerList.getPlayerAL().get(i).setCredit(1000);
        }

        if (currentGame != null) {
            currentGame.resetInstance();
        }
        currentGame = Game.getInstance();
        currentGame.generateGameID();
        currentGame.setGameName(gameName);
        currentGame.setCreationDate(new Date());
        currentGame.setGameSetting(gameSetting);
        currentGame.setPlayerList(playerList);
        currentGame.generateGameEngine();
        currentGame.getGameEngine().shuffleDeck();

        gameList.add(currentGame);
    }

    public static void closeGame() {
        currentGame = null;
        playerNameList = null;
        playerTypeList = null;
    }

    public static void saveGame() throws FileNotFoundException, IOException {
        currentGame.setLastSaveDate(new Date());
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

    public static List<String> getPlayerCreditList() {
        List<String> playerCreditList = new ArrayList<String>();
        for (Player p : currentGame.getPlayerList().getPlayerAL()) {
            playerCreditList.add(formatter.format(p.getCredit()));
        }

        return playerCreditList;
    }

    public static String getPlayerCredit(int playerIndex) {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);
        return formatter.format(player.getCredit());
    }

    public static List<String> getPlayerStakeList() {
        List<String> playerStakeList = new ArrayList<String>();
        for (Player p : currentGame.getPlayerList().getPlayerAL()) {
            playerStakeList.add(formatter.format(p.getStake()));
        }

        return playerStakeList;
    }

    public static String getPlayerStake(int playerIndex) {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);
        return formatter.format(player.getStake());
    }

    public static String getPlayerScore(int playerIndex) {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);
        return formatter.format(player.getScore());
    }

    /**restituisce la posizione del mazziere nella lista dei giocatori
     *
     * @return posizione
     */
    public static int getBankPlayer() {
        return currentGame.getPlayerList().getPlayerAL().indexOf(currentGame.getGameEngine().selectFirstRandomBankPlayer());
    }

    public static List<ImageIcon> getPlayerCards(int playerIndex) {
        List<ImageIcon> playerCards = new ArrayList<ImageIcon>();
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);

        for (Card c : player.getCardList()) {
            playerCards.add(c.getFrontImage());
        }

        return playerCards;
    } // end getPlayerCards

    /**Restituisce l'immagine/icona della carta richiesta dal giocatore
     *
     * @param playerIndex
     * @param bet
     * @return
     * @throws java.lang.Exception
     */
    public static ImageIcon requestCard(int playerIndex, double bet) throws Exception {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);
        Card card;
        try {
            card = currentGame.getGameEngine().requestCard(player, bet);
            return card.getIcon();
        } catch (Exception e) {
            throw e;
        }
    }

    /**Restituisce un array di 2imageicon riguardo la prima carta da distribuire sul tavolo
     * 0 = carta frontale
     * 1 = dorso carta
     *
     * @param playerIndex
     * @return
     */
    public static ImageIcon[] getFirstCard(int playerIndex) {
        ImageIcon[] icons = new ImageIcon[2];
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);
        Card card = currentGame.getGameEngine().getFirstCard(player);
        icons[0] = card.getFrontImage();
        icons[1] = card.getBackImage();
        return icons;
    }

    /**Dichiarazione del giocatore di stare bene con eventuale puntata
     *
     * @param playerIndex
     * @param bet
     * @throws java.lang.Exception
     */
    public static void declareGoodScore(int playerIndex, double bet) throws Exception {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);
        try {
            currentGame.getGameEngine().declareGoodScore(player, bet);
        } catch (Exception e) {
            throw e;
        }
    }

    /**Restituisce la posizione del prossimo giocatore
     *
     * @return
     */
    public static int nextPlayer() {
        return currentGame.getPlayerList().getPlayerAL().indexOf(currentGame.getGameEngine().nextPlayer());
    }

    public static boolean isEndManche(int playerIndex) {
        return currentGame.getGameEngine().isEndManche(currentGame.getPlayerList().getPlayerAL().get(playerIndex));
    }
} //end  class