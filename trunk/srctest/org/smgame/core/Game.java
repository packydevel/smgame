package org.smgame.core;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import org.smgame.core.GameEngine;
import org.smgame.core.card.Deck;
import org.smgame.core.player.PlayerList;

/**Classe gioco
 *
 * @author luca
 * @author pasquale
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
    private Date creationDate, lastSaveDate;

    //Costruttore privato
    private Game() {
    }

    /**imposta la data di creazione
     *
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**imposta il mazzo
     *
     * @param deck
     */
    public void setDeck(Deck deck) {
        this.deck = Deck.getInstance();
    }

    /**Genera/inizializza il motore di gioco
     *
     */
    public void generateGameEngine() {
        deck=Deck.getInstance();
        gameEngine = GameEngine.getInstance();
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

    public long getGameID() {
        return gameID;
    }

    /**imposta il nome della partita
     *
     * @param gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**imposta i settaggi della partita
     *
     * @param gameSetting
     */
    public void setGameSetting(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    /**imposta l'ultima data salvata
     *
     * @param lastSaveDate
     */
    public void setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }

    /**imposta la lista dei player
     *
     * @param playerList
     */
    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    /**Restituisce il nome della partita
     *
     * @return
     */
    public String getGameName() {
        return gameName;
    }

    /**Restituisce la data di creazione
     *
     * @return
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**Restituisce la data dell'ultimo salvataggio
     *
     * @return
     */
    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    /**restituisce la lista dei player
     *
     * @return
     */
    public PlayerList getPlayerList() {
        return playerList;
    }

    /**restituisce l'istanza della partita
     *
     * @return
     */
    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    /**azzera/resetta l'istanza della partita
     *
     */
    public void resetInstance() {
        deck.resetInstance();
        gameSetting.resetInstance();
        gameEngine.resetInstance();
    }

    /**restituisce il motore di gioco
     *
     * @return
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void printTest() {
        System.out.println("Sequenza iniziale di Carte");
        deck.print();

        System.out.println("");

        System.out.println("Sequenza di Carte dopo mescolamento:");
        deck.shuffle();
        deck.print();
    }
}