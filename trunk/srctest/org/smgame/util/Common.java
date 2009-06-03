package org.smgame.util;

import java.io.File;

public class Common {
    
    final static String separ = File.separator;
    final static String curDir = System.getProperty("user.dir");
    final static String dirResource = "src" + separ + "org" + separ + "smgame" + separ +
                "resource" + separ;

    /**Restituisce il percorso di lavoro corrente comprensivo di primo separatore
     *
     * @return
     */
    public static String getWorkspace(){
        return curDir + separ;
    }

    /**Restituisce il percorso delle carte correnti
     *
     * @return
     */
    public static String getResourceCards(){        
        /*TODO: aggiustare il path x le carte in merito alla scelta delle carte*/
        return getWorkspace() + dirResource + separ + "cardimage" + separ + "napoletane" + separ;
    }
    
}//end class