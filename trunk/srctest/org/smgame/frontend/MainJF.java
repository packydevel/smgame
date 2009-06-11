package org.smgame.frontend;

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
import org.smgame.core.GUICoreMediator;
import org.smgame.util.Logging;

public class MainJF extends JFrame implements InternalFrameListener, NewOffLineGameListener, NewOnLineGameListener {

    private static JDesktopPane desktop;
    private MenuJMB menuJMB;
    private ToolBarJTB toolBarJTB;
    private ToolBarJTB statusBarJTB;
    private NewOnLineGameJIF newOnLineGameJIF;
    private NewOffLineGameJIF newGameJIF;
    private LoadGameJIF loadGameJIF;
    private OffLineGameJIF gameJIF;
    private OnLineGameJIF gameonlineJIF;
    private MenuVO menuVO;
    private int desktopWidth,  desktopHeight,  internalFrameWidth,  internalFrameHeight,  xbound,  ybound;

    /**
     * 
     */
    public MainJF() {
        super("SMGame - Gioco Italiano del Sette e 1/2");

        ArrayList<String> menuItemNameList = new ArrayList<String>();

        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        toolBarJTB = new ToolBarJTB();
        statusBarJTB = new ToolBarJTB();
        desktop = new JDesktopPane();
        desktop.setDesktopManager(new GameDM());
        //getContentPane().add(BorderLayout.NORTH, toolBarJTB.getTb());
        //getContentPane().add(BorderLayout.SOUTH, statusBarJTB.getTb());
        getContentPane().add(BorderLayout.CENTER, desktop);
        setVisible(true);
        desktopWidth = desktop.getWidth();
        desktopHeight = desktop.getHeight();
        System.out.println(desktopWidth);
        System.out.println(desktopHeight);

        menuJMB = new MenuJMB();
        for (JMenuItem jmi : menuJMB.getMenuItemListJMI()) {
            jmi.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    jMenu1ActionPerformed(evt);
                }
            });
        }

        setJMenuBar(menuJMB);

        GUICoreMediator.addMenuItem(menuItemNameList);

        refreshMenuItem();
    }

    private void refreshMenuItem() {
        menuVO = GUICoreMediator.requestMenuVO();

        for (JMenuItem jmi : menuJMB.getMenuItemListJMI()) {
            jmi.setEnabled(menuVO.getItemEnabledMap().get(jmi.getName()));
        }
    }

    private void jMenu1ActionPerformed(ActionEvent evt) {
        if ((JMenuItem) evt.getSource() == menuJMB.getNewOnLineGameJMI()) {
            internalFrameWidth = 400;
            internalFrameHeight = 450;
            xbound = (desktopWidth - internalFrameWidth) / 2;
            ybound = (desktopHeight - internalFrameHeight) / 2;
            newOnLineGameJIF = new NewOnLineGameJIF();
            newOnLineGameJIF.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
            newOnLineGameJIF.setBounds(xbound, ybound, internalFrameWidth, internalFrameHeight);
            newOnLineGameJIF.setVisible(true);
            newOnLineGameJIF.addInternalFrameListener(this);
            newOnLineGameJIF.addMyEventListener(this);
            desktop.add(newOnLineGameJIF);
            refreshMenuItem();
        } else if ((JMenuItem) evt.getSource() == menuJMB.getNewOffLineGameJMI()) {
            internalFrameWidth = 400;
            internalFrameHeight = 500;
            xbound = (desktopWidth - internalFrameWidth) / 2;
            ybound = (desktopHeight - internalFrameHeight) / 2;
            newGameJIF = new NewOffLineGameJIF();
            newGameJIF.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
            newGameJIF.setBounds(xbound, ybound, internalFrameWidth, internalFrameHeight);
            newGameJIF.setVisible(true);
            newGameJIF.addInternalFrameListener(this);
            newGameJIF.addMyEventListener(this);
            desktop.add(newGameJIF);
            refreshMenuItem();
        } else if ((JMenuItem) evt.getSource() == menuJMB.getLoadGameJMI()) {
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
            try {
                GUICoreMediator.saveGame();
            } catch (Exception e) {
                Logging.logExceptionSevere(e);
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getCloseGameJMI()) {
            if (JOptionPane.showInternalConfirmDialog(desktop,
                    "Sei sicuro di voler chiudere la Partita? I passaggi di gioco non salvati saranno persi!",
                    "Info", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
                try {
                    GUICoreMediator.saveGame();
                } catch (Exception e) {
                    Logging.logExceptionSevere(e);
                    JOptionPane.showInternalMessageDialog(desktop,
                            "Impossibile salvare la partita! " + e.getMessage(), "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
                GUICoreMediator.closeGame();
                for (JInternalFrame jiframe : desktop.getAllFrames()) {
                    jiframe.dispose();
                    refreshMenuItem();
                }
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getExitGameJMI()) {
            if (JOptionPane.showInternalConfirmDialog(desktop,
                    "Sei sicuro di voler uscire? Le partite non salvate saranno perse!", "Info",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                this.dispose();
            }
        } else if ((JMenuItem) evt.getSource() == menuJMB.getScoreBoardJMI()) {
            JOptionPane.showMessageDialog(this, new ScoreBoardJP("manche finita",
                    GUICoreMediator.requestDataReport(), -1), "Score Board", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     *
     * @param e
     */
    public void newOnLineGameCreating(NewOnLineGameEvent e) {
        internalFrameWidth = 960;
        internalFrameHeight = 660;
        xbound = (desktopWidth - internalFrameWidth) / 2;
        ybound = (desktopHeight - internalFrameHeight) / 2;
        gameonlineJIF = new OnLineGameJIF();
        gameonlineJIF.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
        gameonlineJIF.setBounds(xbound, ybound, internalFrameWidth, internalFrameHeight);
        gameonlineJIF.setVisible(true);
        gameonlineJIF.addInternalFrameListener(this);
        desktop.add(gameonlineJIF);

        refreshMenuItem();
    }

    /**
     *
     * @param e
     */
    public void newOffLineGameCreating(NewOffLineGameEvent e) {
        internalFrameWidth = 960;
        internalFrameHeight = 700;
        xbound = (desktopWidth - internalFrameWidth) / 2;
        ybound = (desktopHeight - internalFrameHeight) / 2;
        System.out.println(ybound);
        gameJIF = new OffLineGameJIF();
        gameJIF.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
        gameJIF.setBounds(xbound, ybound, internalFrameWidth, internalFrameHeight);
        gameJIF.setVisible(true);
        gameJIF.addInternalFrameListener(this);
        desktop.add(gameJIF);

        refreshMenuItem();
    }

    /**
     * 
     * @param e
     */
    public void internalFrameClosed(InternalFrameEvent e) {
        if (e.getInternalFrame() instanceof NewOffLineGameJIF) {
            refreshMenuItem();
        } else if (e.getInternalFrame() instanceof LoadGameJIF) {
            refreshMenuItem();
        } else if (e.getInternalFrame() instanceof OffLineGameJIF) {
            GUICoreMediator.closeGame();
            refreshMenuItem();
        } else if (e.getInternalFrame() instanceof OnLineGameJIF) {
            GUICoreMediator.closeGame();
            refreshMenuItem();
        }
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    public void internalFrameClosing(InternalFrameEvent e) {
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