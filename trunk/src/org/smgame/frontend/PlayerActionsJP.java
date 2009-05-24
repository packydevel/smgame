package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class PlayerActionsJP extends JPanel{
    private JTextField jtxtSetCash;
    private JButton jbCallCard;
    private JButton jbImOK;

    public PlayerActionsJP() {
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(300, 30));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.add(new JLabel("Puntata"));

        jtxtSetCash = new JTextField();
        jtxtSetCash.setEditable(true);
        jtxtSetCash.setEnabled(true);
        jtxtSetCash.setPreferredSize(new Dimension(50, 20));
        jtxtSetCash.setColumns(7);
        jtxtSetCash.setVisible(true);
        jtxtSetCash.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                
            }            
        });
        this.add(jtxtSetCash);

        jbCallCard = new JButton("Chiedi carta");
        jbCallCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbCallCard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {

            }
        });
        this.add(jbCallCard);

        jbImOK = new JButton("Sto bene");
        jbImOK.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jbImOK.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {

            }
        });
        this.add(jbImOK);
    } //end initComponents

}