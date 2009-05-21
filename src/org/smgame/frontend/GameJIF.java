package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import org.smgame.core.card.Card;

public class GameJIF extends JInternalFrame implements IGameJIF {
    
    private JPanel[] panels;
    private LinkedHashMap<String, LinkedList<Card>> hashmap;
    
    public GameJIF(LinkedHashMap temp_map) {
        super();
        setPreferredSize(new Dimension(1000, 600));
        //setBounds(5, 5, 1005, 605);
        setClosable(true);
        hashmap = temp_map;
        initComxponents();
        pack();
    }

    private void initComxponents() {
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.1;
        c.weightx = 0.1;
        c.gridx = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        panels = new JPanel[hashmap.size()];
        Object[] list_keys = hashmap.keySet().toArray();

        for (int i=0; i<hashmap.size();i++){
            panels[i] = new PlayerCardJP((String)list_keys[i]);
            c.gridy = i;
            this.add(panels[i],c);
        }

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