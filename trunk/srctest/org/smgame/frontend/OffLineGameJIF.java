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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.smgame.core.GUICoreMediator;

public class OffLineGameJIF extends JInternalFrame implements IGameJIF {

    private ArrayList<Integer> playerList;
    private HashMap<Integer, JLabel> playerNameMapJL;
    private HashMap<Integer, JLabel> playerCreditMapJL; //lista label credito giocatori;
    private HashMap<Integer, JPanel> playerCardsMapJP; //Lista pannelli giocatore-carte
    private List<Boolean> playerTypeList;
    private HashMap<Integer, JLabel> playerStakeMapJL; //lista label puntate giocatori
    private HashMap<Integer, JLabel> playerScoreMapJL; //lista label punteggio giocatori
    private HashMap<Integer, JLabel> playerStatusMapJL;
    private HashMap<Integer, JPanel> playerActionMapJP; //Lista pannelli giocatore-carte
    private List<JLabel> playerCardsListJL;
    private OffLineGameVO offLineGameVO;
    private GridBagConstraints panelGBC,  labelGBC,  textFieldGBC,  buttonGBC;
    private int size,  currentIndex;
    private BetInputVerifier betInputVerifier = new BetInputVerifier();

    class BetInputVerifier extends InputVerifier implements KeyListener {

        DecimalFormat betValueFormatter;

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
                System.out.println("Testo di merda: " + betText);
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
        super(GUICoreMediator.getGameName(), false, true, false, false);
        int width = 960;
        int height = 640;
        setPreferredSize(new Dimension(width, height));
//        int xbound = (getDesktopPane().getWidth() - width) / 2;
//        int ybound = (getDesktopPane().getHeight() - height) / 2;
//        setBounds(xbound, ybound, xbound + width, ybound + height);
        setLayout(new GridBagLayout());
        initComponents();

        initBoard();

        pack();

    }

    private void initComponents() {
        offLineGameVO = GUICoreMediator.requestOffLineGameVO();
        playerList =
                offLineGameVO.getPlayerIndexList();

        playerTypeList =
                GUICoreMediator.getPlayerTypeList();

        size =
                playerList.size();

        playerNameMapJL =
                new HashMap<Integer, JLabel>(size);
        playerCreditMapJL =
                new HashMap<Integer, JLabel>(size);
        playerCardsMapJP =
                new HashMap<Integer, JPanel>(size);
        playerActionMapJP =
                new HashMap<Integer, JPanel>(size);
        playerStakeMapJL =
                new HashMap<Integer, JLabel>(size);
        playerScoreMapJL =
                new HashMap<Integer, JLabel>(size);
        playerStatusMapJL =
                new HashMap<Integer, JLabel>(size);

        panelGBC =
                new GridBagConstraints();
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

        for (int i = 0; i <
                size; i++) {
            playerNameMapJL.put(i,
                    new JLabel(offLineGameVO.getPlayerNameMap().get(i)));
            playerNameMapJL.get(i).setPreferredSize(new Dimension(120, 20));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i;
            add(playerNameMapJL.get(i), labelGBC);

            playerCreditMapJL.put(i,
                    new JLabel(offLineGameVO.getPlayerCreditMap().get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i + 1;
            add(playerCreditMapJL.get(i), labelGBC);

            panelGBC.gridx = 1;
            panelGBC.gridy = 2 * i;
            panelGBC.gridwidth = 1;
            panelGBC.gridheight = 2;
            panelGBC.weightx = 1;
            panelGBC.weighty = (double) 1 / (double) size;
            playerCardsMapJP.put(i, initPanelPlayersCards());
            add(playerCardsMapJP.get(i), panelGBC);

            if (playerTypeList.get(i).equals(Boolean.FALSE)) {
                setHumanColor(i);
            } else {
                setCPUColor(i);
            }

            playerActionMapJP.put(i, initPanelActions());
            hideActionPanelContent(i);
            panelGBC.gridx = 2;
            panelGBC.gridy = 2 * i;
            panelGBC.gridwidth = 3;
            panelGBC.gridheight = 1;
            panelGBC.weightx = 0;
            panelGBC.weighty = 0;
            //panelGBC.fill = GridBagConstraints.HORIZONTAL;
            add(playerActionMapJP.get(i), panelGBC);

            playerStakeMapJL.put(i, new JLabel(offLineGameVO.getPlayerStakeMap().get(i)));
            playerStakeMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 2;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.NORTHWEST;
            add(playerStakeMapJL.get(i), labelGBC);

            playerScoreMapJL.put(i, new JLabel(offLineGameVO.getPlayerScoreMap().get(i)));
            playerScoreMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 3;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.NORTHWEST;
            add(playerScoreMapJL.get(i), labelGBC);

            playerStatusMapJL.put(i, new JLabel(offLineGameVO.getPlayerStatusMap().get(i)));
            playerStatusMapJL.get(i).setPreferredSize(new Dimension(100, 20));
            labelGBC.gridx = 4;
            labelGBC.gridy = 2 * i + 1;
            labelGBC.anchor = GridBagConstraints.NORTHWEST;
            add(playerStatusMapJL.get(i), labelGBC);
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
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.setPreferredSize(new Dimension(300, 25));
        pane.setBorder(new LineBorder(new Color(212, 208, 200)));
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
        ((JLabel) playerNameMapJL.get(i)).setOpaque(true);
        ((JLabel) playerNameMapJL.get(i)).setBackground(new Color(255, 153, 0));
        getBetJTF(i).setEnabled(false);

    }

//Deseleziona l'ex-mazziere di turno, che diventa un player normale
    private void deselectBank(int i) {
        ((JLabel) playerNameMapJL.get(i)).setOpaque(false);
        ((JLabel) playerNameMapJL.get(i)).setBackground(new Color(212, 208, 200));
        getBetJTF(i).setEnabled(true);
    }

//Imposta il colore del testo del giocatore umano, per differenziarlo dal giocatore CPU
    private void setHumanColor(int i) {
        ((JLabel) playerNameMapJL.get(i)).setForeground(Color.RED);
    }

    private void setCPUColor(int i) {
        ((JLabel) playerNameMapJL.get(i)).setForeground(Color.BLUE);
    }

    private JTextField getBetJTF(int i) {
        return ((JTextField) playerActionMapJP.get(i).getComponent(0));
    }

//preleva il valore della puntata
    private String getBet(int i) {
        return ((JTextField) playerActionMapJP.get(i).getComponent(0)).getText();
    }

    private void resetBetJTF(int i) {
        ((JTextField) playerActionMapJP.get(i).getComponent(0)).setText("");
    }

    private void requestCard(int i) {
        String value;
        double bet;

        if (getBetJTF(i).isEnabled()) {
            value = getBet(i);
            if (!value.equalsIgnoreCase("") && Double.valueOf(value) != 0.00) {
                bet = Double.valueOf(value);
            } else {
                return;
            }

        } else {
            bet = 0.00;
        }

        GUICoreMediator.requestCard(i, bet);
        resetBetJTF(i);
        offLineGameVO =
                GUICoreMediator.requestOffLineGameVO();
        refreshComponent();

    } //end request card

    private void declareGoodScore(int i) {
        String value;
        double bet;

        if (getBetJTF(i).isEnabled()) {
            value = getBet(i);
            if (!value.equalsIgnoreCase("") && Double.valueOf(value) != 0.00) {
                bet = Double.valueOf(value);
            } else {
                return;
            }

        } else {
            bet = 0.00;
        }

        GUICoreMediator.declareGoodScore(i, bet);
        resetBetJTF(i);
        offLineGameVO =
                GUICoreMediator.requestOffLineGameVO();
        refreshComponent();

    }

    private void refreshComponent() {
        Object[][] dataReport = GUICoreMediator.requestDataReport();

        for (int i = 0; i <
                offLineGameVO.getPlayerIndexList().size(); i++) {
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
                selectBank(i);
            } else {
                deselectBank(i);
            }

            if (offLineGameVO.getPlayerPlayingMap().get(i) == Boolean.TRUE) {
                showActionPanelContent(i);
                currentIndex =
                        i;
            } else {
                hideActionPanelContent(i);
            }

            getBetJTF(i).setEnabled(offLineGameVO.getPlayerRequestBetMap().get(i));
        } //end for

        if (offLineGameVO.isEndManche()) {

            JOptionPane.showMessageDialog(this,
                    new ScoreBoardJP("Terminata manche n°", dataReport), "Score Board",
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
