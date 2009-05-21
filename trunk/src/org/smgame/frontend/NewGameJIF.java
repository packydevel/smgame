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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    JLabel playerJL[];
    JTextField playerJTF[];
    int previousPlayersNumber = 0;
    int currentPlayersNumber;

    public NewGameJIF() {
        super();

        setSize(400, 400);

        this.add(panel);

        panel.setLayout(new GridBagLayout());

        c = new GridBagConstraints();
        c.weighty = 0.1;
        c.weightx = 0.1;
        c.insets = new Insets(2, 2, 2, 2);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = c.FIRST_LINE_START;

        playersNumberJL = new JLabel("Seleziona il numero di giocatori:");
        panel.add(playersNumberJL, c);

        c.gridx = 1;
        c.gridy = 0;

        Integer playersNumber[] = {null, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        playersNumberJCB = new JComboBox(playersNumber);

        playersNumberJCB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        panel.add(playersNumberJCB, c);

        cancelJB = new JButton("Cancel");
        c.gridx = 2;
        c.gridy = 13;
        cancelJB.setSize(new Dimension(10, 10));
        cancelJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        panel.add(cancelJB, c);
        cancelJB.setVisible(true);

        okJB = new JButton("OK");
        c.gridx = 1;
        c.gridy = 13;
        okJB.setSize(new Dimension(10, 10));
        okJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        panel.add(okJB, c);
        okJB.setEnabled(false);
        okJB.setVisible(true);
    }

    private void newGameActionPerformed(ActionEvent evt) {

        if (evt.getSource() instanceof JComboBox) {

            if (playersNumberJCB.getSelectedItem() == null) {
                return;
            }

            currentPlayersNumber = ((Integer) playersNumberJCB.getSelectedItem()).intValue();

            if (currentPlayersNumber != previousPlayersNumber && playerJL != null) {
                for (int j = 0; j < previousPlayersNumber; j++) {
                    panel.remove(playerJL[j]);
                    panel.remove(playerJTF[j]);
                    if (j < previousPlayersNumber - 1) {
                        panel.remove(cpuflagJCKB[j]);
                    }
                }
            }

            int i = currentPlayersNumber;

            playerJL = new JLabel[i];

            playerJTF = new JTextField[i];
            cpuflagJCKB = new JCheckBox[i - 1];

            for (int j = 0; j < i; j++) {
                playerJL[j] = new JLabel("Giocatore " + j + ":");
                c.gridx = 0;
                c.gridy = j + 1;
                panel.add(playerJL[j], c);
                playerJL[j].setVisible(true);

                playerJTF[j] = new JTextField(20);
                c.gridx = 1;
                c.gridy = j + 1;
                panel.add(playerJTF[j], c);
                playerJTF[j].setVisible(true);

                if (j > 0) {
                    cpuflagJCKB[j - 1] = new JCheckBox();
                    c.gridx = 2;
                    c.gridy = j + 1;
                    panel.add(cpuflagJCKB[j - 1], c);
                    cpuflagJCKB[j - 1].setVisible(true);
                }
            }
            previousPlayersNumber = currentPlayersNumber;
            okJB.setEnabled(true);
            this.validate();
        } else if (evt.getSource() instanceof JButton) {
            if ((JButton) evt.getSource() == cancelJB) {
                this.dispose();
            }
        }
    }
}
