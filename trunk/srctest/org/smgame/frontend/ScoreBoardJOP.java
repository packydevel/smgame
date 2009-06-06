package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ScoreBoardJOP extends JOptionPane{
    private JTable scoreboardJT;
    private final String[] columnNames = {"Giocatore", "Punteggio", "Vincita", "Credito"};


    public ScoreBoardJOP(){
        super();
        scoreboardJT = new JTable();
        setTableModel(null);
        this.setPreferredSize(new Dimension(150, 400));
        this.setLayout(new BorderLayout());
        this.add(scoreboardJT,BorderLayout.NORTH);
        this.setVisible(true);
    }

    private void setTableModel(Object[][] data){
        DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
        scoreboardJT.setModel(dtm);
    }

}