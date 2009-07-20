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

    private static final long serialVersionUID = 6415076116394106154L;
    private ArrayList<Long> gameIDList = new ArrayList<Long>();
    private HashMap<Long, String> gameNameMap = new HashMap<Long, String>();
    private HashMap<Long, String> gameModeMap = new HashMap<Long, String>();
    private HashMap<Long, String> gameCreationDateMap = new HashMap<Long, String>();
    private HashMap<Long, String> gameLastSaveDateMap = new HashMap<Long, String>();

    /**Restituisce una mappa di valori nome e data creazione
     *
     * @return mappa valori
     */
    public HashMap<Long, String> getGameNameMap() {
        return gameNameMap;
    }

    /**Restituisce una mappa di valori nome e data ultimo salvataggio
     *
     * @return mappa
     */
    public HashMap<Long, String> getGameCreationDateMap() {
        return gameCreationDateMap;
    }

    /**Restituisce una mappa di valori nome e data ultimo salvataggio
     *
     * @return mappa
     */
    public HashMap<Long, String> getGameLastSaveDateMap() {
        return gameLastSaveDateMap;
    }

    /**Restituisce la lista dei nomi partite
     *
     * @return lista nomi
     */
    public ArrayList<Long> getGameIDList() {
        return gameIDList;
    }

    /**Restituisce una mappa di valori nome e tipo di partita
     *
     * @return mappa
     */
    public HashMap<Long, String> getGameModeMap() {
        return gameModeMap;
    }

    /**azzera le istanze rimuovendo gli elementi
     *
     */
    public void clear() {
        gameIDList.clear();
        gameNameMap.clear();
        gameModeMap.clear();
        gameCreationDateMap.clear();
        gameLastSaveDateMap.clear();
    }
}
