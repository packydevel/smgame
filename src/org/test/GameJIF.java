/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.smgame.core.GUICoreMediator;
import org.smgame.frontend.PlayerCardJP;

/**
 *
 * @author packyuser
 */
public class GameJIF extends JInternalFrame {

    public GameJIF() {

        super(GUICoreMediator.getGameName(), false, true, false, false);
        int width = 960;
        int height = 720;
        int xbound = (getContentPane().getWidth() - width) % 2;
        int ybound = (getContentPane().getHeight() - height) % 2;
        setPreferredSize(new Dimension(width, height));
        setBounds(xbound, ybound, xbound + width, ybound + height);
        setLayout(new GridBagLayout());
        initComponents();
        pack();
    }

    private void initComponents() {
        List<String> playerNameList = GUICoreMediator.getPlayerNameList();
        List<Boolean> playerTypeList = GUICoreMediator.getPlayerTypeList();
        List<Double> playerCreditList = GUICoreMediator.getPlayerCreditList();
        List<ImageIcon> cardImageList;
        JLabel[] playerNameJL, playerCreditJL, playerStakeJL, playerScoreJL;
        JLabel[][] playerCardsJL;

        int size = playerNameList.size();

        playerNameJL = new JLabel[size];
        playerCreditJL = new JLabel[size];
        playerStakeJL = new JLabel[size];
        playerScoreJL = new JLabel[size];
        playerCardsJL = new JLabel[size][12];

        GridBagConstraints textFieldGBC = new GridBagConstraints();
        GridBagConstraints labelGBC = new GridBagConstraints();
        GridBagConstraints buttonGBC = new GridBagConstraints();

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

        buttonGBC = new GridBagConstraints();
        buttonGBC.weightx = 0;
        buttonGBC.weighty = 1;
        buttonGBC.insets = new Insets(2, 2, 2, 2);
        buttonGBC.anchor = GridBagConstraints.SOUTHEAST;

        for (int i = 0; i < size; i++) {
            playerNameJL[i] = new JLabel(playerNameList.get(i));
            labelGBC.gridx = 0;
            labelGBC.gridy = i;
            labelGBC.weightx = 0;
            labelGBC.weighty = 0;
            this.add(playerNameJL[i], labelGBC);

            playerCreditJL[i] = new JLabel("Credito: " + playerCreditList.get(i).toString());
            labelGBC.gridx = 1;
            labelGBC.gridy = i;
            labelGBC.weightx = 0;
            labelGBC.weighty = 0;
            this.add(playerCreditJL[i], labelGBC);

            playerStakeJL[i] = new JLabel("Puntata: 0,00");
            labelGBC.gridx = 2;
            labelGBC.gridy = i;
            labelGBC.weightx = 0;
            labelGBC.weighty = 0;
            this.add(playerStakeJL[i], labelGBC);

            playerScoreJL[i] = new JLabel("Punteggio: 0.0");
            labelGBC.gridx = 3;
            labelGBC.gridy = i;
            labelGBC.weightx = 1;
            labelGBC.weighty = 0;
            this.add(playerScoreJL[i], labelGBC);

            labelGBC.weightx = 0;
            labelGBC.weighty = 0.4;//(double) 1 / (double) size;
            for (int j = 0; j < 12; j++) {
                playerCardsJL[i][j] = new JLabel();
                labelGBC.gridx = j;
                labelGBC.gridy = i + 1;
                this.add(playerCardsJL[i][j], labelGBC);
                //System.out.println(i);
                //System.out.println(j);
            }
            System.out.println(labelGBC.weighty);
        }
        //System.out.println(playerCardsJL.length);

    /*
     * 
    cardImageList = GUICoreMediator.getPlayerCards(i);
    int pos = GUICoreMediator.getBankPlayer();
    ((PlayerCardJP) jpPanels[pos]).newLabelIconCard(GUICoreMediator.requestCard(pos, 0));
    
    jbCallCard.addMouseListener(new MouseAdapter() {
    
    public void mouseClicked(MouseEvent evt) {
    requestCard(index, jtxtSetCash.getText());
    }
    });
    jbCallCard.setPreferredSize(new Dimension(75, 20));
    pane.add(jbCallCard);
    
    JButton jbImOK = new JButton("Sto bene");
    //jbImOK.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    jbImOK.addMouseListener(new MouseAdapter() {
    
    public void mouseClicked(MouseEvent evt) {
    imOk(index, jtxtSetCash.getText());
    }
    });
    jbImOK.setPreferredSize(new Dimension(55, 20));
    pane.add(jbImOK);
    
    private void requestCard(int i, String value) {
    double cash = -1;
    try {
    if ((value != null) && (!value.equalsIgnoreCase(""))) {
    cash = Double.valueOf(value);
    }
    ((PlayerCardJP) jpPanels[i]).newLabelIconCard(GUICoreMediator.requestCard(i, cash));
    } catch (Exception e) {
    e.printStackTrace();
    }
    
    }
    
    private void imOk(int i, String value) {
    double cash = -1;
    try {
    if ((value != null) && (!value.equalsIgnoreCase(""))) {
    cash = Double.valueOf(value);
    }
    GUICoreMediator.declareGoodScore(i, cash);
    } catch (Exception e) {
    e.printStackTrace();
    }
    jpActions[i].setVisible(false);
    int pos = GUICoreMediator.nextPlayer();
    jpActions[pos].setVisible(true);
    }
    }
    
    }
     */

    }
}
