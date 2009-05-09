/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.main;

/**
 *
 * @author packyuser
 */
public class GameSetting {

    private final int MIN_PLAYERS = 2;
    private final int MAX_PLAYERS = 12;
    private int numPlayers = 4;

    public void setNumPlayers(int numPlayers) {
        if (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
            System.out.println("Numero errato di giocatori!!!");
        } else {
            this.numPlayers = numPlayers;
        }
    }

    public GameSetting() {
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
