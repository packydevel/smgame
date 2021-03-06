package org.smgame.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.smgame.core.GUICoreMediator;
import org.smgame.util.PrintErrors;
import org.smgame.util.ScoreOverflowException;
public class GameJIF extends JInternalFrame implements IGameJIF {

    private static JPanel[] jpPanels;
    private static JPanel[] jpActions;

    public static JPanel getJpActions(int i) {
        return jpActions[i];
    }

    public static JPanel getJpPanels(int i) {
        return jpPanels[i];
    }

    public GameJIF() {
        super(GUICoreMediator.getGameName());
        int width = 1000;
        int height = 650;
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
        List<String> player_credit = GUICoreMediator.getPlayerCreditList();

        int size = player_list.size();
        jpPanels = new JPanel[size];
        jpActions = new JPanel[size];

        setLayout(new GridBagLayout());
        GridBagConstraints gbcP = new GridBagConstraints();
        GridBagConstraints gbcA = new GridBagConstraints();

        gbcP.weighty = gbcA.weighty = 0.1;
        gbcP.weightx = gbcA.weightx = 0.1;
        gbcP.anchor = GridBagConstraints.NORTHWEST;
        gbcA.anchor = GridBagConstraints.NORTHEAST;
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
        try {
            for (int i = 0; i < size; i++) {                
                ((PlayerCardJP) jpPanels[pos]).setFirstCard(GUICoreMediator.getFirstCard(pos));
                ((createPanelActionsPlayer) jpActions[pos]).setLabelPoints(GUICoreMediator.getPlayerScore(pos));
                pos = ++pos % size;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jpActions[pos] != null) {
            jpActions[pos].setVisible(true);
            ((PlayerCardJP) jpPanels[pos]).setFirstCardDiscovered();
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
        jpNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jpSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        jpNorth.setPreferredSize(new Dimension(280, 25));
        jpSouth.setPreferredSize(new Dimension(280, 25));

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
        jbCallCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbCallCard.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                requestCard(jtxtSetCash.getText());
            }
        });
        jbCallCard.setPreferredSize(new Dimension(85, 20));
        jpNorth.add(jbCallCard);

        jbImOK = new JButton("Sto bene");
        jbImOK.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbImOK.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                imOk(jtxtSetCash.getText());
            }
        });
        jbImOK.setPreferredSize(new Dimension(60, 20));
        jpNorth.add(jbImOK);

        add(jpNorth, BorderLayout.NORTH);
        add(jpSouth, BorderLayout.SOUTH);
    }

    private void requestCard(String value) {
        double cash;
        PlayerCardJP tempPCjp = ((PlayerCardJP) GameJIF.getJpPanels(index));
        try {
            if ((value != null) && (!value.equalsIgnoreCase(""))) {
                cash = Double.valueOf(value);
                tempPCjp.newLabelIconCard(GUICoreMediator.requestCard(index, cash));
                jlTotalCash.setText(GUICoreMediator.getPlayerStake(index));
                setLabelPoints(GUICoreMediator.getPlayerScore(index));
                tempPCjp.setCashLabel(GUICoreMediator.getPlayerCredit(index));
            }
        } catch (ScoreOverflowException soe) {
            if (GUICoreMediator.isEndManche(index)){
                //prendo l'immagine della carta sbagliata
                tempPCjp.newLabelIconCard(soe.getCardException().getFrontImage());
                //aggiorno il punteggio che peraltro è overflow
                setLabelPoints(GUICoreMediator.getPlayerScore(index));
                //richiamo la jdialog x l'eccezione
                PrintErrors.exception(soe);
                //aggiorno il credito residuo del giocatore sballante
                tempPCjp.setCashLabel(GUICoreMediator.getPlayerCredit(index));
                //
                this.setVisible(false);
                //ottengo la posizione del successivo player
                int pos = GUICoreMediator.nextPlayer();
                //ottengo la posizione del mazziere
                int bank = GUICoreMediator.getBankPlayer();
                //aggiorno il credito della mazziere
                ((PlayerCardJP) GameJIF.getJpPanels(bank)).setCashLabel(GUICoreMediator.getPlayerCredit(bank));
                //rivelo la carta nascosta
                ((PlayerCardJP)GameJIF.getJpPanels(pos)).setFirstCardDiscovered();
                //pannello delle azioni visibile x il giocatore successivo
                (GameJIF.getJpActions(pos)).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void imOk(String value) {
        double cash;
        try {
            if ((value != null) && (!value.equalsIgnoreCase(""))) {
                cash = Double.valueOf(value);
                GUICoreMediator.declareGoodScore(index, cash);
                jlTotalCash.setText(GUICoreMediator.getPlayerStake(index));
                PlayerCardJP tempPCjp = ((PlayerCardJP)GameJIF.getJpPanels(index));
                tempPCjp.setFirstCardCovered();
                tempPCjp.setCashLabel(GUICoreMediator.getPlayerCredit(index));
                this.setVisible(false);
                int pos = GUICoreMediator.nextPlayer();
                ((PlayerCardJP)GameJIF.getJpPanels(pos)).setFirstCardDiscovered();
                GameJIF.getJpActions(pos).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void setLabelPoints(String points) {
        jlPoints.setText(points);
    }
}
