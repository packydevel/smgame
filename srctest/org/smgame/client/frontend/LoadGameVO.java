package org.smgame.client.frontend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**Classe basata sul pattern value objects per contenere i valori del caricamento partita
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class LoadGameVO implements Serializable {
    private ArrayList<String> gameNameList = new ArrayList<String>();
    private HashMap<String, String> gameNameGameModeMap = new HashMap<String, String>();
    private HashMap<String, String> gameNameCreationDateMap = new HashMap<String, String>();
    private HashMap<String, String> gameNameLastSaveDateMap = new HashMap<String, String>();

    /**Restituisce una mappa di valori nome e data creazione
     *
     * @return mappa valori
     */
    public HashMap<String, String> getGameNameCreationDateMap() {
        return gameNameCreationDateMap;
    }

    /**Restituisce una mappa di valori nome e data ultimo salvataggio
     *
     * @return mappa
     */
    public HashMap<String, String> getGameNameLastSaveDateMap() {
        return gameNameLastSaveDateMap;
    }

    /**Restituisce la lista dei nomi partite
     *
     * @return lista nomi
     */
    public ArrayList<String> getGameNameList() {
        return gameNameList;
    }

    /**Restituisce una mappa di valori nome e tipo di partita
     *
     * @return mappa
     */
    public HashMap<String, String> getGameNameGameModeMap() {
        return gameNameGameModeMap;
    }

    /**azzera le istanze rimuovendo gli elementi
     *
     */
    public void clear() {
        getGameNameList().clear();
        getGameNameGameModeMap().clear();
        getGameNameCreationDateMap().clear();
        getGameNameLastSaveDateMap().clear();
    }
}