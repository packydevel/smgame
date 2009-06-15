package org.smgame.server.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.smgame.core.GUICoreMediator;
import org.smgame.client.frontend.MessageType;
import org.smgame.server.RMIServer;

/**internal frame new game
 *frame interno nuovo gioco
 *
 * @author luca
 * @author pasquale
 */
public class ServerJF extends JFrame implements WindowListener {

    ServerVO serverVO;
    JPanel statusJP, monitorJP, configJP, fileJP, databaseJP;
    GridBagConstraints panelGBC, labelGBC, textFieldGBC, comboBoxGBC, checkBoxGBC, buttonGBC;
    JLabel pathJL, playersNumberJL, cpuflagJL, mancheNumberJL, jollyCardJL, kingSMPayRuleJL;
    JComboBox playersNumberJCB;
    JButton startJB, stopJB, pathJB;
    JButton okJB;
    JCheckBox allcpuflagJCKB;
    JCheckBox cpuflagJCKB[];
    JLabel gameNameJL;
    JLabel playerJL[];
    JTextField gameNameJTF, mancheNumberJTF, jollyCardJTF, kingSMPayRuleJTF;
    JTextField playerJTF[];
    String eventSource;
    int previousPlayersNumber = 0;
    int currentPlayersNumber;
    boolean online;

    /**Costruttore
     *
     */
    public ServerJF() {
        super("SMGame Server");

        JTabbedPane tabbedPane = new JTabbedPane();

        statusJP = new JPanel();
        tabbedPane.addTab("Start/Stop", null, statusJP,
                "Esegui Start/Stop del Server");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        monitorJP = new JPanel();
        tabbedPane.addTab("Monitor", null, monitorJP,
                "Monitor delle attività del Server");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        panelGBC = new GridBagConstraints();
        panelGBC.weightx = 0;
        panelGBC.weighty = 0;
        panelGBC.insets = new Insets(2, 2, 2, 2);
        panelGBC.anchor = GridBagConstraints.NORTHWEST;
        initConfigJP(panelGBC);
        tabbedPane.addTab("Configurazione", null, configJP,
                "Configurazione del Server");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        statusJP.setLayout(new GridBagLayout());
        monitorJP.setLayout(new GridBagLayout());                

        labelGBC = new GridBagConstraints();
        labelGBC.weightx = 0;
        labelGBC.weighty = 0;
        labelGBC.insets = new Insets(2, 2, 2, 2);
        labelGBC.anchor = GridBagConstraints.NORTHWEST;

        textFieldGBC = new GridBagConstraints();
        textFieldGBC.weightx = 0;
        textFieldGBC.weighty = 0;
        textFieldGBC.insets = new Insets(2, 2, 2, 2);
        textFieldGBC.anchor = GridBagConstraints.NORTHWEST;

        comboBoxGBC = new GridBagConstraints();
        comboBoxGBC.weightx = 0;
        comboBoxGBC.weighty = 0;
        comboBoxGBC.insets = new Insets(2, 2, 2, 2);
        comboBoxGBC.anchor = GridBagConstraints.NORTHWEST;

        checkBoxGBC = new GridBagConstraints();
        checkBoxGBC.weightx = 0;
        checkBoxGBC.weighty = 0;
        checkBoxGBC.insets = new Insets(2, 2, 2, 2);
        checkBoxGBC.anchor = GridBagConstraints.CENTER;

        buttonGBC = new GridBagConstraints();
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 0;
        buttonGBC.insets = new Insets(2, 2, 2, 2);
        buttonGBC.anchor = GridBagConstraints.CENTER;                        

//
//        gameNameJL = new JLabel("Nome Partita:");
//        labelGBC.gridx = 0;
//        labelGBC.gridy = 0;
//        playerJP.add(gameNameJL, labelGBC);
//
//        gameNameJTF = new JTextField(20);
//        textFieldGBC.gridx = 1;
//        textFieldGBC.gridy = 0;
//        textFieldGBC.gridwidth = 2;
//        textFieldGBC.fill = GridBagConstraints.HORIZONTAL;
//        playerJP.add(gameNameJTF, textFieldGBC);
//
//        playersNumberJL = new JLabel("Numero di giocatori:");
//        labelGBC.gridx = 0;
//        labelGBC.gridy = 1;
//        playerJP.add(playersNumberJL, labelGBC);
//
//        Integer playersNumber[] = {null, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
//
//        playersNumberJCB = new JComboBox(playersNumber);
//        comboBoxGBC.gridx = 1;
//        comboBoxGBC.gridy = 1;
//        playerJP.add(playersNumberJCB, comboBoxGBC);
//        playersNumberJCB.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent evt) {
//                newGameActionPerformed(evt);
//            }
//        });
//
//        cpuflagJL = new JLabel("CPU flag");
//        labelGBC.gridx = 2;
//        labelGBC.gridy = 1;
//        playerJP.add(cpuflagJL, labelGBC);
//
//        allcpuflagJCKB = new JCheckBox();
//        if (online) {
//            allcpuflagJCKB.setSelected(true);
//            allcpuflagJCKB.setEnabled(false);
//        }
//        checkBoxGBC.gridx = 2;
//        checkBoxGBC.gridy = 2;
//        checkBoxGBC.ipadx = 50;
//        playerJP.add(allcpuflagJCKB, checkBoxGBC);
//        allcpuflagJCKB.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent evt) {
//                newGameActionPerformed(evt);
//            }
//        });

        startJB = new JButton("Start");
        startJB.setName("startJB");
        startJB.setPreferredSize(new Dimension(70, 20));
        startJB.setVisible(true);
        buttonGBC.gridx = 0;
        buttonGBC.gridy = 0;
        startJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        statusJP.add(startJB, buttonGBC);

        stopJB = new JButton("Stop");
        stopJB.setPreferredSize(new Dimension(70, 20));
        stopJB.setEnabled(false);
        stopJB.setVisible(true);
        buttonGBC.gridx = 1;
        buttonGBC.gridy = 0;
        stopJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        statusJP.add(stopJB, buttonGBC);        

//        textFieldGBC.fill = GridBagConstraints.NONE;
//
//        mancheNumberJL = new JLabel("Numero Manches:");
//        labelGBC.gridx = 0;
//        labelGBC.gridy = 0;
//        preferenceJP.add(mancheNumberJL, labelGBC);
//
//        mancheNumberJTF = new JTextField();
//        mancheNumberJTF.setText("10");
//        mancheNumberJTF.setEnabled(false);
//        textFieldGBC.gridx = 1;
//        textFieldGBC.gridy = 0;
//        preferenceJP.add(mancheNumberJTF, textFieldGBC);
//
//        jollyCardJL = new JLabel("Matta:");
//        labelGBC.gridx = 0;
//        labelGBC.gridy = 1;
//        preferenceJP.add(jollyCardJL, labelGBC);
//
//        jollyCardJTF = new JTextField();
//        jollyCardJTF.setText("Re di Denari");
//        jollyCardJTF.setEnabled(false);
//        textFieldGBC.gridx = 1;
//        textFieldGBC.gridy = 1;
//        preferenceJP.add(jollyCardJTF, textFieldGBC);
//
//        kingSMPayRuleJL = new JLabel("Pagamento del Sette e Mezzo Reale: ");
//        labelGBC.gridx = 0;
//        labelGBC.gridy = 2;
//        preferenceJP.add(kingSMPayRuleJL, labelGBC);
//
//        kingSMPayRuleJTF = new JTextField();
//        kingSMPayRuleJTF.setText("doppio");
//        kingSMPayRuleJTF.setEnabled(false);
//        textFieldGBC.gridx = 1;
//        textFieldGBC.gridy = 2;
//        textFieldGBC.weightx = 1;
//        textFieldGBC.weighty = 1;
//        preferenceJP.add(kingSMPayRuleJTF, textFieldGBC);
//
//        textFieldGBC.weightx = 0;
//        textFieldGBC.weighty = 0;

        add(tabbedPane);
        setSize(500, 400);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    public String getEventSource() {
        return eventSource;
    }

    private void initConfigJP(GridBagConstraints panelGBC){
        configJP = new JPanel();
        configJP.setLayout(new GridBagLayout());

        fileJP = new JPanel();
        fileJP.setPreferredSize(new Dimension(450, 100));
        fileJP.setLayout(new BorderLayout());
        fileJP.setBorder(BorderFactory.createTitledBorder("Path del File delle Partite"));

        pathJL = new JLabel("Path");
        fileJP.add(pathJL, BorderLayout.NORTH);

        pathJB = new JButton("Scegli il Path");
        pathJB.setSize(new Dimension(70, 20));
        pathJB.setPreferredSize(new Dimension(70, 20));
        pathJB.setMinimumSize(new Dimension(70, 20));
        pathJB.setMaximumSize(new Dimension(70, 20));                
        pathJB.setEnabled(true);
        pathJB.setVisible(true);

        pathJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                serverAction(evt);
            }
        });
        fileJP.add(pathJB, BorderLayout.SOUTH);

        databaseJP = new JPanel();
        databaseJP.setPreferredSize(new Dimension(450, 100));
        databaseJP.setLayout(new BorderLayout());
        databaseJP.setBorder(BorderFactory.createTitledBorder("Parametri di Connessione al Database"));

        panelGBC.gridx = 0;
        panelGBC.gridy = 0;
        configJP.add(fileJP, panelGBC);

        //panelGBC.gridx = 0;
        panelGBC.gridy = 1;
        configJP.add(databaseJP, panelGBC);
    }

    private void serverAction(ActionEvent e) {

        JFileChooser fileJFC;

        if (((JButton) e.getSource()).equals(startJB)) {
            RMIServer.getInstance().start();
            serverVO = RMIServer.getInstance().requestServerVO();
            if (serverVO.getMessageType() == MessageType.ERROR) {
                JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                startJB.setEnabled(false);
                stopJB.setEnabled(true);
            }
        } else if (((JButton) e.getSource()).equals(stopJB)) {
            RMIServer.getInstance().stop();
            startJB.setEnabled(true);
            stopJB.setEnabled(false);
        } else if (((JButton) e.getSource()).equals(pathJB)) {
            fileJFC = new JFileChooser();
            fileJFC.setDialogTitle("Seleziona una Directory");
            fileJFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileJFC.setFileHidingEnabled(true);
            fileJFC.setMultiSelectionEnabled(false);

            if (fileJFC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                GUICoreMediator.setSaveDirectory(fileJFC.getSelectedFile());
                serverVO = GUICoreMediator.requestServerVO();
                if (serverVO.messageType == MessageType.ERROR) {
                    JOptionPane.showMessageDialog(this, serverVO.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    pathJL.setText(fileJFC.getSelectedFile().getPath());
                }
            }
        }
//        int i;
//        int y = 3;
//
//        if (evt.getSource() instanceof JComboBox) {
//
//            if (playersNumberJCB.getSelectedItem() == null) {
//                return;
//            }
//
//            currentPlayersNumber = ((Integer) playersNumberJCB.getSelectedItem()).intValue();
//            if (currentPlayersNumber != previousPlayersNumber && previousPlayersNumber != 0) {
//                for (int j = 0; j < previousPlayersNumber; j++) {
//                    playerJP.remove(playerJL[j]);
//                    playerJP.remove(playerJTF[j]);
//                    playerJP.remove(cpuflagJCKB[j]);
//                    validate();
//                }
//                if (online) {
//                    allcpuflagJCKB.setSelected(true);
//                } else {
//                    allcpuflagJCKB.setSelected(false);
//                }
//            }
//
//            i = currentPlayersNumber;
//            playerJL = new JLabel[i];
//            playerJTF = new JTextField[i];
//            cpuflagJCKB = new JCheckBox[i];
//
//            for (int j = 0; j < i; j++) {
//
//                playerJL[j] = new JLabel("Giocatore " + (j + 1) + ":");
//                labelGBC.gridx = 0;
//                labelGBC.gridy = y;
//                playerJP.add(playerJL[j], labelGBC);
//                playerJL[j].setVisible(true);
//
//                playerJTF[j] = new JTextField();
//                playerJTF[j].setPreferredSize(new Dimension(80, 20));
//                if (online && j != 0) {
//                    playerJTF[j].setEnabled(false);
//                }
//                textFieldGBC.gridx = 1;
//                textFieldGBC.gridy = y;
//                textFieldGBC.ipadx = 100;
//                playerJP.add(playerJTF[j], textFieldGBC);
//                playerJTF[j].setVisible(true);
//
//
//                cpuflagJCKB[j] = new JCheckBox();
//                if (online) {
//                    cpuflagJCKB[j].setSelected(true);
//                    cpuflagJCKB[j].setEnabled(false);
//                    if (j != 0) {
//                        playerJTF[j].setText("CPU Player " + j);
//                    }
//                }
//                checkBoxGBC.gridx = 2;
//                checkBoxGBC.gridy = y;
//                checkBoxGBC.ipadx = 50;
//                playerJP.add(cpuflagJCKB[j], checkBoxGBC);
//                if (j == 0) {
//                    cpuflagJCKB[j].setVisible(false);
//                } else {
//                    cpuflagJCKB[j].setVisible(true);
//                }
//
//                y++;
//            }
//            previousPlayersNumber = currentPlayersNumber;
//            okJB.setEnabled(true);
//            validate();
//        } else if (evt.getSource() instanceof JCheckBox) {
//            if (((JCheckBox) evt.getSource()).equals(allcpuflagJCKB)) {
//                for (int j = 0; j < currentPlayersNumber; j++) {
//                    if (j != 0) {
//                        cpuflagJCKB[j].setSelected(allcpuflagJCKB.isSelected());
//                        if (playerJTF[j].getText().length() == 0) {
//                            playerJTF[j].setText("CPU Player " + j);
//                        }
//                    }
//                }
//                validate();
//            }
//        } else if (evt.getSource() instanceof JButton) {
//
//            if ((JButton) evt.getSource() == okJB) {
//                if (gameNameJTF.getText().length() == 0) {
//                    return;
//                }
//                for (int j = 0; j < currentPlayersNumber; j++) {
//                    if (playerJTF[j].getText().length() == 0) {
//                        return;
//                    }
//                }
//
//                String gameName = gameNameJTF.getText();
//                List<String> playerNameList = new ArrayList<String>();
//                List<Boolean> playerTypeList = new ArrayList<Boolean>();
//
//                for (int j = 0; j < currentPlayersNumber; j++) {
//                    playerNameList.add(playerJTF[j].getText());
//                    playerTypeList.add(new Boolean(cpuflagJCKB[j].isSelected()));
//                }
//
//                if (online) {
//                    ClientMediator.getInstance().createOnLineGame(gameName, null, playerNameList, playerTypeList);
//                } else {
//                    ClientMediator.getInstance().createOffLineGame(gameName, null, playerNameList, playerTypeList);
//                }
//
//                fireNewGameEvent(new NewGameEvent(this));
//            }
//            dispose();
//        }
    }
//    protected EventListenerList eventListenerList = new javax.swing.event.EventListenerList();
//
//    // This methods allows classes to register for MyEvents
//    public void addMyEventListener(NewGameListener listener) {
//        listenerList.add(NewGameListener.class, listener);
//    }
//
//    // This methods allows classes to unregister for MyEvents
//    public void removeMyEventListener(NewGameListener listener) {
//        listenerList.remove(NewGameListener.class, listener);
//    }
//
//    // This private class is used to fire MyEvents
//    void fireNewGameEvent(NewGameEvent e) {
//        Object[] listeners = listenerList.getListenerList();
//        for (int i = 0; i < listeners.length; i += 2) {
//            if (listeners[i] == NewGameListener.class) {
//                ((NewGameListener) listeners[i + 1]).newGameCreating(e);
//            }
//        }
//    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        int i;
        i = JOptionPane.showConfirmDialog(this, "Chiudendo il server non sarà più possibile eseguire partite OnLine. Sei Sicuro?", "Info", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (i == 0) {
            RMIServer.getInstance().stop();
            dispose();
        }
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }
}
