package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.smgame.client.ClientProxy;

/**Gestore interfaccia grafica
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class MainJF extends JFrame implements InternalFrameListener, NewGameListener {

    private static JDesktopPane desktop;
    private MenuJMB menuJMB;
    private NewGameJIF newOnLineGameJIF;
    private NewGameJIF newGameJIF;
    private LoadGameJIF loadGameJIF;
    private GameJIF gameJIF;
    private MenuVO menuVO;
    private int desktopWidth, desktopHeight, internalFrameWidth, internalFrameHeight, xbound, ybound;

    /**Costruttore
     * 
     */
    public MainJF() {
        super("SMGame - Gioco Italiano del Sette e 1/2");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<String> menuItemNameList = new ArrayList<String>();

        setSize(1024, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        desktop = new JDesktopPane();
        desktop.setDesktopManager(new CustomDM());
        getContentPane().add(BorderLayout.CENTER, desktop);
        setVisible(true);
        desktopWidth = desktop.getWidth();
        desktopHeight = desktop.getHeight();

        menuJMB = new MenuJMB();
        for (JMenuItem jmi : menuJMB.getMenuItemListJMI()) {
            jmi.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    jMenu1ActionPerformed(evt);
                }
            });
        }

        setJMenuBar(menuJMB);

        ClientProxy.getInstance().addMenuItem(menuItemNameList);

        refreshMenuItem();
    }

    /**aggiorna gli item del menà
     *
     */
    private void refreshMenuItem() {
        menuVO = ClientProxy.getInstance().requestMenuVO();

        if (menuVO == null) {
            analyzeVO(ClientProxy.getInstance().requestMainVO());
        } else {
            for (JMenuItem jmi : menuJMB.getMenuItemListJMI()) {
                jmi.setEnabled(menuVO.getItemEnabledMap().get(jmi.getName()));
                System.out.println(jmi.getName() + " - " + jmi.isEnabled());
            }
        }
    }

    /**analizza i messaggi provenienti dal mainVO
     *
     * @param mainVO oggetto
     * @return eventuale intero indicante opzione scelta dall'utente
     */
    private int analyzeVO(MainVO mainVO) {
        if (mainVO.getMessageType() == MessageType.INFO) {
            JOptionPane.showInternalMessageDialog(desktop, mainVO.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (mainVO.getMessageType() == MessageType.WARNING) {
            return JOptionPane.showInternalConfirmDialog(desktop, mainVO.getMessage(), "Attenzione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        } else if (mainVO.getMessageType() == MessageType.ERROR) {
            JOptionPane.showInternalMessageDialog(desktop, mainVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    /**Intercetta le azioni del menù
     *
     * @param evt eventi da gestire
     */
    private void jMenu1ActionPerformed(ActionEvent evt) {
        if ((JMenuItem) evt.getSource() == menuJMB.getNewOnLineGameJMI()) {
            clearDesktop();
            internalFrameWidth = 400;
            internalFrameHeight = 520;
            xbound = (desktopWidth - internalFrameWidth) / 2;
            ybound = (desktopHeight - internalFrameHeight) / 2;
            newOnLineGameJIF = new NewGameJIF(true);
            newOnLineGameJIF.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
            newOnLineGameJIF.setBounds(xbound, ybound, internalFrameWidth, internalFrameHeight);
            newOnLineGameJIF.setVisible(true);
            newOnLineGameJIF.addInternalFrameListener(this);
            newOnLineGameJIF.addMyEventListener(this);
            desktop.add(newOnLineGameJIF);
            refreshMenuItem();
        } else if ((JMenuItem) evt.getSource() == menuJMB.getNewOffLineGameJMI()) {
            clearDesktop();
            internalFrameWidth = 400;
            internalFrameHeight = 520;
            xbound = (desktopWidth - internalFrameWidth) / 2;
            ybound = (desktopHeight - internalFrameHeight) / 2;
            newGameJIF = new NewGameJIF(false);
            newGameJIF.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
            newGameJIF.setBounds(xbound, ybound, internalFrameWidth, internalFrameHeight);
            newGameJIF.setVisible(true);
            newGameJIF.addInternalFrameListener(this);
            newGameJIF.addMyEventListener(this);
            desktop.add(newGameJIF);
            refreshMenuItem();
        } else if ((JMenuItem) evt.getSource() == menuJMB.getLoadGameJMI()) {
            clearDesktop();
            internalFrameWidth = 600;
            internalFrameHeight = 250;
            xbound = (desktopWidth - internalFrameWidth) / 2;
            ybound = (desktopHeight - internalFrameHeight) / 2;
            loadGameJIF = new LoadGameJIF();
            loadGameJIF.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
            loadGameJIF.setBounds(xbound, ybound, internalFrameWidth, internalFrameHeight);
            loadGameJIF.setVisible(true);
            loadGameJIF.addInternalFrameListener(this);
            loadGameJIF.addMyEventListener(this);
            desktop.add(loadGameJIF);
            refreshMenuItem();
        } else if ((JMenuItem) evt.getSource() == menuJMB.getSaveGameJMI()) {
            ClientProxy.getInstance().saveGame();
            analyzeVO(ClientProxy.getInstance().requestMainVO());
        } else if ((JMenuItem) evt.getSource() == menuJMB.getCloseGameJMI()) {
            executeCloseGame();
        } else if ((JMenuItem) evt.getSource() == menuJMB.getExitGameJMI()) {
            if (JOptionPane.showInternalConfirmDialog(desktop,
                    "Sei sicuro di voler uscire? Le partite non salvate saranno perse!", "Info",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                this.dispose();
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getStoryBoardJMI()) {
            StoryBoardVO storyVO = ClientProxy.getInstance().requestStoryGames();
            if (storyVO.getMessageType() == null) {
                JOptionPane.showMessageDialog(this, new StoryBoardJP(storyVO.getStory()));
            } else {
                analyzeVO(storyVO);
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getTestConnectionJMI()) {
            analyzeVO(ClientProxy.getInstance().connect());
        } else if ((JMenuItem) evt.getSource() == menuJMB.getUserGuideJMI()) {
            new HelpJF("UserGuide");
        } else if ((JMenuItem) evt.getSource() == menuJMB.getRefGuideJMI()) {
            new HelpJF("ReferenceGuide");
        } else if ((JMenuItem) evt.getSource() == menuJMB.getJavadocJMI()) {
            new HelpJF("JavaDoc");
        } else if ((JMenuItem) evt.getSource() == menuJMB.getAboutJMI()) {
            JOptionPane.showMessageDialog(this, new AboutJP(), "About ...", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**Esegue la creazione a livello gui di una nuova partita
     *
     * @param e evento nuova partita
     */
    public void newGameCreating(NewGameEvent e) {
        clearDesktop();

        internalFrameWidth = 1000;
        internalFrameHeight = 700;

        gameJIF = new GameJIF();
//        internalFrameWidth= gameJIF.getWidth();
//        internalFrameHeight=gameJIF.getHeight();
        xbound = (desktopWidth - internalFrameWidth) / 2;
        ybound = (desktopHeight - internalFrameHeight) / 2;
        gameJIF.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
        gameJIF.setBounds(xbound, ybound, internalFrameWidth, internalFrameHeight);
        gameJIF.setVisible(true);
        gameJIF.addInternalFrameListener(this);
        desktop.add(gameJIF);

        refreshMenuItem();

        System.out.println(desktopWidth);
        System.out.println(desktopHeight);
        System.out.println(xbound);
        System.out.println(ybound);
        System.out.println(menuJMB.getHeight());
    }

    /**Esegue la chiusura della partita
     *
     */
    private void executeCloseGame() {
        ClientProxy.getInstance().askCloseGame();

        if (analyzeVO(ClientProxy.getInstance().requestMainVO()) == 0) {
            ClientProxy.getInstance().closeGame();
            refreshMenuItem();
            clearDesktop();
        }
    }

    /**Pulisce il desktop
     *
     */
    private void clearDesktop() {
        for (JInternalFrame jiframe : desktop.getAllFrames()) {
            jiframe.dispose();
        }
    }

    public void internalFrameClosed(InternalFrameEvent e) {
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    public void internalFrameClosing(InternalFrameEvent e) {
        if (e.getInternalFrame() instanceof NewGameJIF) {
        } else if (e.getInternalFrame() instanceof LoadGameJIF) {
            refreshMenuItem();
        } else if (e.getInternalFrame() instanceof GameJIF) {
            executeCloseGame();
        }
    }

    public void internalFrameOpened(InternalFrameEvent e) {
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameActivated(InternalFrameEvent e) {
    }
} //end class
