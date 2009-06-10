/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import javax.swing.table.AbstractTableModel;
import org.smgame.core.GUICoreMediator;

/**
 *
 * @author packyuser
 */
public class LoadGameATM extends AbstractTableModel {

    private final String[] columnNames = {"Partita", "Tipo", "Data di Creazione", "Data ultimo Salvataggio"};
    private LoadGameVO loadGameVO;

    public LoadGameATM() {
        super();
        try {
            loadGameVO = GUICoreMediator.requestLoadGameVO();
        } catch (Exception e) {
            //Logging.logExceptionSevere(e);
        }
    }

    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    public int getRowCount() {
        if (loadGameVO == null) {
            return 0;
        }
        System.out.println(loadGameVO.getGameNameList());
        return loadGameVO.getGameNameList().size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int col) {
        if (col == 0) {
            return loadGameVO.getGameNameList().get(row);
        } else if (col == 1) {
            return loadGameVO.getGameNameGameModeMap().get(row);
        } else if (col == 2) {
            return loadGameVO.getGameNameCreationDateMap().get(row);
        } else if (col == 2) {
            return loadGameVO.getGameNameLastSaveDateMap().get(row);
        }

        return -1;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        fireTableCellUpdated(row, col);
    }
}
