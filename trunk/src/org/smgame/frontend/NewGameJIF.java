/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author packyuser
 */
public class NewGameJIF extends JInternalFrame {

    public NewGameJIF() {
        super();

        setSize(400, 400);
        JPanel panel = new JPanel();
        this.add(panel);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();



        JLabel playerJL[] = new JLabel[12];
        JTextField playerJTF[] = new JTextField[12];
        JButton addJB[] = new JButton[12];
        JButton removeJB[] = new JButton[12];

        for (int i = 0; i < 12; i++) {
            playerJL[i] = new JLabel("Giocatore " + i + ":");
            c.weighty = 0.1;
            c.weightx = 0.1;
            c.insets = new Insets(2, 2, 2, 2);
            c.gridx = 0;
            c.gridy = i;
            c.anchor = c.FIRST_LINE_START;
            panel.add(playerJL[i], c);
            playerJL[i].setVisible(true);

            playerJTF[i] = new JTextField(10);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.gridy = i;
            panel.add(playerJTF[i], c);
            playerJTF[i].setVisible(true);

            addJB[i] = new JButton("+");
            c.gridx = 2;
            c.gridy = i;
            addJB[i].setSize(new Dimension(10, 10));
            panel.add(addJB[i], c);
            if (i == 0) {

                playerJL[i].setVisible(true);
                playerJTF[i].setVisible(true);
                addJB[i].setVisible(true);
            } else {
                playerJL[i].setVisible(false);
                playerJTF[i].setVisible(false);
                addJB[i].setVisible(false);
            }
       }

        JButton cancelJB = new JButton("Cancel");
        c.gridx = 2;
        c.gridy = 12;
        c.anchor = c.CENTER;
        cancelJB.setSize(new Dimension(10, 10));
        panel.add(cancelJB, c);
        cancelJB.setVisible(true);

        JButton okJB = new JButton("OK");
        c.gridx = 1;
        c.gridy = 12;
        c.anchor = c.CENTER;
        okJB.setSize(new Dimension(10, 10));
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
}
