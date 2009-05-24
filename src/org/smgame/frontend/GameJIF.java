package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.smgame.core.card.Card;
import org.smgame.core.player.HumanPlayer;
import org.smgame.core.player.Player;
import org.smgame.core.player.PlayerList;
import org.smgame.main.Game;
import org.smgame.main.GameSetting;

public class GameJIF extends JInternalFrame implements IGameJIF {

    private JPanel[] jpPanels;
    private JPanel[] jpActions;
    private Map<Player, List<Card>> hashmap;
    private List<Player> list_player;

    public GameJIF(Map temp_map, GameSetting gs) {
        super();
        setPreferredSize(new Dimension(1000, 600));
        setBounds(5, 5, 1005, 605);
        hashmap = temp_map;
        initComponentsLoad();
        setClosable(true);
        pack();
    }

    public GameJIF(List<Player> tempList, GameSetting gs) {
        super();
        System.out.println(tempList.size());
        setPreferredSize(new Dimension(1000, 600));
        setBounds(5, 5, 1005, 605);
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
        PlayerList player_list = PlayerList.getInstance();

        for (int i = 0; i < hashmap.size(); i++) {
            gbcP.gridy = gbcA.gridy = i;
            Player tempPlayer = (Player) list_keys[i];
            player_list.getPlayerAL().add(tempPlayer);
            jpPanels[i] = new PlayerCardJP(tempPlayer.getName());
            this.add(jpPanels[i], gbcP);

            if (tempPlayer instanceof HumanPlayer) {
                jpActions[i] = new PlayerActionsJP();
                jpActions[i].setVisible(false);
                this.add(jpActions[i], gbcA);
            } else 
                jpActions[i] = null;            
        } //end for

        Game game = Game.create(null, player_list);
    }//end initComponentsNew

    private void initComponentsNew() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbcP = new GridBagConstraints();
        GridBagConstraints gbcA = new GridBagConstraints();

        gbcP.weighty = gbcA.weighty = 0.1;
        gbcP.weightx = gbcA.weightx = 0.1;
        gbcP.anchor = gbcA.anchor = GridBagConstraints.NORTHWEST;
        gbcP.gridx = 0;
        gbcA.gridx = 1;

        jpPanels = new JPanel[list_player.size()];
        jpActions = new JPanel[list_player.size()];
        PlayerList player_list = PlayerList.getInstance();

        for (int i = 0; i < list_player.size(); i++) {
            gbcP.gridy = gbcA.gridy = i;
            Player tempPlayer = (Player)list_player.get(i);
            player_list.getPlayerAL().add(tempPlayer);
            jpPanels[i] = new PlayerCardJP(tempPlayer.getName());
            this.add(jpPanels[i], gbcP);

            if (tempPlayer instanceof HumanPlayer) {
                jpActions[i] = new PlayerActionsJP();
                jpActions[i].setVisible(false);  
                this.add(jpActions[i], gbcA);
            } else
                jpActions[i] = null;
        } //end for
        Game game = Game.create(null, player_list);
    }
} //end class

/*
    c.gridy = 2;
    JButton jbAdd = new JButton("add");
    jbAdd.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
    jbAddMouseClicked(evt);
    }
    });
    this.add(jbAdd,c);

    c.gridy = 3;
    JButton jbRemoveAll = new JButton("remove all");
    jbRemoveAll.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
    jbRemoveAllMouseClicked(evt);
    }
    });
    this.add(jbRemoveAll,c);
    }

    private void jbAddMouseClicked(MouseEvent evt) {
    ((testPanel) panel).newLabelIconCard("B01.jpg");
    pack();
    }

    private void jbRemoveAllMouseClicked(MouseEvent evt) {
    ((testPanel) panel).resetLabelIconCards();
    pack();
    }

     */
