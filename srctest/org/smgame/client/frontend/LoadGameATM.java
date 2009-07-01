package org.smgame.client.frontend;

import javax.swing.table.AbstractTableModel;
import org.smgame.core.GUICoreMediator;

/**Modello di tabella per il caricamento delle partite
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class LoadGameATM extends AbstractTableModel {

    private final String[] columnNames = {"Partita", "Tipo", "Data Creazione", "Data Ultimo Salvataggio"};
    private LoadGameVO loadGameVO;

    /**Costruttore
     *
     */
    public LoadGameATM() {
        super();
        try {
            loadGameVO = GUICoreMediator.requestLoadGameVO();
        } catch (Exception e) {
            //Logging.logExceptionSevere(e);
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    public int getRowCount() {
        if (loadGameVO == null) {
            return 0;
        }
        return loadGameVO.getGameNameList().size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int col) {
        String gameName = loadGameVO.getGameNameList().get(row);

        if (col == 0) {
            return gameName;
        } else if (col == 1) {
            return loadGameVO.getGameNameGameModeMap().get(gameName);
        } else if (col == 2) {
            return loadGameVO.getGameNameCreationDateMap().get(gameName);
        } else if (col == 3) {
            return loadGameVO.getGameNameLastSaveDateMap().get(gameName);
        }

        return -1;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
