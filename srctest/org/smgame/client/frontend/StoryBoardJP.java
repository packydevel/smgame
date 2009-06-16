package org.smgame.client.frontend;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class StoryBoardJP {

    private JTable storyboardJT;


    public StoryBoardJP(){

    }

    /**Restituisce il modello per la tabella
     *
     * @param data matrice dati
     * @return DTM
     */
    private DefaultTableModel tableModel(Object[][] data) {
        String[] columnNames = {"Manche", "Giocatore", "Punteggio", "Vincita"};
        return new DefaultTableModel(data, columnNames) {
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
        TableColumn col = storyboardJT.getColumnModel().getColumn(nColumn);
        col.setMinWidth(width);
        col.setPreferredWidth(width);
        col.setMaxWidth(width);
    }
}