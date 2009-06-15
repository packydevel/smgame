package org.smgame.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import org.smgame.backend.DBAccess;
import org.smgame.backend.DBTransactions;
import org.smgame.core.player.*;
import org.smgame.client.frontend.LoadGameVO;
import org.smgame.client.frontend.MainVO;
import org.smgame.client.frontend.MenuVO;
import org.smgame.client.frontend.MessageType;
import org.smgame.client.frontend.GameVO;
import org.smgame.server.frontend.ServerVO;
import org.smgame.util.BetOverflowException;
import org.smgame.util.Common;
import org.smgame.util.ImageEdit;
import org.smgame.util.Logging;
import org.smgame.util.NoGamesException;
import org.smgame.util.ScoreOverflowException;

/**Classe GUICoreMediator
 * mediatore tra la gui e il core
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class GUICoreMediator {

    private static HashMap<Long, Game> gameMap = new HashMap<Long, Game>();
    private static ServerVO serverVO = new ServerVO();
    private static MainVO mainVO = new MainVO();
    private static MenuVO menuVO = new MenuVO();
    private static GameVO gameVO = new GameVO();
    private static LoadGameVO loadGameVO = new LoadGameVO();
    private static Game currentGame = null;
    private static String FILENAME = Common.getWorkspace() + "games.dat";
    private static String FILEDIR = Common.getWorkspace();
    private static final NumberFormat numberFormat = new DecimalFormat("#0.00");
    private static final DateFormat dateFormat = DateFormat.getInstance();
    private static final ImageIcon backImage = new ImageIcon(Common.getResourceCards("napoletane") + "dorso.jpg");
    private static DBTransactions trans = new DBTransactions();

    /**Aggiunge al menù gli item
     * 
     * @param menuItemList lista di item/voci da aggiungere
     */
    public static void addMenuItem(List<String> menuItemList) {
        for (String s : menuItemList) {
            menuVO.getItemEnabledMap().put(s, false);
        }
    }

    /**Crea partita offline
     *
     * @param gameName nome partita
     * @param gameSetting settaggi partita
     * @param playerNameList lista giocatori
     * @param playerTypeList lista tipo di giocatore
     */
    public static void createGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {

        PlayerList playerList = new PlayerList();

        for (int i = 0; i < playerNameList.size(); i++) {
            if (playerTypeList.get(i).booleanValue()) {
                playerList.getPlayerAL().add(new CPUPlayer(playerNameList.get(i)));
            } else {
                playerList.getPlayerAL().add(new HumanPlayer(playerNameList.get(i)));
            }
            playerList.getPlayerAL().get(i).setCredit(1000);
        //playerList.getPlayerAL().get(i).setPlayerList(playerList);
        }

        currentGame = new Game();
        currentGame.generateGameID();
        currentGame.setGameName(gameName);
        currentGame.setGameMode(GameMode.OFFLINE);
        currentGame.setCreationDate(new Date());
        currentGame.setGameSetting(new GameSetting());
        currentGame.setPlayerList(playerList);
        currentGame.generateGameEngine();
        System.out.println("Il numero dei giocatori passati al server è: " + currentGame.getPlayerList().getPlayerAL().size());
        currentGame.getGameEngine().start();
    }

    /**Chiede la chiusura del gioco
     *
     */
    public static void askCloseGame() {
        mainVO.setMessageType(MessageType.WARNING);
        mainVO.setMessage("Sei sicuro di voler chiudere la Partita? I passaggi di gioco non salvati saranno persi!");
    }

    /**Chiude la partita
     *
     */
    public static void closeGame() {
        currentGame = null;
        gameVO.clear();
    }

    public static String getSaveDirectory() {
        return FILEDIR;
    }

    public static void setSaveDirectory(File file) {
        String tempFileDir = file.getPath() + File.separator;
        String tempFileName = file.getPath() + File.separator + "games.dat";
        File tempFile = new File(tempFileName);

        serverVO.clear();

        try {
            tempFile.createNewFile();
            tempFile.delete();
            FILEDIR = tempFileDir;
            FILENAME = tempFileName;
        } catch (Exception e) {
            serverVO.setMessage("Impossibile Creare o Leggere il File delle Partite nella Directory Selezionata");
            serverVO.setMessageType(MessageType.ERROR);
        }
    }

    /**Salva partita
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void saveGame() {
        try {
            trans.executeArraylistTransactions();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        trans.resetArraylistTansactions();
        currentGame.setLastSaveDate(new Date());
        gameMap.put(currentGame.getGameID(), currentGame);
        saveGames();
    }

    /**Salva partite
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    private static void saveGames() {
        try {
            FileOutputStream fos = new FileOutputStream(FILENAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameMap);
            oos.flush();
            oos.close();
            fos.close();
            mainVO.setMessageType(MessageType.INFO);
            mainVO.setMessage("La Partita è stata salvata correttamente!");
        } catch (Exception e) {
            Logging.logExceptionSevere(e);
            mainVO.setMessageType(MessageType.ERROR);
            mainVO.setMessage("Non è stato possibile salvare la partita");
        }
    }

    /**Carica partita
     *
     * @param gameName nome partita da caricare
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

    /**Carica la map delle partite
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

    public static ServerVO requestServerVO() {
        return serverVO;
    }

    /**Richiede e restituisce l'oggetto mainVO corrente
     * 
     * @return oggetto mainVO
     */
    public static MainVO requestMainVO() {
        return mainVO;
    }

    /**Restituisce un oggetto contenente l'elenco dei nomi delle partite, il tipo, la data di creazione, la data di ultimo salvataggio
     * secondo il pattern Value Objected
     *
     * @return oggetto loadgameVO
     * @throws org.smgame.util.NoGamesException
     */
    public static LoadGameVO requestLoadGameVO() throws NoGamesException {
        loadGameVO.clear();

        if (gameMap.size() != 0) {
            for (Game g : gameMap.values()) {
                loadGameVO.getGameNameList().add(g.getGameName());
                loadGameVO.getGameNameGameModeMap().put(g.getGameName(), g.getGameMode().toString());
                loadGameVO.getGameNameCreationDateMap().put(g.getGameName(), dateFormat.format(g.getCreationDate()));
                loadGameVO.getGameNameLastSaveDateMap().put(g.getGameName(), dateFormat.format(g.getLastSaveDate()));
            }
            return loadGameVO;
        } else {
            throw new NoGamesException("Non ci sono partite da caricare");
        }
    }

    /**Restituisce il titolo della partita
     *
     * @return titolo
     */
    public static String getGameTitle() {
        return currentGame.getGameName() + " - Manche n° ";
    }

    /**Richiesta della carta da parte del giocatore, con eventuale puntata
     *
     * @param playerIndex indice giocatore
     * @param bet puntata
     */
    public static void requestCard(int playerIndex, double bet) {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);

        try {
            currentGame.getGameEngine().requestCard(player, bet);
        } catch (BetOverflowException boe) {
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }
            gameVO.setExceptionMessage(boe.getMessage());
            Logging.logExceptionWarning(boe);
        } catch (ScoreOverflowException soe) {
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }
            gameVO.setExceptionMessage(soe.getMessage());
            Logging.logExceptionWarning(soe);
        } catch (Exception e) {
            Logging.logExceptionSevere(e);
        }

    }

    /**Dichiarazione del giocatore di stare bene con eventuale puntata
     *
     * @param playerIndex indice giocatore
     * @param bet puntata
     */
    public static void declareGoodScore(int playerIndex, double bet) {
        Player player = currentGame.getPlayerList().getPlayerAL().get(playerIndex);
        try {
            currentGame.getGameEngine().declareGoodScore(player, bet);
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }
        } catch (BetOverflowException boe) {
            gameVO.setExceptionMessage(boe.getMessage());
            Logging.logExceptionWarning(boe);
        }
    }

    /**richiede e Restituisce l'oggetto menuVO (pattern value objected)
     *
     * @return  oggetto menuVO
     */
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

    /**Richiede e restituisce l'oggetto GameVO
     *
     * @return oggetto gameVO
     */
    public static GameVO requestGameVO() {

        ArrayList<ImageIcon> playerCardsImageList = new ArrayList<ImageIcon>();

        if (gameVO.isEndManche()) {
            currentGame.getGameEngine().startManche();
            gameVO.setEndManche(false);
        }

        gameVO.clear();

        for (int i = 0; i < currentGame.getPlayerList().getPlayerAL().size(); i++) {
            Player tempPlayer = currentGame.getPlayerList().getPlayerAL().get(i);

            gameVO.getPlayerIndexList().add(i);

            gameVO.getPlayerNameMap().put(i, tempPlayer.getName());

            if (tempPlayer instanceof CPUPlayer) {
                gameVO.getPlayerTypeMap().put(i, true);
            } else {
                gameVO.getPlayerTypeMap().put(i, false);
            }

            gameVO.getPlayerCreditMap().put(i, "Credito: " +
                    numberFormat.format(tempPlayer.getCredit()));

            gameVO.getPlayerCardsImageMap().put(i, new ArrayList<ImageIcon>());
            playerCardsImageList = gameVO.getPlayerCardsImageMap().get(i);
            for (int j = 0; j < tempPlayer.getCardList().size(); j++) {
                if (j == 0) {
                    if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) || tempPlayer.getStatus() == PlayerStatus.ScoreOverflow || tempPlayer.hasSM() || (currentGame.getGameEngine().isEndManche() && currentGame.getGameEngine().getBankPlayer().getStatus() == PlayerStatus.GoodScore)) {
                        playerCardsImageList.add(ImageEdit.scaledImage(tempPlayer.getCardList().get(j).getFrontImage()));
                    } else {
                        playerCardsImageList.add(ImageEdit.scaledImage(backImage));
                    }
                } else {
                    playerCardsImageList.add(ImageEdit.scaledImage(tempPlayer.getCardList().get(j).getFrontImage()));
                }
            }

            if (tempPlayer.getStatus() == PlayerStatus.ScoreOverflow) {
                for (int j = 0; j < tempPlayer.getCardList().size(); j++) {
                    playerCardsImageList.set(j, ImageEdit.grayScaleImage(playerCardsImageList.get(j)));
                }
            }
//
//            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer())) {
//                offLineGameVO.getPlayerFirstCardDiscoveredMap().put(i, Boolean.TRUE);
//            } else {
//                offLineGameVO.getPlayerFirstCardDiscoveredMap().put(i, Boolean.FALSE);
//            }

            gameVO.getPlayerStakeMap().put(i, "Puntata: " +
                    numberFormat.format(tempPlayer.getStake()));

            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) || tempPlayer.getStatus() == PlayerStatus.ScoreOverflow || tempPlayer.hasSM() || (currentGame.getGameEngine().isEndManche() && currentGame.getGameEngine().getBankPlayer().getStatus() == PlayerStatus.GoodScore)) {
                gameVO.getPlayerScoreMap().put(i, "Punteggio: " +
                        numberFormat.format(tempPlayer.getScore()));
            } else {
                gameVO.getPlayerScoreMap().put(i, "Punteggio: ");
            }

            if (tempPlayer.getStatus() != null) {
                gameVO.getPlayerStatusMap().put(i, tempPlayer.getStatus().toString());
            } else {
                gameVO.getPlayerStatusMap().put(i, null);
            }

            if (tempPlayer.equals(currentGame.getGameEngine().getBankPlayer())) {
                gameVO.getPlayerRoleMap().put(i, true);
            } else {
                gameVO.getPlayerRoleMap().put(i, false);
            }

            if (tempPlayer.getBetList().size() > 0 || tempPlayer.getRole() == PlayerRole.Bank) {
                gameVO.getPlayerRequestBetMap().put(i, false);
            } else {
                gameVO.getPlayerRequestBetMap().put(i, true);
            }

            if (tempPlayer.equals(currentGame.getGameEngine().getCurrentPlayer()) &&
                    !currentGame.getGameEngine().isEndManche() && !currentGame.getGameEngine().isEndGame()) {
                gameVO.getPlayerPlayingMap().put(i, true);
            } else {
                gameVO.getPlayerPlayingMap().put(i, false);
            }
        } //end for

        if (currentGame.getGameEngine().isEndManche()) {
            System.out.println("Ho settato ora la fine della manche!!!");
            currentGame.getGameEngine().closeManche();
            gameVO.setEndManche(true);
            addTransactionAL();
        }

        gameVO.setCurrentManche(currentGame.getGameEngine().getCurrentManche());

        gameVO.setEndGame(currentGame.getGameEngine().isEndGame());


        return gameVO;
    }

    /**setta la posizione del prossimo giocatore
     *
     */
    private static void selectNextPlayer() {
        currentGame.getGameEngine().nextPlayer(currentGame.getGameEngine().getCurrentPlayer());
    }

    /**Richiede i dati per popolare la scoreboard di fine manche
     *
     * @return matrice di dati
     */
    public static Object[][] requestDataReport() {
        int size = gameVO.getPlayerIndexList().size();
        Object[][] data = new Object[size][4];
        for (int i = 0; i < size; i++) {
            Player tempPlayer = currentGame.getPlayerList().getPlayerAL().get(i);
            data[i][0] = gameVO.getPlayerNameMap().get(i);
            //punteggio
            data[i][1] = gameVO.getPlayerScoreMap().get(i).substring(10);
            //vincita
            data[i][2] = numberFormat.format(tempPlayer.getLastWinLoseAmount());
            //credito
            data[i][3] = numberFormat.format(tempPlayer.getCredit());
        }
        return data;
    }

    /**Aggiunge la transazione all'arraylist di transazioni
     *
     */
    private static void addTransactionAL() {
        long game_id = currentGame.getGameID();
        for (Player p : currentGame.getPlayerList().getPlayerAL()) {
            DBTransactions dbt = new DBTransactions(game_id, currentGame.getGameEngine().getCurrentManche(),
                    p.getName(), p.getScore(), p.getLastWinLoseAmount(), p.getCardList());
            trans.addToArraylistTransactions(dbt);
        }
    }

    /**Testa la connessione al db
     *
     */
    public static void testDBConnection() {
        serverVO.clear();

        try {
            Connection con = DBAccess.getConnection();
        } catch (Exception e) {
            serverVO.setMessage("Impossibile Connettersi al DataBase");
            serverVO.setMessageType(MessageType.ERROR);
        }
    }
} //end  class
