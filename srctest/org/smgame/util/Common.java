package org.smgame.util;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Common {
    
    final static String separ = File.separator;
    final static String curDir = System.getProperty("user.dir");
    final static String dirResource = "src" + separ + "org" + separ + "smgame" + separ + "resource" + separ;
    final static String dirResourceCard = dirResource + "cardimage" + separ;


    /**Restituisce il percorso di lavoro corrente comprensivo di primo separatore
     *
     * @return percorso lavoro
     */
    public static String getWorkspace(){
        return curDir + separ;
    }

    /**Restituisce il percorso delle resource
     *
     * @return percorso resource
     */
    public static String getResource(){
        return getWorkspace() + dirResource;
    }

    /**Restituisce il percorso delle carte correnti
     *
     * @return percorso carte
     */
    public static String getResourceCards(String typecard){
        /*TODO: aggiustare il path x le carte in merito alla scelta delle carte*/
        return getWorkspace() + dirResourceCard  + typecard + separ;
    }

    /**Restituisce la cartella dei log
     *
     * @return workspace log
     */
    public static String getWorkspaceLog(){
        String log = getWorkspace() + "log";
        File f = new File(log);
        if (!f.exists())
            f.mkdir();
        return log + separ;
    }

    /**Restituisce data e ora in formato stringa
     *
     * @return aaaammgg_hhmmss
     */
    public static String getCurrentDateTime(){
        GregorianCalendar cal = new GregorianCalendar();
        String data = cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH)+1) + cal.get(Calendar.DATE) + "_" +
                cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND);
        return data;
    }
    
}//end class