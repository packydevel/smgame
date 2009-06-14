/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import org.smgame.core.GUICoreMediator;
import org.smgame.core.GameMode;
import org.smgame.core.GameSetting;
import org.smgame.frontend.LoadGameVO;
import org.smgame.frontend.MenuVO;
import org.smgame.frontend.OffLineGameVO;
import org.smgame.frontend.OnLineGameVO;
import org.smgame.server.IGameMediator;
import org.smgame.util.NoGamesException;

/**
 *
 * @author packyuser
 */
public class ClientMediator {

    private static ClientMediator clientMediator;
    private GameMode gameMode;
    private IGameMediator stub;

    private ClientMediator() throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry = LocateRegistry.getRegistry("localhost");

        stub = (IGameMediator) registry.lookup("//localhost/ServerMediator");

    }

    public static ClientMediator getInstance() {
        if (clientMediator == null) {
            try {
                clientMediator = new ClientMediator();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return clientMediator;
    }

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

    private void createGame(GameMode gameMode, String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        if (gameMode == GameMode.OFFLINE) {
            GUICoreMediator.createGame(gameName, gameSetting, playerNameList, playerTypeList);
        } else {
            this.gameMode = gameMode;
            try {
                stub.createGame(gameName, gameSetting, playerNameList, playerTypeList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createOffLineGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        createGame(GameMode.ONLINE, gameName, gameSetting, playerNameList, playerTypeList);
    }

    public void createOnLineGame(String gameName, GameSetting gameSetting, String playerName) {
        //createGame(GameMode.ONLINE, gameName, gameSetting, playerName);
    }

    public void askCloseGame() {
        try {
            stub.askCloseGame();
        } catch (Exception e) {
        }

    }

    public void closeGame() {
        try {
            stub.closeGame();
        } catch (Exception e) {
        }
    }

    public void saveGame() {
        try {
            stub.saveGame();
        } catch (Exception e) {
        }
    }

    public void loadGame(String gameName) throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            stub.loadGame(gameName);
        } catch (Exception e) {
        }
    }

    public void loadGames() throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            stub.loadGames();
        } catch (Exception e) {
        }
    }

    public LoadGameVO requestLoadGameVO() throws NoGamesException {
        try {
            return stub.requestLoadGameVO();
        } catch (Exception e) {
            return null;
        }

    }

    public String getGameTitle() {

        try {
            return stub.getGameTitle();
        } catch (Exception e) {
            return null;
        }
    }

    public void requestCard(int playerIndex, double bet) {
        try {
            stub.requestCard(playerIndex, bet);
        } catch (Exception e) {
        }
    }

    public void declareGoodScore(int playerIndex, double bet) {
        try {
            stub.declareGoodScore(playerIndex, bet);
        } catch (Exception e) {
        }
    }

    public MenuVO requestMenuVO() {
        try {
            return stub.requestMenuVO();
        } catch (Exception e) {
            return null;
        }
    }

    public OffLineGameVO requestOffLineGameVO() {
        try {
            return stub.requestOffLineGameVO();
        } catch (Exception e) {
            return null;
        }
    }

    public OnLineGameVO requestOnLineGameVO() {
        try {
            return stub.requestOnLineGameVO();
        } catch (Exception e) {
            return null;
        }
    }

    public Object[][] requestDataReport() {
        try {
            return stub.requestDataReport();
        } catch (Exception e) {
            return null;
        }
    }
}
