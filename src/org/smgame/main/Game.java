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
    private static final String fileName = "/packydata/games.dat";

    /**Costruttore
     *
     * @param gameSetting settaggi gioco
     */
    private Game(String gameName, GameSetting gameSetting, PlayerList playerList) {
        this.gameID = new GregorianCalendar().getTimeInMillis();
        this.creationDate = new Date();
        this.gameName = gameName;
        this.gameSetting = gameSetting;
        this.deck = Deck.getInstance();
        this.playerList = playerList;
        this.gameEngine = GameEngine.getInstance(gameSetting, deck, playerList);
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

    public static Game create(String gameName, GameSetting gameSetting, PlayerList playerList) {
        game = new Game(gameName, gameSetting, playerList);
        return game;
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