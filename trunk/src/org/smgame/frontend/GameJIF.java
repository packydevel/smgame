package org.smgame.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import javax.swing.border.BevelBorder;
import org.smgame.core.GUICoreMediator;

public class GameJIF extends JInternalFrame implements IGameJIF {

    private static JPanel[] jpPanels;
    private static JPanel[] jpActions;

    public static JPanel[] getJpActions() {
        return jpActions;
    }

    public static JPanel[] getJpPanels() {
        return jpPanels;
    }

    public GameJIF() {
        super(GUICoreMediator.getGameName());
        int width = 1000;
        int height = 600;
        int xbound = (getContentPane().getWidth() - width) % 2;
        int ybound = (getContentPane().getHeight() - height) % 2;
        setPreferredSize(new Dimension(width, height));
        setBounds(xbound, ybound, xbound + width, ybound + height);
        setClosable(true);
        initComponents();
        pack();
    }

    private void initComponents() {
        List<String> player_list = GUICoreMediator.getPlayerNameList();
        List<Boolean> type_player = GUICoreMediator.getPlayerTypeList();
        List<Double> player_credit = GUICoreMediator.getPlayerCreditList();

        int size = player_list.size();
        jpPanels = new JPanel[size];
        jpActions = new JPanel[size];

        setLayout(new GridBagLayout());
        GridBagConstraints gbcP = new GridBagConstraints();
        GridBagConstraints gbcA = new GridBagConstraints();

        gbcP.weighty = gbcA.weighty = 0.1;
        gbcP.weightx = gbcA.weightx = 0.1;
        gbcP.anchor = gbcA.anchor = GridBagConstraints.NORTHWEST;
        gbcP.gridx = 0;
        gbcA.gridx = 1;

        for (int i = 0; i < size; i++) {
            gbcP.gridy = gbcA.gridy = i;
            //aggiungo al pannello i nomi giocatori
            jpPanels[i] = new PlayerCardJP(player_list.get(i), player_credit.get(i));
            this.add(jpPanels[i], gbcP);

            //aggiungo al pannello le azioni se il giocatore è umano            
            if (type_player.get(i).equals(Boolean.FALSE)) {
                jpActions[i] = new createPanelActionsPlayer(i);
                jpActions[i].setVisible(false);
                this.add(jpActions[i], gbcA);
                ((PlayerCardJP) jpPanels[i]).setHumanColor();
            } else {
                jpActions[i] = null;
            }
        } //end for

        int pos = GUICoreMediator.getBankPlayer();
        ((PlayerCardJP) jpPanels[pos]).selectBank();
        pos = ++pos % size;
        if (jpActions[pos] != null) {
            jpActions[pos].setVisible(true);
        }
        try {
            for (int i = 0; i < size; i++) {
                System.out.print(pos);
                ((PlayerCardJP) jpPanels[pos]).newLabelIconCard(GUICoreMediator.requestCard(pos, 0));
                ((createPanelActionsPlayer) jpActions[pos]).setLabelPoints(GUICoreMediator.getPlayerScore(pos));
                pos = ++pos % size;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.validate();
    }//end initComponentsNew

    /*    private JPanel createPanelActionsPlayer(int i){
    final int index = i;

    JPanel jpGlobal = new JPanel(new BorderLayout());
    JPanel jpNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel jpSouth = new JPanel(new FlowLayout(FlowLayout.LEFT));

    jpGlobal.setPreferredSize(new Dimension(280, 50));
    jpNorth.setPreferredSize(new Dimension(280, 24));
    jpSouth.setPreferredSize(new Dimension(280, 20));

    jpNorth.add(new JLabel("Puntata"));
    jpSouth.add(new JLabel("Puntate totali: "));
    JLabel jlTotalCash = new JLabel();
    jpSouth.add(jlTotalCash);

    JTextField jtxtSetCash = new JTextField();
    jtxtSetCash.setEditable(true);
    jtxtSetCash.setEnabled(true);
    jtxtSetCash.setPreferredSize(new Dimension(30, 20));
    jtxtSetCash.setColumns(4);
    jtxtSetCash.setVisible(true);
    jpNorth.add(jtxtSetCash);

    JButton jbCallCard = new JButton("Chiedi carta");
    jbCallCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    jbCallCard.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent evt) {
    requestCard(index, jtxtSetCash.getText());

    }
    private void requestCard(int i, String value) {
    double cash=-1;
    try {
    if ((value!=null) && (!value.equalsIgnoreCase("")))
    cash = Double.valueOf(value);
    ((PlayerCardJP)jpPanels[i]).newLabelIconCard(GUICoreMediator.requestCard(i, cash));

    } catch (Exception e) {
    e.printStackTrace();
    }
    }
    });
    jbCallCard.setPreferredSize(new Dimension(75, 20));
    jpNorth.add(jbCallCard);

    JButton jbImOK = new JButton("Sto bene");
    jbImOK.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    jbImOK.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent evt) {
    imOk(index, jtxtSetCash.getText());
    }

    });
    jbImOK.setPreferredSize(new Dimension(55,20));
    jpNorth.add(jbImOK);


    jpGlobal.add(jpNorth,BorderLayout.NORTH);
    jpGlobal.add(jpSouth,BorderLayout.SOUTH);
    return jpGlobal;
    }

    private void setTotalCash(int i, String cash){

    }

    

    private void imOk(int i, String value){
    Component[] c = jpActions[0].getComponents();
    for (int j=0; j<c.length; j++){

    }
    /*double cash=-1;
    try {
    if ((value!=null) && (!value.equalsIgnoreCase("")))
    cash = Double.valueOf(value);
    GUICoreMediator.declareGoodScore(i, cash);
    } catch (Exception e) {
    e.printStackTrace();
    }
    jpActions[i].setVisible(false);
    int pos = GUICoreMediator.nextPlayer();
    jpActions[pos].setVisible(true);
    this.validate();
    }*/
} //end class

class createPanelActionsPlayer extends JPanel {

    private int index;
    private JPanel jpNorth;
    private JPanel jpSouth;
    private JLabel jlTotalCash;
    private JLabel jlPoints;
    private JTextField jtxtSetCash;
    private JButton jbCallCard;
    private JButton jbImOK;

    public createPanelActionsPlayer(int i) {
        index = i;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(280, 50));
        initComponents();
    }

    private void initComponents() {
        jpNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpSouth = new JPanel(new FlowLayout(FlowLayout.LEFT));

        jpNorth.setPreferredSize(new Dimension(280, 25));
        jpSouth.setPreferredSize(new Dimension(280, 20));

        jpNorth.add(new JLabel("Puntata"));
        jpSouth.add(new JLabel("Puntate totali: "));

        jlTotalCash = new JLabel();
        jpSouth.add(jlTotalCash);

        jpSouth.add(new JLabel("Punteggio: "));
        jlPoints = new JLabel();
        jpSouth.add(jlPoints);

        jtxtSetCash = new JTextField();
        jtxtSetCash.setEditable(true);
        jtxtSetCash.setEnabled(true);
        jtxtSetCash.setPreferredSize(new Dimension(30, 20));
        jtxtSetCash.setColumns(4);
        jtxtSetCash.setVisible(true);
        jpNorth.add(jtxtSetCash);

        jbCallCard = new JButton("Chiedi carta");
        //jbCallCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbCallCard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                requestCard(jtxtSetCash.getText());
            }
        });
        jbCallCard.setPreferredSize(new Dimension(75, 20));
        jpNorth.add(jbCallCard);

        jbImOK = new JButton("Sto bene");
        //jbImOK.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbImOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                imOk(jtxtSetCash.getText());
            }
        });
        jbImOK.setPreferredSize(new Dimension(55, 20));
        jpNorth.add(jbImOK);


        add(jpNorth, BorderLayout.NORTH);
        add(jpSouth, BorderLayout.SOUTH);
    }

    private void requestCard(String value) {
        double cash = -1;
        try {
            if ((value != null) && (!value.equalsIgnoreCase(""))) {
                cash = Double.valueOf(value);
            }
            ((PlayerCardJP) GameJIF.getJpPanels()[index]).newLabelIconCard(GUICoreMediator.requestCard(index, cash));
            jlTotalCash.setText(Double.toString(GUICoreMediator.getPlayerStake(index)));
            setLabelPoints(GUICoreMediator.getPlayerScore(index));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void imOk(String value) {
        double cash = -1;
        try {
            if ((value != null) && (!value.equalsIgnoreCase(""))) {
                cash = Double.valueOf(value);
                GUICoreMediator.declareGoodScore(index, cash);
                jlTotalCash.setText(Double.toString(GUICoreMediator.getPlayerStake(index)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setVisible(false);
        int pos = GUICoreMediator.nextPlayer();
        GameJIF.getJpActions()[pos].setVisible(true);
    }

    public void setLabelPoints(double points) {
        jlPoints.setText(Double.toString(points));
    }
}
/*
private void jbRemoveAllMouseClicked(MouseEvent evt) {
((testPanel) panel).resetLabelIconCards();
pack();
}
 */
