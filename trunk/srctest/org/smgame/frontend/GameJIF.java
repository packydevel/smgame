package org.smgame.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
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
import org.smgame.util.BetOverflowException;
import org.smgame.util.Common;
import org.smgame.util.PrintErrors;
import org.smgame.util.ScoreOverflowException;

public class GameJIF extends JInternalFrame implements IGameJIF {

    private List<JPanel> playerCardsListJP; //Lista pannelli giocatore-carte
    private List<JLabel> playerNameListJL; //lista label nome giocatori
    private List<JLabel> playerCreditListJL; //lista label credito giocatori;
    private List<String> playerCreditList;
    private List<JLabel> playerStakeListJL; //lista label puntate giocatori
    private List<JLabel> playerScoreListJL; //lista label punteggio giocatori
    private List<JPanel> playerActionsListJP; //Lista pannelli giocatore-carte
    private List<ImageIcon> playerCardsImagesList = new ArrayList<ImageIcon>();
    private List<List<ImageIcon>> playersCardsImagesList;
    private List<JLabel> playerCardsListJL;
    GridBagConstraints panelGBC, labelGBC, textFieldGBC, buttonGBC;
    private final ImageIcon backImage = scaledImage(new ImageIcon(Common.getResourceCards() + "dorso.jpg"));
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
        playerCreditList = GUICoreMediator.getPlayerCreditList();

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
            hideActionPanelContent(i);
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
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pane.setPreferredSize(new Dimension(480, 53));
        pane.setBorder(new LineBorder(new Color(212, 208, 200)));
        ((FlowLayout) pane.getLayout()).setVgap(1);
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
        pane.setBorder(new LineBorder(new Color(212, 208, 200)));
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
        pane.setVisible(true);
        return pane;
    }

    private void initBoard() {
        ImageIcon icon;
        deselectBank(bankPlayerIndex);
        bankPlayerIndex = GUICoreMediator.getBankPlayer();
        selectBank(bankPlayerIndex);

        currentPlayerIndex = GUICoreMediator.nextPlayer();

        for (int i = 0; i < size; i++) {
            icon = scaledImage(GUICoreMediator.getFirstCard(i));
            setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
            playersCardsImagesList.get(i).add(icon);
            if (i == currentPlayerIndex) {
                ((JLabel) playerCardsListJP.get(i).getComponent(0)).setIcon(icon);
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                showActionPanelContent(currentPlayerIndex);
            } else {
                ((JLabel) playerCardsListJP.get(i).getComponent(0)).setIcon(backImage);
                setScoreLabel(i, "");
            }
        }
    }

    private void showActionPanelContent(int playerIndex) {
        for (int i = 0; i < playerActionsListJP.get(playerIndex).getComponentCount(); i++) {
            playerActionsListJP.get(playerIndex).getComponent(i).setVisible(true);
        }
    }

    private void hideActionPanelContent(int playerIndex) {
        for (int i = 0; i < playerActionsListJP.get(playerIndex).getComponentCount(); i++) {
            playerActionsListJP.get(playerIndex).getComponent(i).setVisible(false);
        }
    }

    private void firstCardCovered(int i) {
        JPanel temp = playerCardsListJP.get(i);
        ((JLabel) temp.getComponent(0)).setIcon(backImage);
    }

    private void firstCardDiscovered(int i) {
        JPanel temp = playerCardsListJP.get(i);
        ImageIcon icon = scaledImage(playersCardsImagesList.get(i).get(0));
        ((JLabel) temp.getComponent(0)).setIcon(icon);
    }

    //Seleziona/evidenzia il mazziere di turno
    private void selectBank(int i) {
        ((JLabel) playerNameListJL.get(i)).setOpaque(true);
        ((JLabel) playerNameListJL.get(i)).setBackground(new Color(255, 153, 0));
        getBetJTF(bankPlayerIndex).setEnabled(false);

    }

    //Deseleziona l'ex-mazziere di turno, che diventa un player normale
    private void deselectBank(int i) {
        ((JLabel) playerNameListJL.get(i)).setOpaque(false);
        ((JLabel) playerNameListJL.get(i)).setBackground(new Color(212, 208, 200));
        getBetJTF(bankPlayerIndex).setEnabled(true);
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

    private JTextField getBetJTF(int i) {
        return ((JTextField) playerActionsListJP.get(i).getComponent(0));
    }

    //preleva il valore della puntata
    private String getBet(int i) {
        return ((JTextField) playerActionsListJP.get(i).getComponent(0)).getText();
    }

    private void resetBetJTF(int i) {
        ((JTextField) playerActionsListJP.get(i).getComponent(0)).setText("0");
    }

    //imposta la cartaGUI
    private void setCardImage(int i, ImageIcon icon) {
        int size;
        playersCardsImagesList.get(i).add(icon);
        size = playersCardsImagesList.get(i).size();
        ((JLabel) playerCardsListJP.get(i).getComponent(size - 1)).setIcon(scaledImage(icon));
    }

    //esegue le azioni di richiesta carta
    private void requestCard(int i) {
        String value = getBet(i);
        double bet;
        resetBetJTF(i);
        if ((!value.equalsIgnoreCase("")) || ((value.equalsIgnoreCase("")) && (i == bankPlayerIndex))) {
            try {
                bet = (value.equalsIgnoreCase("") ? 0.00 : Double.valueOf(value));
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
                hideActionPanelContent(i);
                PrintErrors.exception(soe);
                checkEndManche();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } //end request card

    private void declareGoodScore(int i) {
        String value = getBet(i);
        double bet;

        if ((!value.equalsIgnoreCase("")) || ((value.equalsIgnoreCase("")) && (i == bankPlayerIndex))) {
            bet = (value.equalsIgnoreCase("") ? 0.00 : Double.valueOf(value));
            try {
                GUICoreMediator.declareGoodScore(i, bet);
                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
                setStakeLabel(i, GUICoreMediator.getPlayerStake(i));

                if (GUICoreMediator.isMaxScore(i)) {
                    setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                    firstCardDiscovered(i);
                } else {
                    setScoreLabel(i, "");
                    firstCardCovered(i);
                }

                hideActionPanelContent(i);
                checkEndManche();
            } catch (BetOverflowException boe) {
                PrintErrors.exception(boe);
            }
        }
    }

    private void checkEndManche() {
        if (!GUICoreMediator.isEndManche()) {
            currentPlayerIndex = GUICoreMediator.nextPlayer();
            firstCardDiscovered(currentPlayerIndex);
            setScoreLabel(currentPlayerIndex, GUICoreMediator.getPlayerScore(currentPlayerIndex));
            showActionPanelContent(currentPlayerIndex);
        } else {
            for (int i = 0; i < size; i++) {
                firstCardDiscovered(i);
                playerCreditList = GUICoreMediator.getPlayerCreditList();
                playerCreditListJL.get(i).setText("Credito: " + playerCreditList.get(i));
            }

            JOptionPane.showMessageDialog(null, "Questa manche è terminata!!!");

            if (!GUICoreMediator.isEndGame()) {
                for (int i = 0; i < size; i++) {
                    playersCardsImagesList.get(i).clear();
                    for (int j = 0; j < 14; j++) {
                        ((JLabel) playerCardsListJP.get(i).getComponent(j)).setIcon(null);
                    }
                }

                initBoard();
            } else {
                JOptionPane.showMessageDialog(null, "Questa partita è terminata!!!");
                dispose();
            }
        }
    }

    //Resizes an image using a Graphics2D object backed by a BufferedImage.
    private ImageIcon scaledImage(ImageIcon srcImg){
        int w = 32;
        int h = 49;
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg.getImage(), 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
}//end gameJIF