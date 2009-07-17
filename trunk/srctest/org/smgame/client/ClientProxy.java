package org.smgame.client;

import java.util.List;

import java.util.UUID;
import org.smgame.core.CoreProxy;
import org.smgame.core.GameMode;
import org.smgame.client.frontend.LoadGameVO;
import org.smgame.client.frontend.MenuVO;
import org.smgame.client.frontend.GameVO;
import org.smgame.client.frontend.MainVO;
import org.smgame.client.frontend.MessageType;
import org.smgame.client.frontend.NewGameVO;
import org.smgame.client.frontend.StoryBoardVO;
import org.smgame.server.IStub;

/** Classe client proxy, fa da proxy tra client/gui e server
 * 
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ClientProxy {

    private static ClientProxy clientProxy;
    private UUID clientID;
    private GameMode gameMode;
    private IStub stub;
    private MainVO mainVO = new MainVO();
    private NewGameVO newGameVO = new NewGameVO();

    /**Costruttore privato
     *
     * @throws java.lang.Exception
     */
    private ClientProxy() {
        try {
            clientID = UUID.randomUUID();
            stub = RMIClient.getStub();
        } catch (Exception e) {
        }
    }

    /**Restituisce l'istanza della classe, se nulla la inizializza prima
     *
     * @return clientmediator
     */
    public static ClientProxy getInstance() {
        if (clientProxy == null) {
            clientProxy = new ClientProxy();
        }

        return clientProxy;
    }

    /**Testa la connessione al server RMI
     *
     * @return oggetto mainVO
     */
    public MainVO connect() {
        mainVO.clear();
        try {
            stub = RMIClient.getStub();
            stub.test();
            mainVO.setMessageType(MessageType.INFO);
            mainVO.setMessage("Connessione al Server riuscita!");
        } catch (Exception e) {
            mainVO.setMessageType(MessageType.ERROR);
            mainVO.setMessage("Impossibile connettersi al Server!!!");
            return mainVO;
        }

        return mainVO;
    }

    /**Crea il gioco differenziandosi in base alla modalità online/offline
     *
     * @param gameMode tipo partita
     * @param gameName nome partita
     * @param gameSetting settaggi partita
     * @param playerNameList lista nomi giocatori
     * @param playerTypeList lista tipo giocatori
     */
    private void createGame(GameMode gameMode, String gameName, List<String> playerNameList,
            List<Boolean> playerTypeList) {
        newGameVO.clear();

        this.gameMode = gameMode;

        if (gameMode == GameMode.OFFLINE) {
            CoreProxy.createGame(clientID, gameName, gameMode, playerNameList, playerTypeList);
        } else {
            try {
                stub = RMIClient.getStub();
                stub.createGame(clientID, gameName, gameMode, playerNameList, playerTypeList);
            } catch (Exception e) {
                newGameVO.setMessageType(MessageType.ERROR);
                newGameVO.setMessage("Impossibile Giocare una Partita OnLine!");
            }
        }
    }

    /**Crea il gioco offline
     *
     * @param gameName nome partita
     * @param playerNameList lista nomi giocatori
     * @param playerTypeList lista tipo giocatori
     */
    public void createOffLineGame(String gameName, List<String> playerNameList,
            List<Boolean> playerTypeList) {
        createGame(GameMode.OFFLINE, gameName, playerNameList, playerTypeList);
    }

    /**Crea il gioco online
     *
     * @param gameName nome partita
     * @param playerNameList lista nomi giocatori
     * @param playerTypeList lista tipo giocatori
     */
    public void createOnLineGame(String gameName, List<String> playerNameList,
            List<Boolean> playerTypeList) {
        createGame(GameMode.ONLINE, gameName, playerNameList, playerTypeList);
    }

    /**Chiude la partita
     *
     */
    public void closeGame() {
        if (gameMode == GameMode.OFFLINE) {
            CoreProxy.closeGame(clientID);
        } else {
            try {
                stub = RMIClient.getStub();
                stub.closeGame(clientID);
            } catch (Exception e) {
            }
        }
    }

    /**Salva la partita
     *
     */
    public void saveGame() {
        if (gameMode == GameMode.OFFLINE) {
            CoreProxy.saveGame(clientID);
        }
    }

    /**Carica la partita
     *
     * @param gameID idpartita
     * 
     */
    public void loadGame(long gameID) {
        CoreProxy.loadGame(clientID, gameID);
        gameMode = GameMode.OFFLINE;
    }

    /**Carica l'elenco delle partite
     *
     */
    public void loadGames() {
        CoreProxy.loadGames();
    }

    /**Richiede il caricamento del gameVO
     *
     * @return istanza di gameVO
     * 
     */
    public LoadGameVO requestLoadGameVO() {

        return CoreProxy.requestLoadGameVO();
    }

    /**Restituisce il titolo della partita
     *
     * @return titolo partita
     */
    public String getGameTitle() {
        if (gameMode == GameMode.OFFLINE) {
            return CoreProxy.getGameTitle(clientID);
        } else {
            try {
                stub = RMIClient.getStub();
                return stub.getGameTitle(clientID);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**Richiede carta
     *
     * @param playerIndex indice del giocatore
     * @param bet eventuale puntata
     */
    public void requestCard(int playerIndex, double bet) {
        if (gameMode == GameMode.OFFLINE) {
            CoreProxy.requestCard(clientID, playerIndex, bet);
        } else {
            try {
                stub = RMIClient.getStub();
                stub.requestCard(clientID, playerIndex, bet);
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
            CoreProxy.declareGoodScore(clientID, playerIndex, bet);
        } else {
            try {
                stub = RMIClient.getStub();
                stub.declareGoodScore(clientID, playerIndex, bet);
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
            return CoreProxy.requestDataReport(clientID);
        } else {
            try {
                stub = RMIClient.getStub();
                return stub.requestDataReport(clientID);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**richiede i dati per la storia partite
     *
     * @return map dati
     */
    public StoryBoardVO requestStoryGames() {
        StoryBoardVO storyVO = new StoryBoardVO();
        try {
            stub = RMIClient.getStub();
            storyVO = stub.requestStoryGames();
            if (storyVO.getStory().size() == 0) {
                storyVO.setMessageType(MessageType.ERROR);
                storyVO.setMessage("Non ci sono partite da visualizzare");
            }
        } catch (Exception e) {
            storyVO.setMessageType(MessageType.ERROR);
            storyVO.setMessage("Impossibile connettersi al server!!!");
            return storyVO;
        }
        return storyVO;
    }

    /**Richiede e restituisce l'oggetto mainVO (pattern value objects)
     * 
     * @return mainVO
     */
    public MainVO requestMainVO() {
        if (gameMode == GameMode.OFFLINE) {
            return CoreProxy.requestMainVO();
        } else {
            try {
                stub = RMIClient.getStub();
                return stub.requestMainVO();
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**richiede e Restituisce gli oggetti del menù
     *
     * @return oggetti menù
     */
    public MenuVO requestMenuVO() {
        if (gameMode == null || gameMode == GameMode.OFFLINE) {
            return CoreProxy.requestMenuVO(clientID);
        } else {
            try {
                stub = RMIClient.getStub();
                return stub.requestMenuVO(clientID);
            } catch (Exception e) {
                mainVO.clear();
                mainVO.setMessageType(MessageType.ERROR);
                mainVO.setMessage("Impossibile connettersi al Server!!!");
                return null;
            }
        }
    }

    /**richiede restituisce l'oggetto newgame secondo il pattern value objects
     * 
     * @return newgameVO
     */
    public NewGameVO requestNewGameVO() {
        return newGameVO;
    }

    /**richiede e restituisce gli oggetti del gioco
     *
     * @return oggetti gioco
     */
    public GameVO requestGameVO() {
        if (gameMode == GameMode.OFFLINE) {
            return CoreProxy.requestGameVO(clientID);
        } else {
            try {
                stub = RMIClient.getStub();
                return stub.requestGameVO(clientID);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
