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

    /**Crea partita
     *
     * @param gameName nome
     * @param gameSetting settaggi
     * @param playerNameList lista nomi
     * @param playerTypeList lista tipo
     *
     * @throws java.rmi.RemoteException
     */
    public void createGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) throws RemoteException;

    /**chiedi chiusura partita
     *
     * @throws java.rmi.RemoteException
     */
    public void askCloseGame() throws RemoteException;

    /**Chiudi partita
     *
     * @throws java.rmi.RemoteException
     */
    public void closeGame() throws RemoteException;

    /**salva partita
     *
     * @throws java.rmi.RemoteException
     */
    public void saveTransaction() throws RemoteException;
    /**richiede e restituisce l'oggetto mainVO
     *
     * @return mainVO
     *
     * @throws java.rmi.RemoteException
     */
    public MainVO requestMainVO() throws RemoteException;

    /**Richiede e restituisce l'oggetto menuVO per il menù
     *
     * @return menuVO
     *
     * @throws java.rmi.RemoteException
     */
    public MenuVO requestMenuVO() throws RemoteException;

    /**Richiede e restituisce l'oggetto loadgameVO per il caricamento partite
     *
     * @return loadgameVO
     *
     * @throws org.smgame.util.NoGamesException
     * @throws java.rmi.RemoteException
     */
    public LoadGameVO requestLoadGameVO() throws NoGamesException, RemoteException;

    /**richide e restituisce l'oggetto gameVO per la partita
     *
     * @return gamevo
     *
     * @throws java.rmi.RemoteException
     */
    public GameVO requestGameVO() throws RemoteException;

    /**restituisce il titolo
     *
     * @return titolo
     *
     * @throws java.rmi.RemoteException
     */
    public String getGameTitle() throws RemoteException;

    /**richiede carta
     *
     * @param playerIndex indice giocatore
     * @param bet puntata
     *
     * @throws java.rmi.RemoteException
     */
    public void requestCard(int playerIndex, double bet) throws RemoteException;

    /**dichiara di star bene
     *
     * @param playerIndex indice giocatore
     * @param bet puntata
     *
     * @throws java.rmi.RemoteException
     */
    public void declareGoodScore(int playerIndex, double bet) throws RemoteException;

    /**richiede e restituisce la matrice report
     *
     * @return matrice
     *
     * @throws java.rmi.RemoteException
     */
    public Object[][] requestDataReport() throws RemoteException;

    /**richiede e restituisce l'oggetto storico partite
     *
     * @return storyboardVO
     *
     * @throws java.rmi.RemoteException
     */
    public StoryBoardVO requestStoryGames() throws RemoteException;
}