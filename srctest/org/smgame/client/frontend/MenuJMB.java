/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.client.frontend;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuJMB extends JMenuBar {

    private JMenu gameJM;
    private JMenu newGameJM;
    private JMenu scoreBoardJM;
    private JMenu toolJM;
    private JMenu helpJM;
    private JMenuItem newOnLineGameJMI;
    private JMenuItem newOffLineGameJMI;
    private JMenuItem loadGameJMI;
    private JMenuItem saveGameJMI;
    private JMenuItem closeGameJMI;
    private JMenuItem exitGameJMI;
    private JMenuItem scoreBoardJMI;
    private JMenuItem gameSettingsJMI;
    private JMenuItem globalSettingsJMI;
    private JMenuItem testConnectionJMI;
    private JMenuItem helpContentsJMI;
    private JMenuItem javadocJMI;
    private JMenuItem aboutJMI;
    private ArrayList<JMenuItem> menuItemListJMI = new ArrayList<JMenuItem>();

    /**
     *
     * @author packyuser
     */
    public MenuJMB() {

        newOnLineGameJMI = new JMenuItem("NewOnLineGameJMI");
        newOnLineGameJMI.setText("Partita OnLine");
        newOnLineGameJMI.setName("newOnLineGameJMI");
        menuItemListJMI.add(newOnLineGameJMI);

        newOffLineGameJMI = new JMenuItem("NewOffLineGameJMI");
        newOffLineGameJMI.setText("Partita OffLine");
        newOffLineGameJMI.setName("newOffLineGameJMI");
        menuItemListJMI.add(newOffLineGameJMI);

        loadGameJMI = new JMenuItem("LoadGameJMI");
        loadGameJMI.setText("Carica ...");
        loadGameJMI.setName("loadGameJMI");
        menuItemListJMI.add(loadGameJMI);

        saveGameJMI = new JMenuItem("SaveGameJMI");
        saveGameJMI.setText("Salva ...");
        saveGameJMI.setName("saveGameJMI");
        menuItemListJMI.add(saveGameJMI);

        closeGameJMI = new JMenuItem("CloseGameJMI");
        closeGameJMI.setText("Chiudi");
        closeGameJMI.setName("closeGameJMI");
        menuItemListJMI.add(closeGameJMI);

        exitGameJMI = new JMenuItem("ExitGameJMI");
        exitGameJMI.setText("Esci");
        exitGameJMI.setName("exitGameJMI");
        menuItemListJMI.add(exitGameJMI);

        newGameJM = new JMenu("newGameJM");
        newGameJM.setText("Nuova");
        newGameJM.setName("newGameJM");
        newGameJM.add(newOnLineGameJMI);
        newGameJM.add(newOffLineGameJMI);

        gameJM = new JMenu("GameJM");
        gameJM.setText("Partita");
        gameJM.add(newGameJM);
        gameJM.add(loadGameJMI);
        gameJM.add(saveGameJMI);
        gameJM.add(closeGameJMI);
        gameJM.add(exitGameJMI);

        scoreBoardJMI = new JMenuItem("ScoreBoardJMI");
        scoreBoardJMI.setText("ScoreBoard");
        scoreBoardJMI.setName("scoreBoardJMI");
        menuItemListJMI.add(scoreBoardJMI);

        scoreBoardJM = new JMenu("ScoreBoardJM");
        scoreBoardJM.setText("Punteggio");
        scoreBoardJM.add(scoreBoardJMI);

        gameSettingsJMI = new JMenuItem("GameSettingsJMI");
        gameSettingsJMI.setText("Preferenze di gioco");
        gameSettingsJMI.setName("gameSettingsJMI");
        menuItemListJMI.add(gameSettingsJMI);

        globalSettingsJMI = new JMenuItem("GlobalSettingsJMI");
        globalSettingsJMI.setText("Preferenze globali");
        globalSettingsJMI.setName("globalSettingsJMI");
        menuItemListJMI.add(globalSettingsJMI);

        testConnectionJMI = new JMenuItem("TestConnectionJMI");
        testConnectionJMI.setText("Verifica Connessione al Server");
        testConnectionJMI.setName("testConnectionJMI");
        menuItemListJMI.add(testConnectionJMI);

        toolJM = new JMenu("ToolJM");
        toolJM.setText("Strumenti");
        toolJM.add(gameSettingsJMI);
        toolJM.add(globalSettingsJMI);
        toolJM.add(testConnectionJMI);

        javadocJMI = new JMenuItem("JavaDocJMI");
        javadocJMI.setText("SMGame JavaDoc");
        javadocJMI.setName("javadocJMI");
        menuItemListJMI.add(javadocJMI);

        helpContentsJMI = new JMenuItem("HelpContentsJMI");
        helpContentsJMI.setText("Sommario");
        helpContentsJMI.setName("helpContentsJMI");
        menuItemListJMI.add(helpContentsJMI);

        aboutJMI = new JMenuItem("AboutJMI");
        aboutJMI.setText("About ...");
        aboutJMI.setName("aboutJMI");
        menuItemListJMI.add(aboutJMI);

        helpJM = new javax.swing.JMenu("HelpJM");
        helpJM.setText("Help");
        helpJM.add(helpContentsJMI);
        helpJM.add(javadocJMI);
        helpJM.add(aboutJMI);

        add(gameJM);
        add(scoreBoardJM);
        add(toolJM);
        add(helpJM);
        for (JMenuItem jmi: menuItemListJMI){
        System.out.println(jmi.getName());}
    }

    public JMenuItem getCloseGameJMI() {
        return closeGameJMI;
    }

    public JMenuItem getExitGameJMI() {
        return exitGameJMI;
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

    public JMenuItem getSaveGameJMI() {
        return saveGameJMI;
    }

    public JMenuItem getScoreBoardJMI() {
        return scoreBoardJMI;
    }

    public JMenuItem getTestConnectionJMI() {
        return testConnectionJMI;
    }

    public JMenuItem getJavadocJMI() {
        return javadocJMI;
    }

    public List<JMenuItem> getMenuItemListJMI() {
        return menuItemListJMI;
    }
}
