package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**Pannello storico partite
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class StoryBoardJP extends JPanel{

    private JTable storyboardJT;
    private int counter;

    public StoryBoardJP(Object[][] data) {
        setPreferredSize(new Dimension(400, 250));
        setLayout(new BorderLayout());

        storyboardJT = new JTable();
        storyboardJT.setModel(tableModel(data));
        storyboardJT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        storyboardJT.setFocusable(false);
        storyboardJT.getTableHeader().setReorderingAllowed(false);

        setWitdhColumn(0, 70);
        setWitdhColumn(1, 140);
        setWitdhColumn(2, 85);
        setWitdhColumn(3, 100);

        add(new JScrollPane(storyboardJT), BorderLayout.CENTER);
        setVisible(true);
        counter = 1;


        JButton nextJB = new JButton("Successiva");
        JButton previousJB = new JButton("Precedente");
        previousJB.setEnabled(false);
        JLabel gameIdJL = new JLabel();

        JPanel northJP = new JPanel(new BorderLayout());
        northJP.add(previousJB, BorderLayout.WEST);
        northJP.add(gameIdJL, BorderLayout.CENTER);
        northJP.add(nextJB, BorderLayout.EAST);

        this.add(northJP,BorderLayout.NORTH);
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
        TableColumn col = storyboardJT.getColumnModel().getColumn(nColumn);
        col.setMinWidth(width);
        col.setPreferredWidth(width);
        col.setMaxWidth(width);
    }
}