package org.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class NewJFrame1 extends javax.swing.JFrame {
    JComponent component [] = new JComponent[12];
    private int n = 0;

    public NewJFrame1() {
        initComponents();
        initComponents2();
        pack();
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents

        jpNord = new javax.swing.JPanel();
        jpCentroNord = new javax.swing.JPanel();
        jpSud = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpNord.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jpCentroNordLayout = new javax.swing.GroupLayout(jpCentroNord);
        jpCentroNord.setLayout(jpCentroNordLayout);
        jpCentroNordLayout.setHorizontalGroup(
            jpCentroNordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 461, Short.MAX_VALUE)
        );
        jpCentroNordLayout.setVerticalGroup(
            jpCentroNordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jpNord.add(jpCentroNord, java.awt.BorderLayout.CENTER);

        jpSud.setLayout(new java.awt.BorderLayout());

        jButton1.setText("jButton1");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpSud, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jButton1)
                .addContainerGap(226, Short.MAX_VALUE))
            .addComponent(jpNord, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpNord, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 269, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(80, 80, 80)
                .addComponent(jpSud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        component[n] = new JLabel("Player " + String.valueOf(n));
        System.out.println(n);
        switch (n){
            case 0:
                jpCentroNord.add(BorderLayout.WEST, component[n]);
                jpNord.add(BorderLayout.CENTER, jpCentroNord);
                break;
            case 1:
                jpSud.add(BorderLayout.CENTER, component[n]);
                break;
            case 4:
                jpNord.add(BorderLayout.WEST, component[n]);
                break;
            case 5:
                jpNord.add(BorderLayout.EAST, component[n]);
                break;
            case 6:
                jpSud.add(BorderLayout.EAST, component[n]);
                break;
            case 7:
                jpSud.add(BorderLayout.WEST, component[n]);
                break;
        }
        if (n<11)
            n++;
        this.validate();
    }//GEN-LAST:event_jButton1MouseClicked


    private void initComponents2(){
        this.setPreferredSize(new Dimension(300, 300));
        jpNord.setPreferredSize(new Dimension(300, 50));
        jpNord.setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame1().setVisible(true);
            }
        });
    }  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jpCentroNord;
    private javax.swing.JPanel jpNord;
    private javax.swing.JPanel jpSud;
    // End of variables declaration//GEN-END:variables
}