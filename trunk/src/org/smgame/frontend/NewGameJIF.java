/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import org.smgame.core.card.Card;
import org.smgame.core.player.CPUPlayer;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;

/**
 *
 * @author packyuser
 */
public class NewGameJIF extends JInternalFrame {

    JPanel playersJP, preferencesJP;
    GridBagConstraints labelGBC, textFieldGBC, comboBoxGBC, checkBoxGBC, buttonGBC;
    JLabel playersNumberJL;
    JComboBox playersNumberJCB;
    JButton cancelJB;
    JButton okJB;
    JCheckBox cpuflagJCKB[];
    JLabel gameNameJL;
    JLabel playerJL[];
    JTextField gameNameJTF;
    JTextField playerJTF[];
    String eventSource;
    int previousPlayersNumber = 0;
    int currentPlayersNumber;

    public NewGameJIF() {
        super("Nuova Partita", false, true, false, false);
        setSize(400, 450);

        JTabbedPane tabbedPane = new JTabbedPane();
//ImageIcon icon = createImageIcon("images/middle.gif");

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

        cancelJB = new JButton("Annulla");
        cancelJB.setName("cancelJB");
        //cancelJB.setMaximumSize(new Dimension(60, 20));
        cancelJB.setPreferredSize(new Dimension(70, 20));
        cancelJB.setVisible(true);
        buttonGBC.gridx = 2;
        buttonGBC.gridy = 14;
        cancelJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        playersJP.add(cancelJB, buttonGBC);

        okJB = new JButton("OK");
        //okJB.setMaximumSize(new Dimension(80, 20));
        okJB.setPreferredSize(new Dimension(70, 20));
        okJB.setEnabled(false);
        okJB.setVisible(true);
        buttonGBC.gridx = 1;
        buttonGBC.gridy = 14;
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

        if (evt.getSource() instanceof JComboBox) {

            if (playersNumberJCB.getSelectedItem() == null) {
                return;
            }

            currentPlayersNumber = ((Integer) playersNumberJCB.getSelectedItem()).intValue();

            if (currentPlayersNumber != previousPlayersNumber && previousPlayersNumber != 0) {
                for (int j = 0; j < previousPlayersNumber; j++) {
                    playersJP.remove(playerJL[j]);
                    playersJP.remove(playerJTF[j]);
                    if (j < previousPlayersNumber - 1) {
                        playersJP.remove(cpuflagJCKB[j]);
                    }
                }
            }

            this.validate();

            int i = currentPlayersNumber;
            int y = 2;

            playerJL = new JLabel[i];
            playerJTF = new JTextField[i];
            cpuflagJCKB = new JCheckBox[i - 1];

            for (int j = 0; j < i; j++) {

                playerJL[j] = new JLabel("Giocatore " + (j + 1) + ":");
                labelGBC.gridx = 0;
                labelGBC.gridy = y;
                playersJP.add(playerJL[j], labelGBC);
                playerJL[j].setVisible(true);

                playerJTF[j] = new JTextField();
                //playerJTF[j].setMinimumSize(new Dimension(80, 20));
                //playerJTF[j].setMaximumSize(new Dimension(80, 20));
                playerJTF[j].setPreferredSize(new Dimension(80, 20));
                textFieldGBC.gridx = 1;
                textFieldGBC.gridy = y;
                textFieldGBC.ipadx = 100;
                playersJP.add(playerJTF[j], textFieldGBC);
                playerJTF[j].setVisible(true);

                if (j > 0) {
                    cpuflagJCKB[j - 1] = new JCheckBox();
                    checkBoxGBC.gridx = 2;
                    checkBoxGBC.gridy = y;
                    checkBoxGBC.ipadx = 50;
                    playersJP.add(cpuflagJCKB[j - 1], checkBoxGBC);
                    cpuflagJCKB[j - 1].setVisible(true);
                }
                y++;
            }
            previousPlayersNumber = currentPlayersNumber;
            okJB.setEnabled(true);
            this.validate();
        } else if (evt.getSource() instanceof JButton) {

            if ((JButton) evt.getSource() == okJB) {
                for (int j = 0; j < currentPlayersNumber; j++) {
                    if (playerJTF[j].getText().length() == 0) {
                        return;
                    }
                }
                final Map<Player, List<Card>> hmPlayerCards = new HashMap<Player, List<Card>>();
                hmPlayerCards.put(new HumanPlayer(playerJTF[0].getText()), null);

                for (int j = 1; j < currentPlayersNumber; j++) {
                    if (cpuflagJCKB[j - 1].isSelected())
                        hmPlayerCards.put(new CPUPlayer(playerJTF[j].getText()), null);
                    else
                        hmPlayerCards.put(new HumanPlayer(playerJTF[j].getText()), null);
                }

                GameJIF gameJIF = new GameJIF(hmPlayerCards);
                getDesktopPane().add(gameJIF).setVisible(true);
            } else {
                eventSource=cancelJB.getName();
            }

            this.dispose();
        }
    }
}
