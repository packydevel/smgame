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
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.util.ListIterator;
import javax.swing.ImageIcon;

import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.core.player.PlayerStatus;
import org.smgame.frontend.OffLineGameVO;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;
import org.smgame.util.BetOverflowException;
import org.smgame.util.Common;
import org.smgame.util.NoGamesException;

/**
 *
 * @author 
 */
public class GUICoreMediator {

    private static ArrayList<Game> gameList = new ArrayList<Game>();
    private static OffLineGameVO offLineGameVO = new OffLineGameVO();
    private static Game currentGame = null;
    private static List<String> playerNameList;
    private static List<Boolean> playerTypeList;
    private static final String FILENAME = Common.getWorkspace() + "games.dat";
    private static final NumberFormat formatter = new DecimalFormat("#0.00");
    private static final ImageIcon backImage = new ImageIcon(Common.getResourceCards() + "dorso.jpg");

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
        offLineGameVO.getPlayerIndexList().clear();

        for (int i = 0; i < playerNameList.size(); i++) {
            System.out.println(playerNameList.get(i));
            if (playerTypeList.get(i).booleanValue()) {
                playerList.getPlayerAL().add(new CPUPlayer(playerNameList.get(i)));
            } else {
                playerList.getPlayerAL().add(new HumanPlayer(playerNameList.get(i)));
            }
            offLineGameVO.getPlayerIndexList().add(Integer.valueOf(i));
            offLineGameVO.getPlayerNameMap().put(Integer.valueOf(i), playerNameList.get(i));
            playerList.getPlayerAL().get(i).setCredit(1000);
        }

        if (currentGame != null) {
            currentGame.resetInstance();
        }
        currentGame = Game.getInstance();
        currentGame.generateGameID();
        currentGame.setGameName(gameName);
        currentGame.setCreationDate(new Date());
        currentGame.setGameSetting(GameSetting.getInstance());
        currentGame.setPlayerList(playerList);
        currentGame.generateGameEngine();
        currentGame.getGameEngine().startManche();

        gameList.add(currentGame);
    }

    public static void createOnLineGame(String gameName, GameSetting gameSetting, String playerName) {
        PlayerList.getInstance().resetInstance();
        PlayerList playerList = PlayerList.getInstance();                

        String cpuName = "Alan Turing";
        playerList.getPlayerAL().add(new CPUPlayer(cpuName));
        playerList.getPlayerAL().get(0).setCredit(1000);
        
        playerList.getPlayerAL().add(new HumanPlayer(playerName));
        playerList.getPlayerAL().get(1).setCredit(1000);

        List<String> playerNameList = new ArrayList<String>(2);
        playerNameList.add(cpuName);
        playerNameList.add(playerName);

        List<Boolean> playerTypeList = new ArrayList<Boolean>(2);
        playerTypeList.add(new Boolean(true));
        playerTypeList.add(new Boolean(false));

        GUICoreMediator.playerNameList = playerNameList;
        GUICoreMediator.playerTypeList = playerTypeList;

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
        currentGame.getGameEngine().startManche();

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

    /**restituisce la lista dei tipi dei giocatori
     * human = Boolean.false
     *
     * @return lista Boolean cpu/human
     */
    public static List<Boolean> getPlayerTypeList() {
        return playerTypeList;
    }

    /**Restituisce l'immagine/icona della carta richiesta dal giocatore
     *
     * @param playerIndex
     * @param bet
     * @return
     * @throws java.lang.Exception
     */
    public static void requestCard(int playerIndex, double bet) {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);

        try {
            currentGame.getGameEngine().requestCard(player, bet);
        } catch (Exception e) {
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }
            offLineGameVO.setExceptionMessage(e.getMessage());
        }
    }

    /**Dichiarazione del giocatore di stare bene con eventuale puntata
     *
     * @param playerIndex
     * @param bet
     * @throws java.lang.Exception
     */
    public static void declareGoodScore(int playerIndex, double bet) {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);
        try {
            currentGame.getGameEngine().declareGoodScore(player, bet);
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }

        } catch (BetOverflowException boe) {
            offLineGameVO.setExceptionMessage(boe.getMessage());
        }
    }

    public static OffLineGameVO requestOffLineGameVO() {

        ArrayList<ImageIcon> playerCardsImageList = new ArrayList<ImageIcon>();

        offLineGameVO.getPlayerCreditMap().clear();
        offLineGameVO.getPlayerCardsImageMap().clear();
        offLineGameVO.getPlayerStakeMap().clear();
        offLineGameVO.getPlayerScoreMap().clear();
        offLineGameVO.getPlayerFirstCardDiscoveredMap().clear();
        offLineGameVO.getPlayerRoleMap().clear();
        offLineGameVO.getPlayerPlayingMap().clear();

        for (int i = 0; i < offLineGameVO.getPlayerIndexList().size(); i++) {
            offLineGameVO.getPlayerCreditMap().put(Integer.valueOf(i), "Credito: " + formatter.format(currentGame.getPlayerList().getPlayerAL().get(i).getCredit()));
            offLineGameVO.getPlayerCardsImageMap().put(Integer.valueOf(i), new ArrayList<ImageIcon>());
            playerCardsImageList = offLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i));
            for (int j = 0; j < currentGame.getPlayerList().getPlayerAL().get(i).getCardList().size(); j++) {
                if (!currentGame.getPlayerList().getPlayerAL().get(i).equals(currentGame.getGameEngine().getCurrentPlayer())) {
                    if (j == 0) {
                        playerCardsImageList.add(backImage);
                    } else {
                        playerCardsImageList.add(currentGame.getPlayerList().getPlayerAL().get(i).getCardList().get(j).getFrontImage());
                    }
                } else {
                    playerCardsImageList.add(currentGame.getPlayerList().getPlayerAL().get(i).getCardList().get(j).getFrontImage());
                }
            }

            if (currentGame.getPlayerList().getPlayerAL().get(i).equals(currentGame.getGameEngine().getCurrentPlayer())) {
                offLineGameVO.getPlayerFirstCardDiscoveredMap().put(Integer.valueOf(i), Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerFirstCardDiscoveredMap().put(Integer.valueOf(i), Boolean.FALSE);
            }

            offLineGameVO.getPlayerStakeMap().put(Integer.valueOf(i), "Puntata: " + formatter.format(currentGame.getPlayerList().getPlayerAL().get(i).getStake()));
            offLineGameVO.getPlayerScoreMap().put(Integer.valueOf(i), "Punteggio: " + formatter.format(currentGame.getPlayerList().getPlayerAL().get(i).getScore()));

            if (currentGame.getPlayerList().getPlayerAL().get(i).equals(currentGame.getGameEngine().getBankPlayer())) {
                offLineGameVO.getPlayerRoleMap().put(Integer.valueOf(i), Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerRoleMap().put(Integer.valueOf(i), Boolean.FALSE);
            }

            if (currentGame.getPlayerList().getPlayerAL().get(i).getStatus() == PlayerStatus.ScoreOverflow) {
                offLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i)).remove(0);
                offLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i)).add(0, currentGame.getPlayerList().getPlayerAL().get(i).getCardList().get(0).getFrontImage());
            }

            if (currentGame.getPlayerList().getPlayerAL().get(i).hasMaxScore()) {
                offLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i)).remove(0);
                offLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i)).add(0, currentGame.getPlayerList().getPlayerAL().get(i).getCardList().get(0).getFrontImage());
            }

            if (currentGame.getPlayerList().getPlayerAL().get(i).equals(currentGame.getGameEngine().getCurrentPlayer()) && !currentGame.getGameEngine().isEndManche() && !currentGame.getGameEngine().isEndGame()) {
                offLineGameVO.getPlayerPlayingMap().put(Integer.valueOf(i), Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerPlayingMap().put(Integer.valueOf(i), Boolean.FALSE);
            }
        }

        offLineGameVO.setEndManche(currentGame.getGameEngine().isEndManche());
        offLineGameVO.setEndGame(currentGame.getGameEngine().isEndGame());

        System.out.println("La manche è finita? " + currentGame.getGameEngine().isEndManche());

        return offLineGameVO;
    }

    /**Restituisce la posizione del prossimo giocatore
     *
     * @return
     */
    private static void selectNextPlayer() {
        currentGame.getGameEngine().nextPlayer();
    }

    public static void closeManche() {
        currentGame.getGameEngine().closeManche();
    }
} //end  class