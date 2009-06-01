package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.smgame.core.GUICoreMediator;

public class GameOnlineJIF extends JInternalFrame implements IGameJIF{

    private JPanel playerOneJP;
    private JPanel playerTwoJP;

    private JLabel nameOneJL;
    private JLabel creditOneJL;
    private JLabel scoreOneJL;
    private JLabel stakeOneJL;
    private JTextField stakeOneJTF;

    private JLabel nameTwoJL;
    private JLabel creditTwoJL;
    private JLabel scoreTwoJL;
    private JLabel stakeTwoJL;
    private JButton askCardOneJB;
    private JButton declareGoodOneJB;
    private JTextField stakeTwoJTF;
    private JButton askCardTwoJB;


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
        askCardOneJB = new JButton("Chiedi carta");
        playerOneJP.add(scoreOneJL);
        declareGoodOneJB = new JButton("Sto bene");
        playerOneJP.add(scoreOneJL);


        playerTwoJP = new JPanel();
        nameTwoJL = new JLabel(GUICoreMediator.getPlayerNameList().get(1));
        creditTwoJL = new JLabel();
        scoreTwoJL = new JLabel();
        stakeTwoJL = new JLabel();
        stakeTwoJTF = new JTextField();
        askCardTwoJB = new JButton("Chiedi carta");
        declareGoodOneJB = new JButton("Sto bene");
    }



}
