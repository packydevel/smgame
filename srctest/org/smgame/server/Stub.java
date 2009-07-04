package org.smgame.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import org.smgame.core.GUICoreMediator;
import org.smgame.core.GameSetting;
import org.smgame.client.frontend.GameVO;
import org.smgame.client.frontend.LoadGameVO;
import org.smgame.client.frontend.MainVO;
import org.smgame.client.frontend.MenuVO;
import org.smgame.client.frontend.StoryBoardVO;
import org.smgame.util.NoGamesException;

/**Oggetto remoto
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class Stub implements IGameMediator {

    @Override
    public void test() throws RemoteException {
    }

    @Override
    public void addMenuItem(List<String> menuItemList) {
        GUICoreMediator.addMenuItem(menuItemList);
    }

    @Override
    public void createGame(String gameName, GameSetting gameSetting, List<String> playerNameList, List<Boolean> playerTypeList) {
        GUICoreMediator.createGame(gameName, gameSetting, playerNameList, playerTypeList);
    }

    @Override
    public void askCloseGame() {
        GUICoreMediator.askCloseGame();
    }

    @Override
    public void closeGame() {
        GUICoreMediator.closeGame();
    }

    @Override
    public void saveTransaction() {
        GUICoreMediator.saveTransaction();
    }

    public MainVO requestMainVO() {
        return GUICoreMediator.requestMainVO();
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

    public StoryBoardVO requestStoryGames(){
        return GUICoreMediator.requestStoryGames();
    }
}