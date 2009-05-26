package org.smgame.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerCardJP extends JPanel{
   
    private JComponent[] components;
    private String curDir; //directory per le img
    private String name_player; //nome giocatore
    private double cash;
    private int number=2; //numero carta (iteratore)
    private int max=18;

    public PlayerCardJP(String tplayer, double tcash) {        
        name_player = tplayer;
        cash = tcash;
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(700, 50));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));        

        components = new JComponent[max];

        components[0]= new JLabel(name_player);
        components[1]= new JLabel("Credito: ");
        components[2]= new JLabel(Double.toString(cash));
        this.add(components[0]);
        this.add(components[1]);
        this.add(components[2]);
        this.setVisible(true);
    }//end initComponents

    /**Aggiunge una carta GUI tramite label e icon
     *
     * @param icon
     */
    public void newLabelIconCard(ImageIcon icon){
        System.out.println(icon.getDescription());
        if (number<max-1) {
            components[++number] = new JLabel(icon);
            this.add(components[number]);
        }
    }

    public void setCashLabel(Double tcash){
        ((JLabel)components[2]).setText(Double.toString(tcash));
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

    public void setHumanColor(){
        components[0].setForeground(new Color(0, 0, 255));
    }

}//end class