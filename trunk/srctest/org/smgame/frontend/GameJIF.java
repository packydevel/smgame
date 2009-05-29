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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.smgame.core.GUICoreMediator;
import org.smgame.util.BetOverflowException;
import org.smgame.util.Common;
import org.smgame.util.PrintErrors;
import org.smgame.util.ScoreOverflowException;

public class GameJIF extends JInternalFrame implements IGameJIF {

    private List<JPanel> playerCardsListJP; //Lista pannelli giocatore-carte
    private List<JLabel> playerNameListJL; //lista label nome giocatori
    private List<JLabel> playerCreditListJL; //lista label credito giocatori;
    private List<JLabel> playerStakeListJL; //lista label puntate giocatori
    private List<JLabel> playerScoreListJL; //lista label punteggio giocatori
    private List<JPanel> playerActionsListJP; //Lista pannelli giocatore-carte
    private List<ImageIcon> playerCardsImagesList = new ArrayList<ImageIcon>();
    private List<List<ImageIcon>> playersCardsImagesList;
    private List<JLabel> playerCardsListJL;
    GridBagConstraints panelGBC, labelGBC, textFieldGBC, buttonGBC;
    private final ImageIcon backImage = new ImageIcon(Common.getResourceCards() + "dorso.gif");
    private int size,  bankPlayerIndex,  currentPlayerIndex;

    /**Costruttore
     *
     */
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
        initBoard();
        pack();
    }

    private void initComponents() {
        List<String> playerNameList = GUICoreMediator.getPlayerNameList();
        List<Boolean> playerTypeList = GUICoreMediator.getPlayerTypeList();
        List<String> playerCreditList = GUICoreMediator.getPlayerCreditList();

        size = playerNameList.size();

        playerCardsListJP = new ArrayList<JPanel>(size);
        playerNameListJL = new ArrayList<JLabel>(size);
        playerCreditListJL = new ArrayList<JLabel>(size);
        playerStakeListJL = new ArrayList<JLabel>(size);
        playerScoreListJL = new ArrayList<JLabel>(size);
        playerActionsListJP = new ArrayList<JPanel>(size);
        playersCardsImagesList = new ArrayList<List<ImageIcon>>(size);

        panelGBC = new GridBagConstraints();
        panelGBC.insets = new Insets(1, 1, 1, 1);
        panelGBC.weighty = 0;
        panelGBC.weightx = 0;
        panelGBC.anchor = GridBagConstraints.NORTHWEST;

        labelGBC = new GridBagConstraints();
        labelGBC.insets = new Insets(1, 1, 1, 1);
        labelGBC.weighty = 0;
        labelGBC.weightx = 0;
        labelGBC.anchor = GridBagConstraints.NORTHWEST;

        for (int i = 0; i < size; i++) {
            playerNameListJL.add(new JLabel(playerNameList.get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i;
            add(playerNameListJL.get(i), labelGBC);

            playerCreditListJL.add(new JLabel("Credito: " + playerCreditList.get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i + 1;
            add(playerCreditListJL.get(i), labelGBC);

            playerCardsImagesList = GUICoreMediator.getPlayerCards(i);
            playersCardsImagesList.add(playerCardsImagesList);
            panelGBC.gridx = 1;
            panelGBC.gridy = 2 * i;
            panelGBC.gridwidth = 1;
            panelGBC.gridheight = 2;
            panelGBC.weightx = 1;
            panelGBC.weighty = (double) 1 / (double) size;
            playerCardsListJP.add(initPanelPlayersCards());
            add(playerCardsListJP.get(i), panelGBC);

            if (playerTypeList.get(i).equals(Boolean.FALSE)) {
                setHumanColor(i);
                playerActionsListJP.add(initPanelActions());
            } else {
                playerActionsListJP.add(null);
            }
            panelGBC.gridx = 2;
            panelGBC.gridy = 2 * i;
            panelGBC.gridwidth = 2;
            panelGBC.gridheight = 1;
            panelGBC.weightx = 0;
            panelGBC.weighty = 0;
            //panelGBC.fill = GridBagConstraints.HORIZONTAL;
            add(playerActionsListJP.get(i), panelGBC);

            playerStakeListJL.add(new JLabel("Puntata: 0,00"));
            labelGBC.gridx = 2;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.NORTHWEST;
            add(playerStakeListJL.get(i), labelGBC);

            playerScoreListJL.add(new JLabel("Punteggio: 0,0"));
            labelGBC.gridx = 3;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.NORTHWEST;
            add(playerScoreListJL.get(i), labelGBC);
        //playerScoreListJL.get(i).setVisible(false);
        }
    } //end initComponents

    //inizializza il pannello del player - carte
    private JPanel initPanelPlayersCards() {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.setPreferredSize(new Dimension(480, 50));
        pane.setBorder(new LineBorder(new Color(212, 208, 200)));
        ((FlowLayout) pane.getLayout()).setHgap(1);
        playerCardsListJL = new ArrayList<JLabel>(14);
        for (int j = 0; j < 14; j++) {
            playerCardsListJL.add(new JLabel());
            playerCardsListJL.get(j).setPreferredSize(new Dimension(32, 49));
            playerCardsListJL.get(j).setBorder(new LineBorder(new Color(212, 208, 200)));
            pane.add(playerCardsListJL.get(j));
        }
        return pane;
    } // initPanelPlayersCards

    //inizializza il pannello delle azioni
    private JPanel initPanelActions() {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.setPreferredSize(new Dimension(300, 25));
        ((FlowLayout) pane.getLayout()).setHgap(2);
        ((FlowLayout) pane.getLayout()).setVgap(2);
        JTextField betJTF = new JTextField();
        betJTF.setPreferredSize(new Dimension(80, 20));
        pane.add(betJTF);

        JButton requestCardJB = new JButton("Chiedi una carta");
        requestCardJB.setPreferredSize(new Dimension(120, 20));
        requestCardJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                requestCard(currentPlayerIndex);
            }
        });
        pane.add(requestCardJB);

        JButton declareGoodScoreJB = new JButton("Sto bene");
        declareGoodScoreJB.setPreferredSize(new Dimension(80, 20));
        declareGoodScoreJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                declareGoodScore(currentPlayerIndex);
            }
        });
        pane.add(declareGoodScoreJB);
        pane.setVisible(false);
        return pane;
    }

    private void initBoard() {
        ImageIcon icon;
        bankPlayerIndex = GUICoreMediator.getBankPlayer();
        selectBank(bankPlayerIndex);
        currentPlayerIndex = GUICoreMediator.nextPlayer();
        playerActionsListJP.get(currentPlayerIndex).setVisible(true);

        for (int i = 0; i < size; i++) {
            setStakeLabel(i, GUICoreMediator.getPlayerStake(bankPlayerIndex));
            setScoreLabel(i, GUICoreMediator.getPlayerScore(bankPlayerIndex));
            icon = GUICoreMediator.getFirstCard(i);
            playersCardsImagesList.get(i).add(icon);
            if (i == currentPlayerIndex) {
                ((JLabel) playerCardsListJP.get(i).getComponent(0)).setIcon(icon);
            } else {
                ((JLabel) playerCardsListJP.get(i).getComponent(0)).setIcon(backImage);
            }
        }
    }

    private void firstCardCovered(int i) {
        JPanel temp = playerCardsListJP.get(i);
        System.out.println(i);
        ((JLabel) temp.getComponent(0)).setIcon(backImage);
    }

    private void firstCardDiscovered(int i) {
        ImageIcon icon;
        JPanel temp = playerCardsListJP.get(i);
        icon = playersCardsImagesList.get(i).get(0);
        ((JLabel) temp.getComponent(0)).setIcon(icon);
    }
    //Seleziona/evidenzia il mazziere di turno

    private void selectBank(int i) {
        ((JLabel) playerNameListJL.get(i)).setOpaque(true);
        ((JLabel) playerNameListJL.get(i)).setBackground(new Color(255, 153, 0));
    }

    //Deseleziona l'ex-mazziere di turno, che diventa un player normale
    private void deselectBank(int i) {
        ((JLabel) playerNameListJL.get(i)).setOpaque(false);
        ((JLabel) playerNameListJL.get(i)).setBackground(new Color(212, 208, 200));
    }

    //Imposta il colore del testo del giocatore umano, per differenziarlo dal giocatore CPU
    private void setHumanColor(int i) {
        ((JLabel) playerNameListJL.get(i)).setForeground(new Color(0, 0, 255));
    }

    //Imposta il testo della label relativa al credito residuo
    private void setCreditLabel(int i, String credit) {
        ((JLabel) playerCreditListJL.get(i)).setText("Credito: " + credit);
    }

    //Imposta il testo della label relativa alla puntata
    private void setStakeLabel(int i, String stake) {
        ((JLabel) playerStakeListJL.get(i)).setText("Puntata: " + stake);
    }

    //Imposta il testo della label relativa al punteggio
    private void setScoreLabel(int i, String score) {
        ((JLabel) playerScoreListJL.get(i)).setText("Punteggio: " + score);
    }

    //preleva il valore della puntata
    private String getBetJTF(int i) {
        return ((JTextField) playerActionsListJP.get(i).getComponent(0)).getText();
    }

    //imposta la cartaGUI
    private void setCardImage(int i, ImageIcon icon) {
        int size;
        playersCardsImagesList.get(i).add(icon);
        size = playersCardsImagesList.get(i).size();
        ((JLabel) playerCardsListJP.get(i).getComponent(size - 1)).setIcon(icon);
    }

    //esegue le azioni di richiesta carta
    private void requestCard(int i) {
        String value = getBetJTF(i);
        ImageIcon icon;
        if ((value != null) && (!value.equalsIgnoreCase(""))) {
            try {
                double bet = Double.valueOf(value);
                setCardImage(i, GUICoreMediator.requestCard(i, bet));
                setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
            } catch (BetOverflowException boe) {
                PrintErrors.exception(boe);
            } catch (ScoreOverflowException soe) {
                setCardImage(i, soe.getCardException().getFrontImage());
                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
                setCreditLabel(bankPlayerIndex, GUICoreMediator.getPlayerCredit(bankPlayerIndex));
                firstCardCovered(i);
                playerActionsListJP.get(i).setVisible(false);
                PrintErrors.exception(soe);
                if (!GUICoreMediator.isEndManche(currentPlayerIndex)) {
                    currentPlayerIndex = GUICoreMediator.nextPlayer();
                    firstCardDiscovered(currentPlayerIndex);
                    playerActionsListJP.get(currentPlayerIndex).setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } //end request card

    private void declareGoodScore(int i) {
        String value = getBetJTF(i);
        double bet;

        if ((value != null) && (!value.equalsIgnoreCase(""))) {
            bet = Double.valueOf(value);
            try {
                GUICoreMediator.declareGoodScore(i, bet);
                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
                firstCardCovered(i);
                playerActionsListJP.get(i).setVisible(false);
                firstCardDiscovered(currentPlayerIndex);
                if (!GUICoreMediator.isEndManche(currentPlayerIndex)) {
                    currentPlayerIndex = GUICoreMediator.nextPlayer();
                    firstCardDiscovered(currentPlayerIndex);
                    playerActionsListJP.get(currentPlayerIndex).setVisible(true);
                    firstCardCovered(i);
                    playerActionsListJP.get(i).setVisible(false);
                } else {
                }
            } catch (BetOverflowException boe) {
                PrintErrors.exception(boe);
            }
        }
    }
}//end gameJIF