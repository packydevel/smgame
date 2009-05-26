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

public class MainJF extends JFrame implements InternalFrameListener, NewGameListener {

    private static JDesktopPane desktop;
    private MenuJMB menuJMB;
    private ToolBarJTB toolBarJTB;
    private ToolBarJTB statusBarJTB;
    private NewGameJIF newGameJIF;
    private LoadGameJIF loadGameJIF;
    private GameJIF gameJIF;

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

        menuJMB.getNewGameJMI().addActionListener(new ActionListener() {

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

        if ((JMenuItem) evt.getSource() == menuJMB.getNewGameJMI()) {
            if (GUICoreMediator.askForNewGame()) {
                newGameJIF = new NewGameJIF();
                newGameJIF.setVisible(true);
                newGameJIF.addInternalFrameListener(this);
                newGameJIF.addMyEventListener(this);
                desktop.add(newGameJIF);
                menuJMB.getNewGameJMI().setEnabled(false);
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
                menuJMB.getNewGameJMI().setEnabled(true);
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
        if (e.getInternalFrame() instanceof NewGameJIF) {
            if (gameJIF == null) {
                menuJMB.getNewGameJMI().setEnabled(true);
                menuJMB.getCloseGameJMI().setEnabled(false);
                menuJMB.getSaveGameJMI().setEnabled(false);
            }
        } else if (e.getInternalFrame() instanceof GameJIF) {
            GUICoreMediator.closeGame();
            menuJMB.getNewGameJMI().setEnabled(true);
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

    public void newGameCreating(NewGameEvent e) {
        gameJIF = new GameJIF();
        gameJIF.setVisible(true);
        gameJIF.addInternalFrameListener(this);
        desktop.add(gameJIF);
        menuJMB.getNewGameJMI().setEnabled(false);
        menuJMB.getSaveGameJMI().setEnabled(true);
        menuJMB.getCloseGameJMI().setEnabled(true);
    }
} 
