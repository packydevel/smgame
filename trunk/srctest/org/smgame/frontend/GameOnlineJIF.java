package org.smgame.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.smgame.core.GUICoreMediator;

public class GameOnlineJIF extends JInternalFrame implements IGameJIF{

    private List<JPanel> playerCardsListJP; //Lista pannelli giocatore-carte
    private List<JLabel> playerCardsListJL;

    private JPanel playerOneJP;
    private JPanel playerTwoJP;

    private JLabel nameOneJL;
    private JLabel creditOneJL;
    private JLabel scoreOneJL;
    private JLabel stakeOneJL;
    private JTextField stakeOneJTF;
    private JButton requestCardOneJB;
    private JButton declareGoodOneJB;

    private JLabel nameTwoJL;
    private JLabel creditTwoJL;
    private JLabel scoreTwoJL;
    private JLabel stakeTwoJL;
    private JTextField stakeTwoJTF;
    private JButton requestCardTwoJB;
    private JButton declareGoodTwoJB;

    public GameOnlineJIF() {
        super(GUICoreMediator.getGameName(), false, true, false, false);
        int width = 960;
        int height = 640;
        int xbound = (getContentPane().getWidth() - width) / 2;
        int ybound = (getContentPane().getHeight() - height) / 2;
        setPreferredSize(new Dimension(width, height));
        //setBounds(xbound, ybound, xbound + width, ybound + height);
        setLayout(new GridBagLayout());
        initComponents();
        setVisible(true);
        pack();

    }

    private void initComponents(){
        GridBagConstraints playerOneGBC = new GridBagConstraints();
        GridBagConstraints playerTwoGBC = new GridBagConstraints();

        playerOneGBC.insets = new Insets(1, 1, 1, 1);
        playerOneGBC.weighty = 0;
        playerOneGBC.weightx = 0;
        playerOneGBC.anchor = GridBagConstraints.NORTHWEST;       
        playerOneGBC.gridx = 0;
        playerOneGBC.gridy = 0;

        playerTwoGBC.insets = new Insets(1, 1, 1, 1);
        playerTwoGBC.weighty = 0;
        playerTwoGBC.weightx = 0;
        playerTwoGBC.anchor = GridBagConstraints.NORTHWEST;
        playerTwoGBC.gridx = 0;
        playerTwoGBC.gridy = 6;

        playerOneJP = new JPanel(new FlowLayout());


        nameOneJL = new JLabel(GUICoreMediator.getPlayerNameList().get(0));
        playerOneJP.add(nameOneJL);
        creditOneJL = new JLabel("Credito: " + GUICoreMediator.getPlayerCredit(0));
        playerOneJP.add(creditOneJL);
        scoreOneJL = new JLabel("Punteggio: " + GUICoreMediator.getPlayerScore(0));
        playerOneJP.add(scoreOneJL);
        stakeOneJL = new JLabel("Puntata: "+GUICoreMediator.getPlayerStake(0));
        playerOneJP.add(stakeOneJL);
        stakeOneJTF = new JTextField();
        stakeOneJTF.setColumns(10);
        playerOneJP.add(stakeOneJTF);
        requestCardOneJB = new JButton("Chiedi carta");
        playerOneJP.add(requestCardOneJB);
        declareGoodOneJB = new JButton("Sto bene");
        playerOneJP.add(declareGoodOneJB);
        playerOneJP.setVisible(true);
        this.add(playerOneJP,playerOneGBC);


        playerTwoJP = new JPanel();
        nameTwoJL = new JLabel(GUICoreMediator.getPlayerNameList().get(1));
        playerTwoJP.add(nameTwoJL);
        creditTwoJL = new JLabel("Credito: " + GUICoreMediator.getPlayerCredit(1));
        playerTwoJP.add(creditTwoJL);
        scoreTwoJL = new JLabel("Punteggio: " + GUICoreMediator.getPlayerScore(1));
        playerTwoJP.add(scoreTwoJL);
        stakeTwoJL = new JLabel("Puntata: "+GUICoreMediator.getPlayerStake(0));
        playerTwoJP.add(stakeTwoJL);
        stakeTwoJTF = new JTextField();
        stakeTwoJTF.setColumns(10);
        playerTwoJP.add(stakeTwoJTF);
        requestCardTwoJB = new JButton("Chiedi carta");
        playerTwoJP.add(requestCardTwoJB);
        declareGoodTwoJB = new JButton("Sto bene");
        playerTwoJP.add(declareGoodTwoJB);
        playerTwoJP.setVisible(true);
        this.add(playerTwoJP,playerTwoGBC);

        playerCardsListJP = new ArrayList<JPanel>(4);
        for (int i=0; i<4; i++){
            playerCardsListJP.add(initPanelPlayersCards());
            //this.add(playerCardsListJP.get(i), panelGBC);
        }
    }

    //inizializza il pannello del player - carte
    private JPanel initPanelPlayersCards() {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.setPreferredSize(new Dimension(480, 100));
        pane.setBorder(new LineBorder(new Color(212, 208, 200)));
        ((FlowLayout) pane.getLayout()).setHgap(1);
        playerCardsListJL = new ArrayList<JLabel>(7);
        for (int j = 0; j < 7; j++) {
            playerCardsListJL.add(new JLabel());
            playerCardsListJL.get(j).setPreferredSize(new Dimension(63, 99));
            playerCardsListJL.get(j).setBorder(new LineBorder(new Color(212, 208, 200)));
            pane.add(playerCardsListJL.get(j));
        }
        return pane;
    } // initPanelPlayersCards

}
