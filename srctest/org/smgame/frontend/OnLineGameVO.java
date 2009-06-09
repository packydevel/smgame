package org.smgame.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;


public class OnLineGameVO {

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

        /**Restituisce la mappa dei booleani relativa ai giocatori che giocano,
     *quindi il possesso del turno attuale
     *
     * @return
     */
    public HashMap<Integer, Boolean> getPlayerPlayingMap() {
        return playerPlayingMap;
    }

    /**Restituisce la mappa dei booleani relativa allo stato del momento del giocatore
     *
     * @return
     */
    public HashMap<Integer, Boolean> getPlayerStatusMap() {
        return playerStatusMap;
    }

    /**Restituisce la mappa dei booleani dei giocatori che hanno il massimo punteggio
     *
     * @return
     */
    public HashMap<Integer, Boolean> getPlayerMaxScoreMap() {
        return playerMaxScoreMap;
    }

    /**REstituisce la mappa dei booleanni dei player che hanno richiesto la carta
     *
     * @return
     */
    public HashMap<Integer, Boolean> getPlayerRequestBetMap() {
        return playerRequestBetMap;
    }

    /**restituisce se è finita la manche
     *
     * @return
     */
    public boolean isEndManche() {
        return endManche;
    }

    /**imposta la fine della manche
     *
     * @param flag
     */
    public void setEndManche(boolean flag) {
        endManche = flag;
    }

    /**REstituisce la fine dell partita
     *
     * @return
     */
    public boolean isEndGame() {
        return endGame;
    }

    /**imposta la fine della partita
     *
     * @param flag
     */
    public void setEndGame(boolean flag) {
        endGame = flag;
    }

    /**Restituisce il messaggio dell'eccezione
     *
     * @return
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**imposta il messaggio dell'eccezione
     *
     * @param message
     */
    public void setExceptionMessage(String message) {
        exceptionMessage = message;
    }

}//end class