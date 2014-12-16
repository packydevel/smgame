package org.smgame.main;

/**Classe 
 *
 * @author luca
 * @author pasquale
 */
public class SMGame {

    public static void main(String args[]) {
        GameSetting gameSetting = GameSetting.getInstance();
        gameSetting.setNumPlayers(4);
        Game game = Game.getInstance();
        game.setGameSetting(gameSetting);
    }
}