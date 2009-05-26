package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.smgame.core.GUICoreMediator;

public class GameJIF extends JInternalFrame implements IGameJIF {

    private JPanel[] jpPanels;
    private JPanel[] jpActions;
    
    public GameJIF() {
        super(GUICoreMediator.getGameName());
        int width = 1000;
        int height = 600;
        int xbound = (getContentPane().getWidth() - width) % 2;
        int ybound = (getContentPane().getHeight() - height) % 2;
        setPreferredSize(new Dimension(width, height));
        setBounds(xbound, ybound, xbound + width, ybound + height);
        setClosable(true);
        initComponents();
        pack();
    }

    private void initComponents() {
        List<String> player_list = GUICoreMediator.getPlayerNameList();
        List<Boolean> type_player = GUICoreMediator.getPlayerTypeList();
        List<Double> player_credit = GUICoreMediator.getPlayerCreditList();
        
        int size = player_list.size();
        jpPanels = new JPanel[size];
        jpActions = new JPanel[size];

        setLayout(new GridBagLayout());
        GridBagConstraints gbcP = new GridBagConstraints();
        GridBagConstraints gbcA = new GridBagConstraints();

        gbcP.weighty = gbcA.weighty = 0.1;
        gbcP.weightx = gbcA.weightx = 0.1;
        gbcP.anchor = gbcA.anchor = GridBagConstraints.NORTHWEST;
        gbcP.gridx = 0;
        gbcA.gridx = 1;       
        
        for (int i = 0; i < size; i++) {
            gbcP.gridy = gbcA.gridy = i;
            //aggiungo al pannello i nomi giocatori
            jpPanels[i] = new PlayerCardJP(player_list.get(i), player_credit.get(i));
            this.add(jpPanels[i], gbcP);            

            //aggiungo al pannello le azioni se il giocatore è umano            
            if (type_player.get(i).equals(Boolean.FALSE)) {
                jpActions[i] = createPanelActionsPlayer(i);
                jpActions[i].setVisible(false);
                this.add(jpActions[i], gbcA);
                ((PlayerCardJP)jpPanels[i]).setHumanColor();
            } else
                jpActions[i] = null;            
        } //end for

        int pos = GUICoreMediator.getBankPlayer();        
        ((PlayerCardJP)jpPanels[pos]).selectBank();
        pos = ++pos % size;        
        if (jpActions[pos]!=null)
            jpActions[pos].setVisible(true);
        try {
            for (int i=0; i<size; i++) {
                System.out.print(pos);
                ((PlayerCardJP)jpPanels[pos]).newLabelIconCard(GUICoreMediator.requestCard(pos, 0));
                pos = ++pos % size;
                
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        this.validate();
    }//end initComponentsNew

    private JPanel createPanelActionsPlayer(int i){
        final int index = i;

        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pane.setPreferredSize(new Dimension(280, 25));

        pane.add(new JLabel("Puntata"));

        final JTextField jtxtSetCash = new JTextField();
        jtxtSetCash.setEditable(true);
        jtxtSetCash.setEnabled(true);
        jtxtSetCash.setPreferredSize(new Dimension(30, 20));
        jtxtSetCash.setColumns(4);
        jtxtSetCash.setVisible(true);        
        pane.add(jtxtSetCash);

        JButton jbCallCard = new JButton("Chiedi carta");
        //jbCallCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbCallCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                requestCard(index, jtxtSetCash.getText());
            }
        });
        jbCallCard.setPreferredSize(new Dimension(75, 20));
        pane.add(jbCallCard);

        JButton jbImOK = new JButton("Sto bene");
        //jbImOK.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbImOK.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                imOk(index, jtxtSetCash.getText());
            }
        });
        jbImOK.setPreferredSize(new Dimension(55,20));
        pane.add(jbImOK);

        return pane;
    }

    private void requestCard(int i, String value) {
        double cash=-1;
        try {
        if ((value!=null) && (!value.equalsIgnoreCase("")))
            cash = Double.valueOf(value);
            ((PlayerCardJP)jpPanels[i]).newLabelIconCard(GUICoreMediator.requestCard(i, cash));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void imOk(int i, String value){
        double cash=-1;
        try {
        if ((value!=null) && (!value.equalsIgnoreCase("")))
            cash = Double.valueOf(value);
            GUICoreMediator.declareGoodScore(i, cash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jpActions[i].setVisible(false);
        int pos = GUICoreMediator.nextPlayer();
        jpActions[pos].setVisible(true);
    }

} //end class

/*
private void jbRemoveAllMouseClicked(MouseEvent evt) {
((testPanel) panel).resetLabelIconCards();
pack();
}
 */
