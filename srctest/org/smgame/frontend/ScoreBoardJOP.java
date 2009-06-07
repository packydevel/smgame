package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ScoreBoardJOP extends JPanel{
    private static ScoreBoardJOP scoreboard;
    private JLabel typeEndJL;
    private JTable scoreboardJT;
    private final String[] columnNames = {"Giocatore", "Punteggio", "Vincita", "Credito"};


    private ScoreBoardJOP(String status, Object[][] data){
        typeEndJL = new JLabel(status);
        scoreboardJT = new JTable();        
        setTableModel(data);
        this.setPreferredSize(new Dimension(150, 400));
        this.setLayout(new BorderLayout());
        this.add(typeEndJL,BorderLayout.NORTH);
        this.add(scoreboardJT,BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void showScoreBoard(String status, Object[][] data){
        if (scoreboard==null)
            scoreboard = new ScoreBoardJOP(status, data);
    }

    private void setTableModel(Object[][] data){
        DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
        scoreboardJT.setModel(dtm);
    }

    public static void resetIstance(){
        scoreboard=null;
    }

}
