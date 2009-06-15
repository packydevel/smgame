/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.smgame.core.GUICoreMediator;
import org.smgame.core.GameSetting;
import org.smgame.client.frontend.GameVO;
import org.smgame.client.frontend.LoadGameVO;
import org.smgame.client.frontend.MenuVO;
import org.smgame.util.NoGamesException;

/**
 *
 * @author packyuser
 */
public class Stub implements IGameMediator {

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
