package org.smgame.server;

import java.rmi.RemoteException;
import java.util.List;

import java.util.UUID;
import org.smgame.core.GUICoreMediator;
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
    public void test() throws RemoteException { }

//    @Override
//    public void addMenuItem(List<String> menuItemList) {
//        GUICoreMediator.addMenuItem(menuItemList);
//    }

    @Override
    public void createGame(UUID clientID, String gameName, GameMode gameMode, List<String> playerNameList, List<Boolean> playerTypeList) {
        GUICoreMediator.createGame(clientID, gameName, gameMode, playerNameList, playerTypeList);
    }

    @Override
    public void askCloseGame() {
        GUICoreMediator.askCloseGame();
    }

    @Override
    public void closeGame(UUID clientID) {
        GUICoreMediator.closeGame(clientID);
    }

    @Override
    public MainVO requestMainVO() {
        return GUICoreMediator.requestMainVO();
    }

    @Override
    public String getGameTitle(UUID clientID) {
        return GUICoreMediator.getGameTitle(clientID);
    }

    @Override
    public void requestCard(UUID clientID, int playerIndex, double bet) {
        GUICoreMediator.requestCard(clientID, playerIndex, bet);
    }

    @Override
    public void declareGoodScore(UUID clientID, int playerIndex, double bet) {
        GUICoreMediator.declareGoodScore(clientID, playerIndex, bet);
    }

    @Override
    public MenuVO requestMenuVO(UUID clientID) {
        return GUICoreMediator.requestMenuVO(clientID);
    }

    @Override
    public GameVO requestGameVO(UUID clientID) {
        return GUICoreMediator.requestGameVO(clientID);
    }

    @Override
    public Object[][] requestDataReport(UUID clientID) {
        return GUICoreMediator.requestDataReport(clientID);
    }

    @Override
    public StoryBoardVO requestStoryGames() throws RemoteException {
        try {
            return GUICoreMediator.requestStoryGames();
        } catch (Exception ex) {
            throw new RemoteException();
        }
    }
}
