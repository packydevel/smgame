package org.smgame.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.smgame.core.GUICoreMediator;
import org.smgame.util.Common;

public class OnLineGameJIF extends JInternalFrame implements IGameJIF {

    private List<JPanel> playerCardsListJP; //Lista pannelli giocatore-carte
    private List<JLabel> playerCardsListJL;
    private List<ImageIcon> playerCardsImagesList = new ArrayList<ImageIcon>();
    private List<List<ImageIcon>> playersCardsImagesList;
    private JPanel playerCpuJP;
    private JPanel playerHumanJP;
    private JPanel actionHumanJP;
    private JLabel nameCpuJL;
    private JLabel creditCpuJL;
    private JLabel scoreCpuJL;
    private JLabel stakeCpuJL;
    private JLabel nameHumanJL;
    private JLabel creditHumanJL;
    private JLabel scoreHumanJL;
    private JLabel stakeHumanJL;
    private JTextField stakeHumanJTF;
    private JButton requestCardJB;
    private JButton declareGoodScoreJB;
    private Font font = new Font("TimesRoman", Font.PLAIN, 20);
    private Dimension dimension = new Dimension(200, 30);
    private final ImageIcon backImage = new ImageIcon(Common.getResourceCards() + "dorso.jpg");
    private int currentIndex;
    private OnLineGameVO onLineGameVO;

    public OnLineGameJIF() {
        super(GUICoreMediator.getGameName(), false, true, false, false);
        int width = 960;
        int height = 640;
        //int xbound = (getContentPane().getWidth() - width) / 2;
        //int ybound = (getContentPane().getHeight() - height) / 2;
        setPreferredSize(new Dimension(width, height));
        //setBounds(xbound, ybound, xbound + width, ybound + height);
        setLayout(new GridBagLayout());
        initComponents();
        initBoard();
        setVisible(true);
        pack();

    }

    private void initComponents() {
        onLineGameVO = GUICoreMediator.requestOnLineGameVO();

        playersCardsImagesList = new ArrayList<List<ImageIcon>>(2);

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

        nameCpuJL = new JLabel(onLineGameVO.getPlayerNameMap().get(Integer.valueOf(0)));
        nameCpuJL.setPreferredSize(dimension);
        nameCpuJL.setForeground(new Color(255, 0, 0));
        nameCpuJL.setFont(font);
        nameCpuJL.setVisible(true);
        playerCpuJP.add(nameCpuJL);

        creditCpuJL = new JLabel();
        creditCpuJL.setPreferredSize(dimension);
        creditCpuJL.setFont(font);
        playerCpuJP.add(creditCpuJL);

        scoreCpuJL = new JLabel();
        scoreCpuJL.setPreferredSize(dimension);
        scoreCpuJL.setFont(font);
        playerCpuJP.add(scoreCpuJL);

        stakeCpuJL = new JLabel();
        stakeCpuJL.setPreferredSize(dimension);
        stakeCpuJL.setFont(font);
        playerCpuJP.add(stakeCpuJL);
        this.add(playerCpuJP, playerCpuGBC);

        actionHumanGBC.insets = new Insets(1, 1, 1, 1);
        actionHumanGBC.weighty = 0;
        actionHumanGBC.weightx = 0;
        actionHumanGBC.anchor = GridBagConstraints.CENTER;
        actionHumanGBC.gridx = 0;
        actionHumanGBC.gridy = 4;

        actionHumanJP = new JPanel(new FlowLayout());

        stakeHumanJTF = new JTextField();
        stakeHumanJTF.setPreferredSize(dimension);
        stakeHumanJTF.setFont(font);
        stakeHumanJTF.setHorizontalAlignment(JTextField.RIGHT);
        stakeHumanJTF.setColumns(10);
        actionHumanJP.add(stakeHumanJTF);

        requestCardJB = new JButton("Chiedi carta");
        requestCardJB.setFont(font);
        requestCardJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                requestCard();
            }
        });
        actionHumanJP.add(requestCardJB);

        declareGoodScoreJB = new JButton("Sto bene");
        declareGoodScoreJB.setFont(font);
        declareGoodScoreJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                declareGoodScore();
            }
        });
        actionHumanJP.add(declareGoodScoreJB);

        this.add(actionHumanJP, actionHumanGBC);

        playerHumanGBC.insets = new Insets(1, 1, 1, 1);
        playerHumanGBC.weighty = 0;
        playerHumanGBC.weightx = 0;
        playerHumanGBC.anchor = GridBagConstraints.CENTER;
        playerHumanGBC.gridx = 0;
        playerHumanGBC.gridy = 5;

        playerHumanJP = new JPanel();

        nameHumanJL = new JLabel(onLineGameVO.getPlayerNameMap().get(Integer.valueOf(1)));
        nameHumanJL.setFont(font);
        nameHumanJL.setPreferredSize(new Dimension(200, 30));
        nameHumanJL.setForeground(new Color(0, 0, 255));
        nameHumanJL.setVisible(true);
        playerHumanJP.add(nameHumanJL);

        creditHumanJL = new JLabel();
        creditHumanJL.setFont(font);
        creditHumanJL.setPreferredSize(new Dimension(200, 30));
        playerHumanJP.add(creditHumanJL);

        scoreHumanJL = new JLabel();
        scoreHumanJL.setFont(font);
        scoreHumanJL.setPreferredSize(new Dimension(200, 30));
        playerHumanJP.add(scoreHumanJL);

        stakeHumanJL = new JLabel();
        stakeHumanJL.setFont(font);
        stakeHumanJL.setPreferredSize(new Dimension(200, 30));
        playerHumanJP.add(stakeHumanJL);

        this.add(playerHumanJP, playerHumanGBC);

        paneCardsGBC.insets = new Insets(1, 1, 1, 1);
        paneCardsGBC.weighty = 0;
        paneCardsGBC.weightx = 0;
        paneCardsGBC.anchor = GridBagConstraints.CENTER;
        paneCardsGBC.gridx = 0;
        playerCardsListJP = new ArrayList<JPanel>(2);
        int j = 1;
        for (int i = 0; i < 2; i++) {
            playerCardsImagesList = onLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i));
            playersCardsImagesList.add(playerCardsImagesList);
            playerCardsListJP.add(initPanelPlayersCards());
            paneCardsGBC.gridy = i + j;
            this.add(playerCardsListJP.get(i), paneCardsGBC);
            j++;
        }
        JPanel tempPane = new JPanel(new FlowLayout());
        tempPane.add(new JLabel());
        tempPane.setPreferredSize(new Dimension(910, 100));
        tempPane.setVisible(true);
        paneCardsGBC.gridy = 2;
        this.add(tempPane, paneCardsGBC);

    }

    //inizializza il pannello del player - carte
    private JPanel initPanelPlayersCards() {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.setPreferredSize(new Dimension(900, 99));
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

    private void initBoard() {
        onLineGameVO = GUICoreMediator.requestOnLineGameVO();
        refreshComponents();
    }

    private void refreshComponents() {
        for (int i = 0; i < onLineGameVO.getPlayerIndexList().size(); i++) {
            //imposto la label credito
            setCreditLabel(i, onLineGameVO.getPlayerCreditMap().get(i));
            //azzero le label icon
            for (int j = 0; j < 14; j++) {
                ((JLabel) playerCardsListJP.get(i).getComponent(j)).setIcon(null);
            }
            //
            for (int j = 0; j < onLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i)).size(); j++) {
                ((JLabel) playerCardsListJP.get(i).getComponent(j)).setIcon(
                        onLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i)).get(j));
            }
            //visualizzo il testo delle label punteggio e puntata
            setStakeLabel(i, onLineGameVO.getPlayerStakeMap().get(Integer.valueOf(i)));
            setScoreLabel(i, onLineGameVO.getPlayerScoreMap().get(Integer.valueOf(i)));
            //seleziono mazziere di turno
            if (onLineGameVO.getPlayerRoleMap().get(Integer.valueOf(i)) == Boolean.TRUE) {
                selectBank(i);
            } else {
                deselectBank(i);
            }

            if (onLineGameVO.getPlayerPlayingMap().get(Integer.valueOf(i)) == Boolean.TRUE) {
                setEnabledActionHumanJP(true);
                currentIndex = i;
            } else {
                setEnabledActionHumanJP(false);
            }

            if (onLineGameVO.getPlayerStatusMap().get(Integer.valueOf(i)) == Boolean.TRUE ||
                    onLineGameVO.getPlayerMaxScoreMap().get(Integer.valueOf(i)) == Boolean.TRUE) {
                ((JLabel) playerCardsListJP.get(i).getComponent(0)).setIcon(
                        onLineGameVO.getPlayerCardsImageMap().get(Integer.valueOf(i)).get(0));
            }
        }//end for iniziale
        if (onLineGameVO.isEndManche()) {
            JOptionPane.showMessageDialog(this, "Questa manche è terminata!!!");
            //TODO: richiamare in qualche modo la scoreboard
            //GUICoreMediator.closeManche();
            if (onLineGameVO.isEndGame()) {
                JOptionPane.showMessageDialog(this, "Questa partita è terminata!!!");
                //GUICoreMediator.closeGame();
                dispose();
            } else {
                initBoard();
            }
        } //end isEndManche
    } //end refresh

    private void setEnabledActionHumanJP(boolean enabled) {
        stakeHumanJTF.setEditable(enabled);
        stakeHumanJTF.setEnabled(enabled);
        declareGoodScoreJB.setVisible(enabled);
        requestCardJB.setVisible(enabled);
    }

    private void requestCard() {
        String value;
        double bet;

        if (stakeHumanJTF.isEnabled()) {
            value = getBet();
            if (!value.equalsIgnoreCase("") && Double.valueOf(value) != 0.00) {
                bet = Double.valueOf(value);
            } else {
                return;
            }
        } else {
            bet = 0.00;
        }
        GUICoreMediator.requestCard(1, bet);
        resetBetJTF();
        onLineGameVO = GUICoreMediator.requestOnLineGameVO();
        refreshComponents();
    }    

    private void declareGoodScore() {
        String value;
        double bet;

        if (stakeHumanJTF.isEnabled()) {
            value = getBet();
            if (!value.equalsIgnoreCase("") && Double.valueOf(value) != 0.00) {
                bet = Double.valueOf(value);
            } else {
                return;
            }
        } else {
            bet = 0.00;
        }

        GUICoreMediator.declareGoodScore(1, bet);
        resetBetJTF();
        onLineGameVO = GUICoreMediator.requestOnLineGameVO();
        refreshComponents();
    }

    private void resetBetJTF() {
        stakeHumanJTF.setText("");
    }

    private String getBet() {
        return stakeHumanJTF.getText();
    }

    //Seleziona l'ex-mazziere di turno
    private void selectBank(int i) {
        if (i == 0) {
            nameCpuJL.setOpaque(true);
            nameCpuJL.setBackground(new Color(255, 153, 0));
        } else {
            nameHumanJL.setOpaque(true);
            nameHumanJL.setBackground(new Color(255, 153, 0));
        }
    //getBetJTF(bankPlayerIndex).setEnabled(true);
    }

    //Deseleziona l'ex-mazziere di turno, che diventa un player normale
    private void deselectBank(int i) {
        if (i == 0) {
            nameCpuJL.setOpaque(false);
            nameCpuJL.setBackground(new Color(212, 208, 200));
        } else {
            nameHumanJL.setOpaque(false);
            nameHumanJL.setBackground(new Color(212, 208, 200));
        }
    //getBetJTF(bankPlayerIndex).setEnabled(true);
    }

    //Imposta il testo della label relativa al credito residuo
    private void setCreditLabel(int i, String credit) {
        if (i == 0) {
            creditCpuJL.setText(credit);
        } else {
            creditHumanJL.setText(credit);
        }
    }

    //Imposta il testo della label relativa alla puntata
    private void setStakeLabel(int i, String stake) {
        if (i == 0) {
            stakeCpuJL.setText(stake);
        } else {
            stakeHumanJL.setText(stake);
        }
    }

    //Imposta il testo della label relativa al punteggio
    private void setScoreLabel(int i, String score) {
        if (i == 0) {
            scoreCpuJL.setText(score);
        } else {
            scoreHumanJL.setText(score);
        }
    }

    private void firstCardCovered(int i) {
        ((JLabel) playerCardsListJP.get(i).getComponent(0)).setIcon(backImage);
    }

    private void firstCardDiscovered(int i) {
        JPanel temp = playerCardsListJP.get(i);
        ImageIcon icon = playersCardsImagesList.get(i).get(0);
        ((JLabel) temp.getComponent(0)).setIcon(icon);
    }
}