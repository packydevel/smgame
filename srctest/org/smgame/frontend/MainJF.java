package org.smgame.frontend;

import org.smgame.core.GUICoreMediator;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class MainJF extends JFrame implements InternalFrameListener, NewOffLineGameListener, NewOnLineGameListener {

    private static JDesktopPane desktop;
    private MenuJMB menuJMB;
    private ToolBarJTB toolBarJTB;
    private ToolBarJTB statusBarJTB;
    private NewOnLineGameJIF newOnLineGameJIF;
    private NewOffLineGameJIF newGameJIF;
    private LoadGameJIF loadGameJIF;
    private GameJIF gameJIF;
    private GameOnlineJIF gameonlineJIF;

    public MainJF() {
        super("SMGame - Gioco Italiano del Sette e 1/2");

        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        toolBarJTB = new ToolBarJTB();
        statusBarJTB = new ToolBarJTB();
        desktop = new JDesktopPane();
        desktop.setDesktopManager(new GameDM());
        getContentPane().add(BorderLayout.NORTH, toolBarJTB.getTb());
        getContentPane().add(BorderLayout.SOUTH, statusBarJTB.getTb());
        getContentPane().add(BorderLayout.CENTER, desktop);
        setVisible(true);

        menuJMB = new MenuJMB();

        menuJMB.getNewOnLineGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuJMB.getNewOffLineGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuJMB.getLoadGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuJMB.getSaveGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuJMB.getCloseGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuJMB.getExitGameJMI().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        setJMenuBar(menuJMB.getJMenuBar1());
    }

    private void jMenu1ActionPerformed(ActionEvent evt) {

        if ((JMenuItem) evt.getSource() == menuJMB.getNewOnLineGameJMI()) {
            if (GUICoreMediator.askForNewGame()) {
                newOnLineGameJIF = new NewOnLineGameJIF();
                newOnLineGameJIF.setVisible(true);
                newOnLineGameJIF.addInternalFrameListener(this);
                newOnLineGameJIF.addMyEventListener(this);
                desktop.add(newOnLineGameJIF);
                menuJMB.getNewOnLineGameJMI().setEnabled(false);
                menuJMB.getCloseGameJMI().setEnabled(true);
            } else {
                System.out.println("Esiste già una partita aperta");
            }

        } else if ((JMenuItem) evt.getSource() == menuJMB.getNewOffLineGameJMI()) {
            if (GUICoreMediator.askForNewGame()) {
                newGameJIF = new NewOffLineGameJIF();
                newGameJIF.setVisible(true);
                newGameJIF.addInternalFrameListener(this);
                newGameJIF.addMyEventListener(this);
                desktop.add(newGameJIF);
                menuJMB.getNewOnLineGameJMI().setEnabled(false);
                menuJMB.getCloseGameJMI().setEnabled(true);
            } else {
                System.out.println("Esiste già una partita aperta");
            }

        } else if ((JMenuItem) evt.getSource() == menuJMB.getLoadGameJMI()) {
            if (GUICoreMediator.askForLoadGame()) {
                loadGameJIF = new LoadGameJIF();
                loadGameJIF.setVisible(true);
                loadGameJIF.addInternalFrameListener(this);
                desktop.add(loadGameJIF);
                menuJMB.getLoadGameJMI().setEnabled(false);
            } else {
                System.out.println("Esiste già una partita aperta");
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getSaveGameJMI()) {
            try {
                GUICoreMediator.saveGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getCloseGameJMI()) {
            GUICoreMediator.closeGame();
            for (JInternalFrame jiframe : desktop.getAllFrames()) {
                jiframe.dispose();
                menuJMB.getNewOffLineGameJMI().setEnabled(true);
                menuJMB.getCloseGameJMI().setEnabled(false);
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getExitGameJMI()) {
            this.dispose();
        }
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    public void internalFrameClosing(InternalFrameEvent e) {
    }

    public void internalFrameClosed(InternalFrameEvent e) {
        if (e.getInternalFrame() instanceof NewOffLineGameJIF) {
            if (gameJIF == null) {
                menuJMB.getNewOnLineGameJMI().setEnabled(true);
                menuJMB.getCloseGameJMI().setEnabled(false);
                menuJMB.getSaveGameJMI().setEnabled(false);
            }
        } else if (e.getInternalFrame() instanceof LoadGameJIF) {
            menuJMB.getNewOnLineGameJMI().setEnabled(true);
            menuJMB.getCloseGameJMI().setEnabled(false);
            menuJMB.getSaveGameJMI().setEnabled(false);
        } else if (e.getInternalFrame() instanceof GameJIF) {
            GUICoreMediator.closeGame();
            menuJMB.getNewOnLineGameJMI().setEnabled(true);
            menuJMB.getCloseGameJMI().setEnabled(false);
            menuJMB.getSaveGameJMI().setEnabled(false);
        } else if (e.getInternalFrame() instanceof GameOnlineJIF) {
            GUICoreMediator.closeGame();
            menuJMB.getNewOffLineGameJMI().setEnabled(true);
            menuJMB.getCloseGameJMI().setEnabled(false);
            menuJMB.getSaveGameJMI().setEnabled(false);
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

    public void newOnLineGameCreating(NewOnLineGameEvent e) {
        gameonlineJIF = new GameOnlineJIF();
        gameonlineJIF.setVisible(true);
        gameonlineJIF.addInternalFrameListener(this);
        desktop.add(gameonlineJIF);
        System.out.println("gameonlinejif");
        menuJMB.getNewOffLineGameJMI().setEnabled(false);
        menuJMB.getSaveGameJMI().setEnabled(true);
        menuJMB.getCloseGameJMI().setEnabled(true);
    }

    public void newOffLineGameCreating(NewOffLineGameEvent e) {
        gameJIF = new GameJIF();
        gameJIF.setVisible(true);
        gameJIF.addInternalFrameListener(this);
        desktop.add(gameJIF);
        menuJMB.getNewOnLineGameJMI().setEnabled(false);
        menuJMB.getSaveGameJMI().setEnabled(true);
        menuJMB.getCloseGameJMI().setEnabled(true);
    }
} 
