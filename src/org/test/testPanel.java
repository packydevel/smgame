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
    private String name_player;

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

        JLabel label = new JLabel(name_player);
        this.add(label);
        components[0]=label;
        for (int i=1;i<15; i++){
            components[i]= newLabelIconCard("C01.jpg");
            this.add(components[i]);
        }
        this.setVisible(true);
    }//end initComponents

    public JLabel newLabelIconCard(String img){
        ImageIcon icon = new ImageIcon(curDir + img);
        return new JLabel(icon);
    }

    public void resetLabelIconCards(){
        for (int i=14;i>0; i--){
            this.remove(components[i]);
            components[i]= null;
            System.out.println(i);
        }
    }

}// end class