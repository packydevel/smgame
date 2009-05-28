/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuJMB {

    private JMenu gameJM;
    private JMenu newGameJM;
    private JMenu playerJM;
    private JMenu settingsJM;
    private JMenu helpJM;
    private JMenuItem newOnLineGameJMI;
    private JMenuItem newOffLineGameJMI;
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

        newOnLineGameJMI = new JMenuItem("NewOnLineGameJMI");
        newOnLineGameJMI.setText("Partita OnLine");

        newOffLineGameJMI = new JMenuItem("NewOffLineGameJMI");
        newOffLineGameJMI.setText("Partita OffLine");

        loadGameJMI = new JMenuItem("LoadGameJMI");
        loadGameJMI.setText("Carica ...");

        saveGameJMI = new JMenuItem("SaveGameJMI");
        saveGameJMI.setText("Salva ...");
        saveGameJMI.setEnabled(false);

        closeGameJMI = new JMenuItem("CloseGameJMI");
        closeGameJMI.setText("Chiudi");
        closeGameJMI.setEnabled(false);

        exitGameJMI = new JMenuItem("ExitGameJMI");
        exitGameJMI.setText("Esci");

        newGameJM = new JMenu("newGameJM");
        newGameJM.setText("Nuova");
        newGameJM.add(newOnLineGameJMI);
        newGameJM.add(newOffLineGameJMI);

        gameJM = new JMenu("GameJM");
        gameJM.setText("Game");
        gameJM.add(newGameJM);
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

    public JMenuItem getNewOnLineGameJMI() {
        return newOnLineGameJMI;
    }

    public JMenuItem getNewOffLineGameJMI() {
        return newOffLineGameJMI;
    }

    public JMenu getPlayerJM() {
        return playerJM;
    }

    public JMenuItem getSaveGameJMI() {
        return saveGameJMI;
    }
}
