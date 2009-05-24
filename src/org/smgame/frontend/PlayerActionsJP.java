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
        this.setPreferredSize(new Dimension(200, 30));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.add(new JLabel("Puntata"));

        jtxtSetCash = new JTextField();
        jtxtSetCash.setPreferredSize(new Dimension(20, 20));
        jtxtSetCash.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }

            private void jTextField1KeyPressed(KeyEvent evt) {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        });

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