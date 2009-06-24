package org.smgame.client.frontend;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**Pannello punteggi dopo la manche
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ScoreBoardJP extends JPanel {
    private JLabel typeEndJL;
    private JTable scoreboardJT;        

    /**Costruttore
     *
     * @param status stato della partita
     * @param data matrice dei dati
     * @param playerColorLHM mappa dei colori associati ai player
     */
    public ScoreBoardJP(String status, Object[][] data, 
            LinkedHashMap<Integer, Color> playerColorLHM, boolean endgame) {

        setPreferredSize(new Dimension(400, 250));
        setLayout(new BorderLayout());
        typeEndJL = new JLabel(status);

        scoreboardJT = new JTable();
        scoreboardJT.setModel(tableModel(data));
        scoreboardJT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scoreboardJT.setFocusable(false);
        scoreboardJT.setCellSelectionEnabled(false);

        scoreboardJT.getTableHeader().setReorderingAllowed(false);
        scoreboardJT.getColumn("Giocatore").setCellRenderer(new JLabelRenderer(playerColorLHM));
        scoreboardJT.getColumn("Punteggio").setCellRenderer(new JLabelRenderer(playerColorLHM));
        scoreboardJT.getColumn("Vincita").setCellRenderer(new JLabelRenderer(playerColorLHM));
        scoreboardJT.getColumn("Credito").setCellRenderer(new JLabelRenderer(playerColorLHM));
        scoreboardJT.repaint();

        if (endgame){
            // Disable autoCreateColumnsFromModel otherwise all the column customizations
            // and adjustments will be lost when the model data is sorted
            scoreboardJT.setAutoCreateColumnsFromModel(false);
            sortAllRowsBy((DefaultTableModel) scoreboardJT.getModel(), 3, true);
        }


        setWitdhColumn(0, 140);
        setWitdhColumn(1, 70);
        setWitdhColumn(2, 85);
        setWitdhColumn(3, 100);

        

        add(typeEndJL, BorderLayout.NORTH);
        add(new JScrollPane(scoreboardJT), BorderLayout.CENTER);
        setVisible(true);
        repaint();
    }

    /**Restituisce il modello per la tabella
     *
     * @param data matrice dati
     * @return DTM
     */
    private DefaultTableModel tableModel(Object[][] data) {
        final Object[][] d = data;
        String[] columnNames = {"Giocatore", "Punteggio", "Vincita", "Credito"};
        return new DefaultTableModel(d, columnNames) {
            Class[] types = new Class[]{java.lang.String.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            boolean[] canEdit = new boolean[]{false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }//end

    /**imposta la dimensione della colonna
     *
     * @param nColumn numero colonna
     * @param width larghezza
     */
    private void setWitdhColumn(int nColumn, int width) {
        TableColumn col = scoreboardJT.getColumnModel().getColumn(nColumn);
        col.setMinWidth(width);
        col.setPreferredWidth(width);
        col.setMaxWidth(width);
    }

    // Regardless of sort order (ascending or descending), null values always appear last.
    // colIndex specifies a column in model.
    private void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending) {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }

} //end class

class JLabelRenderer extends JLabel implements TableCellRenderer {
    LinkedHashMap<Integer, Color> colorLHM;

    public JLabelRenderer(LinkedHashMap<Integer, Color> playerColorLHM) {
        colorLHM = playerColorLHM;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (column == 0) {
            setForeground(colorLHM.get(row));
        } else {
            setHorizontalAlignment(JLabel.TRAILING);
        }

        setText(value.toString());

        return this;
    }
} //end class JLabelRenderer

class ColumnSorter implements Comparator {
    int colIndex;
    boolean ascending;

    ColumnSorter(int colIndex, boolean ascending) {
        this.colIndex = colIndex;
        this.ascending = ascending;
    }

    public int compare(Object a, Object b) {
        Vector v1 = (Vector)a;
        Vector v2 = (Vector)b;
        Object o1 = v1.get(colIndex);
        Object o2 = v2.get(colIndex);

        // Treat empty strains like nulls
        if (o1 instanceof String && ((String)o1).length() == 0) {
            o1 = null;
        }
        if (o2 instanceof String && ((String)o2).length() == 0) {
            o2 = null;
        }

        // Sort nulls so they appear last, regardless
        // of sort order
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return 1;
        } else if (o2 == null) {
            return -1;
        } else if (o1 instanceof Comparable) {
            if (ascending) {
                return ((Comparable)o1).compareTo(o2);
            } else {
                return ((Comparable)o2).compareTo(o1);
            }
        } else {
            if (ascending) {
                return o1.toString().compareTo(o2.toString());
            } else {
                return o2.toString().compareTo(o1.toString());
            }
        }
    }
} //end class column sorter