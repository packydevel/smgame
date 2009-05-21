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

        Integer playersNumber[] = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        playersNumberJCB = new JComboBox(playersNumber);

        playersNumberJCB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        panel.add(playersNumberJCB, c);

        cancelJB = new JButton("Cancel");
        c.gridx = 2;
        c.gridy = 12;
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
        c.gridy = 12;
        okJB.setSize(new Dimension(10, 10));
        okJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        panel.add(okJB, c);
        okJB.setEnabled(false);
        okJB.setVisible(true);

//
//        panel.add(button, c);
//        button = new JButton("Button 3");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 2;
//        c.gridy = 0;
//
//        panel.add(button, c);
//        button = new JButton("Long-Named Button 4");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 40;      //make this component tall
//        c.weightx = 0.0;
//        c.gridwidth = 3;
//        c.gridx = 0;
//        c.gridy = 1;
//
//        panel.add(button, c);
//        button = new JButton("5");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 0;       //reset to default
//        c.weighty = 1.0;   //request any extra vertical space
//        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
//        c.insets = new Insets(10, 0, 0, 0);  //top padding
//        c.gridx = 1;       //aligned with button 2
//        c.gridwidth = 2;   //2 columns wide
//        c.gridy = 2;       //third row
//        panel.add(button, c);
    }

    private void newGameActionPerformed(ActionEvent evt) {

        if (evt.getSource() instanceof JComboBox) {
            playersNumberJCB.setEnabled(false);
            int i = ((Integer) playersNumberJCB.getSelectedItem()).intValue();
            JLabel playerJL[] = new JLabel[i];
            JTextField playerJTF[] = new JTextField[i];
            JCheckBox cpuflagJCKB[] = new JCheckBox[i - 1];

            //JButton addJB[] = new JButton[12];
            //JButton removeJB[] = new JButton[12];

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

//                addJB[j+1] = new JButton("+");
//                c.gridx = 2;
//                c.gridy = i;
//                addJB[j+1].setSize(new Dimension(10, 10));
//                panel.add(addJB[j+1], c);
//                if (i == 0) {
//
//                    playerJL[j+1].setVisible(true);
//                    playerJTF[j+1].setVisible(true);
//                    addJB[j+1].setVisible(true);
//                } else {
//                    playerJL[i].setVisible(false);
//                    playerJTF[i].setVisible(false);
//                    addJB[i].setVisible(false);
//                }
            }
            okJB.setEnabled(true);
            this.validate();
        } else if (evt.getSource() instanceof JButton) {
            if ((JButton) evt.getSource() == cancelJB) {
                this.dispose();
            }
        }
    }
}
