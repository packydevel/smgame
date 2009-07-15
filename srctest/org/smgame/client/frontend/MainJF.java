package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public class MainJF extends JFrame implements WindowListener, InternalFrameListener, NewGameListener {

    private static JDesktopPane desktop;
    private MenuJMB menuJMB;
    private NewGameJIF newOnLineGameJIF;
    private NewGameJIF newGameJIF;
    private LoadGameJIF loadGameJIF;
    private GameJIF gameJIF;
    private HelpJF helpJF;
    private MenuVO menuVO;
    private int desktopWidth, desktopHeight, internalFrameWidth, internalFrameHeight, xbound, ybound;

    /**Costruttore
     * 
     */
    public MainJF() {
        super("SMGame - Gioco Italiano del Sette e 1/2");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        ArrayList<String> menuItemNameList = new ArrayList<String>();

        setSize(1024, 768);
        setResizable(false);
        addWindowListener(this);
        desktop = new JDesktopPane();
        desktop.setDesktopManager(new CustomDM());
        getContentPane().add(BorderLayout.CENTER, desktop);
        setVisible(true);
        desktopWidth = desktop.getWidth();
        desktopHeight = desktop.getHeight();

        menuJMB = new MenuJMB();
        for (JMenuItem jmi : menuJMB.getMenuItemListJMI()) {
            jmi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    jMenu1ActionPerformed(evt);
                }
            });
        }

        setJMenuBar(menuJMB);

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
           closeFrame();
        } else if ((JMenuItem) evt.getSource() == menuJMB.getStoryBoardJMI()) {
            StoryBoardVO storyVO = ClientProxy.getInstance().requestStoryGames();
            if (storyVO.getMessageType() == null) {
                JOptionPane.showInternalMessageDialog(desktop, new StoryBoardJP(storyVO.getStory()), "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                analyzeVO(storyVO);
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getTestConnectionJMI()) {
            analyzeVO(ClientProxy.getInstance().connect());
        } else if ((JMenuItem) evt.getSource() == menuJMB.getUserGuideJMI()) {
            showHelp("UserGuide");
        } else if ((JMenuItem) evt.getSource() == menuJMB.getRefGuideJMI()) {
            showHelp("ReferenceGuide");
        } else if ((JMenuItem) evt.getSource() == menuJMB.getJavadocJMI()) {
            showHelp("JavaDoc");
        } else if ((JMenuItem) evt.getSource() == menuJMB.getAboutJMI()) {
            JOptionPane.showMessageDialog(this, new AboutJP(), "About ...", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**Esegue la creazione a livello gui di una nuova partita
     *
     * @param e evento nuova partita
     */
    @Override
    public void newGameCreating(NewGameEvent e) {
        clearDesktop();

        internalFrameWidth = 1010;
        internalFrameHeight = 720;

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

    /**Pulisce il desktop
     *
     * @param doc stringa che rappresenta il documento da visualizzare
     */
    private void showHelp(String doc) {
        if (helpJF != null) {
            helpJF.dispose();
        }
        helpJF = new HelpJF(doc);
        helpJF.setVisible(true);
    }

    /**Chiude il Frame corrente, l'Applicazione ed eventualmente anche il Frame HelpJF
     *
     * @param doc stringa che rappresenta il documento da visualizzare
     */
    private void closeFrame() {
        if (JOptionPane.showInternalConfirmDialog(desktop,
                "Sei sicuro di voler uscire? Le partite non salvate saranno perse!", "Info",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
            if (helpJF != null) {
                helpJF.dispose();
            }
            this.dispose();
            System.exit(0);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        closeFrame();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        if (e.getInternalFrame() instanceof NewGameJIF) {
        } else if (e.getInternalFrame() instanceof LoadGameJIF) {
            refreshMenuItem();
        } else if (e.getInternalFrame() instanceof GameJIF) {
            executeCloseGame();
        }

    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }
} //end class

