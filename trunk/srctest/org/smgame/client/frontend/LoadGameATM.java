package org.smgame.client.frontend;

import javax.swing.table.AbstractTableModel;
import org.smgame.core.CoreProxy;

/**Modello di tabella per il caricamento delle partite
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class LoadGameATM extends AbstractTableModel {

    private final String[] columnNames = {"GameID", "Partita", "Tipo", "Data Creazione", "Data Ultimo Salvataggio"};
    private LoadGameVO loadGameVO;

    /**Costruttore
     *
     */
    public LoadGameATM() {
        super();
        try {
            loadGameVO = CoreProxy.requestLoadGameVO();
        } catch (Exception e) {
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    @Override
    public int getRowCount() {
        if (loadGameVO == null) {
            return 0;
        }
        return loadGameVO.getGameIDList().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        long gameID = loadGameVO.getGameIDList().get(row);


        if (col == 0) {
            return gameID;
        } else if (col == 1) {
            return loadGameVO.getGameNameMap().get(gameID);
        } else if (col == 2) {
            return loadGameVO.getGameModeMap().get(gameID);
        } else if (col == 3) {
            return loadGameVO.getGameCreationDateMap().get(gameID);
        } else if (col == 4) {
            return loadGameVO.getGameLastSaveDateMap().get(gameID);
        }

        return -1;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
