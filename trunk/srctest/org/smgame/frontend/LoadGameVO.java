/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.frontend;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author packyuser
 */
public class LoadGameVO {
    private ArrayList<String> gameNameList = new ArrayList<String>();
    private HashMap<String, String> gameNameGameModeMap = new HashMap<String, String>();
    private HashMap<String, Date> gameNameCreationDateMap = new HashMap<String, Date>();
    private HashMap<String, Date> gameNameLastSaveDateMap = new HashMap<String, Date>();

    public HashMap<String, Date> getGameNameCreationDateMap() {
        return gameNameCreationDateMap;
    }

    public HashMap<String, Date> getGameNameLastSaveDateMap() {
        return gameNameLastSaveDateMap;
    }

    public ArrayList<String> getGameNameList() {
        return gameNameList;
    }

    public HashMap<String, String> getGameNameGameModeMap() {
        return gameNameGameModeMap;
    }
}
