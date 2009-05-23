package org.smgame.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.smgame.core.GameEngine;
import org.smgame.core.card.Deck;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.PlayerList;

/**Classe gioco
 *
 * @author luca
 * @author pasquale
 */
public class Game implements Serializable {

    private static Game game;
    private static GameSetting gameSetting;
    private static GameEngine gameEngine;
    private static Deck deck;
    private static PlayerList playerList;

    /**Costruttore
     *
     * @param gameSetting settaggi gioco
     */
    private Game(GameSetting gameSetting, PlayerList playerList) {

        char playerType = 'H';

        Game.gameSetting = gameSetting;
        Game.deck = Deck.getInstance();
        Game.playerList = playerList;

        Game.gameEngine = GameEngine.getInstance(gameSetting, deck, playerList);
    }

    public static Game create(GameSetting gameSetting, PlayerList playerList) {
        game = new Game(gameSetting, playerList);
        return game;
    }

    public void printTest() {
        System.out.println("Sequenza iniziale di Carte");
        deck.print();

        System.out.println("");

        System.out.println("Sequenza di Carte dopo mescolamento:");
        deck.shuffle();
        deck.print();
    }

    public static void save(String fileName) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(game);
        oos.flush();
        oos.close();
        fos.close();
    }

    public static void load(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        game = (Game) ois.readObject();
        ois.close();
        fis.close();
    }
}