package org.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class testPanel extends JPanel{

    private JComponent[] components;
    private String curDir; //directory per le img
    private String name_player; //nome giocatore
    private int number=0; //numero carta (iteratore)
    private int max=16;

    public testPanel(String player) {
        name_player = player;
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(600, 50));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        curDir = System.getProperty("user.dir");
        String separ = File.separator;
        curDir += separ + "src" + separ + "org" + separ + "smgame" + separ +
                "resource" + separ + "cartemini" + separ + "napoletane" + separ;        

        components = new JComponent[max];

        JLabel jlName = new JLabel(name_player);
        this.add(jlName);
        components[number]=jlName;        
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

    /**elimina le label delle carte
     *
     */
    public void resetLabelIconCards(){
        for (int i=number;i>0; i--){
            this.remove(components[i]);
            components[i]= null;
        }
        number=0;
    }

}// end class