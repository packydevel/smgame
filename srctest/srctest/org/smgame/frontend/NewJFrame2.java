package org.smgame.frontend;

import org.smgame.frontend.testPanel;
import org.smgame.frontend.NewJFrame2;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.smgame.core.card.Card;

public class NewJFrame2 extends javax.swing.JFrame {

    private JPanel[] panels;
    private LinkedHashMap<String,LinkedList<Card>> hashmap;

    public NewJFrame2(LinkedHashMap temp_map) {
        hashmap = temp_map;
        this.setPreferredSize(new Dimension(1024, 768));
        initComponents();
        initComponents2();

    }

    private void initComponents() {//GEN-BEGIN:initComponents

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pack();
    }//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        final LinkedHashMap tempMap = new LinkedHashMap();
        tempMap.put("pippo", null);
        tempMap.put("topolino", null);
        tempMap.put("pluto", null);
        tempMap.put("paperino", null);
        tempMap.put("paperoga", null);
        tempMap.put("paperone", null);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame2(tempMap).setVisible(true);
            }
        });
    }

    private void initComponents2(){        
        GridBagConstraints c = new GridBagConstraints();        
        c.gridx = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        panels = new JPanel[hashmap.size()];
        Object[] list_keys = hashmap.keySet().toArray();

        for (int i=0; i<hashmap.size();i++){
            panels[i] = new testPanel((String)list_keys[i]);
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
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}