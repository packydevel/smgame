/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.frontend;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import org.smgame.core.player.Player;
import org.smgame.main.GameSetting;

/**
 *
 * @author packyuser
 */
public class NewGameEvent extends EventObject {

    private ArrayList<Player> playerList;
    private GameSetting gameSetting;

    public NewGameEvent(Object source,  List<Player> playerList, GameSetting gameSetting) {
        super(source);
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