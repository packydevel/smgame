package org.smgame.server;

import java.rmi.RemoteException;
import java.util.List;

import java.util.UUID;
import org.smgame.core.CoreProxy;
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

    @Override
    public void createGame(UUID clientID, String gameName, GameMode gameMode, List<String> playerNameList, List<Boolean> playerTypeList) {
        CoreProxy.createGame(clientID, gameName, gameMode, playerNameList, playerTypeList);
    }

    @Override
    public void askCloseGame() {
        CoreProxy.askCloseGame();
    }

    @Override
    public void closeGame(UUID clientID) {
        CoreProxy.closeGame(clientID);
    }

    @Override
    public MainVO requestMainVO() {
        return CoreProxy.requestMainVO();
    }

    @Override
    public String getGameTitle(UUID clientID) {
        return CoreProxy.getGameTitle(clientID);
    }

    @Override
    public void requestCard(UUID clientID, int playerIndex, double bet) {
        CoreProxy.requestCard(clientID, playerIndex, bet);
    }

    @Override
    public void declareGoodScore(UUID clientID, int playerIndex, double bet) {
        CoreProxy.declareGoodScore(clientID, playerIndex, bet);
    }

    @Override
    public MenuVO requestMenuVO(UUID clientID) {
        return CoreProxy.requestMenuVO(clientID);
    }

    @Override
    public GameVO requestGameVO(UUID clientID) {
        return CoreProxy.requestGameVO(clientID);
    }

    @Override
    public Object[][] requestDataReport(UUID clientID) {
        return CoreProxy.requestDataReport(clientID);
    }

    @Override
    public StoryBoardVO requestStoryGames() throws RemoteException {
        try {
            return CoreProxy.requestStoryGames();
        } catch (Exception ex) {
            throw new RemoteException();
        }
    }
}
