package org.smgame.frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;

public class MainJF extends JFrame implements InternalFrameListener, NewGameListener {

    private static JDesktopPane desktop;
    private int frameNumber = 0;
    private int xPos = 0;
    private int yPos = 0;
    private JTextField titleTextField;
    private JCheckBox resizableCheckBox;
    private JCheckBox closableCheckBox;
    private JCheckBox maximizableCheckBox;
    private JCheckBox iconifiableCheckBox;
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
            newGameJIF = new NewGameJIF();
            newGameJIF.setVisible(true);
            newGameJIF.addInternalFrameListener(this);
            newGameJIF.addMyEventListener(this);
            desktop.add(newGameJIF);
            menuJMB.getNewGameJMI().setEnabled(false);
            menuJMB.getCloseGameJMI().setEnabled(true);
        } else if ((JMenuItem) evt.getSource() == menuJMB.getLoadGameJMI()) {
            GUIGameEngine.loadGame();
            loadGameJIF = new LoadGameJIF();
            loadGameJIF.setVisible(true);
            loadGameJIF.addInternalFrameListener(this);
            desktop.add(loadGameJIF);
            menuJMB.getLoadGameJMI().setEnabled(false);
        } else if ((JMenuItem) evt.getSource() == menuJMB.getSaveGameJMI()) {
            try {
                GUIGameEngine.saveGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getCloseGameJMI()) {
            for (JInternalFrame jiframe : desktop.getAllFrames()) {
                jiframe.dispose();
                menuJMB.getNewGameJMI().setEnabled(true);
                menuJMB.getCloseGameJMI().setEnabled(false);
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getExitGameJMI()) {
            this.dispose();
        }
    }

    protected JInternalFrame createFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        //gameJIF = new GameJIF();
        return gameJIF;
    }

    class GenerateButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String title = titleTextField.getText();
            boolean resizable = resizableCheckBox.isSelected();
            boolean closable = closableCheckBox.isSelected();
            boolean maximizable = maximizableCheckBox.isSelected();
            boolean iconifiable = iconifiableCheckBox.isSelected();
            JInternalFrame frame = createFrame(title,
                    resizable,
                    closable,
                    maximizable,
                    iconifiable);
            // aggiunge al JDesktopPane
            desktop.add(frame);
            // lo mette in cima agli altri JInternalFrame
            frame.moveToFront();
            // lo rende visibile
            frame.setVisible(true);
            titleTextField.setText("Frame " + String.valueOf(
                    frameNumber++));
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
        PlayerList playerList = PlayerList.getInstance();
        Iterator<Player> playerIterator = playerList.getPlayerAL().iterator();
        while (playerIterator.hasNext()) {
            playerList.getPlayerAL().add(playerIterator.next());
        }
        GUIGameEngine.createGame(e.getGameName(), e.getGameSetting(), playerList);
        gameJIF = new GameJIF(e.getGameName(), e.getPlayerList(), e.getGameSetting());
        gameJIF.setVisible(true);
        gameJIF.addInternalFrameListener(this);
        desktop.add(gameJIF);
        menuJMB.getNewGameJMI().setEnabled(false);
        menuJMB.getSaveGameJMI().setEnabled(true);
        menuJMB.getCloseGameJMI().setEnabled(true);
    }
} 
