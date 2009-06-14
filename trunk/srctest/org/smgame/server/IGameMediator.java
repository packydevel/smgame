/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import org.smgame.core.GameSetting;
import org.smgame.frontend.LoadGameVO;
import org.smgame.frontend.MenuVO;
import org.smgame.frontend.OffLineGameVO;
import org.smgame.frontend.OnLineGameVO;
import org.smgame.util.NoGamesException;

/**
 *
 * @author packyuser
 */
public interface IGameMediator extends Remote {

    public void addMenuItem(List<String> menuItemList) throws RemoteException;

    public void createGame(String gameName, GameSetting gameSetting, List<String> playerNameList,
            List<Boolean> playerTypeList) throws RemoteException;

    public void createOnLineGame(String gameName, GameSetting gameSetting, String playerName) throws RemoteException;

    public void askCloseGame() throws RemoteException;

    public void closeGame() throws RemoteException;

    public void saveGame() throws RemoteException;

    public void loadGame(String gameName) throws FileNotFoundException, IOException, ClassNotFoundException, RemoteException;

    public void loadGames() throws FileNotFoundException, IOException, ClassNotFoundException, RemoteException;

    public LoadGameVO requestLoadGameVO() throws NoGamesException, RemoteException;

    public String getGameTitle() throws RemoteException;

    public void requestCard(int playerIndex, double bet) throws RemoteException;

    public void declareGoodScore(int playerIndex, double bet) throws RemoteException;

    public MenuVO requestMenuVO() throws RemoteException;

    public OffLineGameVO requestOffLineGameVO() throws RemoteException;

    public OnLineGameVO requestOnLineGameVO() throws RemoteException;

    public Object[][] requestDataReport() throws RemoteException;
}
