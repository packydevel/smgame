package org.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
        jpSud = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jpOvest = new javax.swing.JPanel();
        jpEst = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpNord.setLayout(new java.awt.BorderLayout());

        jpSud.setLayout(new java.awt.BorderLayout());

        jButton1.setText("jButton1");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jpOvest.setLayout(new java.awt.BorderLayout());

        jpEst.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpNord, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(jpSud, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpOvest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 311, Short.MAX_VALUE)
                .addComponent(jpEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpNord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jpOvest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jpEst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(jpSud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        test();
    }//GEN-LAST:event_jButton1MouseClicked

    private void test(){
        component[n] = new JLabel("Player " + String.valueOf(n));
        System.out.println(n);
        switch (n){
            case 0:                                
                jpCentroNord.add(BorderLayout.WEST, component[n]);
                break;
            case 1:
                jpCentroSud.add(BorderLayout.WEST, component[n]);
                break;
            case 2:
                jpOvest.add(BorderLayout.NORTH, component[n]);
                break;
            case 3:
                jpEst.add(BorderLayout.NORTH, component[n]);
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
            case 8:                                
                jpCentroNord.add(BorderLayout.EAST, component[n]);
                break;
            case 9:
                jpCentroSud.add(BorderLayout.EAST, component[n]);
                break;
            case 10:
                jpOvest.add(BorderLayout.SOUTH, component[n]);
                break;
            case 11:
                jpEst.add(BorderLayout.SOUTH, component[n]);
                break;
        }
        if (n<11)
            n++;
        this.validate();
        pack();
    }

    private void initComponents2(){
        this.setPreferredSize(new Dimension(400, 400));

        jpNord.setPreferredSize(new Dimension(400, 50));
        jpNord.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jpSud.setPreferredSize(new Dimension(400, 50));
        jpSud.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        jpOvest.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        jpEst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 255)));
        
        jpCentroNord = new JPanel(new BorderLayout());
        jpCentroNord.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0)));
        jpCentroNord.setPreferredSize(new Dimension(200, 50));
        
        jpCentroSud = new JPanel(new BorderLayout());
        jpCentroSud.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        jpCentroSud.setPreferredSize(new Dimension(200, 50));
        

        jpNord.add(BorderLayout.CENTER,jpCentroNord);
        jpSud.add(BorderLayout.CENTER,jpCentroSud);

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
    private javax.swing.JPanel jpEst;
    private javax.swing.JPanel jpNord;
    private javax.swing.JPanel jpOvest;
    private javax.swing.JPanel jpSud;
    // End of variables declaration//GEN-END:variables
    private JPanel jpCentroNord;
    private JPanel jpCentroSud;
}