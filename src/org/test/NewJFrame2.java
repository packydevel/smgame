package org.test;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class NewJFrame2 extends javax.swing.JFrame {

    public NewJFrame2() {
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame2().setVisible(true);
            }
        });
    }

    private void initComponents2(){
        panel = new testPanel("pippo");
        panel1 = new testPanel("pluto");

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.LINE_START;
        this.add(panel,c);

        c.gridy = 1;
        this.add(panel1,c);

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
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JPanel panel;
    private JPanel panel1;
}
