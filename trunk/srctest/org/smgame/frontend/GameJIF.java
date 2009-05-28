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
    //private List<List<JLabel>> playersCardsListJL;
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

        GridBagConstraints panelGBC = initGridBagC();
        panelGBC.gridheight = 2;
        panelGBC.weighty = (double) 1 / (double) size;
        panelGBC.weightx = 1;

        GridBagConstraints labelGBC = initGridBagC();
        labelGBC.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints actionsGBC = initGridBagC();
        actionsGBC.gridwidth = 2;
        actionsGBC.gridx = 2;

        for (int i = 0; i < size; i++) {
            playerNameListJL.add(new JLabel(playerNameList.get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i;
            add(playerNameListJL.get(i), labelGBC);

            playerCreditListJL.add(new JLabel("Credito: " + playerCreditList.get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i + 1;
            add(playerCreditListJL.get(i), labelGBC);

            if (playerTypeList.get(i).equals(Boolean.FALSE)) {
                setHumanColor(i);
                actionsGBC.gridy = 2 * i;
                playerActionsListJP.add(initPanelActions());
            } else {
                playerActionsListJP.add(null);
            }
            add(playerActionsListJP.get(i), actionsGBC);

            playerStakeListJL.add(new JLabel("Puntata: 0,00"));
            labelGBC.gridx = 2;
            labelGBC.gridy = 2 * i + 1;
            add(playerStakeListJL.get(i), labelGBC);

            playerScoreListJL.add(new JLabel("Punteggio: 0.0"));
            labelGBC.gridx = 3;
            labelGBC.gridy = 2 * i + 1;
            add(playerScoreListJL.get(i), labelGBC);
            //playerScoreListJL.get(i).setVisible(false);

            panelGBC.gridx = 1;
            panelGBC.gridy = 2 * i;
            playerCardsImagesList = GUICoreMediator.getPlayerCards(i);
            playersCardsImagesList.add(playerCardsImagesList);

            playerCardsListJP.add(initPanelPlayersCards());
            add(playerCardsListJP.get(i), panelGBC);
        }
    } //end initComponents

    //inizializza il pannello del player - carte
    private JPanel initPanelPlayersCards() {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.setPreferredSize(new Dimension(500, 50));
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
        JTextField betJTF = new JTextField();
        betJTF.setPreferredSize(new Dimension(50, 20));
        pane.add(betJTF);

        JButton requestCardJB = new JButton("Chiedi una carta");
        requestCardJB.setPreferredSize(new Dimension(120, 20));
        requestCardJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                //System.out.println(evt.getActionCommand());
                //requestCard(searchJButton(evt, 1));
                requestCard(currentPlayerIndex);
            }
        });
        pane.add(requestCardJB);

        JButton declareGoodScoreJB = new JButton("Sto bene");
        declareGoodScoreJB.setPreferredSize(new Dimension(80, 20));
        declareGoodScoreJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                //System.out.println(evt.getActionCommand());
                //declareGoodScore(searchJButton(evt, 2));
                declareGoodScore(currentPlayerIndex);
            }
        });
        pane.add(declareGoodScoreJB);
        pane.setVisible(false);
        return pane;
    }

    //inizializza il tavolo
    private void initBoard() {
        //TODO
        bankPlayerIndex = GUICoreMediator.getBankPlayer();
        selectBank(bankPlayerIndex);
        //pos = ++pos % size;
        for (int i = 0; i < size; i++) {
            //setFirstCard(pos, GUICoreMediator.getFirstCard(pos));
            setStakeLabel(i, GUICoreMediator.getPlayerStake(bankPlayerIndex));
            setScoreLabel(i, GUICoreMediator.getPlayerScore(bankPlayerIndex));
            ((JLabel) playerCardsListJP.get(i).getComponent(0)).setIcon(backImage);
        //this.validate();
        }
        currentPlayerIndex = GUICoreMediator.nextPlayer();
        playerActionsListJP.get(currentPlayerIndex).setVisible(true);
    //playerScoreListJL.get(bankPlayerIndex).setVisible(true);
    //    firstCardDiscovered(bankPlayerIndex);
    }

    //inizializza il gridbagconstraints per tutti per la parte in comune
    private GridBagConstraints initGridBagC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        return gbc;
    }
    //imposta la prima carta compreso dorso
/*
    private void setFirstCard(int i, ImageIcon[] icon) {
    playerCardsImagesList.add(icon[1]);
    playersCardsImagesList.add(cardImagesList);
    firstCardCovered(i);
    }
     */

    private void firstCardCovered(int i) {
        JPanel temp = playerCardsListJP.get(i);
        System.out.println(i);
        ((JLabel) temp.getComponent(0)).setIcon(backImage);
    //pack();
    }

    private void firstCardDiscovered(int i) {
        JPanel temp = playerCardsListJP.get(i);
        ((JLabel) temp.getComponent(0)).setIcon(null);
    //pack();
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

    //cerca il bottone nel pannello
    private int searchJButton(ActionEvent evt, int c) {
        JButton temp = (JButton) evt.getSource();
        int i = -1;
        for (int j = 0; j < playerActionsListJP.size(); j++) {
            if (((JPanel) playerActionsListJP.get(j)).getComponent(c).equals(temp)) {
                i = j;
                break;
            }
        }
        System.out.println(i);
        return i;
    }

    //imposta la cartaGUI
    private void setCardImage(int i, ImageIcon icon) {
        int size = playersCardsImagesList.get(i).size();
        //JPanel temp = playerCardsJP.get(i);
        //int size = playerCardsImagesList.get(i).size();
        playersCardsImagesList.get(i).add(icon);
        ((JLabel) playerCardsListJP.get(i).getComponent(++size)).setIcon(icon);
    //playerCardsImagesList.get(i).add(icon);
    }

    //esegue le azioni di richiesta carta
    private void requestCard(int i) {
        String value = getBetJTF(i);
        if ((value != null) && (!value.equalsIgnoreCase(""))) {
            try {
                double bet = Double.valueOf(value);
                //richiamo il mediatore e aggiungo la nuova carta sul tavolo
                setCardImage(i, GUICoreMediator.requestCard(i, bet));
                //aggiorna il valore delle puntate del player
                setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
                //aggiorna il punteggio delle carte
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                //aggiorna il credito residuo del player
                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
            } catch (BetOverflowException boe) {
                PrintErrors.exception(boe);
            } catch (ScoreOverflowException soe) {
                //prendo l'immagine della carta sbagliata
                setCardImage(i, soe.getCardException().getFrontImage());
                //aggiorno il credito residuo del giocatore sballante
                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
                //aggiorno il punteggio che peraltro è overflow
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                //aggiorno la scommessa totale che peraltro è overflow
                setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
                //aggiorno il credito della mazziere
                setCreditLabel(bankPlayerIndex, GUICoreMediator.getPlayerCredit(bankPlayerIndex));
                //ottengo la posizione del successivo player
                //pannello delle azioni visibile x il giocatore che sta lasciando la mano
                playerActionsListJP.get(i).setVisible(false);
                //richiamo la jdialog x l'eccezione
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
        //System.out.println("entered");
        if ((value != null) && (!value.equalsIgnoreCase(""))) {
            //System.out.println("if");
            bet = Double.valueOf(value);
            try {
                GUICoreMediator.declareGoodScore(i, bet);

                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
                //System.out.println("stake");
                firstCardCovered(i);
                //System.out.println("credit");
                playerActionsListJP.get(i).setVisible(false);
                firstCardDiscovered(currentPlayerIndex);
                if (!GUICoreMediator.isEndManche(currentPlayerIndex)) {
                    currentPlayerIndex = GUICoreMediator.nextPlayer();
                    playerActionsListJP.get(currentPlayerIndex).setVisible(true);
                }
            } catch (BetOverflowException boe) {
                PrintErrors.exception(boe);
            }
        }
    }
}//end gameJIF