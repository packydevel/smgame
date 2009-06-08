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
    private final String[] columnNames = {"Giocatore", "Punteggio", "Vincita", "Credito Residuo"};


    public ScoreBoardJOP(String status, Object[][] data){
        typeEndJL = new JLabel(status);
        scoreboardJT = new JTable();
        setTableModel(data);
        setPreferredSize(new Dimension(300, 400));
        setLayout(new BorderLayout());
        add(typeEndJL, BorderLayout.NORTH);
        add(scoreboardJT, BorderLayout.CENTER);
        setVisible(true);
    }
//
//    public static void showScoreBoard(String status, Object[][] data){
//        if (scoreboard==null)
//            scoreboard = new ScoreBoardJOP(status, data);
//    }

    private void setTableModel(Object[][] data){
        DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
        scoreboardJT.setModel(dtm);
    }
//    public static void resetIstance(){
//        scoreboard=null;
//    }

}
