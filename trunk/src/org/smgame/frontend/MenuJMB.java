/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuJMB extends JMenuBar {

    private JMenu gameJM;
    private JMenu playerJM;
    private JMenuBar jMenuBar1;
    private JMenuItem newGameJMI;
    private JMenuItem loadGameJMI;
    private JMenuItem saveGameJMI;
    private JMenuItem closeGameJMI;
    private JMenuItem exitGameJMI;

    /**
     *
     * @author packyuser
     */
    public MenuJMB() {

        jMenuBar1 = new JMenuBar();
        gameJM = new JMenu("GameJM");

        gameJM.setText("Game");
        newGameJMI = new javax.swing.JMenuItem("NewGameJMI");

        newGameJMI.setText("New");
        loadGameJMI = new javax.swing.JMenuItem("LoadGameJMI");

        loadGameJMI.setText("Load ...");
        saveGameJMI = new javax.swing.JMenuItem("SaveGameJMI");

        saveGameJMI.setText("Save ...");
        closeGameJMI = new javax.swing.JMenuItem("CloseGameJMI");

        closeGameJMI.setText("Close");
        exitGameJMI = new javax.swing.JMenuItem("ExitGameJMI");

        exitGameJMI.setText("Exit");

        gameJM.add(newGameJMI);

        gameJM.add(loadGameJMI);

        gameJM.add(saveGameJMI);

        gameJM.add(closeGameJMI);

        gameJM.add(exitGameJMI);
        playerJM = new javax.swing.JMenu("PlayerJM");

        playerJM.setText("Player"); // NOI18N

        jMenuBar1.add(gameJM);

        jMenuBar1.add(playerJM);
    }

    public JMenuItem getCloseGameJMI() {
        return closeGameJMI;
    }

    public JMenuItem getExitGameJMI() {
        return exitGameJMI;
    }

    public JMenu getGameJM() {
        return gameJM;
    }

    public JMenuBar getJMenuBar1() {
        return jMenuBar1;
    }

    public JMenuItem getLoadGameJMI() {
        return loadGameJMI;
    }

    public JMenuItem getNewGameJMI() {
        return newGameJMI;
    }

    public JMenu getPlayerJM() {
        return playerJM;
    }

    public JMenuItem getSaveGameJMI() {
        return saveGameJMI;
    }
}
