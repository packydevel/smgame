package org.smgame.client.frontend;

import java.io.Serializable;
import java.util.HashMap;

/**Oggetto value objects riguardante il menù
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class MenuVO implements Serializable {
    private HashMap<String, Boolean> itemEnabledMap = new HashMap<String, Boolean>();

    /**restituisce la mappa con gli item e i relativi booleani per l'abilitazione
     *
     * @return mappa item booleani
     */
    public HashMap<String, Boolean> getItemEnabledMap() {
        return itemEnabledMap;
    }

    /**azzera l'istanza
     *
     */
    public void clear() {
        itemEnabledMap.clear();
    }
}