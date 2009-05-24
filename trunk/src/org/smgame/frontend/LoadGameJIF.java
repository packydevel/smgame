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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author packyuser
 */
public class LoadGameJIF extends JInternalFrame {

    JTable gameJT;
    LoadGameATM gameATM;
    JPanel rootJP;
    JScrollPane tableJSP;
    GridBagConstraints rootGBC, tableGBC, buttonGBC;
    JButton cancelJB;
    JButton okJB;
    JLabel gameNameJL;
    int previousPlayersNumber = 0;
    int currentPlayersNumber;

    public LoadGameJIF() {
        super("Carica Partita", false, true, false, false);
        setSize(400, 450);
        gameATM = new LoadGameATM();
        gameATM.setValueAt(GUIGameEngine.getGame().getGameName(), 1, 1);
        gameJT = new JTable(gameATM);
        gameJT.setFillsViewportHeight(true);
        gameJT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameJT.setColumnSelectionAllowed(false);

        rootJP = new JPanel();
        rootJP.setLayout(new GridBagLayout());

        tableJSP = new JScrollPane(gameJT);

        rootGBC = new GridBagConstraints();
        rootGBC.weightx = 0;
        rootGBC.weighty = 0;
        rootGBC.insets = new Insets(2, 2, 2, 2);
        rootGBC.anchor = GridBagConstraints.NORTHWEST;

        tableGBC = new GridBagConstraints();
        tableGBC.weightx = 0;
        tableGBC.weighty = 0;
        tableGBC.insets = new Insets(2, 2, 2, 2);
        tableGBC.anchor = GridBagConstraints.NORTHWEST;

        buttonGBC = new GridBagConstraints();
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 1;
        buttonGBC.insets = new Insets(2, 2, 2, 2);
        buttonGBC.anchor = GridBagConstraints.SOUTHEAST;

        cancelJB = new JButton("Annulla");
        cancelJB.setName("cancelJB");
        cancelJB.setPreferredSize(new Dimension(70, 20));
        cancelJB.setVisible(true);
        buttonGBC.gridx = 2;
        buttonGBC.gridy = 14;
        cancelJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                //newGameActionPerformed(evt);
            }
        });
        rootJP.add(cancelJB, buttonGBC);

        okJB = new JButton("OK");
        okJB.setPreferredSize(new Dimension(70, 20));
        okJB.setEnabled(false);
        okJB.setVisible(true);
        buttonGBC.gridx = 1;
        buttonGBC.gridy = 14;
        buttonGBC.weightx = 1;
        okJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                //newGameActionPerformed(evt);
            }
        });
        rootJP.add(okJB, buttonGBC);

        add(rootJP);
    }
}
