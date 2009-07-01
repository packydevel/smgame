package org.smgame.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.smgame.core.GameSetting;
import org.smgame.client.frontend.LoadGameVO;
import org.smgame.client.frontend.MenuVO;
import org.smgame.client.frontend.GameVO;
import org.smgame.client.frontend.MainVO;
import org.smgame.client.frontend.StoryBoardVO;
import org.smgame.util.NoGamesException;

/**Interfaccia mediatore di gioco
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public interface IGameMediator extends Remote {

    /**testa la connessione
     *
     * @throws java.rmi.RemoteException
     */
    public void test() throws RemoteException;

    /**Aggiunge item al menù
     *
     * @param menuItemList lista menù
     * @throws java.rmi.RemoteException
     */
    public void addMenuItem(List<String> menuItemList) throws RemoteException;

    public void createGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) throws RemoteException;

    public void askCloseGame() throws RemoteException;

    public void closeGame() throws RemoteException;

    public void saveGame() throws RemoteException;

    public void loadGame(String gameName) throws FileNotFoundException, IOException, ClassNotFoundException, RemoteException;

    public void loadGames() throws FileNotFoundException, IOException, ClassNotFoundException, RemoteException;

    public MainVO requestMainVO() throws RemoteException;

    public MenuVO requestMenuVO() throws RemoteException;

    public LoadGameVO requestLoadGameVO() throws NoGamesException, RemoteException;

    public GameVO requestGameVO() throws RemoteException;

    public String getGameTitle() throws RemoteException;

    public void requestCard(int playerIndex, double bet) throws RemoteException;

    public void declareGoodScore(int playerIndex, double bet) throws RemoteException;

    public Object[][] requestDataReport() throws RemoteException;

    /**richiede e restituisce l'oggetto storico partite
     *
     * @return 
     * @throws java.rmi.RemoteException
     */
    public StoryBoardVO requestStoryGames() throws RemoteException;
}