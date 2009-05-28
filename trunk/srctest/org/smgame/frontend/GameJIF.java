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
import org.smgame.util.PrintErrors;
import org.smgame.util.ScoreOverflowException;

public class GameJIF extends JInternalFrame {

    private List<JPanel> playerCardsJP; //Lista pannelli giocatore-carte
    private List<JLabel> playerNameJL; //lista label nome giocatori
    private List<JLabel> playerCreditJL; //lista label credito giocatori
    private List<JLabel> playerStakeJL; //lista label puntate giocatori
    private List<JLabel> playerScoreJL; //lista label punteggio giocatori
    private List<JPanel> playerActionsJP; //Lista pannelli giocatore-carte    

    private int iterator=1;
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
        pack();
        initBoard();
        pack();
    }

    private void initComponents() {        
        List<String> playerNameList = GUICoreMediator.getPlayerNameList();
        List<Boolean> playerTypeList = GUICoreMediator.getPlayerTypeList();
        List<String> playerCreditList = GUICoreMediator.getPlayerCreditList();        
        List<ImageIcon> cardImagesList = new ArrayList<ImageIcon>();
        List<List<ImageIcon>> playerCardsImagesList = new ArrayList<List<ImageIcon>>();        
        

        int size = playerNameList.size();

        playerCardsJP = new ArrayList<JPanel>(size);
        playerNameJL = new ArrayList<JLabel>(size);
        playerCreditJL = new ArrayList<JLabel>(size);
        playerStakeJL = new ArrayList<JLabel>(size);
        playerScoreJL = new ArrayList<JLabel>(size);        
        playerActionsJP = new ArrayList<JPanel>(size);
        
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
            playerNameJL.add(new JLabel(playerNameList.get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i;
            add(playerNameJL.get(i), labelGBC);

            playerCreditJL.add(new JLabel("Credito: " + playerCreditList.get(i)));
            labelGBC.gridx = 0;
            labelGBC.gridy = 2 * i + 1;
            add(playerCreditJL.get(i), labelGBC);

            if (playerTypeList.get(i).equals(Boolean.FALSE)){
                setHumanColor(i);
                playerActionsJP.add(initPanelActions());
                actionsGBC.gridy = 2 * i;
                this.add(playerActionsJP.get(i), actionsGBC);
            } else
                playerActionsJP.add(null);
            
            playerStakeJL.add(new JLabel("Puntata: 0,00"));
            labelGBC.gridx = 2;
            labelGBC.gridy = 2 * i + 1;
            this.add(playerStakeJL.get(i), labelGBC);

            playerScoreJL.add(new JLabel("Punteggio: 0.0"));
            labelGBC.gridx = 3;
            labelGBC.gridy = 2 * i + 1;
            add(playerScoreJL.get(i), labelGBC);
            playerScoreJL.get(i).setVisible(false);

            panelGBC.gridx = 1;
            panelGBC.gridy = 2 * i;
            cardImagesList = GUICoreMediator.getPlayerCards(i);
            playerCardsImagesList.add(cardImagesList);                        
            
            playerCardsJP.add(initPanelPlayersCards());
            add(playerCardsJP.get(i), panelGBC);
        }
    } //end initComponents

    //inizializza il pannello del player - carte
    private JPanel initPanelPlayersCards(){
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pane.setPreferredSize(new Dimension(500, 50));
        pane.setBorder(new LineBorder(new Color(212, 208, 200)));
        ((FlowLayout)pane.getLayout()).setHgap(1);
        for (int j = 0; j < 14; j++) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(32, 49));
            label.setBorder(new LineBorder(new Color(212, 208, 200)));
            pane.add(label);
        }
        return pane;
    } // initPanelPlayersCards

    //inizializza il pannello delle azioni
    private JPanel initPanelActions(){
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField jtf = new JTextField();
        jtf.setPreferredSize(new Dimension(50, 20));
        pane.add(jtf);

        JButton jbRequest = new JButton("Chiedi una carta");
        jbRequest.setPreferredSize(new Dimension(110, 20));
        jbRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {                
                requestCard(searchJButton(evt,1));
            }
        });
        pane.add(jbRequest);

        JButton jbGood = new JButton("Sto bene");
        jbGood.setPreferredSize(new Dimension(80, 20));
        jbGood.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goodScore(searchJButton(evt,2));
            }
        });
        pane.add(jbGood);
        pane.setVisible(false);
        return pane;
    }

    //inizializza il tavolo
    private void initBoard(){
        //TODO
        int pos = GUICoreMediator.getBankPlayer();
        int size = playerNameJL.size();
        selectBank(pos);
        pos = ++pos % size;
        for (int i = 0; i < size; i++) {
            setFirstCard(pos, GUICoreMediator.getFirstCard(pos));
            setScoreLabel(pos, GUICoreMediator.getPlayerScore(pos));
            pos = ++pos % size;
        }
        if (playerActionsJP.get(pos) != null) {
            playerActionsJP.get(pos).setVisible(true);
            playerScoreJL.get(pos).setVisible(true);
            firstCardDiscovered(pos);
        }
        this.validate();
    }

    //inizializza il gridbagconstraints per tutti per la parte in comune
    private GridBagConstraints initGridBagC(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        return gbc;
    }

    private void setFirstCard(int i, ImageIcon[] icon){
        JPanel temp = playerCardsJP.get(i);
        ((JLabel)temp.getComponent(0)).setIcon(icon[1]);
        ((JLabel)temp.getComponent(1)).setIcon(icon[0]);
        firstCardCovered(i);
    }

    private void firstCardCovered(int i){
        JPanel temp = playerCardsJP.get(i);
        ((JLabel)temp.getComponent(0)).setVisible(true);
        ((JLabel)temp.getComponent(1)).setVisible(false);
    }

    private void firstCardDiscovered(int i){
        JPanel temp = playerCardsJP.get(i);
        ((JLabel)temp.getComponent(0)).setVisible(false);
        ((JLabel)temp.getComponent(1)).setVisible(true);
    }

    //Seleziona/evidenzia il mazziere di turno
    private void selectBank(int i) {
        ((JLabel) playerNameJL.get(i)).setOpaque(true);
        ((JLabel) playerNameJL.get(i)).setBackground(new Color(255, 153, 0));
    }

    //Deseleziona l'ex-mazziere di turno, che diventa un player normale
    private void deselectBank(int i) {
        ((JLabel) playerNameJL.get(i)).setOpaque(false);
        ((JLabel) playerNameJL.get(i)).setBackground(new Color(212, 208, 200));
    }

    //Imposta il colore del testo del giocatore umano, per differenziarlo dal giocatore CPU
    private void setHumanColor(int i) {
        ((JLabel) playerNameJL.get(i)).setForeground(new Color(0, 0, 255));
    }

    //Imposta il testo della label relativa al credito residuo
    private void setCreditLabel(int i, String credit) {
        ((JLabel) playerCreditJL.get(i)).setText("Credito: " + credit);
    }

    //Imposta il testo della label relativa alla puntata
    private void setStakeLabel(int i, String stake) {
        ((JLabel) playerStakeJL.get(i)).setText("Puntata: " + stake);
    }

    //Imposta il testo della label relativa al punteggio
    private void setScoreLabel(int i, String score) {
        ((JLabel) playerScoreJL.get(i)).setText("Punteggio: " + score);
    }

    //preleva il valore della puntata
    private String getCashJTF(int i){
        return ((JTextField)playerActionsJP.get(i).getComponent(0)).getText();
    }

    //cerca il bottone nel pannello
    private int searchJButton(ActionEvent evt, int c) {
        JButton temp = (JButton) evt.getSource();
        int i=-1;
        for (int j=0; j<playerActionsJP.size(); j++){
            if (((JPanel)playerActionsJP.get(j)).getComponent(c).equals(temp)){
                i=j;
                break;
            }
        }
        return i;
    }

    //imposta la cartaGUI
    private void setIconCard(int i, ImageIcon icon) {
        JPanel temp = playerCardsJP.get(i);
        ((JLabel)temp.getComponent(++iterator)).setIcon(icon);
    }

    //esegue le azioni di richiesta carta
    private void requestCard(int i) {
        String value = getCashJTF(i);        
        try {
            if ((value != null) && (!value.equalsIgnoreCase(""))) {
                //trasformo la stringa della puntata in double
                double cash = Double.valueOf(value);
                //richiamo il mediatore e aggiungo la nuova carta sul tavolo
                setIconCard(i, GUICoreMediator.requestCard(i, cash));
                //aggiorna il valore delle puntate del player
                setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
                //aggiorna il punteggio delle carte
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                //aggiorna il credito residuo del player
                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
            }
        } catch (ScoreOverflowException soe) {
                //prendo l'immagine della carta sbagliata
                setIconCard(i, soe.getCardException().getFrontImage());
                //aggiorno il punteggio che peraltro è overflow
                setScoreLabel(i, GUICoreMediator.getPlayerScore(i));
                //richiamo la jdialog x l'eccezione
                PrintErrors.exception(soe);
                //pannello delle azioni visibile x il giocatore che sta lasciando la mano
                playerActionsJP.get(i).setVisible(true);
                //aggiorno il credito residuo del giocatore sballante
                setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
                //ottengo la posizione del successivo player
                int pos = GUICoreMediator.nextPlayer();
                //ottengo la posizione del mazziere
                int bank = GUICoreMediator.getBankPlayer();
                //aggiorno il credito della mazziere
                setCreditLabel(bank,GUICoreMediator.getPlayerCredit(bank));
                //rivelo la carta nascosta del nuovo player
                iterator=1;
                firstCardDiscovered(pos);
                System.out.println(pos);
                //pannello delle azioni visibile x il giocatore successivo
                playerActionsJP.get(pos).setVisible(true);
                playerScoreJL.get(pos).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //end request card

    private void goodScore(int i) {
        String value = getCashJTF(i);
        System.out.println("entered");
        if ((value != null) && (!value.equalsIgnoreCase(""))) {
            System.out.println("if");
            double cash = Double.valueOf(value);
            try {
                GUICoreMediator.declareGoodScore(i, cash);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("goodscore");
            //aggiorna il valore delle puntate del player
            setStakeLabel(i, GUICoreMediator.getPlayerStake(i));
            System.out.println("stake");
            firstCardCovered(i);
            System.out.println("1st card covered");
            setCreditLabel(i, GUICoreMediator.getPlayerCredit(i));
            System.out.println("credit");
            playerActionsJP.get(i).setVisible(false);
            playerScoreJL.get(i).setVisible(false);
            System.out.println("visibile");
            int pos = GUICoreMediator.nextPlayer();
            System.out.println("pos");
            firstCardDiscovered(pos);
            System.out.println("1st card pos discover");
            playerActionsJP.get(pos).setVisible(true);
            playerScoreJL.get(pos).setVisible(true);
            System.out.println("jpanel pos");
            iterator=1;
        }
    }

}//end gameJIF