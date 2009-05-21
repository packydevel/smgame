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
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    GridBagConstraints c = new GridBagConstraints();
    JPanel panel = new JPanel();
    JLabel playersNumberJL;
    JComboBox playersNumberJCB;
    JButton cancelJB;
    JButton okJB;
    JCheckBox cpuflagJCKB[];
    JLabel gameNameJL;
    JLabel playerJL[];
    JTextField gameNameJTF;
    JTextField playerJTF[];
    int previousPlayersNumber = 0;
    int currentPlayersNumber;

    public NewGameJIF() {
        super("Nuova Partita", false, true, false, false);
        setSize(400, 420);
        this.add(panel);

        panel.setLayout(new GridBagLayout());

        c = new GridBagConstraints();
        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(2, 2, 2, 2);
        c.anchor = c.FIRST_LINE_START;

        gameNameJL = new JLabel("Fornisci un nome per questa Partita:");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(gameNameJL, c);

        gameNameJTF = new JTextField();
        gameNameJTF.setMaximumSize(new Dimension(200, 10));
        c.gridx = 1;
        c.gridy = 0;
        panel.add(gameNameJTF, c);

        playersNumberJL = new JLabel("Seleziona il numero di giocatori:");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(playersNumberJL, c);

        Integer playersNumber[] = {null, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        playersNumberJCB = new JComboBox(playersNumber);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(playersNumberJCB, c);

        playersNumberJCB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        cancelJB = new JButton("Cancel");
        cancelJB.setMaximumSize(new Dimension(60, 20));
        cancelJB.setPreferredSize(new Dimension(60, 20));
        c.gridx = 2;
        c.gridy = 14;
        c.weightx = 0;
        c.anchor = GridBagConstraints.SOUTH;
        cancelJB.setSize(new Dimension(10, 10));
        cancelJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        panel.add(cancelJB, c);
        cancelJB.setVisible(true);

        okJB = new JButton("OK");
        okJB.setMaximumSize(new Dimension(60, 20));
        okJB.setPreferredSize(new Dimension(60, 20));
        c.gridx = 1;
        c.gridy = 14;
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;
        okJB.setSize(new Dimension(10, 10));
        okJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        panel.add(okJB, c);
        okJB.setEnabled(false);
        okJB.setVisible(true);
        c.weighty = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
    }

    private void newGameActionPerformed(ActionEvent evt) {

        if (evt.getSource() instanceof JComboBox) {

            if (playersNumberJCB.getSelectedItem() == null) {
                return;
            }

            currentPlayersNumber = ((Integer) playersNumberJCB.getSelectedItem()).intValue();

            if (currentPlayersNumber != previousPlayersNumber && previousPlayersNumber != 0) {
                for (int j = 0; j < previousPlayersNumber; j++) {
                    panel.remove(playerJL[j]);
                    panel.remove(playerJTF[j]);
                    if (j < previousPlayersNumber - 1) {
                        panel.remove(cpuflagJCKB[j]);
                    }
                }
            }

            this.validate();

            int i = currentPlayersNumber;
            int y = i + 2;

            playerJL = new JLabel[i];
            playerJTF = new JTextField[i];
            cpuflagJCKB = new JCheckBox[i - 1];

            for (int j = 0; j < i; j++) {

                playerJL[j] = new JLabel("Giocatore " + (j + 1) + ":");
                c.gridx = 0;
                c.gridy = y;
                panel.add(playerJL[j], c);
                playerJL[j].setVisible(true);

                playerJTF[j] = new JTextField();
                playerJTF[j].setMaximumSize(new Dimension(200, 10));
                playerJTF[j].setPreferredSize(new Dimension(200, 10));
                c.gridx = 1;
                c.gridy = y;
                c.ipadx = 100;
                panel.add(playerJTF[j], c);
                playerJTF[j].setVisible(true);

                if (j > 0) {
                    cpuflagJCKB[j - 1] = new JCheckBox();
                    c.gridx = 2;
                    c.gridy = y;
                    c.ipadx = 50;
                    panel.add(cpuflagJCKB[j - 1], c);
                    cpuflagJCKB[j - 1].setVisible(true);
                }
                y++;
            }
            previousPlayersNumber = currentPlayersNumber;
            okJB.setEnabled(true);
            this.validate();
        } else if (evt.getSource() instanceof JButton) {
            if ((JButton) evt.getSource() == cancelJB) {
                this.dispose();
            } else if ((JButton) evt.getSource() == okJB) {
                for (int j = 0; j < currentPlayersNumber; j++) {
                    if (playerJTF[j].getText().length() == 0) {
                        return;
                    }
                }
                final Map<Player, List<Card>> hmPlayerCards = new HashMap<Player, List<Card>>();

                for (int j = 0; j < currentPlayersNumber; j++) {
                    if (j > 0) {
                        if (cpuflagJCKB[j].isSelected()) {
                            hmPlayerCards.put(new CPUPlayer(playerJTF[j].getText()), null);
                        } else {
                            hmPlayerCards.put(new HumanPlayer(playerJTF[j].getText()), null);
                        }
                    } else {
                        hmPlayerCards.put(new HumanPlayer(playerJTF[j].getText()), null);
                    }

                }
                GameJIF gameJIF = new GameJIF(hmPlayerCards);
                getDesktopPane().add(gameJIF).setVisible(true);

                this.dispose();
            }
        }
    }
}
