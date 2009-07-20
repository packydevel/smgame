package org.smgame.client.frontend;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class GameVO implements Serializable {

    private static final long serialVersionUID = -7561966468991273552L;
    private ArrayList<Integer> playerList = new ArrayList<Integer>();
    private HashMap<Integer, String> playerNameMap = new HashMap<Integer, String>();
    private HashMap<Integer, Boolean> playerTypeMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, String> playerCreditMap = new HashMap<Integer, String>();
    private HashMap<Integer, ArrayList<ImageIcon>> playerCardsImageMap = new HashMap<Integer, ArrayList<ImageIcon>>();
    private HashMap<Integer, String> playerStakeMap = new HashMap<Integer, String>();
    private HashMap<Integer, String> playerScoreMap = new HashMap<Integer, String>();
    private HashMap<Integer, Boolean> playerFirstCardDiscoveredMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> playerRoleMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> playerPlayingMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, String> playerStatusMap = new HashMap<Integer, String>();
    private HashMap<Integer, Boolean> playerMaxScoreMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> playerRequestBetMap = new HashMap<Integer, Boolean>();
    private HashMap<Integer,Color> playerMaxCreditList = new HashMap<Integer,Color>();
    private boolean endManche = false,  endGame = false;
    private int currentManche;
    String exceptionMessage;

    /**Restituisce la mappa dei nomi giocatori
     *
     * @return hashmap numero-nome
     */
    public HashMap<Integer, String> getPlayerNameMap() {
        return playerNameMap;
    }

    /**Restituisce la mappa del tipo di giocatori (umano/cpu)
     *
     * @return hashmap numero-booleano
     */
    public HashMap<Integer, Boolean> getPlayerTypeMap() {
        return playerTypeMap;
    }

    /**Restituisce la mappa dei crediti giocatori
     *
     * @return hashmap numero-credito
     */
    public HashMap<Integer, String> getPlayerCreditMap() {
        return playerCreditMap;
    }

    /**Restituisce la mappa di lista di imageicon
     *
     * @return hashmap numero-lista immagini
     */
    public HashMap<Integer, ArrayList<ImageIcon>> getPlayerCardsImageMap() {
        return playerCardsImageMap;
    }

    /**Restituisce la lista indice dei giocatori
     *
     * @return lista
     */
    public ArrayList<Integer> getPlayerIndexList() {
        return playerList;
    }

    /**Restituisce la mappa dei punteggi giocatori
     *
     * @return hashmap numero-punteggio
     */
    public HashMap<Integer, String> getPlayerScoreMap() {
        return playerScoreMap;
    }

    /**Restituisce la mappa delle puntate dei giocatori
     *
     * @return hashmap numero-puntata
     */
    public HashMap<Integer, String> getPlayerStakeMap() {
        return playerStakeMap;
    }

    /**restituisce la mappa dei booleani per la prima carta scoperta dei giocatori
     *
     * @return hashmap numero-booleano
     */
    public HashMap<Integer, Boolean> getPlayerFirstCardDiscoveredMap() {
        return playerFirstCardDiscoveredMap;
    }

    /**restituisce la mappa dei booleani relatiiv al ruolo dei giocatori (mazziere/normale)
     *
     * @return hashmap numero-booleano
     */
    public HashMap<Integer, Boolean> getPlayerRoleMap() {
        return playerRoleMap;
    }

    /**Restituisce la mappa dei booleani relativa ai giocatori che giocano,
     *quindi il possesso del turno attuale
     *
     * @return hashmap numero-booleano
     */
    public HashMap<Integer, Boolean> getPlayerPlayingMap() {
        return playerPlayingMap;
    }

    /**Restituisce la mappa dei booleani relativa allo stato del momento del giocatore
     *
     * @return hashmap numero-stato
     */
    public HashMap<Integer, String> getPlayerStatusMap() {
        return playerStatusMap;
    }

    /**Restituisce la mappa dei booleani dei giocatori che hanno il massimo punteggio
     *
     * @return hashmap numero-booleano
     */
    public HashMap<Integer, Boolean> getPlayerMaxScoreMap() {
        return playerMaxScoreMap;
    }

    /**REstituisce la mappa dei booleanni dei player che hanno richiesto la carta
     *
     * @return hashmap numero-booleano
     */
    public HashMap<Integer, Boolean> getPlayerRequestBetMap() {
        return playerRequestBetMap;
    }

    /**restituisce se è finita la manche
     *
     * @return true = manche finita
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
     * @return true = fine partita
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

    /**restituisce il numero manche corrente
     *
     * @return numero manche
     */
    public int getCurrentManche() {
        return currentManche;
    }

    /**imposta il numero di manche corrente
     *
     * @param currentManche
     */
    public void setCurrentManche(int currentManche) {
        this.currentManche = currentManche;
    }

    /**Restituisce il messaggio dell'eccezione
     *
     * @return eccezione
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**imposta il messaggio dell'eccezione
     *
     * @param message eccezione
     */
    public void setExceptionMessage(String message) {
        exceptionMessage = message;
    }

    /**restituisce la lista delle posizioni dei player col massimo credito
     *
     * @return lista/singolo player
     */
    public HashMap<Integer,Color> getPlayerMaxCreditList() {
        return playerMaxCreditList;
    }

    /**imposta la lista dei colori per il credito del player
     *
     * @param playerMaxCreditList lista player
     */
    public void setPlayerMaxCreditList(HashMap<Integer,Color> playerMaxCreditList) {
        this.playerMaxCreditList = playerMaxCreditList;
    }


    /**resetta i valori
     *
     */
    public void clear() {
        getPlayerIndexList().clear();
        getPlayerNameMap().clear();
        getPlayerTypeMap().clear();
        getPlayerCreditMap().clear();
        getPlayerCardsImageMap().clear();
        getPlayerStakeMap().clear();
        getPlayerScoreMap().clear();
        getPlayerStatusMap().clear();
        getPlayerFirstCardDiscoveredMap().clear();
        getPlayerRoleMap().clear();
        getPlayerPlayingMap().clear();
        getPlayerRequestBetMap().clear();
        getPlayerMaxCreditList().clear();
        exceptionMessage = null;
    }
}//end class