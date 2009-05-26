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
        try {
            ArrayList<String> gameNameList = (ArrayList<String>) GUICoreMediator.getGameNameList();
            ArrayList<Date> gameCreationDateList = (ArrayList<Date>) GUICoreMediator.getGameCreationDateList();
            ArrayList<Date> gameLastDateList;
            data = new Object[10][3];
            for (int i = 0; i < gameNameList.size(); i++) {
                data[i][0] = gameNameList.get(i);
                data[i][1] = gameCreationDateList.get(i);
                data[i][2] = gameCreationDateList.get(i);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
