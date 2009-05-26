package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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
            jpPanels[i] = new PlayerCardJP(player_list.get(i));                        
            this.add(jpPanels[i], gbcP);

            //aggiungo al pannello le azioni se il giocatore è umano            
            if (type_player.get(i).equals(Boolean.FALSE)) {
                jpActions[i] = createPanelActionsPlayer();
                jpActions[i].setVisible(false);
                this.add(jpActions[i], gbcA);
            } else
                jpActions[i] = null;            
        } //end for

        int pos = GUICoreMediator.getBankPlayer();
                
        ((PlayerCardJP)jpPanels[pos]).selectBank();
        pos = ++pos % size;
        
        if (jpActions[pos]!=null)
            jpActions[pos].setVisible(true);
        
    }//end initComponentsNew

    private JPanel createPanelActionsPlayer(){
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pane.setPreferredSize(new Dimension(280, 30));

        pane.add(new JLabel("Puntata"));

        JTextField jtxtSetCash = new JTextField();
        jtxtSetCash.setEditable(true);
        jtxtSetCash.setEnabled(true);
        jtxtSetCash.setPreferredSize(new Dimension(50, 20));
        jtxtSetCash.setColumns(7);
        jtxtSetCash.setVisible(true);        
        pane.add(jtxtSetCash);

        JButton jbCallCard = new JButton("Chiedi carta");
        jbCallCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbCallCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                requestCard();
            }
        });
        pane.add(jbCallCard);

        JButton jbImOK = new JButton("Sto bene");
        jbImOK.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbImOK.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {

            }
        });
        pane.add(jbImOK);

        return pane;
    }

    private void requestCard(){
        /*
        try {
            //TODO:sistemare la puntata
            System.out.println("TODO:sistemare la puntata " + engine.getCurrentPlayer().getName());
            engine.requestCard(engine.getCurrentPlayer(), 1);
        } catch (BetOverflowException boe) {
            PrintErrors.exception(boe);
            //Logger.getLogger(GameJIF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScoreOverflowException soe) {
            PrintErrors.exception(soe);
            //Logger.getLogger(GameJIF.class.getName()).log(Level.SEVERE, null, ex);
        }  */
    }

} //end class

/*
private void jbAddMouseClicked(MouseEvent evt) {
((testPanel) panel).newLabelIconCard("B01.jpg");
pack();
}

private void jbRemoveAllMouseClicked(MouseEvent evt) {
((testPanel) panel).resetLabelIconCards();
pack();
}
 */