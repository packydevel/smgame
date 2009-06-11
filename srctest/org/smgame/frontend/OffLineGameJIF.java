package org.smgame.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import org.smgame.core.GUICoreMediator;
import org.smgame.util.Logging;

public class OffLineGameJIF extends JInternalFrame implements IGameJIF {

    private ArrayList<Integer> playerList;
    private HashMap<Integer, JPanel> playerNameMapJP;
    private HashMap<Integer, JLabel> playerCreditMapJL; //lista label credito giocatori;
    private HashMap<Integer, JPanel> playerCardsMapJP; //Lista pannelli giocatore-carte
    private HashMap<Integer, JLabel> playerStakeMapJL; //lista label puntate giocatori
    private HashMap<Integer, JLabel> playerScoreMapJL; //lista label punteggio giocatori
    private HashMap<Integer, JLabel> playerStatusMapJL;
    private HashMap<Integer, JPanel> playerActionMapJP; //Lista pannelli giocatore-carte
    private List<JLabel> playerCardsListJL;
    private OffLineGameVO offLineGameVO;
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
    public OffLineGameJIF() {
        super(null, false, true, false, false);

        setLayout(new GridBagLayout());
        initComponents();
        initBoard();
        pack();
    }

    private void initComponents() {
        offLineGameVO = GUICoreMediator.requestOffLineGameVO();

        setTitle(GUICoreMediator.getGameTitle() + offLineGameVO.getCurrentManche());

        playerList = offLineGameVO.getPlayerIndexList();

        size = playerList.size();
        System.out.println(offLineGameVO.getPlayerNameMap());
        playerNameMapJP = new HashMap<Integer, JPanel>(size);
        playerCreditMapJL = new HashMap<Integer, JLabel>(size);
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

        labelGBC =
                new GridBagConstraints();
        labelGBC.insets = new Insets(1, 1, 1, 1);
        labelGBC.weighty = 0;
        labelGBC.weightx = 0;
        labelGBC.anchor = GridBagConstraints.NORTHWEST;

        for (int i = 0; i < size; i++) {
            playerNameMapJP.put(i, new JPanel(new GridLayout(1, 1), false));
            playerNameMapJP.get(i).setPreferredSize(new Dimension(140, 40));
            playerNameMapJP.get(i).setBorder(BorderFactory.createTitledBorder(offLineGameVO.getPlayerNameMap().get(i)));
            playerNameMapJP.get(i).add(new JLabel(offLineGameVO.getPlayerCreditMap().get(i)));
            panelGBC.gridx = 0;
            panelGBC.gridy = 2 * i;
            panelGBC.gridheight = 2;
            panelGBC.anchor = GridBagConstraints.WEST;
            add(playerNameMapJP.get(i), panelGBC);

            playerCreditMapJL.put(i, new JLabel(offLineGameVO.getPlayerCreditMap().get(i)));

            panelGBC.gridx = 1;
            panelGBC.gridy = 2 * i;
            panelGBC.gridwidth = 1;
            panelGBC.gridheight = 2;
            panelGBC.weightx = 1;
            panelGBC.weighty = (double) 1 / (double) size;
            panelGBC.anchor = GridBagConstraints.WEST;
            playerCardsMapJP.put(i, initPanelPlayersCards());
            add(playerCardsMapJP.get(i), panelGBC);

            if (offLineGameVO.getPlayerTypeMap().get(i) == false) {
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

            playerStakeMapJL.put(i, new JLabel(offLineGameVO.getPlayerStakeMap().get(i)));
            playerStakeMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 2;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.WEST;
            add(playerStakeMapJL.get(i), labelGBC);

            playerScoreMapJL.put(i, new JLabel(offLineGameVO.getPlayerScoreMap().get(i)));
            playerScoreMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 3;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.WEST;
            add(playerScoreMapJL.get(i), labelGBC);

            playerStatusMapJL.put(i, new JLabel(offLineGameVO.getPlayerStatusMap().get(i)));
            playerStatusMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 4;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.WEST;
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
        playerCardsListJL =
                new ArrayList<JLabel>(14);
        for (int j = 0; j <
                14; j++) {
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
        offLineGameVO = GUICoreMediator.requestOffLineGameVO();
        refreshComponent();

    }

    private void showActionPanelContent(int playerIndex) {
        for (int i = 0; i <
                playerActionMapJP.get(playerIndex).getComponentCount(); i++) {
            playerActionMapJP.get(playerIndex).getComponent(i).setVisible(true);
        }

    }

    private void hideActionPanelContent(int playerIndex) {
        for (int i = 0; i <
                playerActionMapJP.get(playerIndex).getComponentCount(); i++) {
            playerActionMapJP.get(playerIndex).getComponent(i).setVisible(false);
        }

    }

    //Seleziona/evidenzia il mazziere di turno
    private void selectBank(int i) {
        playerNameMapJP.get(i).setBackground(Color.GREEN);
        getBetJTF(i).setEnabled(false);

    }

//Deseleziona l'ex-mazziere di turno, che diventa un player normale
    private void deselectBank(int i) {
        playerNameMapJP.get(i).setBackground(new Color(212, 208, 200));
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

        GUICoreMediator.requestCard(i, bet);
        resetBetJTF(i);
        offLineGameVO =
                GUICoreMediator.requestOffLineGameVO();
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

        GUICoreMediator.declareGoodScore(i, bet);
        resetBetJTF(i);
        offLineGameVO =
                GUICoreMediator.requestOffLineGameVO();
        refreshComponent();
    }

    private void refreshComponent() {
        int bank = -1;
        Object[][] dataReport = GUICoreMediator.requestDataReport();

        for (int i = 0; i < offLineGameVO.getPlayerIndexList().size(); i++) {
            playerCreditMapJL.get(i).setText(offLineGameVO.getPlayerCreditMap().get(i));
            for (int j = 0; j <
                    14; j++) {
                ((JLabel) playerCardsMapJP.get(i).getComponent(j)).setIcon(null);
            }

            for (int j = 0; j <
                    offLineGameVO.getPlayerCardsImageMap().get(i).size(); j++) {
                ((JLabel) playerCardsMapJP.get(i).getComponent(j)).setIcon(
                        scaledImage(offLineGameVO.getPlayerCardsImageMap().get(i).get(j)));
            }

            playerStakeMapJL.get(i).setText(offLineGameVO.getPlayerStakeMap().get(i));
            playerScoreMapJL.get(i).setText(offLineGameVO.getPlayerScoreMap().get(i));
            playerStatusMapJL.get(i).setText(offLineGameVO.getPlayerStatusMap().get(i));

            if (offLineGameVO.getPlayerRoleMap().get(i) == Boolean.TRUE) {
                bank = i;
                selectBank(i);
            } else {
                deselectBank(i);
            }

            if (offLineGameVO.getPlayerPlayingMap().get(i) == Boolean.TRUE) {
                showActionPanelContent(i);
                currentIndex = i;
            } else {
                hideActionPanelContent(i);
            }

            getBetJTF(i).setEnabled(offLineGameVO.getPlayerRequestBetMap().get(i));
            getRequestCardJB(i).setEnabled(!offLineGameVO.getPlayerRequestBetMap().get(i));
            getDeclareGoodScoreJB(i).setEnabled(!offLineGameVO.getPlayerRequestBetMap().get(i));
        } //end for

        if (offLineGameVO.isEndManche()) {
            offLineGameVO.getPlayerRoleMap();

            JOptionPane.showMessageDialog(this,
                    new ScoreBoardJP("Terminata Manche n° " + offLineGameVO.getCurrentManche(), dataReport, bank), "Score Board",
                    JOptionPane.INFORMATION_MESSAGE);

            if (offLineGameVO.isEndGame()) {
                JOptionPane.showMessageDialog(this, "Questa partita è terminata!!!");
                dispose();

            } else {
                initBoard();
            }

        }
    }

    //Resizes an image using a Graphics2D object backed by a BufferedImage.
    private ImageIcon scaledImage(ImageIcon srcImg) {
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
