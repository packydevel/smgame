package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
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
    private int counter, max_size;
    private LinkedHashMap<Long, Object[][]> dataLHM;
    private Object[] keyset;

    /**Costruttore
     *
     * @param map linkedhashmap
     */
    public StoryBoardJP(LinkedHashMap<Long, Object[][]> map) {
        setPreferredSize(new Dimension(400, 250));
        setLayout(new BorderLayout());
        dataLHM = map;
        keyset = (Object[]) dataLHM.keySet().toArray();

        storyboardJT = new JTable();        
        storyboardJT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        storyboardJT.setFocusable(false);
        storyboardJT.getTableHeader().setReorderingAllowed(false);
        counter = 0;
        setTableModel(tableModel(dataLHM.get(keyset[counter])));
        setTextGameJL("id partita: "+keyset[counter]);
        add(new JScrollPane(storyboardJT), BorderLayout.CENTER);        
        
        max_size = dataLHM.size();


        nextJB = new JButton("Successiva");
        nextJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                try {
                    nextGames();
                } catch (ClassNotFoundException ex) {

                } catch (SQLException ex) {

                } catch (IOException ex) {

                } catch (Exception ex) {

                }
            }
        });

        if (max_size<=1)
            nextJB.setEnabled(false);

        previousJB = new JButton("Precedente");
        previousJB.setEnabled(false);
        previousJB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                try {
                    previousGames();
                } catch (ClassNotFoundException ex) {

                } catch (SQLException ex) {

                } catch (IOException ex) {

                } catch (Exception ex) {
                    
                }
            }
        });

        gameJL = new JLabel();

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

    /**esegue l'azione di caricamento del successivo storico
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    private void nextGames() throws ClassNotFoundException, SQLException, IOException, Exception{        
        if (max_size > (counter + 1)){
            ++counter;            
            setTableModel(tableModel(dataLHM.get(keyset[counter])));
            setTextGameJL("id partita: "+keyset[counter]);
        }
        if (counter == 1)
            previousJB.setEnabled(true);
        if (max_size == (counter + 1)){
            nextJB.setEnabled(false);
        }
    }

    /**Esegue l'azione di caricamento del precedente storico
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    private void previousGames() throws ClassNotFoundException, SQLException, IOException, Exception{
        if (counter > 0){
            --counter;            
            setTableModel(tableModel(dataLHM.get(keyset[counter])));
            setTextGameJL("id partita: "+keyset[counter]);
        }
        if (max_size > (counter + 1))
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
        setWitdhColumn(0, 55);
        setWitdhColumn(1, 150);
        setWitdhColumn(2, 80);
        setWitdhColumn(3, 95);
    }

    /**imposta il testo della label
     *
     * @param text testo
     */
    private void setTextGameJL(String text){
        gameJL.setText(text);
    }
}