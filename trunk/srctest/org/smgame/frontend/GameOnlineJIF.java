package org.smgame.frontend;

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

    private JPanel playerCpuJP;
    private JPanel playerHumanJP;
    private JPanel actionHumanJP;

    private JLabel nameCpuJL;
    private JLabel creditCpuJL;
    private JLabel scoreCpuJL;
    private JLabel stakeCpuJL;
    //private JTextField stakeOneJTF;
    //private JButton requestCardOneJB;
    //private JButton declareGoodOneJB;

    private JLabel nameHumanJL;
    private JLabel creditHumanJL;
    private JLabel scoreHumanJL;
    private JLabel stakeHumanJL;
    private JTextField stakeHumanJTF;
    private JButton requestCardJB;
    private JButton declareGoodScoreJB;

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
        GridBagConstraints playerCpuGBC = new GridBagConstraints();
        GridBagConstraints playerHumanGBC = new GridBagConstraints();
        GridBagConstraints actionHumanGBC = new GridBagConstraints();
        GridBagConstraints paneCardsGBC = new GridBagConstraints();

        playerCpuGBC.insets = new Insets(1, 1, 1, 1);
        playerCpuGBC.weighty = 0;
        playerCpuGBC.weightx = 0;
        playerCpuGBC.anchor = GridBagConstraints.CENTER;
        playerCpuGBC.gridx = 0;
        playerCpuGBC.gridy = 0;

        playerCpuJP = new JPanel(new FlowLayout());

        nameCpuJL = new JLabel(GUICoreMediator.getPlayerNameList().get(0));
        nameCpuJL.setPreferredSize(new Dimension(100, 25));
        playerCpuJP.add(nameCpuJL);

        creditCpuJL = new JLabel("Credito: " + GUICoreMediator.getPlayerCredit(0));
        creditCpuJL.setPreferredSize(new Dimension(100, 25));
        playerCpuJP.add(creditCpuJL);

        scoreCpuJL = new JLabel("Punteggio: " + GUICoreMediator.getPlayerScore(0));
        scoreCpuJL.setPreferredSize(new Dimension(100, 25));
        playerCpuJP.add(scoreCpuJL);

        stakeCpuJL = new JLabel("Puntata: "+GUICoreMediator.getPlayerStake(0));
        stakeCpuJL.setPreferredSize(new Dimension(100, 25));
        playerCpuJP.add(stakeCpuJL);        
        this.add(playerCpuJP,playerCpuGBC);

        actionHumanGBC.insets = new Insets(1, 1, 1, 1);
        actionHumanGBC.weighty = 0;
        actionHumanGBC.weightx = 0;
        actionHumanGBC.anchor = GridBagConstraints.CENTER;
        actionHumanGBC.gridx = 0;
        actionHumanGBC.gridy = 4;

        actionHumanJP = new JPanel(new FlowLayout());

        stakeHumanJTF = new JTextField();
        stakeHumanJTF.setColumns(10);
        actionHumanJP.add(stakeHumanJTF);

        requestCardJB = new JButton("Chiedi carta");
        requestCardJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                requestCard();
            }
        });
        actionHumanJP.add(requestCardJB);

        declareGoodScoreJB = new JButton("Sto bene");
        declareGoodScoreJB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                declareGoodScore();
            }
        });
        actionHumanJP.add(declareGoodScoreJB);

        this.add(actionHumanJP,actionHumanGBC);

        playerHumanGBC.insets = new Insets(1, 1, 1, 1);
        playerHumanGBC.weighty = 0;
        playerHumanGBC.weightx = 0;
        playerHumanGBC.anchor = GridBagConstraints.CENTER;
        playerHumanGBC.gridx = 0;
        playerHumanGBC.gridy = 5;

        playerHumanJP = new JPanel();

        nameHumanJL = new JLabel(GUICoreMediator.getPlayerNameList().get(1));
        nameHumanJL.setPreferredSize(new Dimension(100, 25));
        playerHumanJP.add(nameHumanJL);

        creditHumanJL = new JLabel("Credito: " + GUICoreMediator.getPlayerCredit(1));
        creditHumanJL.setPreferredSize(new Dimension(100, 25));
        playerHumanJP.add(creditHumanJL);

        scoreHumanJL = new JLabel("Punteggio: " + GUICoreMediator.getPlayerScore(1));
        scoreHumanJL.setPreferredSize(new Dimension(100, 25));
        playerHumanJP.add(scoreHumanJL);

        stakeHumanJL = new JLabel("Puntata: "+GUICoreMediator.getPlayerStake(1));
        stakeHumanJL.setPreferredSize(new Dimension(100, 25));
        playerHumanJP.add(stakeHumanJL);
        
        this.add(playerHumanJP,playerHumanGBC);

        paneCardsGBC.insets = new Insets(1, 1, 1, 1);
        paneCardsGBC.weighty = 0;
        paneCardsGBC.weightx = 0;
        paneCardsGBC.anchor = GridBagConstraints.CENTER;
        paneCardsGBC.gridx = 0;
        playerCardsListJP = new ArrayList<JPanel>(2);
        int j = 1;
        for (int i=0; i<2; i++){
            playerCardsListJP.add(initPanelPlayersCards());
            paneCardsGBC.gridy = i+j;
            this.add(playerCardsListJP.get(i),paneCardsGBC);
            j++;
        }
        JPanel tempPane = new JPanel(new FlowLayout());
        tempPane.add(new JLabel());
        tempPane.setPreferredSize(new Dimension(910, 100));
        tempPane.setVisible(true);
        paneCardsGBC.gridy = 2;
        this.add(tempPane,paneCardsGBC);
    }

    //inizializza il pannello del player - carte
    private JPanel initPanelPlayersCards() {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.setPreferredSize(new Dimension(910, 100));
        pane.setBorder(new LineBorder(new Color(212, 208, 200)));
        ((FlowLayout) pane.getLayout()).setHgap(1);
        playerCardsListJL = new ArrayList<JLabel>(7);
        for (int j = 0; j < 14; j++) {
            playerCardsListJL.add(new JLabel());
            playerCardsListJL.get(j).setPreferredSize(new Dimension(63, 99));
            playerCardsListJL.get(j).setBorder(new LineBorder(new Color(212, 208, 200)));
            pane.add(playerCardsListJL.get(j));
        }
        return pane;
    } // initPanelPlayersCards

    private void setEnabledActionHumanJP(boolean enabled){
        stakeHumanJTF.setEditable(enabled);
        stakeHumanJTF.setEnabled(enabled);
        declareGoodScoreJB.setVisible(enabled);
        requestCardJB.setVisible(enabled);
    }

    private void requestCard(){

    }

    private void declareGoodScore(){

    }

}
