package org.smgame.main;

/**Classe 
 *
 * @author luca
 * @author pasquale
 */
public class SMGame {

    public static void main(String args[]) {
        GameSetting gameSetting = new GameSetting();
        gameSetting.setNumPlayers(4);
        Game game = new Game(gameSetting);
        game.printTest();
    }
}