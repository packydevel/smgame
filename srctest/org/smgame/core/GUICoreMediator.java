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
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

import org.smgame.core.player.*;
import org.smgame.frontend.LoadGameVO;
import org.smgame.frontend.MenuVO;
import org.smgame.frontend.OffLineGameVO;
import org.smgame.frontend.OnLineGameVO;
import org.smgame.main.Game;
import org.smgame.main.GameMode;
import org.smgame.main.GameSetting;
import org.smgame.util.BetOverflowException;
import org.smgame.util.Common;
import org.smgame.util.Logging;
import org.smgame.util.NoGamesException;
import org.smgame.util.ScoreOverflowException;

/**Classe GUICoreMediator
 * mediatore tra la gui e il core
 *
 * @author 
 */
public class GUICoreMediator {

    private static HashMap<Long, Game> gameMap = new HashMap<Long, Game>();
    private static MenuVO menuVO = new MenuVO();
    private static OffLineGameVO offLineGameVO = new OffLineGameVO();
    private static OnLineGameVO onLineGameVO = new OnLineGameVO();
    private static LoadGameVO loadGameVO = new LoadGameVO();
    private static Game currentGame = null;
    private static final String FILENAME = Common.getWorkspace() + "games.dat";
    private static final NumberFormat formatter = new DecimalFormat("#0.00");
    private static final ImageIcon backImage = new ImageIcon(Common.getResourceCards("napoletane") + "dorso.jpg");

    public static void addMenuItem(List<String> menuItemList) {
        for (String s : menuItemList) {
            menuVO.getItemEnabledMap().put(s, false);
        }
    }

    /**Richiesta di poter creare una nuova partita
     *
     * @return
     */
    public static boolean askForNewGame() {
        if (currentGame != null) {
            return false;
        }
        return true;
    }

    /**Richiesta di caricare una partita
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
        currentGame.setGameMode(GameMode.OFFLINE);
        currentGame.setCreationDate(new Date());
        currentGame.setGameSetting(GameSetting.getInstance());
        currentGame.setPlayerList(playerList);
        currentGame.generateGameEngine();
        currentGame.getGameEngine().start();
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
        onLineGameVO.getPlayerIndexList().clear();

        String cpuName = "Alan Turing";
        playerList.getPlayerAL().add(new CPUPlayer(cpuName));
        playerList.getPlayerAL().get(0).setCredit(1000);
        onLineGameVO.getPlayerIndexList().add(Integer.valueOf(0));
        onLineGameVO.getPlayerNameMap().put(Integer.valueOf(0), cpuName);

        playerList.getPlayerAL().add(new HumanPlayer(playerName));
        playerList.getPlayerAL().get(1).setCredit(1000);
        onLineGameVO.getPlayerIndexList().add(Integer.valueOf(1));
        onLineGameVO.getPlayerNameMap().put(Integer.valueOf(1), playerName);

        offLineGameVO.getPlayerNameMap().put(1, cpuName);
        offLineGameVO.getPlayerNameMap().put(1, playerName);

        offLineGameVO.getPlayerTypeMap().put(1, true);
        offLineGameVO.getPlayerTypeMap().put(2, false);

        if (currentGame != null) {
            currentGame.resetInstance();
        }
        currentGame = Game.getInstance();
        currentGame.generateGameID();
        currentGame.setGameName(gameName);
        currentGame.setGameMode(GameMode.ONLINE);
        currentGame.setCreationDate(new Date());
        currentGame.setGameSetting(GameSetting.getInstance());
        currentGame.setPlayerList(playerList);
        currentGame.generateGameEngine();
        currentGame.getGameEngine().start();

        gameMap.put(currentGame.getGameID(), currentGame);
    }

    /**Chiudi partita
     *
     */
    public static void closeGame() {
        currentGame = null;
        offLineGameVO.clear();
    }

    /**Salva partita
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void saveGame() throws FileNotFoundException, IOException {
        currentGame.setLastSaveDate(new Date());
        gameMap.put(currentGame.getGameID(), currentGame);
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
        oos.writeObject(gameMap);
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
    public static void loadGame(String gameName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        for (Game g : gameMap.values()) {
            if (g.getGameName().equals(gameName)) {
                currentGame = g;
                return;
            }
        }
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
        gameMap = (HashMap<Long, Game>) ois.readObject();
        ois.close();
        fis.close();
    }

    /**Restituisce un oggetto contenente l'elenco dei nomi delle partite, il tipo, la data di creazione, la data di ultimo salvataggio
     * secondo il pattern Value Objected
     *
     * @return
     * @throws org.smgame.util.NoGamesException
     */
    public static LoadGameVO requestLoadGameVO() throws NoGamesException {
        loadGameVO.clear();

        if (gameMap.size() != 0) {
            for (Game g : gameMap.values()) {
                loadGameVO.getGameNameList().add(g.getGameName());
                loadGameVO.getGameNameGameModeMap().put(g.getGameName(), g.getGameMode().toString());
                loadGameVO.getGameNameCreationDateMap().put(g.getGameName(), g.getCreationDate());
                loadGameVO.getGameNameLastSaveDateMap().put(g.getGameName(), g.getLastSaveDate());
                System.out.println(loadGameVO.getGameNameGameModeMap());
            }
            return loadGameVO;
        } else {
            throw new NoGamesException("Non ci sono partite da caricare");
        }
    }

    /**Restituisce il nome della partita
     *
     * @return
     */
    public static String getGameTitle() {
        return currentGame.getGameName() + " - Manche n° ";
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
        } catch (BetOverflowException boe) {
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }
            offLineGameVO.setExceptionMessage(boe.getMessage());
            Logging.logExceptionWarning(boe);
        } catch (ScoreOverflowException soe) {
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }
            offLineGameVO.setExceptionMessage(soe.getMessage());
            Logging.logExceptionWarning(soe);
        } catch (Exception e) {
            Logging.logExceptionSevere(e);
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
            Logging.logExceptionWarning(boe);
        }
    }

    public static MenuVO requestMenuVO() {

        menuVO.getItemEnabledMap().clear();

        if (currentGame == null) {
            menuVO.getItemEnabledMap().put("newOnLineGameJMI", true);
            menuVO.getItemEnabledMap().put("newOffLineGameJMI", true);
            menuVO.getItemEnabledMap().put("loadGameJMI", true);
            menuVO.getItemEnabledMap().put("saveGameJMI", false);
            menuVO.getItemEnabledMap().put("closeGameJMI", false);
            menuVO.getItemEnabledMap().put("scoreBoardJMI", false);
        } else {
            menuVO.getItemEnabledMap().put("newOnLineGameJMI", false);
            menuVO.getItemEnabledMap().put("newOffLineGameJMI", false);
            menuVO.getItemEnabledMap().put("loadGameJMI", false);
            menuVO.getItemEnabledMap().put("saveGameJMI", true);
            menuVO.getItemEnabledMap().put("closeGameJMI", true);
            menuVO.getItemEnabledMap().put("scoreBoardJMI", true);
        }


        menuVO.getItemEnabledMap().put("exitGameJMI", true);
        menuVO.getItemEnabledMap().put("gameSettingsJMI", true);
        menuVO.getItemEnabledMap().put("globalSettingsJMI", true);
        menuVO.getItemEnabledMap().put("helpContentsJMI", true);
        menuVO.getItemEnabledMap().put("aboutJMI", true);

        return menuVO;
    }

    /**Richiede l'oggetto OffLineGameVO
     *
     * @return
     */
    public static OffLineGameVO requestOffLineGameVO() {

        ArrayList<ImageIcon> playerCardsImageList = new ArrayList<ImageIcon>();

        if (offLineGameVO.isEndManche()) {
            currentGame.getGameEngine().startManche();
            offLineGameVO.setEndManche(false);
        }

        offLineGameVO.clear();

        for (int i = 0; i < currentGame.getPlayerList().getPlayerAL().size(); i++) {
            Player tempPlayer = currentGame.getPlayerList().getPlayerAL().get(i);

            offLineGameVO.getPlayerIndexList().add(i);

            offLineGameVO.getPlayerNameMap().put(i, tempPlayer.getName());

            if (tempPlayer instanceof CPUPlayer) {
                offLineGameVO.getPlayerTypeMap().put(i, true);
            } else {
                offLineGameVO.getPlayerTypeMap().put(i, false);
            }

            offLineGameVO.getPlayerCreditMap().put(i, "Credito: " +
                    formatter.format(tempPlayer.getCredit()));

            offLineGameVO.getPlayerCardsImageMap().put(i, new ArrayList<ImageIcon>());
            playerCardsImageList = offLineGameVO.getPlayerCardsImageMap().get(i);
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
                offLineGameVO.getPlayerFirstCardDiscoveredMap().put(i, Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerFirstCardDiscoveredMap().put(i, Boolean.FALSE);
            }

            offLineGameVO.getPlayerStakeMap().put(i, "Puntata: " +
                    formatter.format(tempPlayer.getStake()));

            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) || tempPlayer.getStatus() == PlayerStatus.ScoreOverflow || tempPlayer.hasSM() || (currentGame.getGameEngine().isEndManche() && currentGame.getGameEngine().getBankPlayer().getStatus() == PlayerStatus.GoodScore)) {
                offLineGameVO.getPlayerScoreMap().put(i, "Punteggio: " +
                        formatter.format(tempPlayer.getScore()));
            } else {
                offLineGameVO.getPlayerScoreMap().put(i, "Punteggio: ");
            }

            if (tempPlayer.getStatus() != null) {
                offLineGameVO.getPlayerStatusMap().put(i, tempPlayer.getStatus().toString());
            } else {
                offLineGameVO.getPlayerStatusMap().put(i, null);
            }

            if (tempPlayer.equals(currentGame.getGameEngine().getBankPlayer())) {
                offLineGameVO.getPlayerRoleMap().put(i, Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerRoleMap().put(i, Boolean.FALSE);
            }

            if (tempPlayer.getBetList().size() > 0 || tempPlayer.getRole() == PlayerRole.Bank) {
                offLineGameVO.getPlayerRequestBetMap().put(i, Boolean.FALSE);
            } else {
                offLineGameVO.getPlayerRequestBetMap().put(i, Boolean.TRUE);
            }

            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) &&
                    !currentGame.getGameEngine().isEndManche() && !currentGame.getGameEngine().isEndGame()) {
                offLineGameVO.getPlayerPlayingMap().put(i, Boolean.TRUE);
            } else {
                offLineGameVO.getPlayerPlayingMap().put(i, Boolean.FALSE);
            }
        } //end for

        if (currentGame.getGameEngine().isEndManche()) {
            System.out.println("Ho settato ora la fine della manche!!!");
            currentGame.getGameEngine().closeManche();
            offLineGameVO.setEndManche(true);
        }

        offLineGameVO.setCurrentManche(currentGame.getGameEngine().getCurrentManche());

        offLineGameVO.setEndGame(currentGame.getGameEngine().isEndGame());


        return offLineGameVO;
    }

    /**richiede l'oggetto OnLineGameVO
     *
     * @return
     */
    public static OnLineGameVO requestOnLineGameVO() {
        ArrayList<ImageIcon> playerCardsImageList = new ArrayList<ImageIcon>();

        onLineGameVO.getPlayerCreditMap().clear();
        onLineGameVO.getPlayerCardsImageMap().clear();
        onLineGameVO.getPlayerStakeMap().clear();
        onLineGameVO.getPlayerScoreMap().clear();
        onLineGameVO.getPlayerFirstCardDiscoveredMap().clear();
        onLineGameVO.getPlayerRoleMap().clear();
        onLineGameVO.getPlayerPlayingMap().clear();
        onLineGameVO.getPlayerRequestBetMap().clear();

        for (int i = 0; i < onLineGameVO.getPlayerIndexList().size(); i++) {
            Player tempPlayer = currentGame.getPlayerList().getPlayerAL().get(i);

            onLineGameVO.getPlayerCreditMap().put(i, "Credito: " +
                    formatter.format(tempPlayer.getCredit()));
            onLineGameVO.getPlayerCardsImageMap().put(i, new ArrayList<ImageIcon>());
            playerCardsImageList = onLineGameVO.getPlayerCardsImageMap().get(i);
            for (int j = 0; j < tempPlayer.getCardList().size(); j++) {
                if (j == 0) {
                    if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) ||
                            tempPlayer.getStatus() == PlayerStatus.ScoreOverflow || tempPlayer.hasSM() ||
                            (currentGame.getGameEngine().isEndManche() && currentGame.getGameEngine().getBankPlayer().getStatus() == PlayerStatus.GoodScore)) {
                        playerCardsImageList.add(tempPlayer.getCardList().get(j).getFrontImage());
                    } else {
                        playerCardsImageList.add(backImage);
                    }
                } else {
                    playerCardsImageList.add(tempPlayer.getCardList().get(j).getFrontImage());
                }
            } //end for interno

            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer())) {
                onLineGameVO.getPlayerFirstCardDiscoveredMap().put(i, Boolean.TRUE);
            } else {
                onLineGameVO.getPlayerFirstCardDiscoveredMap().put(i, Boolean.FALSE);
            }

            onLineGameVO.getPlayerStakeMap().put(i, "Puntata: " +
                    formatter.format(tempPlayer.getStake()));
            onLineGameVO.getPlayerScoreMap().put(i, "Punteggio: " +
                    formatter.format(tempPlayer.getScore()));

            if (tempPlayer.getStatus() == PlayerStatus.ScoreOverflow) {
                onLineGameVO.getPlayerStatusMap().put(i, Boolean.TRUE);
            } else {
                onLineGameVO.getPlayerStatusMap().put(i, Boolean.FALSE);
            }

            if (tempPlayer.equals(currentGame.getGameEngine().getBankPlayer())) {
                onLineGameVO.getPlayerRoleMap().put(i, Boolean.TRUE);
            } else {
                onLineGameVO.getPlayerRoleMap().put(i, Boolean.FALSE);
            }

            if (tempPlayer.getBetList().size() > 0 || tempPlayer.getRole() == PlayerRole.Bank) {
                onLineGameVO.getPlayerRequestBetMap().put(i, Boolean.FALSE);
            } else {
                onLineGameVO.getPlayerRequestBetMap().put(i, Boolean.TRUE);
            }

            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) &&
                    !currentGame.getGameEngine().isEndManche() && !currentGame.getGameEngine().isEndGame()) {
                onLineGameVO.getPlayerPlayingMap().put(i, Boolean.TRUE);
            } else {
                onLineGameVO.getPlayerPlayingMap().put(i, Boolean.FALSE);
            }
        }//end for

        onLineGameVO.setEndManche(currentGame.getGameEngine().isEndManche());
        onLineGameVO.setEndGame(currentGame.getGameEngine().isEndGame());

        if (currentGame.getGameEngine().isEndManche()) {
            currentGame.getGameEngine().closeManche();
            currentGame.getGameEngine().startManche();
        }

        return onLineGameVO;
    }

    //Restituisce la posizione del prossimo giocatore
    private static void selectNextPlayer() {
        currentGame.getGameEngine().nextPlayer(currentGame.getGameEngine().getCurrentPlayer());
    }

    /**Richiede i dati per popolare la scoreboard di fine manche
     *
     * @return matrice di dati
     */
    public static Object[][] requestDataReport() {
        int size = offLineGameVO.getPlayerIndexList().size();
        Object[][] data = new Object[size][4];
        for (int i = 0; i < size; i++) {
            data[i][0] = offLineGameVO.getPlayerNameMap().get(i);
            //punteggio
            data[i][1] = offLineGameVO.getPlayerScoreMap().get(i).substring(10);
            //vincita
            double winlose = currentGame.getPlayerList().getPlayerAL().get(i).getLastWinLoseAmount();
            data[i][2] = formatter.format(winlose);
            //credito
            //data[i][3] = offLineGameVO.getPlayerCreditMap().get(i).substring(9);
            data[i][3] = formatter.format(currentGame.getPlayerList().getPlayerAL().get(i).getCredit());
        }
        return data;
    }
} //end  class
