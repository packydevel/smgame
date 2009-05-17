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
    private Game(GameSetting gameSetting) {

        char playerType = 'H';

        this.gameSetting = gameSetting;
        deck = Deck.getInstance();
        playerList = PlayerList.getInstance();

        for (int i = 1; i <= gameSetting.getNumPlayers(); ++i) {
            if (playerType == 'H') {
                playerList.getPlayerAL().add(new HumanPlayer());
            } else {
                playerList.getPlayerAL().add(new CPUPlayer());
            }
        }

        gameEngine = GameEngine.getInstance(gameSetting, deck, playerList);
    }

    public static Game create(GameSetting gameSetting) {
        game = new Game(gameSetting);
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