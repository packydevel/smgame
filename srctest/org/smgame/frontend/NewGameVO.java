/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author packyuser
 */
public class NewGameVO {

    String gameName;
    ArrayList<String> playerName;
    ArrayList<Boolean> playerType;

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setPlayerName(List<String> playerName) {
        this.playerName = (ArrayList<String>) playerName;
    }

    public void setPlayerType(List<Boolean> playerType) {
        this.playerType = (ArrayList<Boolean>) playerType;
    }
}
