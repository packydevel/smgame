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

import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.core.player.PlayerRole;
import org.smgame.core.player.PlayerStatus;
import org.smgame.frontend.OffLineGameVO;
import org.smgame.frontend.OnLineGameVO;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;
import org.smgame.util.BetOverflowException;
import org.smgame.util.Common;
import org.smgame.util.NoGamesException;

/**Classe GUICoreMediator
 * mediatore tra la gui e il core
 *
 * @author 
 */
public class GUICoreMediator {

    private static ArrayList<Game> gameList = new ArrayList<Game>();
    private static OffLineGameVO offLineGameVO = new OffLineGameVO();
    private static OnLineGameVO onLineGameVO = new OnLineGameVO();
    private static Game currentGame = null;
    private static List<String> playerNameList;
    private static List<Boolean> playerTypeList;
    private static final String FILENAME = Common.getWorkspace() + "games.dat";
    private static final NumberFormat formatter = new DecimalFormat("#0.00");
    private static final ImageIcon backImage = new ImageIcon(Common.getResourceCards() + "dorso.jpg");

    /**Chiedi per nuova partita
     *
     * @return
     */
    public static boolean askForNewGame() {
        if (currentGame != null) {
            return false;
        }
        return true;
    }

    /**Chiedi per caricare partita
     *
     * @return
     */
    public static boolean askForLoadGame() {
        if (currentGame != null) {
            return false;
        }
        return true;
    }

    /**Crea partita offline
     *
     * @param gameName
     * @param gameSetting
     * @param playerNameList
     * @param playerTypeList
     */
    public static void createGame(String gameName, GameSetting gameSetting, List<String> playerNameList,
            List<Boolean> playerTypeList) {
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
        currentGame.getGameEngine().start();

        gameList.add(currentGame);
    }

    /**Crea partita online
     *
     * @param gameName
     * @param gameSetting
     * @param playerName
     */
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
        currentGame.getGameEngine().start();

        gameList.add(currentGame);
    }

    /**Chiudi partita
     *
     */
    public static void closeGame() {
        currentGame = null;
        playerNameList = null;
        playerTypeList = null;
    }

    /**Salva partita
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void saveGame() throws FileNotFoundException, IOException {
        currentGame.setLastSaveDate(new Date());
        saveGames();
    }

    /**Salva partite
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void saveGames() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(FILENAME);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(gameList);
        oos.flush();
        oos.close();
        fos.close();
    }

    /**Carica partita
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void loadGame()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        //
    }

    /**Carica partite
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void loadGames()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(FILENAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        gameList = (ArrayList<Game>) ois.readObject();
        ois.close();
        fis.close();
    }

    /**Restituisce la lista dei nomi delle partite
     *
     * @return
     * @throws org.smgame.util.NoGamesException
     */
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

    /**Restituisce la lista delle date di creazione delle partite
     *
     * @return
     * @throws org.smgame.util.NoGamesException
     */
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

    /**Restituisce la data dell'ultima partita
     *
     * @return
     * @throws org.smgame.util.NoGamesException
     */
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

    /**Restituisce il nome della partita
     *
     * @return
     */
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

    /**Richiesta della carta da parte del giocatore, con eventuale puntata
     *
     * @param playerIndex
     * @param bet
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

    /**Richiede l'oggetto OffLineGameVO
     *
     * @return
     */
    public static OffLineGameVO requestOffLineGameVO() {

        ArrayList<ImageIcon> playerCardsImageList = new ArrayList<ImageIcon>();

        offLineGameVO.getPlayerCreditMap().clear();
        offLineGameVO.getPlayerCardsImageMap().clear();
        offLineGameVO.getPlayerStakeMap().clear();
        offLineGameVO.getPlayerScoreMap().clear();
        offLineGameVO.getPlayerFirstCardDiscoveredMap().clear();
        offLineGameVO.getPlayerRoleMap().clear();
        offLineGameVO.getPlayerPlayingMap().clear();
        offLineGameVO.getPlayerRequestBetMap().clear();

        for (int i = 0; i < offLineGameVO.getPlayerIndexList().size(); i++) {
            Player tempPlayer = currentGame.getPlayerList().getPlayerAL().get(i);

            offLineGameVO.getPlayerCreditMap().put(Integer.valueOf(i), "Credito: " +
                    formatter.format(tempPlayer.getCredit()));
            offLineGameVO.getPlayerCardsImageMap().put(Integer.valueOf(i), new ArrayList<ImageIcon>());
            playerCardsImageList = offLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i));
            for (int j = 0; j < tempPlayer.getCardList().size(); j++) {
                if (j == 0) {
                    if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) || tempPlayer.getStatus() == PlayerStatus.ScoreOverflow || tempPlayer.hasSM() || (currentGame.getGameEngine().isEndManche() && currentGame.getGameEngine().getBankPlayer().getStatus() == PlayerStatus.GoodScore)) {
                        playerCardsImageList.add(tempPlayer.getCardList().get(j).getFrontImage());
                    } else {
                        playerCardsImageList.add(backImage);
                    }
                } else {
                    playerCardsImageList.add(tempPlayer.getCardList().get(j).getFrontImage());
                }
            }

            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer())) {
                offLineGameVO.getPlayerFirstCardDiscoveredMap().put(Integer.valueOf(i), Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerFirstCardDiscoveredMap().put(Integer.valueOf(i), Boolean.FALSE);
            }

            offLineGameVO.getPlayerStakeMap().put(Integer.valueOf(i), "Puntata: " +
                    formatter.format(tempPlayer.getStake()));
            offLineGameVO.getPlayerScoreMap().put(Integer.valueOf(i), "Punteggio: " +
                    formatter.format(tempPlayer.getScore()));

            if (tempPlayer.equals(currentGame.getGameEngine().getBankPlayer())) {
                offLineGameVO.getPlayerRoleMap().put(Integer.valueOf(i), Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerRoleMap().put(Integer.valueOf(i), Boolean.FALSE);
            }

            if (tempPlayer.getBetList().size() > 0 || tempPlayer.getRole() == PlayerRole.Bank) {
                offLineGameVO.getPlayerRequestBetMap().put(Integer.valueOf(i), Boolean.FALSE);
            } else {
                offLineGameVO.getPlayerRequestBetMap().put(Integer.valueOf(i), Boolean.TRUE);
            }

            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) &&
                    !currentGame.getGameEngine().isEndManche() && !currentGame.getGameEngine().isEndGame()) {
                offLineGameVO.getPlayerPlayingMap().put(Integer.valueOf(i), Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerPlayingMap().put(Integer.valueOf(i), Boolean.FALSE);
            }
        } //end for

        offLineGameVO.setEndManche(currentGame.getGameEngine().isEndManche());
        offLineGameVO.setEndGame(currentGame.getGameEngine().isEndGame());

        System.out.println("La manche è finita? " + currentGame.getGameEngine().isEndManche());

        if (currentGame.getGameEngine().isEndManche()) {
            currentGame.getGameEngine().closeManche();
            currentGame.getGameEngine().startManche();
        }

        return offLineGameVO;
    }

    /**richiede l'oggetto OnLineGameVO
     *
     * @return
     */
    public static OnLineGameVO requestOnLineGameVO() {
        return onLineGameVO;
    }

    //Restituisce la posizione del prossimo giocatore
    private static void selectNextPlayer() {
        currentGame.getGameEngine().nextPlayer(currentGame.getGameEngine().getCurrentPlayer());
    }
} //end  class