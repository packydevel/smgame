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

public class GameJIF extends JInternalFrame implements IGameJIF {

    private JPanel[] jpPanels;
    private JPanel[] jpActions;
    private Map<Player, List<Card>> hashmap;

    public GameJIF(Map temp_map) {        
        super();        
        setPreferredSize(new Dimension(1000, 600));
        //setBounds(5, 5, 1005, 605);
        setClosable(true);
        hashmap = temp_map;
        initComponents();        
        this.setVisible(true);
        pack();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.1;
        c.weightx = 0.1;        
        c.anchor = GridBagConstraints.NORTHWEST;
        jpPanels = new JPanel[hashmap.size()];
        jpActions = new JPanel[hashmap.size()];
        Object[] list_keys = hashmap.keySet().toArray();
        PlayerList player_list = PlayerList.getInstance();

        for (int i = 0; i < hashmap.size(); i++) {
            c.gridy = i;
            Player tempPlayer = (Player)list_keys[i];
            player_list.getPlayerAL().add(tempPlayer);
            jpPanels[i] = new PlayerCardJP(tempPlayer.getName());

            c.gridx = 0;
            this.add(jpPanels[i], c);

            
            if (list_keys[i] instanceof HumanPlayer) {
                c.gridx = 1;
                jpActions[i] = new PlayerActionsJP();
                this.add(jpActions[i],c);
            } else
                jpActions[i] = null;
        }

        Game game = Game.create(null, player_list);

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
    }//end initComponents
} //end class 