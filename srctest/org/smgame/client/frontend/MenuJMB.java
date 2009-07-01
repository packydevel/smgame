package org.smgame.client.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**Classe barra del menù
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class MenuJMB extends JMenuBar {

    private JMenu gameJM;
    private JMenu newGameJM;
    private JMenu toolJM;
    private JMenu helpJM;
    private JMenuItem newOnLineGameJMI;
    private JMenuItem newOffLineGameJMI;
    private JMenuItem loadGameJMI;
    private JMenuItem saveGameJMI;
    private JMenuItem closeGameJMI;
    private JMenuItem exitGameJMI;
    private JMenuItem storyBoardJMI;
    private JMenuItem testConnectionJMI;
    private JMenuItem userGuideJMI;
    private JMenuItem refGuideJMI;
    private JMenuItem javadocJMI;
    private JMenuItem aboutJMI;
    private ArrayList<JMenuItem> menuItemListJMI = new ArrayList<JMenuItem>();

    /**Costruttore
     *
     */
    public MenuJMB() {

        newOnLineGameJMI = new JMenuItem();
        newOnLineGameJMI.setText("Partita OnLine");
        newOnLineGameJMI.setName("newOnLineGameJMI");
        newOnLineGameJMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItemListJMI.add(newOnLineGameJMI);

        newOffLineGameJMI = new JMenuItem();
        newOffLineGameJMI.setText("Partita OffLine");
        newOffLineGameJMI.setName("newOffLineGameJMI");
        newOffLineGameJMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        menuItemListJMI.add(newOffLineGameJMI);

        loadGameJMI = new JMenuItem();
        loadGameJMI.setText("Carica ...");
        loadGameJMI.setName("loadGameJMI");
        loadGameJMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        menuItemListJMI.add(loadGameJMI);

        saveGameJMI = new JMenuItem();
        saveGameJMI.setText("Salva ...");
        saveGameJMI.setName("saveGameJMI");
        saveGameJMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItemListJMI.add(saveGameJMI);

        closeGameJMI = new JMenuItem();
        closeGameJMI.setText("Chiudi");
        closeGameJMI.setName("closeGameJMI");
        closeGameJMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        menuItemListJMI.add(closeGameJMI);

        exitGameJMI = new JMenuItem();
        exitGameJMI.setText("Esci");
        exitGameJMI.setName("exitGameJMI");
        exitGameJMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItemListJMI.add(exitGameJMI);

        newGameJM = new JMenu("newGameJM");
        newGameJM.setText("Nuova");
        newGameJM.setMnemonic(KeyEvent.VK_N);
        newGameJM.add(newOnLineGameJMI);
        newGameJM.add(newOffLineGameJMI);

        gameJM = new JMenu("GameJM");
        gameJM.setText("Partita");
        gameJM.setMnemonic(KeyEvent.VK_P);
        gameJM.add(newGameJM);
        gameJM.add(loadGameJMI);
        gameJM.add(saveGameJMI);
        gameJM.add(closeGameJMI);
        gameJM.add(exitGameJMI);

        storyBoardJMI = new JMenuItem();
        storyBoardJMI.setText("Storico Partite OnLine");
        storyBoardJMI.setName("storyBoardJMI");
        menuItemListJMI.add(storyBoardJMI);

        testConnectionJMI = new JMenuItem();
        testConnectionJMI.setText("Verifica Connessione al Server");
        testConnectionJMI.setName("testConnectionJMI");
        menuItemListJMI.add(testConnectionJMI);

        toolJM = new JMenu("toolJM");
        toolJM.setText("Strumenti");
        toolJM.setMnemonic(KeyEvent.VK_S);
        toolJM.add(storyBoardJMI);
        toolJM.add(testConnectionJMI);

        userGuideJMI = new JMenuItem();
        userGuideJMI.setText("Manuale d'uso");
        userGuideJMI.setName("userGuideJMI");
        userGuideJMI.setAccelerator(KeyStroke.getKeyStroke("F1"));
        menuItemListJMI.add(userGuideJMI);

        refGuideJMI = new JMenuItem();
        refGuideJMI.setText("Manuale tecnico");
        refGuideJMI.setName("refGuideJMI");
        refGuideJMI.setAccelerator(KeyStroke.getKeyStroke("F2"));
        menuItemListJMI.add(refGuideJMI);

        javadocJMI = new JMenuItem();
        javadocJMI.setText("SMGame JavaDoc");
        javadocJMI.setName("javadocJMI");
        javadocJMI.setAccelerator(KeyStroke.getKeyStroke("F3"));
        menuItemListJMI.add(javadocJMI);

        aboutJMI = new JMenuItem();
        aboutJMI.setText("About ...");
        aboutJMI.setName("aboutJMI");
        aboutJMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, ActionEvent.SHIFT_MASK));
        menuItemListJMI.add(aboutJMI);

        helpJM = new javax.swing.JMenu("helpJM");
        helpJM.setText("Help");
        helpJM.setMnemonic(KeyEvent.VK_H);
        helpJM.add(userGuideJMI);
        helpJM.add(refGuideJMI);
        helpJM.add(javadocJMI);
        helpJM.add(aboutJMI);

        add(gameJM);
        add(toolJM);
        add(helpJM);
    }

    /**Restituisce l'item chiusura partita
     *
     * @return item chiusura
     */
    public JMenuItem getCloseGameJMI() {
        return closeGameJMI;
    }

    /**Restituisce l'item uscita dal gioco
     *
     * @return item uscita
     */
    public JMenuItem getExitGameJMI() {
        return exitGameJMI;
    }

    /**Restituisce l'item carica partite
     *
     * @return item carica
     */
    public JMenuItem getLoadGameJMI() {
        return loadGameJMI;
    }

    /**restituisce l'item nuova partita online
     *
     * @return new online
     */
    public JMenuItem getNewOnLineGameJMI() {
        return newOnLineGameJMI;
    }

    /**Restituisce l'item nuova partita offline
     *
     * @return item new offline
     */
    public JMenuItem getNewOffLineGameJMI() {
        return newOffLineGameJMI;
    }

    /**Restituisce l'item salva partita
     *
     * @return item salva
     */
    public JMenuItem getSaveGameJMI() {
        return saveGameJMI;
    }

    /**Restituisce l'item storico partite
     *
     * @return item storico
     */
    public JMenuItem getStoryBoardJMI() {
        return storyBoardJMI;
    }

    /**Restituisce l'item testa connessione
     *
     * @return item test
     */
    public JMenuItem getTestConnectionJMI() {
        return testConnectionJMI;
    }

    /**Restituisce l'item guida utente
     *
     * @return guida utente
     */
    public JMenuItem getUserGuideJMI() {
        return userGuideJMI;
    }

    /**restituisce l'item javadoc
     *
     * @return item javadoc
     */
    public JMenuItem getJavadocJMI() {
        return javadocJMI;
    }

    /**restituisce l'item guida di riferimento
     *
     * @return item guida
     */
    public JMenuItem getRefGuideJMI() {
        return refGuideJMI;
    }

    /**Restituisce item about
     *
     * @return item about
     */
    public JMenuItem getAboutJMI() {
        return aboutJMI;
    }

    /**Restituisce lista di item di menù
     *
     * @return lista item
     */
    public List<JMenuItem> getMenuItemListJMI() {
        return menuItemListJMI;
    }
}
