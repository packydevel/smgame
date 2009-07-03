package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

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
    JButton nextJB, previousJB;
    JLabel gameJL;
    private int counter;
    private LinkedHashMap<Long, Object[][]> dataLHM;
    private Object[] keyset;

    /**Costruttore
     *
     * @param map linkedhashmap
     */
    public StoryBoardJP(LinkedHashMap<Long, Object[][]> map) {
        setPreferredSize(new Dimension(950, 250));
        setLayout(new BorderLayout());

        storyboardJT = new JTable();
        storyboardJT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        storyboardJT.setFocusable(false);
        storyboardJT.getTableHeader().setReorderingAllowed(false);

        gameJL = new JLabel();

        counter = 0;

        if ((map==null) || (map.isEmpty())){
            dataLHM = null;
            setTableModel(tableModel(null));
            
        } else {
            keyset = dataLHM.keySet().toArray();
            dataLHM = map;
            setTableModel(tableModel(dataLHM.get(keyset[counter])));
            gameJL.setText("id partita: " + keyset[counter].toString());
        }                                
        
        add(new JScrollPane(storyboardJT), BorderLayout.CENTER);                

        nextJB = new JButton("Successiva");
        nextJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {                
                nextGames();
            }
        });

        if ((dataLHM==null) || (dataLHM.size()<=1))
            nextJB.setEnabled(false);

        previousJB = new JButton("Precedente");
        previousJB.setEnabled(false);
        previousJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                previousGames();
            }
        });
                

        JPanel northJP = new JPanel(new BorderLayout());
        northJP.add(previousJB, BorderLayout.WEST);
        northJP.add(gameJL, BorderLayout.CENTER);
        northJP.add(nextJB, BorderLayout.EAST);

        this.add(northJP,BorderLayout.NORTH);
        setVisible(true);
    }

    /**Restituisce il modello per la tabella
     *
     * @param data matrice dati
     * @return DTM
     */
    private DefaultTableModel tableModel(Object[][] data) {
        String[] columnNames = {"Manche", "Giocatore", "Punteggio", "Vincita", "Carte"};
        return new DefaultTableModel(data, columnNames) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false};

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

    /**esegue l'azione di caricamento del successivo storico
     *
     */
    private void nextGames() {        
        if (dataLHM.size() > (counter + 1)){
            ++counter;            
            setTableModel(tableModel(dataLHM.get(keyset[counter])));
            gameJL.setText("id partita: "+keyset[counter].toString());
        }
        if (counter == 1)
            previousJB.setEnabled(true);
        if (dataLHM.size() == (counter + 1)){
            nextJB.setEnabled(false);
        }
    }

    /**Esegue l'azione di caricamento del precedente storico
     *
     */
    private void previousGames() {
        if (counter > 0){
            --counter;            
            setTableModel(tableModel(dataLHM.get(keyset[counter])));
            gameJL.setText("id partita: "+keyset[counter].toString());
        }
        if (dataLHM.size() > (counter + 1))
            nextJB.setEnabled(true);
        if (counter == 0)
            previousJB.setEnabled(false);
    }

    /**imposta il modello della tabella
     *
     * @param dtm modello
     */
    private void setTableModel(DefaultTableModel dtm){
        storyboardJT.setModel(dtm);
        setWitdhColumn(0, 50);
        setWitdhColumn(1, 150);
        setWitdhColumn(2, 70);
        setWitdhColumn(3, 75);
        setWitdhColumn(4, 580);
    }
}