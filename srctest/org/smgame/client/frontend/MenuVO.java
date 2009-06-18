/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.smgame.client.frontend;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author packyuser
 */
public class MenuVO implements Serializable {
    private HashMap<String, Boolean> itemEnabledMap = new HashMap<String, Boolean>();

    public HashMap<String, Boolean> getItemEnabledMap() {
        return itemEnabledMap;
    }

    public void clear() {
        itemEnabledMap.clear();
    }
}
