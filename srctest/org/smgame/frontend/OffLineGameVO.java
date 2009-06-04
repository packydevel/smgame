/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author packyuser
 */
public class OffLineGameVO {

    private ArrayList<Integer> playerList = new ArrayList<Integer>();
    private HashMap<Integer, String> playerNameMap = new HashMap<Integer, String>();
    private HashMap<Integer, String> playerCreditMap = new HashMap<Integer, String>();
    private HashMap<Integer, ArrayList<ImageIcon>> playerCardsImageMap = new HashMap<Integer, ArrayList<ImageIcon>>();
    private HashMap<Integer, String> playerStakeMap = new HashMap<Integer, String>();
    private HashMap<Integer, String> playerScoreMap = new HashMap<Integer, String>();
    private HashMap<Integer, Boolean> playerFirstCardDiscoveredMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> playerRoleMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> playerPlayingMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> playerStatusMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> playerMaxScore = new HashMap<Integer, Boolean>();
    private boolean endManche = false,  endGame = false;
    String exceptionMessage;

    public HashMap<Integer, String> getPlayerNameMap() {
        return playerNameMap;
    }

    public HashMap<Integer, String> getPlayerCreditMap() {
        return playerCreditMap;
    }

    public HashMap<Integer, ArrayList<ImageIcon>> getPlayerCardsImageMap() {
        return playerCardsImageMap;
    }

    public ArrayList<Integer> getPlayerIndexList() {
        return playerList;
    }

    public HashMap<Integer, String> getPlayerScoreMap() {
        return playerScoreMap;
    }

    public HashMap<Integer, String> getPlayerStakeMap() {
        return playerStakeMap;
    }

    public HashMap<Integer, Boolean> getPlayerFirstCardDiscoveredMap() {
        return playerFirstCardDiscoveredMap;
    }

    public HashMap<Integer, Boolean> getPlayerRoleMap() {
        return playerRoleMap;
    }

    public HashMap<Integer, Boolean> getPlayerPlayingMap() {
        return playerPlayingMap;
    }

    public HashMap<Integer, Boolean> getPlayerStatusMap() {
        return playerStatusMap;
    }

    public HashMap<Integer, Boolean> getPlayerMaxScore() {
        return playerMaxScore;
    }

    public boolean isEndManche() {
        return endManche;
    }

    public void setEndManche(boolean flag) {
        endManche = flag;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean flag) {
        endGame = flag;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String message) {
        exceptionMessage = message;
    }
}
