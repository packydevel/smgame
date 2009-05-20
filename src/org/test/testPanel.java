package org.test;

import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class testPanel extends JPanel{

    private JComponent[] components;
    private String curDir;
    private String name_player; //nome giocatore
    private int number=0; //numero carta (iteratore)

    public testPanel(String player) {
        name_player = player;
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(600, 50));
        curDir = System.getProperty("user.dir");
        String separ = File.separator;
        curDir += separ + "src" + separ + "org" + separ + "smgame" + separ +
                "resource" + separ + "cartemini" + separ + "napoletane" + separ;        

        components = new JComponent[15];

        JLabel jlName = new JLabel(name_player);
        this.add(jlName);
        components[number]=jlName;        
        this.setVisible(true);
    }//end initComponents

    public void newLabelIconCard(String img){
        ImageIcon icon = new ImageIcon(curDir + img);
        components[++number] = new JLabel(icon);
        this.add(components[number]);
    }

    public void resetLabelIconCards(){
        for (int i=number;i>0; i--){
            this.remove(components[i]);
            components[i]= null;
        }
        number=0;
    }

}// end class