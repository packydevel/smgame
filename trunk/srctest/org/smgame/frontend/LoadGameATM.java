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
    private ArrayList<String> gameNameList;
    private ArrayList<Date> gameCreationDateList;
    private ArrayList<Date> gameLastDateList;

    public LoadGameATM() {
        super();
        try {
            gameNameList = (ArrayList<String>) GUICoreMediator.getGameNameList();
            gameCreationDateList = (ArrayList<Date>) GUICoreMediator.getGameCreationDateList();
            gameLastDateList = (ArrayList<Date>) GUICoreMediator.getGameLastDateList();

            System.out.println();
            System.out.println();
            System.out.println();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getColumnName(
            int col) {
        return columnNames[col].toString();
    }

    public int getRowCount() {
        return gameNameList.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int col) {

        if (col == 0) {
            return gameNameList.get(row);
        } else if (col == 1) {
            return gameCreationDateList.get(row);
        } else if (col == 2) {
            return gameLastDateList.get(row);
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
