package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import org.smgame.core.GameEngine;
import org.smgame.core.card.Card;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.core.player.PlayerRole;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;
import org.smgame.util.BetOverflowException;
import org.smgame.util.PrintErrors;
import org.smgame.util.ScoreOverflowException;

public class GameJIF extends JInternalFrame implements IGameJIF {

    private JPanel[] jpPanels;
    private JPanel[] jpActions;
    private String gameName;
    private Map<Player, List<Card>> hashmap;
    private List<Player> list_player;
    private Game game;
    private GameEngine engine;

    public GameJIF(String gameName, Map temp_map, GameSetting gs) {
        super(gameName);
        this.gameName = gameName;
        setPreferredSize(new Dimension(1000, 600));
        hashmap = temp_map;
        initComponentsLoad();
        setClosable(true);
        pack();
    }

    public GameJIF(String gameName, List<Player> tempList, GameSetting gs) {
        super(gameName);
        this.gameName = gameName;
        int width = 1000;
        int height = 600;
        int xbound = (getContentPane().getWidth() - width) % 2;
        int ybound = (getContentPane().getHeight() - height) % 2;
        setPreferredSize(new Dimension(width, height));
        setBounds(xbound, ybound, xbound + width, ybound + height);
        setClosable(true);
        list_player = tempList;
        initComponentsNew();
        pack();
    }

    private void initComponentsLoad() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbcP = new GridBagConstraints();
        GridBagConstraints gbcA = new GridBagConstraints();

        gbcP.weighty = gbcA.weighty = 0.1;
        gbcP.weightx = gbcA.weightx = 0.1;
        gbcP.anchor = gbcA.anchor = GridBagConstraints.NORTHWEST;
        gbcP.gridx = 0;
        gbcA.gridx = 1;

        jpPanels = new JPanel[hashmap.size()];
        jpActions = new JPanel[hashmap.size()];
        Object[] list_keys = hashmap.keySet().toArray();


        for (int i = 0; i < hashmap.size(); i++) {
            gbcP.gridy = gbcA.gridy = i;
            Player tempPlayer = (Player) list_keys[i];

            jpPanels[i] = new PlayerCardJP(tempPlayer.getName());
            if (tempPlayer.getRole() == PlayerRole.Bank) {
                ((PlayerCardJP) jpPanels[i]).selectBank();
            }
            this.add(jpPanels[i], gbcP);

            if (tempPlayer instanceof HumanPlayer) {
                jpActions[i] = createPanelActionsPlayer();
                jpActions[i].setVisible(false);
                this.add(jpActions[i], gbcA);
            } else {
                jpActions[i] = null;
            }
        } //end for
    }//end initComponentsNew

    private void initComponentsNew() {
        PlayerList player_list = PlayerList.getInstance();
        player_list.getPlayerAL().addAll(list_player);
        game = Game.create(gameName, null, player_list);
        engine = game.getGameEngine();

        int size = player_list.getPlayerAL().size();        

        setLayout(new GridBagLayout());
        GridBagConstraints gbcP = new GridBagConstraints();
        GridBagConstraints gbcA = new GridBagConstraints();

        gbcP.weighty = gbcA.weighty = 0.1;
        gbcP.weightx = gbcA.weightx = 0.1;
        gbcP.anchor = gbcA.anchor = GridBagConstraints.NORTHWEST;
        gbcP.gridx = 0;
        gbcA.gridx = 1;

        jpPanels = new JPanel[size];
        jpActions = new JPanel[size];
        
        for (int i = 0; i < size; i++) {
            gbcP.gridy = gbcA.gridy = i;
            Player tempPlayer = player_list.getPlayerAL().get(i);
            jpPanels[i] = new PlayerCardJP(tempPlayer.getName());
            if (tempPlayer.getRole() == PlayerRole.Bank)
                ((PlayerCardJP) jpPanels[i]).selectBank();
            
            this.add(jpPanels[i], gbcP);

            if (tempPlayer instanceof HumanPlayer) {
                jpActions[i] = createPanelActionsPlayer();
                jpActions[i].setVisible(false);
                this.add(jpActions[i], gbcA);
            } else
                jpActions[i] = null;            
        } //end for

        int pos = player_list.getPlayerAL().indexOf(engine.selectFirstRandomBankPlayer());
        
        ((PlayerCardJP)jpPanels[pos]).selectBank();
        jpActions[(pos+1)%size].setVisible(true);
        
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
        /*jtxtSetCash.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {

            }
        });*/
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
        try {
            //TODO:sistemare la puntata
            System.out.println("TODO:sistemare la puntata");
            engine.requestCard(engine.getCurrentPlayer(), 1);
        } catch (BetOverflowException boe) {
            PrintErrors.exception(boe);
            //Logger.getLogger(GameJIF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScoreOverflowException soe) {
            PrintErrors.exception(soe);
            //Logger.getLogger(GameJIF.class.getName()).log(Level.SEVERE, null, ex);
        }
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