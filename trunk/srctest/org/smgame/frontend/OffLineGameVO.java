package org.smgame.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

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
    private HashMap<Integer, Boolean> playerMaxScoreMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> playerRequestBetMap = new HashMap<Integer, Boolean>();
    private boolean endManche = false,  endGame = false;
    String exceptionMessage;

    /**Restituisce la mappa dei nomi giocatori
     *
     * @return
     */
    public HashMap<Integer, String> getPlayerNameMap() {
        return playerNameMap;
    }

    /**Restituisce la mappa dei crediti giocatori
     *
     * @return
     */
    public HashMap<Integer, String> getPlayerCreditMap() {
        return playerCreditMap;
    }

    /**Restituisce la mappa di lista di imageicon
     *
     * @return
     */
    public HashMap<Integer, ArrayList<ImageIcon>> getPlayerCardsImageMap() {
        return playerCardsImageMap;
    }

    /**Restituisce la lista indice dei giocatori
     *
     * @return
     */
    public ArrayList<Integer> getPlayerIndexList() {
        return playerList;
    }

    /**Restituisce la mappa dei punteggi giocatori
     *
     * @return
     */
    public HashMap<Integer, String> getPlayerScoreMap() {
        return playerScoreMap;
    }

    /**Restituisce la mappa delle puntate dei giocatori
     *
     * @return
     */
    public HashMap<Integer, String> getPlayerStakeMap() {
        return playerStakeMap;
    }

    /**restituisce la mappa dei booleani per la prima carta scoperta dei giocatori
     *
     * @return
     */
    public HashMap<Integer, Boolean> getPlayerFirstCardDiscoveredMap() {
        return playerFirstCardDiscoveredMap;
    }

    /**restituisce la mappa dei booleani relatiiv al ruolo dei giocatori (mazziere/normale)
     *
     * @return
     */
    public HashMap<Integer, Boolean> getPlayerRoleMap() {
        return playerRoleMap;
    }


    public HashMap<Integer, Boolean> getPlayerPlayingMap() {
        return playerPlayingMap;
    }

    public HashMap<Integer, Boolean> getPlayerStatusMap() {
        return playerStatusMap;
    }

    public HashMap<Integer, Boolean> getPlayerMaxScoreMap() {
        return playerMaxScoreMap;
    }

    public HashMap<Integer, Boolean> getPlayerRequestBetMap() {
        return playerRequestBetMap;
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
