/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.frontend;

import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import org.smgame.core.GUICoreMediator;

/**
 *
 * @author packyuser
 */
public class LoadGameATM extends AbstractTableModel {

    private final String[] columnNames = {"Partita",
        "Data di Creazione",
        "Data ultimo Salvataggio"};
    private Object[][] data;

    public LoadGameATM() {
        super();
        ArrayList<String> gameNameList=(ArrayList<String>) GUICoreMediator.getGameNameList();
        ArrayList<Date> gameCreationDateList=(ArrayList<Date>) GUICoreMediator.getGameCreationDateList();
        ArrayList<Date> gameLastDateList;
        
        gameATM.setValueAt(GUICoreMediator.getGame().getGameName(), 0, 0);
        data = new Object[3][3];
    }

    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int col) {
        return 0;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
