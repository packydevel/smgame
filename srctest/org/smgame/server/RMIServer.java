/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.smgame.core.GUICoreMediator;
import org.smgame.core.GameSetting;
import org.smgame.frontend.LoadGameVO;
import org.smgame.frontend.MenuVO;
import org.smgame.frontend.GameVO;
import org.smgame.util.NoGamesException;

/**
 *
 * @author packyuser
 */
public class RMIServer implements IGameMediator {

    private static RMIServer server;

// Must implement constructor
// to throw RemoteException:
    private RMIServer() {

        try {
            String name = "//localhost/ServerMediator";
            System.setSecurityManager(new RMISecurityManager());
            Registry registry = LocateRegistry.getRegistry();
            IGameMediator stub = (IGameMediator) UnicastRemoteObject.exportObject(this, 0);//, 2005);
            registry.rebind(name, stub);
            System.out.println("Ready to do time");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static RMIServer getInstance() {
        if (server == null) {
            server = new RMIServer();
        }

        return server;
    }

    public void addMenuItem(List<String> menuItemList) {
        GUICoreMediator.addMenuItem(menuItemList);
    }

    public void createGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        GUICoreMediator.createGame(gameName, gameSetting, playerNameList, playerTypeList);
    }

    public void askCloseGame() {
        GUICoreMediator.askCloseGame();
    }

    public void closeGame() {
        GUICoreMediator.closeGame();
    }

    public void saveGame() {
        GUICoreMediator.saveGame();
    }

    public void loadGame(String gameName) throws FileNotFoundException, IOException, ClassNotFoundException {
        GUICoreMediator.loadGame(gameName);
    }

    public void loadGames() throws FileNotFoundException, IOException, ClassNotFoundException {
        GUICoreMediator.loadGames();
    }

    public LoadGameVO requestLoadGameVO() throws NoGamesException {
        return GUICoreMediator.requestLoadGameVO();
    }

    public String getGameTitle() {
        return GUICoreMediator.getGameTitle();
    }

    public void requestCard(int playerIndex, double bet) {
        GUICoreMediator.requestCard(playerIndex, bet);
    }

    public void declareGoodScore(int playerIndex, double bet) {
        GUICoreMediator.declareGoodScore(playerIndex, bet);
    }

    public MenuVO requestMenuVO() {
        return GUICoreMediator.requestMenuVO();
    }

    public GameVO requestGameVO() {
        return GUICoreMediator.requestGameVO();
    }

    public Object[][] requestDataReport() {
        return GUICoreMediator.requestDataReport();
    }
}

