package org.smgame.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

    }

    private void initComponents(){
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
        playerOneJP.add(stakeOneJTF);
        requestCardOneJB = new JButton("Chiedi carta");
        playerOneJP.add(requestCardOneJB);
        declareGoodOneJB = new JButton("Sto bene");
        playerOneJP.add(declareGoodOneJB);

        this.add(playerOneJP).setVisible(true);


        playerTwoJP = new JPanel();
        nameTwoJL = new JLabel(GUICoreMediator.getPlayerNameList().get(1));
        playerTwoJP.add(nameTwoJL);
        creditTwoJL = new JLabel();
        playerTwoJP.add(creditTwoJL);
        scoreTwoJL = new JLabel();
        playerTwoJP.add(scoreTwoJL);
        stakeTwoJL = new JLabel();
        playerTwoJP.add(stakeTwoJL);
        stakeTwoJTF = new JTextField();
        playerTwoJP.add(stakeTwoJTF);
        requestCardTwoJB = new JButton("Chiedi carta");
        playerTwoJP.add(requestCardTwoJB);
        declareGoodTwoJB = new JButton("Sto bene");
        playerTwoJP.add(declareGoodTwoJB);
        this.add(playerTwoJP).setVisible(true);

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
