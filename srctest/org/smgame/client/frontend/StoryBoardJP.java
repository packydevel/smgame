package org.smgame.client.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.smgame.backend.DBTransactions;

/**Pannello storico partite
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class StoryBoardJP extends JPanel{

    private JTable storyboardJT;
    JButton nextJB, previousJB;
    private int counter;

    public StoryBoardJP(Object[][] data) {
        setPreferredSize(new Dimension(400, 250));
        setLayout(new BorderLayout());

        storyboardJT = new JTable();        
        storyboardJT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        storyboardJT.setFocusable(false);
        storyboardJT.getTableHeader().setReorderingAllowed(false);
        setTableModel(tableModel(data));
        
        

        add(new JScrollPane(storyboardJT), BorderLayout.CENTER);
        setVisible(true);
        counter = 0;


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

    private void nextGames() throws ClassNotFoundException, SQLException, IOException, Exception{
        int size = DBTransactions.sizeStoryGames();
        if (size > (counter + 1)){
            DBTransactions dbt = new DBTransactions();
            setTableModel(tableModel(dbt.getStoryGame(++counter)));
        }
        if (counter == 1)
            previousJB.setEnabled(true);
        if (size == (counter + 1)){
            nextJB.setEnabled(false);
        }
    }

    private void previousGames() throws ClassNotFoundException, SQLException, IOException, Exception{
        int size = DBTransactions.sizeStoryGames();
        if (counter > 0){
            DBTransactions dbt = new DBTransactions();
            setTableModel(tableModel(dbt.getStoryGame(--counter)));
        }
        if (size > (counter + 1))
            nextJB.setEnabled(true);
        if (counter == 0)
            previousJB.setEnabled(false);
    }

    private void setTableModel(DefaultTableModel dtm){
        storyboardJT.setModel(dtm);
        setWitdhColumn(0, 55);
        setWitdhColumn(1, 150);
        setWitdhColumn(2, 80);
        setWitdhColumn(3, 95);
    }
}