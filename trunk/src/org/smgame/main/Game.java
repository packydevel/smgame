package org.smgame.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private Date creationDate, lastSaveDate;
    private static final String fileName="/packydata/games.dat";

    /**Costruttore
     *
     * @param gameSetting settaggi gioco
     */
    private Game(String gameName, GameSetting gameSetting, PlayerList playerList) {
        this.gameID=new GregorianCalendar().getTimeInMillis();
        this.creationDate=new Date();
        this.gameName=gameName;
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

    public static void save() throws FileNotFoundException, IOException {
        Date lastSaveDate= new Date();
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(game);
        oos.flush();
        oos.close();
        fos.close();
    }

    public static Game load() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        game = (Game) ois.readObject();
        ois.close();
        fis.close();
        return game;
    }
}