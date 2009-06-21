package org.smgame.client.frontend;

import java.awt.Dimension;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
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

    private class JLabelRenderer extends JLabel implements TableCellRenderer {

        int posBankPlayer;

        public JLabelRenderer(int posBankPlayer) {
            this.posBankPlayer = posBankPlayer;
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (row == posBankPlayer) {
                setForeground(Color.ORANGE);
            } else {
                setForeground(Color.RED);
            }

            setText(value.toString());

            return this;
        }
    }

    /**Costruttore
     *
     * @param status stato della partita
     * @param data matrice dei dati
     */
    public ScoreBoardJP(String status, Object[][] data, int posBankPlayer) {
        setPreferredSize(new Dimension(400, 250));
        setLayout(new BorderLayout());
        typeEndJL =
                new JLabel(status);

        scoreboardJT =
                new JTable();
        scoreboardJT.setModel(tableModel(data));
        scoreboardJT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scoreboardJT.setFocusable(false);
        scoreboardJT.getTableHeader().setReorderingAllowed(false);
        scoreboardJT.getColumn("Giocatore").setCellRenderer(new JLabelRenderer(posBankPlayer));

        setWitdhColumn(0, 140);
        setWitdhColumn(1, 70);
        setWitdhColumn(2, 85);
        setWitdhColumn(3, 100);

        add(typeEndJL, BorderLayout.NORTH);
        add(new JScrollPane(scoreboardJT), BorderLayout.CENTER);
        setVisible(true);
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

            boolean[] canEdit = new boolean[]{false, false, false, false};

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
} //end class