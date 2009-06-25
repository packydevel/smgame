package org.smgame.client.frontend;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
     * @param maxpos lista delle posizioni dei giocatori vincenti
     */
    public ScoreBoardJP(String status, Object[][] data, 
            LinkedHashMap<Integer, Color> playerColorLHM, ArrayList<Integer> maxPos) {

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

        if (maxPos!=null){
            for (int i=0; i<maxPos.size(); i++) {
                scoreboardJT.getColumn("Credito").setCellRenderer(new JLabelRenderer(maxPos.get(i)));
                System.out.println(maxPos.get(i));
            }
        } else
            scoreboardJT.getColumn("Credito").setCellRenderer(new JLabelRenderer(-1));

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
} //end class

/**Classe che restituisce la jlabel della cella tabella con determinati colori di testo */
class JLabelRenderer extends JLabel implements TableCellRenderer {
    LinkedHashMap<Integer, Color> colorLHM;
    int pos;

    /**costruttore per settare il colore di chi ha vinto la partita
     *
     * @param _pos posizione
     */
    public JLabelRenderer(int _pos){        
        this.pos = _pos;
    }

    /**costruttore per settare la mappa giocatore colore
     *
     * @param playerColorLHM mappa dati
     */
    public JLabelRenderer(LinkedHashMap<Integer, Color> playerColorLHM) {
        colorLHM = playerColorLHM;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (column == 0) {
            setForeground(colorLHM.get(row));
        } else {
            setHorizontalAlignment(JLabel.TRAILING);
        }
        if (column==3){
            if (pos==row)
                setForeground(Color.GREEN);
            else
                setForeground(Color.BLACK);
        }

        setText(value.toString());
        return this;
    }
} //end class JLabelRenderer