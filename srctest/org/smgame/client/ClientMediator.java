package org.smgame.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.smgame.core.GUICoreMediator;
import org.smgame.core.GameMode;
import org.smgame.core.GameSetting;
import org.smgame.client.frontend.LoadGameVO;
import org.smgame.client.frontend.MenuVO;
import org.smgame.client.frontend.GameVO;
import org.smgame.client.frontend.MainVO;
import org.smgame.client.frontend.MessageType;
import org.smgame.client.frontend.NewGameVO;
import org.smgame.server.IGameMediator;
import org.smgame.util.NoGamesException;

/** Classe client mediator
 * è il mediatore tra server e gui
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ClientMediator {

    private static ClientMediator clientMediator;
    private GameMode gameMode;
    private IGameMediator stub;
    private MainVO mainVO = new MainVO();
    private NewGameVO newGameVO = new NewGameVO();

    /**Costruttore privato
     *
     * @throws java.lang.Exception
     */
    private ClientMediator() {
        try {
            stub = RMIClient.getStub();
        } catch (Exception e) {
        }
    }

    /**Restituisce l'istanza della classe, se nulla la inizializza prima
     *
     * @return clientmediator
     */
    public static ClientMediator getInstance() {
        if (clientMediator == null) {
            clientMediator = new ClientMediator();
        }

        return clientMediator;
    }

    public void connect() {
        mainVO.clear();

        try {
            stub = RMIClient.getStub();
            mainVO.setMessageType(MessageType.INFO);
            mainVO.setMessage("Connessione al Server RMI riuscita!");
        } catch (Exception e) {
            mainVO.setMessageType(MessageType.ERROR);
            mainVO.setMessage("Impossibile connettersi al server RMI!");
        }
    }

    /**Aggiunge il menu
     *
     * @param menuItemList lista di voci per menù
     */
    public void addMenuItem(List<String> menuItemList) {
        if (gameMode == GameMode.OFFLINE) {
            GUICoreMediator.addMenuItem(menuItemList);
        } else {
            try {
                stub.addMenuItem(menuItemList);
            } catch (Exception e) {
            }
        }
    }

    /**Crea il gioco differenziandosi in base alla modalità online/offline
     *
     * @param gameMode tipo partita
     * @param gameName nome partita
     * @param gameSetting settaggi partita
     * @param playerNameList lista nomi giocatori
     * @param playerTypeList lista tipo giocatori
     */
    private void createGame(GameMode gameMode, String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        newGameVO.clear();

        this.gameMode = gameMode;
        
        if (gameMode == GameMode.OFFLINE) {
            GUICoreMediator.createGame(gameName, gameSetting, playerNameList, playerTypeList);
        } else {
            try {
                stub.createGame(gameName, gameSetting, playerNameList, playerTypeList);
            } catch (Exception e) {
                newGameVO.setMessageType(MessageType.ERROR);
                newGameVO.setMessage("Impossibile Giocare una Partita OnLine!");
            }
        }
    }

    /**Crea il gioco offline
     *
     * @param gameName nome partita
     * @param gameSetting settaggi partita
     * @param playerNameList lista nomi giocatori
     * @param playerTypeList lista tipo giocatori
     */
    public void createOffLineGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        createGame(GameMode.OFFLINE, gameName, gameSetting, playerNameList, playerTypeList);
    }

    /**Crea il gioco online
     *
     * @param gameName nome partita
     * @param gameSetting settaggi partita
     * @param playerNameList lista nomi giocatori
     * @param playerTypeList lista tipo giocatori
     */
    public void createOnLineGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        createGame(GameMode.ONLINE, gameName, gameSetting, playerNameList, playerTypeList);
    }

    /**Chiede la chiusura della partita
     *
     */
    public void askCloseGame() {
        try {
            stub.askCloseGame();
        } catch (Exception e) {
        }

    }

    /**Chiude la partita
     *
     */
    public void closeGame() {
        try {
            stub.closeGame();
        } catch (Exception e) {
        }
    }

    /**Salva la partita
     *
     */
    public void saveGame() {
        try {
            stub.saveGame();
        } catch (Exception e) {
        }
    }

    /**Carica la partita
     *
     * @param gameName nome partita
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void loadGame(String gameName) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            stub.loadGame(gameName);
        } catch (Exception e) {
        }
    }

    /**Carica l'elenco delle partite
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void loadGames() throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            stub.loadGames();
        } catch (Exception e) {
        }
    }

    /**Richiede il caricamento del gameVO
     *
     * @return istanza di gameVO
     * @throws org.smgame.util.NoGamesException
     */
    public LoadGameVO requestLoadGameVO() throws NoGamesException {
        try {
            return stub.requestLoadGameVO();
        } catch (Exception e) {
            return null;
        }

    }

    /**Restituisce il titolo della partita
     *
     * @return titolo partita
     */
    public String getGameTitle() {
        try {
            return stub.getGameTitle();
        } catch (Exception e) {
            return null;
        }

    }

    /**Richiede carta
     *
     * @param playerIndex indice del giocatore
     * @param bet eventuale puntata
     */
    public void requestCard(int playerIndex, double bet) {
        if (gameMode == GameMode.OFFLINE) {
            GUICoreMediator.requestCard(playerIndex, bet);
        } else {
            try {
                stub.requestCard(playerIndex, bet);
            } catch (Exception e) {
            }
        }
    }

    /**Dichiarazione di star bene
     *
     * @param playerIndex indice giocatore
     * @param bet eventuale puntata
     */
    public void declareGoodScore(int playerIndex, double bet) {
        if (gameMode == GameMode.OFFLINE) {
            GUICoreMediator.declareGoodScore(playerIndex, bet);
        } else {
            try {
                stub.declareGoodScore(playerIndex, bet);
            } catch (Exception e) {
            }
        }
    }

    /**Richiede e restituisce i dati per il report della scoreboard
     *
     * @return matrice dati
     */
    public Object[][] requestDataReport() {
        if (gameMode == GameMode.OFFLINE) {
            return GUICoreMediator.requestDataReport();
        } else {
            try {
                return stub.requestDataReport();
            } catch (Exception e) {
                return null;
            }
        }
    }

    public MainVO requestMainVO() {
        if (gameMode == GameMode.OFFLINE) {
            return GUICoreMediator.requestMainVO();
        } else {
            try {
                return stub.requestMainVO();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**richiede e Restituisce gli oggetti del menù
     *
     * @return oggetti menù
     */
    public MenuVO requestMenuVO() {
        if (gameMode == GameMode.OFFLINE) {
            return GUICoreMediator.requestMenuVO();
        } else {
            try {
                return stub.requestMenuVO();
            } catch (Exception e) {
                return null;
            }
        }
    }

    public NewGameVO requestNewGameVO() {
        return newGameVO;
    }

    /**richiede e restituisce gli oggetti del gioco
     *
     * @return oggetti gioco
     */
    public GameVO requestGameVO() {
        if (gameMode == GameMode.OFFLINE) {
            return GUICoreMediator.requestGameVO();
        } else {
            try {
                return stub.requestGameVO();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}