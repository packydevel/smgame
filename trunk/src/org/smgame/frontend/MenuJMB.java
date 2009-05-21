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
    private JMenu settingsJM;
    private JMenu helpJM;
    private JMenuItem newGameJMI;
    private JMenuItem loadGameJMI;
    private JMenuItem saveGameJMI;
    private JMenuItem closeGameJMI;
    private JMenuItem exitGameJMI;
    private JMenuItem gameSettingsJMI;
    private JMenuItem globalSettingsJMI;
    private JMenuItem helpContentsJMI;
    private JMenuItem aboutJMI;
    private JMenuBar mainJMB;

    /**
     *
     * @author packyuser
     */
    public MenuJMB() {

        newGameJMI = new JMenuItem("NewGameJMI");
        newGameJMI.setText("New");

        loadGameJMI = new JMenuItem("LoadGameJMI");
        loadGameJMI.setText("Load ...");

        saveGameJMI = new JMenuItem("SaveGameJMI");
        saveGameJMI.setText("Save ...");

        closeGameJMI = new JMenuItem("CloseGameJMI");
        closeGameJMI.setText("Close");
        closeGameJMI.setEnabled(false);

        exitGameJMI = new JMenuItem("ExitGameJMI");
        exitGameJMI.setText("Exit");

        gameJM = new JMenu("GameJM");
        gameJM.setText("Game");
        gameJM.add(newGameJMI);
        gameJM.add(loadGameJMI);
        gameJM.add(saveGameJMI);
        gameJM.add(closeGameJMI);
        gameJM.add(exitGameJMI);

        playerJM = new JMenu("PlayerJM");
        playerJM.setText("Player");

        gameSettingsJMI = new JMenuItem("GameSettingsJMI");
        gameSettingsJMI.setText("Game Settings ...");

        globalSettingsJMI = new JMenuItem("GlobalSettingsJMI");
        globalSettingsJMI.setText("Global Settings ...");

        settingsJM = new JMenu("SettingsJM");
        settingsJM.setText("Settings");
        settingsJM.add(gameSettingsJMI);
        settingsJM.add(globalSettingsJMI);

        helpContentsJMI = new JMenuItem("HelpContentsJMI");
        helpContentsJMI.setText("Contents Help");

        aboutJMI = new JMenuItem("HboutJMI");
        aboutJMI.setText("About ...");

        helpJM = new javax.swing.JMenu("HelpJM");
        helpJM.setText("Help");
        helpJM.add(helpContentsJMI);
        helpJM.add(aboutJMI);


        mainJMB = new JMenuBar();
        mainJMB.add(gameJM);
        mainJMB.add(playerJM);
        mainJMB.add(settingsJM);
        mainJMB.add(helpJM);
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
        return mainJMB;
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
