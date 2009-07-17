package org.smgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import java.util.UUID;
import org.smgame.client.frontend.MenuVO;
import org.smgame.client.frontend.GameVO;
import org.smgame.client.frontend.MainVO;
import org.smgame.client.frontend.StoryBoardVO;
import org.smgame.core.GameMode;

/**Interfaccia mediatore di gioco
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public interface IStub extends Remote {

    /**testa la connessione
     *
     * @throws java.rmi.RemoteException
     */
    public void test() throws RemoteException;

    /**Crea partita
     *
     * @param clientID identificativo unico
     * @param gameName nome partita
     * @param gameMode tipo partita
     * @param playerNameList lista nomi
     * @param playerTypeList lista tipo
     *
     * @throws java.rmi.RemoteException
     */
    public void createGame(UUID clientID, String gameName, GameMode gameMode,
            List<String> playerNameList, List<Boolean> playerTypeList) throws RemoteException;

    /**richiede e restituisce l'oggetto mainVO
     *
     * @return mainVO
     *
     * @throws java.rmi.RemoteException
     */
    public MainVO requestMainVO() throws RemoteException;

    /**Richiede e restituisce l'oggetto menuVO per il menù
     *
     * @param clientID identificativo unico
     * @return menuVO
     *
     * @throws java.rmi.RemoteException
     */
    public MenuVO requestMenuVO(UUID clientID) throws RemoteException;

    /**richide e restituisce l'oggetto gameVO per la partita
     *
     * @param clientID identificativo unico
     * @return gamevo
     *
     * @throws java.rmi.RemoteException
     */
    public GameVO requestGameVO(UUID clientID) throws RemoteException;

    /**restituisce il titolo
     *
     * @param clientID identificativo unico
     * @return titolo
     *
     * @throws java.rmi.RemoteException
     */
    public String getGameTitle(UUID clientID) throws RemoteException;

    /**richiede carta
     *
     * @param clientID identificativo unico
     * @param playerIndex indice giocatore
     * @param bet puntata
     *
     * @throws java.rmi.RemoteException
     */
    public void requestCard(UUID clientID, int playerIndex, double bet) throws RemoteException;

    /**dichiara di star bene
     *
     * @param clientID identificativo unico
     * @param playerIndex indice giocatore
     * @param bet puntata
     *
     * @throws java.rmi.RemoteException
     */
    public void declareGoodScore(UUID clientID, int playerIndex, double bet) throws RemoteException;

    /**richiede e restituisce la matrice report
     *
     * @param clientID identificativo unico
     * @return matrice
     *
     * @throws java.rmi.RemoteException
     */
    public Object[][] requestDataReport(UUID clientID) throws RemoteException;

    /**richiede e restituisce l'oggetto storico partite
     *
     * @return storyboardVO
     *
     * @throws java.rmi.RemoteException
     */
    public StoryBoardVO requestStoryGames() throws RemoteException;
}
