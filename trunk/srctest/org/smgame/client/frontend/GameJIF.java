package org.smgame.client.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.smgame.client.ClientProxy;
import org.smgame.util.Logging;

public class GameJIF extends JInternalFrame implements IGameJIF {

    private ArrayList<Integer> playerList;
    private HashMap<Integer, JPanel> playerNameMapJP;
    private HashMap<Integer, JPanel> playerCardsMapJP; //Lista pannelli giocatore-carte
    private HashMap<Integer, JLabel> playerStakeMapJL; //lista label puntate giocatori
    private HashMap<Integer, JLabel> playerScoreMapJL; //lista label punteggio giocatori
    private HashMap<Integer, JLabel> playerStatusMapJL;
    private HashMap<Integer, JPanel> playerActionMapJP; //Lista pannelli giocatore-carte
    private List<JLabel> playerCardsListJL;
    private GameVO gameVO;
    private GridBagConstraints panelGBC,  labelGBC,  textFieldGBC,  buttonGBC;
    private int size,  currentIndex;
    private BetInputVerifier betInputVerifier = new BetInputVerifier();
    private DecimalFormat betValueFormatter;

    class BetInputVerifier extends InputVerifier implements KeyListener {

        BetInputVerifier() {
            super();
            betValueFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
            betValueFormatter.applyPattern("#0.00");
            betValueFormatter.setMaximumFractionDigits(2);
            betValueFormatter.setMinimumFractionDigits(2);
        }

        public boolean shouldYieldFocus(JComponent input) {
            return verify(input);
        }

        //This method checks input, but should cause no side effects.
        public boolean verify(JComponent input) {
            String betText;
            JTextField betJTF = ((JTextField) input);
            JButton requestCardJB = new JButton(), declareGoodScoreJB = new JButton();

            betText = betJTF.getText();

            for (JPanel p : playerActionMapJP.values()) {
                if (p.getComponent(0).equals(betJTF)) {
                    requestCardJB = (JButton) p.getComponent(1);
                    declareGoodScoreJB = (JButton) p.getComponent(2);
                }
            }

            try {
                if ((betValueFormatter.format(betValueFormatter.parse(betText).doubleValue())).equals(betText)) {
                    requestCardJB.setEnabled(true);
                    declareGoodScoreJB.setEnabled(true);
                    betJTF.setText(betValueFormatter.format(betValueFormatter.parse(betText).doubleValue()));
                }

            } catch (ParseException pe) {
                requestCardJB.setEnabled(false);
                declareGoodScoreJB.setEnabled(false);
                return false;
            }

            return true;
        }

        public void keyPressed(KeyEvent e) {
            JTextField source = (JTextField) e.getSource();
            shouldYieldFocus(source); //ignore return value
        }

        public void keyReleased(KeyEvent e) {
            JTextField source = (JTextField) e.getSource();
            shouldYieldFocus(source); //ignore return value
        }

        public void keyTyped(KeyEvent e) {
            JTextField source = (JTextField) e.getSource();
            shouldYieldFocus(source); //ignore return value
        }
    }

    /**Costruttore
     *
     */
    public GameJIF() {
        super(null, false, true, false, false);
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridBagLayout());
        initComponents();
        initBoard();
        pack();
    }

    private void initComponents() {
        gameVO = ClientProxy.getInstance().requestGameVO();
        playerList = gameVO.getPlayerIndexList();

        size = playerList.size();
        System.out.println(gameVO.getPlayerNameMap());
        System.out.println(gameVO.getPlayerTypeMap());

        playerNameMapJP = new HashMap<Integer, JPanel>(size);
        playerCardsMapJP = new HashMap<Integer, JPanel>(size);
        playerActionMapJP = new HashMap<Integer, JPanel>(size);
        playerStakeMapJL = new HashMap<Integer, JLabel>(size);
        playerScoreMapJL = new HashMap<Integer, JLabel>(size);
        playerStatusMapJL = new HashMap<Integer, JLabel>(size);

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
            playerNameMapJP.put(i, new JPanel(new GridLayout(1, 1), false));
            playerNameMapJP.get(i).setPreferredSize(new Dimension(140, 40));
            playerNameMapJP.get(i).setBorder(BorderFactory.createTitledBorder(
                    new LineBorder(Color.BLACK), gameVO.getPlayerNameMap().get(i)));
            playerNameMapJP.get(i).add(new JLabel(gameVO.getPlayerCreditMap().get(i)));
            panelGBC.gridx = 0;
            panelGBC.gridy = 2 * i;
            panelGBC.gridwidth = 1;
            panelGBC.gridheight = 2;
            panelGBC.weightx = 0;
            panelGBC.weighty = (double) 1 / (double) size;
            panelGBC.anchor = GridBagConstraints.NORTH;
            add(playerNameMapJP.get(i), panelGBC);

            playerCardsMapJP.put(i, initPanelPlayersCards());
            panelGBC.gridx = 1;
            panelGBC.gridy = 2 * i;
            panelGBC.gridwidth = 1;
            panelGBC.gridheight = 2;
            panelGBC.weightx = 1;
            panelGBC.weighty = 0;
            panelGBC.anchor = GridBagConstraints.NORTH;
            add(playerCardsMapJP.get(i), panelGBC);

            if (gameVO.getPlayerTypeMap().get(i) == false) {
                setPlayerColor(i, Color.RED);
            } else {
                setPlayerColor(i, Color.BLUE);
            }

            playerActionMapJP.put(i, initPanelActions());
            hideActionPanelContent(i);
            panelGBC.gridx = 2;
            panelGBC.gridy = 2 * i;
            panelGBC.gridwidth = 3;
            panelGBC.gridheight = 1;
            panelGBC.weightx = 0;
            panelGBC.weighty = 0;
            panelGBC.anchor = GridBagConstraints.WEST;
            add(playerActionMapJP.get(i), panelGBC);

            playerStakeMapJL.put(i, new JLabel(gameVO.getPlayerStakeMap().get(i)));
            playerStakeMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 2;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.NORTH;
            add(playerStakeMapJL.get(i), labelGBC);

            playerScoreMapJL.put(i, new JLabel(gameVO.getPlayerScoreMap().get(i)));
            playerScoreMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 3;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.NORTH;
            add(playerScoreMapJL.get(i), labelGBC);

            playerStatusMapJL.put(i, new JLabel(gameVO.getPlayerStatusMap().get(i)));
            playerStatusMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 4;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.NORTH;
            add(playerStatusMapJL.get(i), labelGBC);
        }

    } //end initComponents

    //inizializza il pannello del player - carte
    private JPanel initPanelPlayersCards() {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pane.setPreferredSize(new Dimension(480, 53));
        pane.setBorder(new LineBorder(Color.BLACK));
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
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pane.setPreferredSize(new Dimension(300, 28));
        pane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        ((FlowLayout) pane.getLayout()).setHgap(2);
        ((FlowLayout) pane.getLayout()).setVgap(2);
        JTextField betJTF = new JTextField();
        betJTF.setPreferredSize(new Dimension(80, 20));
        betJTF.setInputVerifier(betInputVerifier);
        betJTF.addKeyListener(betInputVerifier);
        pane.add(betJTF);

        JButton requestCardJB = new JButton("Chiedi una carta");
        requestCardJB.setPreferredSize(new Dimension(120, 20));
        requestCardJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                requestCard(currentIndex);
            }
        });
        pane.add(requestCardJB);

        JButton declareGoodScoreJB = new JButton("Sto bene");
        declareGoodScoreJB.setPreferredSize(new Dimension(80, 20));
        declareGoodScoreJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                declareGoodScore(currentIndex);
            }
        });
        pane.add(declareGoodScoreJB);
        pane.setVisible(true);
        return pane;
    }

    private void initBoard() {
        refreshComponent();
    }

    private void showActionPanelContent(int playerIndex) {
        for (int i = 0; i < playerActionMapJP.get(playerIndex).getComponentCount(); i++) {
            playerActionMapJP.get(playerIndex).getComponent(i).setVisible(true);
        }

    }

    private void hideActionPanelContent(int playerIndex) {
        for (int i = 0; i < playerActionMapJP.get(playerIndex).getComponentCount(); i++) {
            playerActionMapJP.get(playerIndex).getComponent(i).setVisible(false);
        }

    }

    //Seleziona/evidenzia il mazziere di turno
    private void selectBank(int i) {
        ((TitledBorder) playerNameMapJP.get(i).getBorder()).setBorder(new LineBorder(Color.ORANGE));
        getBetJTF(i).setEnabled(false);

    }

//Deseleziona l'ex-mazziere di turno, che diventa un player normale
    private void deselectBank(int i) {
        ((TitledBorder) playerNameMapJP.get(i).getBorder()).setBorder(new LineBorder(Color.BLACK));
        getBetJTF(i).setEnabled(true);
    }

    /**Imposta il colore del testo del giocatore umano, per differenziarlo dal giocatore CPU
     *
     * @param i posizione del giocatore nell'hashmap
     */
    private void setPlayerColor(int i, Color color) {
        ((TitledBorder) playerNameMapJP.get(i).getBorder()).setTitleColor(color);
    }

    private JTextField getBetJTF(int i) {
        return ((JTextField) playerActionMapJP.get(i).getComponent(0));
    }

    private JButton getRequestCardJB(int i) {
        return ((JButton) playerActionMapJP.get(i).getComponent(1));
    }

    private JButton getDeclareGoodScoreJB(int i) {
        return ((JButton) playerActionMapJP.get(i).getComponent(2));
    }

//preleva il valore della puntata
    private String getBet(int i) {
        return ((JTextField) playerActionMapJP.get(i).getComponent(0)).getText();
    }

    private void resetBetJTF(int i) {
        ((JTextField) playerActionMapJP.get(i).getComponent(0)).setText("");
    }

    private void requestCard(int i) {
        double bet = 0.00;

        if (getBetJTF(i).isEnabled()) {
            try {
                bet = betValueFormatter.parse(getBet(i)).doubleValue();
            } catch (Exception e) {
                Logging.logExceptionSevere(e);
            }
        }

        ClientProxy.getInstance().requestCard(i, bet);
        resetBetJTF(i);
        refreshComponent();

    } //end request card

    private void declareGoodScore(int i) {
        double bet = 0.00;

        if (getBetJTF(i).isEnabled()) {
            try {
                bet = betValueFormatter.parse(getBet(i)).doubleValue();
            } catch (Exception e) {
                Logging.logExceptionSevere(e);
            }
        }

        ClientProxy.getInstance().declareGoodScore(i, bet);
        resetBetJTF(i);
        refreshComponent();
    }

    private void refreshComponent() {
        int bank = -1;

        gameVO = ClientProxy.getInstance().requestGameVO();

        setTitle(ClientProxy.getInstance().getGameTitle() + gameVO.getCurrentManche());
        Object[][] dataReport = ClientProxy.getInstance().requestDataReport();

        for (int i = 0; i < gameVO.getPlayerIndexList().size(); i++) {
            ((JLabel) playerNameMapJP.get(i).getComponent(0)).setText(gameVO.getPlayerCreditMap().get(i));

            for (int j = 0; j < 14; j++) {
                ((JLabel) playerCardsMapJP.get(i).getComponent(j)).setIcon(null);
            }

            for (int j = 0; j < gameVO.getPlayerCardsImageMap().get(i).size(); j++) {
                ((JLabel) playerCardsMapJP.get(i).getComponent(j)).setIcon(gameVO.getPlayerCardsImageMap().get(i).get(j));
            }

            playerStakeMapJL.get(i).setText(gameVO.getPlayerStakeMap().get(i));
            playerScoreMapJL.get(i).setText(gameVO.getPlayerScoreMap().get(i));
            playerStatusMapJL.get(i).setText(gameVO.getPlayerStatusMap().get(i));

            if (gameVO.getPlayerTypeMap().get(i) == false) {
                setPlayerColor(i, Color.RED);
            } else {
                setPlayerColor(i, Color.BLUE);
            }

            if (gameVO.getPlayerRoleMap().get(i) == true) {
                bank = i;
                selectBank(i);
                System.out.println("Ti riconosco come mazziere:" + i);
            } else {
                deselectBank(i);
                System.out.println("Non ti riconosco come mazziere:" + i);
            }

            if (gameVO.getPlayerPlayingMap().get(i) == true) {
                showActionPanelContent(i);
                currentIndex = i;
            } else {
                hideActionPanelContent(i);
            }

            getBetJTF(i).setEnabled(gameVO.getPlayerRequestBetMap().get(i));
            getRequestCardJB(i).setEnabled(!gameVO.getPlayerRequestBetMap().get(i));
            getDeclareGoodScoreJB(i).setEnabled(!gameVO.getPlayerRequestBetMap().get(i));
        } //end for

        if (gameVO.isEndManche()) {
            gameVO.getPlayerRoleMap();

            JOptionPane.showMessageDialog(this,
                    new ScoreBoardJP("Terminata Manche n° " + gameVO.getCurrentManche(), dataReport, bank), "Score Board",
                    JOptionPane.INFORMATION_MESSAGE);

            if (gameVO.isEndGame()) {
                JOptionPane.showMessageDialog(this, "Questa partita è terminata!!!");
                dispose();

            } else {
                initBoard();
            }

        }
    }
}//end gameJIF