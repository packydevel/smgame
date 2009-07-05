package org.smgame.server;

import java.rmi.RemoteException;
import java.util.List;

import org.smgame.core.GUICoreMediator;
import org.smgame.core.GameSetting;
import org.smgame.client.frontend.GameVO;
import org.smgame.client.frontend.MainVO;
import org.smgame.client.frontend.MenuVO;
import org.smgame.client.frontend.StoryBoardVO;
import org.smgame.core.GameMode;

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
    public void createGame(String gameName, GameSetting gameSetting, GameMode gameMode, List<String> playerNameList, List<Boolean> playerTypeList) {
        GUICoreMediator.createGame(gameName, gameSetting, gameMode, playerNameList, playerTypeList);
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
    public void saveTransaction() throws RemoteException {
        try {
            GUICoreMediator.saveTransaction();
        } catch (Exception e) {
            throw new RemoteException();
        }
    }

    @Override
    public MainVO requestMainVO() {
        return GUICoreMediator.requestMainVO();
    }

    @Override
    public String getGameTitle() {
        return GUICoreMediator.getGameTitle();
    }

    @Override
    public void requestCard(int playerIndex, double bet) {
        GUICoreMediator.requestCard(playerIndex, bet);
    }

    @Override
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

    public StoryBoardVO requestStoryGames() {
        return GUICoreMediator.requestStoryGames();
    }
}
