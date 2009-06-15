package org.smgame.core;

import java.io.Serializable;

import java.util.Date;
import java.util.GregorianCalendar;

import org.smgame.core.card.Deck;
import org.smgame.core.player.PlayerList;

/**Classe gioco
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class Game implements Serializable {

    private static Game game;
    private long gameID;
    private String gameName;
    private GameMode gameMode;
    private GameSetting gameSetting;
    private GameEngine gameEngine;
    private Deck deck;
    private PlayerList playerList;
    private Date creationDate,  lastSaveDate;

    /**Costruttore vuoto
     *
     */
    public Game() { }

    /**imposta la data di creazione
     *
     * @param creationDate data creazione
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**imposta il mazzo
     *
     * @param deck mazzo
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**Genera/inizializza il motore di gioco
     *
     */
    public void generateGameEngine() {
        deck = new Deck();
        gameEngine = new GameEngine();
        gameEngine.setDeck(deck);
        gameEngine.setGameSetting(gameSetting);
        gameEngine.setPlayerList(playerList);
    }

    /**Genera l'id della partita
     *
     */
    public void generateGameID() {
        this.gameID = new GregorianCalendar().getTimeInMillis();
    }

    /**Restituisce l'id della partita
     *
     * @return id
     */
    public long getGameID() {
        return gameID;
    }

    /**imposta il nome della partita
     *
     * @param gameName nome
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**Restituisce il tipo di partita
     *
     * @return tipo partita
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**Imposta il tipo di partita
     *
     * @param gameMode tipo partita
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**imposta i settaggi della partita
     *
     * @param gameSetting settaggi
     */
    public void setGameSetting(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    /**imposta l'ultima data salvata
     *
     * @param lastSaveDate data
     */
    public void setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }

    /**imposta la lista dei player
     *
     * @param playerList lista giocatori
     */
    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    /**Restituisce il nome della partita
     *
     * @return nome
     */
    public String getGameName() {
        return gameName;
    }

    /**Restituisce la data di creazione
     *
     * @return data
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**Restituisce la data dell'ultimo salvataggio
     *
     * @return data
     */
    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    /**restituisce la lista dei player
     *
     * @return lista giocatori
     */
    public PlayerList getPlayerList() {
        return playerList;
    }

    /**restituisce il motore di gioco
     *
     * @return motore
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }
/*
    public void printTest() {
        System.out.println("Sequenza iniziale di Carte");
        deck.print();
        System.out.println("");
        System.out.println("Sequenza di Carte dopo mescolamento:");
        deck.shuffle();
        deck.print();
    }
*/
}
