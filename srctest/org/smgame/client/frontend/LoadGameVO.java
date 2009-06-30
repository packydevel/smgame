package org.smgame.client.frontend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class LoadGameVO implements Serializable {
    private ArrayList<String> gameNameList = new ArrayList<String>();
    private HashMap<String, String> gameNameGameModeMap = new HashMap<String, String>();
    private HashMap<String, String> gameNameCreationDateMap = new HashMap<String, String>();
    private HashMap<String, String> gameNameLastSaveDateMap = new HashMap<String, String>();

    public HashMap<String, String> getGameNameCreationDateMap() {
        return gameNameCreationDateMap;
    }

    public HashMap<String, String> getGameNameLastSaveDateMap() {
        return gameNameLastSaveDateMap;
    }

    public ArrayList<String> getGameNameList() {
        return gameNameList;
    }

    public HashMap<String, String> getGameNameGameModeMap() {
        return gameNameGameModeMap;
    }

    public void clear() {
        getGameNameList().clear();
        getGameNameGameModeMap().clear();
        getGameNameCreationDateMap().clear();
        getGameNameLastSaveDateMap().clear();
    }
}
