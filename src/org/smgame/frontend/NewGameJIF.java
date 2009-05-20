/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 *
 * @author packyuser
 */
public class NewGameJIF extends JInternalFrame {

    public NewGameJIF() {
        super();

        setSize(300, 200);
        JPanel panel = new JPanel();

        JButton button;

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;


        button = new JButton("Button 1");
        c.weightx = 0.5;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        panel.add(button, c);
        button = new JButton("Button 2");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;

        panel.add(button, c);
        button = new JButton("Button 3");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;

        panel.add(button, c);
        button = new JButton("Long-Named Button 4");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;

        panel.add(button, c);
        button = new JButton("5");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10, 0, 0, 0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        panel.add(button, c);
    }
}
