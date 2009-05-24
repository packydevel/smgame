package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class PlayerActionsJP extends JPanel{
    private JComboBox jcbActions;
    private JButton jbRun;

    public PlayerActionsJP() {
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(200, 30));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        jcbActions = new JComboBox();
        String[] operations = new String[] {null, "Fai puntata", "Chiedi carta", "Ritirati", "Dichiara 7mezzo"};
        jcbActions.setModel(new javax.swing.DefaultComboBoxModel(operations));
        this.add(jcbActions);

        jbRun = new JButton("Esegui");
        jbRun.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbRun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });
        this.add(jbRun);
    } //end initComponents

}