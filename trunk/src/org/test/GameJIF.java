/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import org.smgame.core.GUICoreMediator;

/**
 *
 * @author packyuser
 */
public class GameJIF extends JInternalFrame {

    public GameJIF() {

        super(GUICoreMediator.getGameName(), false, true, false, false);
        int width = 960;
        int height = 640;
        int xbound = (getContentPane().getWidth() - width) / 2;
        int ybound = (getContentPane().getHeight() - height) / 2;
        setPreferredSize(new Dimension(width, height));
        //setBounds(xbound, ybound, xbound + width, ybound + height);
        setLayout(new GridBagLayout());
        initComponents();
        pack();
    }

    private void initComponents() {
        List<JPanel> playerCardsList;
        List<String> playerNameList = GUICoreMediator.getPlayerNameList();
        List<Boolean> playerTypeList = GUICoreMediator.getPlayerTypeList();
        List<String> playerCreditList = GUICoreMediator.getPlayerCreditList();
        List<JLabel> playerNameJL, playerCreditJL, playerStakeJL, playerScoreJL;
        List<ImageIcon> cardImagesList = new ArrayList<ImageIcon>();
        List<List<ImageIcon>> playerCardsImagesList = new ArrayList<List<ImageIcon>>();
        List<JTextField> textFieldJTF;
        List<JButton> requestCardJB, goodScoreJB;

        int size = playerNameList.size();

        playerCardsList = new ArrayList<JPanel>(size);
        playerNameJL = new ArrayList<JLabel>(size);
        playerCreditJL = new ArrayList<JLabel>(size);
        playerStakeJL = new ArrayList<JLabel>(size);
        playerScoreJL = new ArrayList<JLabel>(size);
        textFieldJTF = new ArrayList<JTextField>(size);
        requestCardJB = new ArrayList<JButton>(size);
        goodScoreJB = new ArrayList<JButton>(size);

        GridBagConstraints panelGBC = new GridBagConstraints();
        GridBagConstraints textFieldGBC = new GridBagConstraints();
        GridBagConstraints labelGBC = new GridBagConstraints();
        GridBagConstraints buttonGBC = new GridBagConstraints();

        labelGBC = new GridBagConstraints();
        labelGBC.weightx = 0;
        labelGBC.weighty = 0;
        labelGBC.fill = GridBagConstraints.HORIZONTAL;
        labelGBC.insets = new Insets(2, 2, 2, 2);
        labelGBC.anchor = GridBagConstraints.NORTHWEST;

        panelGBC = new GridBagConstraints();
        panelGBC.weightx = 1;
        panelGBC.weighty = (double) 1 / (double) size;
        panelGBC.gridheight = 2;
        panelGBC.insets = new Insets(2, 2, 2, 2);
        panelGBC.anchor = GridBagConstraints.NORTHWEST;

        textFieldGBC = new GridBagConstraints();
        textFieldGBC.weightx = 0;
        textFieldGBC.weighty = 0;
        textFieldGBC.insets = new Insets(2, 2, 2, 2);
        textFieldGBC.anchor = GridBagConstraints.NORTHWEST;

        buttonGBC = new GridBagConstraints();
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 0;
        buttonGBC.insets = new Insets(2, 2, 2, 2);
        buttonGBC.anchor = GridBagConstraints.NORTHWEST;

        for (int i = 0; i < size; i++) {
            playerNameJL.add(new JLabel(playerNameList.get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i;
            add(playerNameJL.get(i), labelGBC);

            playerCreditJL.add(new JLabel("Credito: " + playerCreditList.get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i + 1;
            add(playerCreditJL.get(i), labelGBC);

            textFieldJTF.add(new JTextField());
            textFieldJTF.get(i).setPreferredSize(new Dimension(50, 20));
            textFieldGBC.gridx = 2;
            textFieldGBC.gridy = 2 * i;
            add(textFieldJTF.get(i), textFieldGBC);

            requestCardJB.add(new JButton("Chiedi una carta"));
            requestCardJB.get(i).setPreferredSize(new Dimension(120, 20));
            buttonGBC.gridx = 3;
            buttonGBC.gridy = 2 * i;
            requestCardJB.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                }
            });
            add(requestCardJB.get(i), buttonGBC);

            goodScoreJB.add(new JButton("Sto bene"));
            goodScoreJB.get(i).setPreferredSize(new Dimension(120, 20));
            buttonGBC.gridx = 4;
            buttonGBC.gridy = 2 * i;
            goodScoreJB.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                }
            });
            add(goodScoreJB.get(i), buttonGBC);

            playerStakeJL.add(new JLabel("Puntata: 0,00"));
            labelGBC.gridx = 2;
            labelGBC.gridy = 2 * i + 1;
            add(playerStakeJL.get(i), labelGBC);

            playerScoreJL.add(new JLabel("Punteggio: 0.0"));
            labelGBC.gridx = 3;
            labelGBC.gridy = 2 * i + 1;
            add(playerScoreJL.get(i), labelGBC);

            panelGBC.gridx = 1;
            panelGBC.gridy = 2 * i;
            cardImagesList = GUICoreMediator.getPlayerCards(i);
            playerCardsImagesList.add(cardImagesList);
            playerCardsList.add(new JPanel());
            playerCardsList.get(i).setPreferredSize(new Dimension(420, 52));
            playerCardsList.get(i).setBorder(new LineBorder(new Color(0, 0, 0)));
            playerCardsList.get(i).setLayout(new FlowLayout(FlowLayout.LEFT));
            ((FlowLayout) playerCardsList.get(i).getLayout()).setHgap(1);
            for (int j = 0; j < 12; j++) {
                playerCardsList.get(i).add(new JLabel());
                //playerCardsList.get(i).add(new JLabel(cardImagesList.get(j)));
                ((JLabel) playerCardsList.get(i).getComponent(j)).setPreferredSize(new Dimension(32, 52));
                ((JLabel) playerCardsList.get(i).getComponent(j)).setBorder(new LineBorder(new Color(0, 0, 0)));
            }

            add(playerCardsList.get(i), panelGBC);
        }
    
    }
}
