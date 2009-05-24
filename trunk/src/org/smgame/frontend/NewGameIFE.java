/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.frontend;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import org.smgame.core.player.Player;
import org.smgame.main.GameSetting;

/**
 *
 * @author packyuser
 */
public class NewGameIFE extends InternalFrameEvent {

    private ArrayList<Player> playerList;
    private GameSetting gameSetting;

    public NewGameIFE(JInternalFrame source, int id, List<Player> playerList, GameSetting gameSetting) {
        super(source, id);
        this.playerList = (ArrayList<Player>) playerList;
        this.gameSetting = gameSetting;
    }

    public GameSetting getGameSetting() {
        return gameSetting;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}
