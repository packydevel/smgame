package org.smgame.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerCardJP extends JPanel{

    private JComponent[] components;
    private String curDir; //directory per le img
    private String name_player; //nome giocatore
    private int number=2; //numero carta (iteratore)
    private int max=18;

    public PlayerCardJP(String player) {
        name_player = player;
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(700, 50));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        curDir = System.getProperty("user.dir");
        String separ = File.separator;
        /*TODO: aggiustare il path x le carte in merito alla scelta delle carte*/
        curDir += separ + "src" + separ + "org" + separ + "smgame" + separ +
                "resource" + separ + "cartemini" + separ + "napoletane" + separ;

        components = new JComponent[max];

        components[0]= new JLabel(name_player);
        components[1]= new JLabel("Credito: ");
        components[2]= new JLabel();
        this.add(components[0]);
        this.add(components[1]);
        this.add(components[2]);
        this.setVisible(true);
    }//end initComponents

    /**Aggiunge una carta GUI tramite label e icon
     *
     * @param img
     */
    public void newLabelIconCard(String img){
        if (number<max-1) {
            ImageIcon icon = new ImageIcon(curDir + img);
            components[++number] = new JLabel(icon);
            this.add(components[number]);
        }
    }

    public void setCashLabel(String credit){
        ((JLabel)components[2]).setText(credit);
    }

    /**elimina le label delle carte
     *
     */
    public void resetLabelIconCards(){
        for (int i=number;i>2; i--){
            this.remove(components[i]);
            components[i]= null;
        }
        number=0;
    }

    public void selectBank(){
        components[0].setOpaque(true);
        components[0].setBackground(new Color(255, 153, 0));
    }

    public void deselectBank(){
        components[0].setOpaque(false);
        components[0].setBackground(new Color(212, 208, 200));
    }

}//end class