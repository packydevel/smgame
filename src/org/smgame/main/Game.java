package org.smgame.main;

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
    private GameSetting gameSetting;
    private GameEngine gameEngine;
    private Deck deck;
    private PlayerList playerList;
    private Date creationDate,  lastSaveDate;

    /**Costruttore
     *
     * @param gameSetting settaggi gioco
     */
    private Game() {
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setDeck(Deck deck) {
        this.deck = Deck.getInstance();
    }

    public void generateGameEngine() {
        gameEngine = GameEngine.getInstance();
        gameEngine.setDeck(deck);
        gameEngine.setGameSetting(gameSetting);
        gameEngine.setPlayerList(playerList);
    }

    public void generateGameID() {
        this.gameID = new GregorianCalendar().getTimeInMillis();
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGameSetting(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    public void setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    public String getGameName() {
        return gameName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public void resetInstance() {
        deck.resetInstance();
        gameSetting.resetInstance();
        gameEngine.resetInstance();
    }

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