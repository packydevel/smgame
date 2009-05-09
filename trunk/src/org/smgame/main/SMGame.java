/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.main;

/**
 *
 * @author packyuser
 */
public class SMGame {

    public static void main(String args[]) {

        GameSetting gameSetting = new GameSetting();
        gameSetting.setNumPlayers(4);
        Game game = new Game(gameSetting);

        game.printTest();
    }
}
