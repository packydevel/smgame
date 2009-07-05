package org.smgame.core;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

import org.smgame.backend.DBAccess;
import org.smgame.backend.DBTransactions;
import org.smgame.core.player.*;
import org.smgame.client.frontend.LoadGameVO;
import org.smgame.client.frontend.MainVO;
import org.smgame.client.frontend.MenuVO;
import org.smgame.client.frontend.MessageType;
import org.smgame.client.frontend.GameVO;
import org.smgame.client.frontend.StoryBoardVO;
import org.smgame.core.card.Card;
import org.smgame.server.frontend.ServerVO;
import org.smgame.util.BetOverflowException;
import org.smgame.util.ResourceLocator;
import org.smgame.util.ImageEdit;
import org.smgame.util.Logging;
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
    private static String fileName = ResourceLocator.getWorkspace() + "games.dat";
    private static String fileDir = ResourceLocator.getWorkspace();
    private static final NumberFormat numberFormat = new DecimalFormat("#0.00");
    private static final DateFormat dateFormat = DateFormat.getInstance();
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

    /**Crea partita
     *
     * @param gameName nome partita
     * @param gameSetting settaggi partita
     * @param playerNameList lista giocatori
     * @param playerTypeList lista tipo di giocatore
     */
    public static void createGame(String gameName, GameSetting gameSetting, GameMode gameMode, List<String> playerNameList, List<Boolean> playerTypeList) {

        PlayerList playerList = new PlayerList();

        for (int i = 0; i < playerNameList.size(); i++) {
            if (playerTypeList.get(i).booleanValue()) {
                playerList.addPlayer(new CPUPlayer(playerNameList.get(i)));
            } else {
                playerList.addPlayer(new Player(playerNameList.get(i)));
            }
            playerList.getPlayer(i).setCredit(1000);
            playerList.getPlayer(i).setPlayerList(playerList);
        }

        currentGame = new Game();
        currentGame.generateGameID();
        currentGame.setGameName(gameName);
        currentGame.setGameMode(gameMode);
        currentGame.setCreationDate(new Date());
        currentGame.setGameSetting(new GameSetting());
        currentGame.setPlayerList(playerList);
        currentGame.generateGameEngine();
        currentGame.getGameEngine().start();
    }

    /**Chiede la chiusura del gioco
     *
     */
    public static void askCloseGame() {
        mainVO.clear();
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

    /**Restituisce la directory di salvataggio
     *
     * @return directory
     */
    public static String getSaveDirectory() {
        return fileDir;
    }

    /**Imposta la nuova directory per salvare
     *
     * @param file directory
     */
    public static void setSaveDirectory(File file) {
        String tempFileDir = file.getPath() + File.separator;
        String tempFileName = file.getPath() + File.separator + "games.dat";
        File tempFile = new File(tempFileName);

        serverVO.clear();

        try {
            tempFile.createNewFile();
            tempFile.delete();
            fileDir = tempFileDir;
            fileName = tempFileName;
        } catch (Exception e) {
            serverVO.setMessage("Impossibile Creare o Leggere il File delle Partite nella Directory Selezionata");
            serverVO.setMessageType(MessageType.ERROR);
        }
    }

    /**Salva partita offline
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void saveGame() {
        currentGame.setLastSaveDate(new Date());
        gameMap.put(currentGame.getGameID(), currentGame);
        saveGames();
    }

    /**Salva la partita online e scrive sul db
     *
     */
    public static void saveTransaction() throws Exception {
        try {
            trans.executeArraylistTransactions();
            trans.resetArraylistTansactions();
        } catch (Exception e) {
            mainVO.setMessage("Impossibile Connettersi al DataBase");
            mainVO.setMessageType(MessageType.ERROR);
            throw new Exception();
        }
    }

    /**Salva partite
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    private static void saveGames() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameMap);
            oos.flush();
            oos.close();
            fos.close();
            mainVO.setMessageType(MessageType.INFO);
            mainVO.setMessage("La Partita è stata salvata correttamente!");
        } catch (Exception e) {
            mainVO.setMessageType(MessageType.ERROR);
            mainVO.setMessage("Non è stato possibile salvare la partita!!!");
        }
    }

    /**Carica partita
     *
     * @param gameName nome partita da caricare
     *
     */
    public static void loadGame(long gameID) {
        loadGames();
        currentGame = gameMap.get(gameID);
    }

    /**Carica la map delle partite
     *
     */
    public static void loadGames() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            gameMap = (HashMap<Long, Game>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            gameMap.clear();
        }
    }

    /**Richiede e restituisce l'oggetto serverVO
     *
     * @return serverVO
     */
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
     * 
     */
    public static LoadGameVO requestLoadGameVO() {
        loadGameVO.clear();

        for (Game g : gameMap.values()) {
            loadGameVO.getGameIDList().add(g.getGameID());
            loadGameVO.getGameNameMap().put(g.getGameID(), g.getGameName());
            loadGameVO.getGameModeMap().put(g.getGameID(), g.getGameMode().toString());
            loadGameVO.getGameCreationDateMap().put(g.getGameID(), dateFormat.format(g.getCreationDate()));
            loadGameVO.getGameLastSaveDateMap().put(g.getGameID(), dateFormat.format(g.getLastSaveDate()));
        }

        return loadGameVO;
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
        Player player = currentGame.getPlayerList().getPlayer(playerIndex);

        try {
            currentGame.getGameEngine().requestCard(player, bet);
            gameVO.setExceptionMessage(null);
        } catch (BetOverflowException boe) {
            gameVO.setExceptionMessage(boe.getMessage());
        } catch (ScoreOverflowException soe) {
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }
            gameVO.setExceptionMessage(soe.getMessage());
        } catch (Exception e) {
        }

    }

    /**Dichiarazione del giocatore di stare bene con eventuale puntata
     *
     * @param playerIndex indice giocatore
     * @param bet puntata
     */
    public static void declareGoodScore(int playerIndex, double bet) {
        Player player = currentGame.getPlayerList().getPlayer(playerIndex);
        try {
            currentGame.getGameEngine().declareGoodScore(player, bet);
            if (!currentGame.getGameEngine().isEndManche()) {
                selectNextPlayer();
            }
            gameVO.setExceptionMessage(null);
        } catch (BetOverflowException boe) {
            gameVO.setExceptionMessage(boe.getMessage());
        }
    }

    /**richiede e Restituisce l'oggetto menuVO (pattern value objected)
     *
     * @return  oggetto menuVO
     */
    public static MenuVO requestMenuVO() {

        menuVO.clear();

        if (currentGame == null) {
            menuVO.getItemEnabledMap().put("newOnLineGameJMI", true);
            menuVO.getItemEnabledMap().put("newOffLineGameJMI", true);
            menuVO.getItemEnabledMap().put("loadGameJMI", true);
            menuVO.getItemEnabledMap().put("saveGameJMI", false);
            menuVO.getItemEnabledMap().put("closeGameJMI", false);
        } else {
            menuVO.getItemEnabledMap().put("newOnLineGameJMI", false);
            menuVO.getItemEnabledMap().put("newOffLineGameJMI", false);
            menuVO.getItemEnabledMap().put("loadGameJMI", false);
            if (currentGame.getGameMode() == GameMode.OFFLINE) {
                menuVO.getItemEnabledMap().put("saveGameJMI", true);
            } else {
                menuVO.getItemEnabledMap().put("saveGameJMI", false);
            }
            menuVO.getItemEnabledMap().put("closeGameJMI", true);
        }


        menuVO.getItemEnabledMap().put("exitGameJMI", true);
        menuVO.getItemEnabledMap().put("storyBoardJMI", true);
        menuVO.getItemEnabledMap().put("gameSettingsJMI", true);
        menuVO.getItemEnabledMap().put("globalSettingsJMI", true);
        menuVO.getItemEnabledMap().put("testConnectionJMI", true);
        menuVO.getItemEnabledMap().put("userGuideJMI", true);
        menuVO.getItemEnabledMap().put("javadocJMI", true);
        menuVO.getItemEnabledMap().put("refGuideJMI", true);
        menuVO.getItemEnabledMap().put("aboutJMI", true);

        return menuVO;
    }

    /**Richiede e restituisce l'oggetto GameVO
     *
     * @return oggetto gameVO
     */
    public static GameVO requestGameVO() {

        ArrayList<ImageIcon> playerCardsImageList = new ArrayList<ImageIcon>();

        if (gameVO.getExceptionMessage() == null || currentGame.getGameEngine().isEndManche()) {

            if (gameVO.isEndManche()) {
                currentGame.getGameEngine().startManche();
                gameVO.setEndManche(false);
            }

            gameVO.clear();

            for (int i = 0; i < currentGame.getPlayerList().size(); i++) {
                Player tempPlayer = currentGame.getPlayerList().getPlayer(i);

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
                            playerCardsImageList.add(ImageEdit.scaledImage(Card.getBackImage()));
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
                currentGame.getGameEngine().closeManche();
                gameVO.setPlayerMaxCreditList(colorPlayerCredit());
                gameVO.setEndManche(true);
                addTransactionAL();
            }

            gameVO.setCurrentManche(currentGame.getGameEngine().getCurrentManche());

            gameVO.setEndGame(currentGame.getGameEngine().isEndGame());

        }
        return gameVO;
    }

    /**seleziona il prossimo giocatore
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
            Player tempPlayer = currentGame.getPlayerList().getPlayer(i);
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
        Iterator<Player> playerListIterator;
        Player p;

        playerListIterator = currentGame.getPlayerList().getPlayerListIterator();
        while (playerListIterator.hasNext()) {
            p = playerListIterator.next();
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
            if (DBAccess.testConnection()) {
                serverVO.setMessage("Connessione al DataBase riuscita");
                serverVO.setMessageType(MessageType.INFO);
            } else {
                serverVO.setMessage("Impossibile Connettersi al DataBase");
                serverVO.setMessageType(MessageType.ERROR);
            }
        } catch (Exception e) {
            serverVO.setMessage("Impossibile Connettersi al DataBase");
            serverVO.setMessageType(MessageType.ERROR);
            Logging.logExceptionSevere(GUICoreMediator.class, e);
        }
    }

    /**Richiede e restituisce lo storico della partita tramite oggetto storyboardVO
     *
     * @return oggetto storyboardVO
     */
    public static StoryBoardVO requestStoryGames() {
        StoryBoardVO storyVO = new StoryBoardVO();
        DBTransactions dbt = new DBTransactions();
        try {
            storyVO.setStory(dbt.getStoryGame());
        } catch (Exception e) {
        }
        return storyVO;
    }

    /**restituisce le posizioni dei player col massimo credito
     *
     * @return arraylist posizioni
     */
    private static HashMap<Integer, Color> colorPlayerCredit() {
        HashMap<Integer, Color> playersHM = new HashMap<Integer, Color>();
        List<Player> maxL = currentGame.getPlayerList().maxPlayerCreditList();
        PlayerList playerL = currentGame.getPlayerList();
        for (int i = 0; i < playerL.size(); i++) {
            playersHM.put(i, Color.BLACK);
        }
        for (int i = 0; i < maxL.size(); i++) {
            playersHM.remove(playerL.indexOfPlayer(maxL.get(i)));
            playersHM.put(playerL.indexOfPlayer(maxL.get(i)), Color.GREEN);
        }


        return playersHM;
    }
} //end  class

