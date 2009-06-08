package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ScoreBoardJOP extends JPanel {

    private JLabel typeEndJL;
    private JTable scoreboardJT;

    /**Costruttore
     *
     * @param status stato della partita
     * @param data matrice dei dati
     */
    public ScoreBoardJOP(String status, Object[][] data) {
        setPreferredSize(new Dimension(400, 250));
        setLayout(new BorderLayout());
        typeEndJL = new JLabel(status);
        scoreboardJT = new JTable();
        scoreboardJT.setModel(tableModel(data));
        scoreboardJT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setWitdhColumn(0, 140);
        setWitdhColumn(1, 80);
        setWitdhColumn(2, 80);
        setWitdhColumn(1, 100);

        scoreboardJT.setFocusable(false);
        scoreboardJT.setCellSelectionEnabled(false);

        add(typeEndJL, BorderLayout.NORTH);
        add(new JScrollPane(scoreboardJT), BorderLayout.CENTER);
        setVisible(true);
    }

    //restituisce il DTM per la tabella
    private DefaultTableModel tableModel(Object[][] data) {
        String[] columnNames = {"Giocatore", "Punteggio", "Vincita", "Credito"};
        return new DefaultTableModel(data, columnNames) {

            boolean[] canEdit = new boolean[]{false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }//end

    //imposta la dimensione della colonna
    private void setWitdhColumn(int nColumn, int width) {
        TableColumn col = scoreboardJT.getColumnModel().getColumn(nColumn);
        col.setMinWidth(width);
        col.setPreferredWidth(width);
        col.setMaxWidth(width);
    }

} //end class