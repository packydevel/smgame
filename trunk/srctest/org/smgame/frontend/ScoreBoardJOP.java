package org.smgame.frontend;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import org.smgame.util.ColumnSorter;

public class ScoreBoardJOP extends JPanel{
    private JLabel typeEndJL;
    private JTable scoreboardJT;    

    public ScoreBoardJOP(String status, Object[][] data){
        setPreferredSize(new Dimension(400, 200));
        setLayout(new BorderLayout());
        typeEndJL = new JLabel(status);
        scoreboardJT = new JTable();
        scoreboardJT.setModel(tableModel(data));
        scoreboardJT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        resizeColumn(0, 140);
        resizeColumn(1, 80);
        resizeColumn(2, 80);
        resizeColumn(1, 100);

        JTableHeader header = scoreboardJT.getTableHeader();


        add(typeEndJL,BorderLayout.NORTH);
        add(new JScrollPane(scoreboardJT),BorderLayout.CENTER);
        setVisible(true);
    }

    private DefaultTableModel tableModel(Object[][] data){
        String[] columnNames = {"Giocatore", "Punteggio", "Vincita", "Credito"};
        return new DefaultTableModel(data, columnNames){
            boolean[] canEdit = new boolean [] {false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
    }//end 

    private void resizeColumn(int nColumn, int width){
        TableColumn col = scoreboardJT.getColumnModel().getColumn(nColumn);
        col.setMinWidth(width);
        col.setPreferredWidth(width);
        col.setMaxWidth(width);
    }

    // Disable autoCreateColumnsFromModel otherwise all the column customizations
    // and adjustments will be lost when the model data is sorted
    //table.setAutoCreateColumnsFromModel(false);

    // Sort all the rows in descending order based on the
    // values in the second column of the model
    //sortAllRowsBy(model, 1, false);

    // Regardless of sort order (ascending or descending), null values always appear last.
    // colIndex specifies a column in model.
    private void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending) {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }

} //end class
