package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javax.swing.event.EventListenerList;
import org.smgame.core.GUICoreMediator;

/**internal frame new game
 *frame interno nuovo gioco
 *
 * @author luca
 * @author pasquale
 */
public class NewOffLineGameJIF extends JInternalFrame {

    JPanel playersJP, preferencesJP;
    GridBagConstraints labelGBC, textFieldGBC, comboBoxGBC, checkBoxGBC, buttonGBC;
    JLabel playersNumberJL, cpuflagJL;
    JComboBox playersNumberJCB;
    JButton cancelJB;
    JButton okJB;
    JCheckBox allcpuflagJCKB;
    JCheckBox cpuflagJCKB[];
    JLabel gameNameJL;
    JLabel playerJL[];
    JTextField gameNameJTF;
    JTextField playerJTF[];
    String eventSource;
    int previousPlayersNumber = 0;
    int currentPlayersNumber;

    /**Costruttore
     *
     */
    public NewOffLineGameJIF() {
        super("Nuova Partita", false, true, false, false);
        setSize(400, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        playersJP = new JPanel();
        tabbedPane.addTab("Giocatori", null, playersJP,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        preferencesJP = new JPanel();
        tabbedPane.addTab("Preferenze", null, preferencesJP,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);


        playersJP.setLayout(new GridBagLayout());
        preferencesJP.setLayout(new GridBagLayout());

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
        buttonGBC.weighty = 1;
        buttonGBC.insets = new Insets(2, 2, 2, 2);
        buttonGBC.anchor = GridBagConstraints.SOUTHEAST;


        gameNameJL = new JLabel("Nome Partita:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 0;
        playersJP.add(gameNameJL, labelGBC);

        gameNameJTF = new JTextField(20);
        textFieldGBC.gridx = 1;
        textFieldGBC.gridy = 0;
        textFieldGBC.gridwidth = 2;
        textFieldGBC.fill = GridBagConstraints.HORIZONTAL;
        playersJP.add(gameNameJTF, textFieldGBC);

        playersNumberJL = new JLabel("Numero di giocatori:");
        labelGBC.gridx = 0;
        labelGBC.gridy = 1;
        playersJP.add(playersNumberJL, labelGBC);

        Integer playersNumber[] = {null, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        playersNumberJCB = new JComboBox(playersNumber);
        comboBoxGBC.gridx = 1;
        comboBoxGBC.gridy = 1;
        playersJP.add(playersNumberJCB, comboBoxGBC);
        playersNumberJCB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        cpuflagJL = new JLabel("CPU flag");
        labelGBC.gridx = 2;
        labelGBC.gridy = 1;
        playersJP.add(cpuflagJL, labelGBC);

        allcpuflagJCKB = new JCheckBox();
        checkBoxGBC.gridx = 2;
        checkBoxGBC.gridy = 2;
        checkBoxGBC.ipadx = 50;
        playersJP.add(allcpuflagJCKB, checkBoxGBC);
        allcpuflagJCKB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        cancelJB = new JButton("Annulla");
        cancelJB.setName("cancelJB");
        cancelJB.setPreferredSize(new Dimension(70, 20));
        cancelJB.setVisible(true);
        buttonGBC.gridx = 2;
        buttonGBC.gridy = 15;
        cancelJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        playersJP.add(cancelJB, buttonGBC);

        okJB = new JButton("OK");
        okJB.setPreferredSize(new Dimension(70, 20));
        okJB.setEnabled(false);
        okJB.setVisible(true);
        buttonGBC.gridx = 1;
        buttonGBC.gridy = 15;
        buttonGBC.weightx = 1;
        okJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        playersJP.add(okJB, buttonGBC);

        textFieldGBC.fill = GridBagConstraints.NONE;

        add(tabbedPane);
    }

    public String getEventSource() {
        return eventSource;
    }

    private void newGameActionPerformed(ActionEvent evt) {
        int i;
        int y = 3;

        if (evt.getSource() instanceof JComboBox) {

            if (playersNumberJCB.getSelectedItem() == null) {
                return;
            }

            currentPlayersNumber = ((Integer) playersNumberJCB.getSelectedItem()).intValue();
            if (currentPlayersNumber != previousPlayersNumber && previousPlayersNumber != 0) {
                for (int j = 0; j < previousPlayersNumber; j++) {
                    playersJP.remove(playerJL[j]);
                    playersJP.remove(playerJTF[j]);
                    playersJP.remove(cpuflagJCKB[j]);
                }
            }

            validate();

            i = currentPlayersNumber;
            playerJL = new JLabel[i];
            playerJTF = new JTextField[i];
            cpuflagJCKB = new JCheckBox[i];

            for (int j = 0; j < i; j++) {

                playerJL[j] = new JLabel("Giocatore " + (j + 1) + ":");
                labelGBC.gridx = 0;
                labelGBC.gridy = y;
                playersJP.add(playerJL[j], labelGBC);
                playerJL[j].setVisible(true);

                playerJTF[j] = new JTextField();
                playerJTF[j].setPreferredSize(new Dimension(80, 20));
                textFieldGBC.gridx = 1;
                textFieldGBC.gridy = y;
                textFieldGBC.ipadx = 100;
                playersJP.add(playerJTF[j], textFieldGBC);
                playerJTF[j].setVisible(true);


                cpuflagJCKB[j] = new JCheckBox();
                checkBoxGBC.gridx = 2;
                checkBoxGBC.gridy = y;
                checkBoxGBC.ipadx = 50;
                playersJP.add(cpuflagJCKB[j], checkBoxGBC);
                if (j == 0) {
                    cpuflagJCKB[j].setVisible(false);
                } else {
                    cpuflagJCKB[j].setVisible(true);
                }

                y++;
            }
            previousPlayersNumber = currentPlayersNumber;
            okJB.setEnabled(true);
            validate();
        } else if (evt.getSource() instanceof JCheckBox) {
            if (((JCheckBox) evt.getSource()).equals(allcpuflagJCKB)) {
                for (int j = 0; j < currentPlayersNumber; j++) {
                    if (j != 0) {
                        cpuflagJCKB[j].setSelected(allcpuflagJCKB.isSelected());
                        if (playerJTF[j].getText().length() == 0) {
                            playerJTF[j].setText("CPU Player " + j);
                        }
                    }
                }
                validate();
            }
        } else if (evt.getSource() instanceof JButton) {

            if ((JButton) evt.getSource() == okJB) {
                if (gameNameJTF.getText().length() == 0) {
                    return;
                }
                for (int j = 0; j < currentPlayersNumber; j++) {
                    if (playerJTF[j].getText().length() == 0) {
                        return;
                    }
                }

                String gameName = gameNameJTF.getText();
                List<String> playerNameList = new ArrayList<String>();
                List<Boolean> playerTypeList = new ArrayList<Boolean>();

                for (int j = 0; j < currentPlayersNumber; j++) {
                    playerNameList.add(playerJTF[j].getText());
                    playerTypeList.add(new Boolean(cpuflagJCKB[j].isSelected()));
                }

                GUICoreMediator.createGame(gameName, null, playerNameList, playerTypeList);

                fireNewOffLineGameEvent(new NewOffLineGameEvent(this));
            }
            dispose();
        }
    }
    protected EventListenerList eventListenerList = new javax.swing.event.EventListenerList();

    // This methods allows classes to register for MyEvents
    public void addMyEventListener(NewOffLineGameListener listener) {
        listenerList.add(NewOffLineGameListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeMyEventListener(NewOffLineGameListener listener) {
        listenerList.remove(NewOffLineGameListener.class, listener);
    }

    // This private class is used to fire MyEvents
    void fireNewOffLineGameEvent(NewOffLineGameEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i <
                listeners.length; i +=
                        2) {
            if (listeners[i] == NewOffLineGameListener.class) {
                ((NewOffLineGameListener) listeners[i + 1]).newOffLineGameCreating(e);
            }
        }
    }
}
