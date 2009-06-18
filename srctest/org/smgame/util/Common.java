package org.smgame.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**Classe metodi comuni
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class Common {

    final static String separ = File.separator;
    final static String curDir = "c:" + separ + "smgamelog";
    //final static String dirResource = separ + "org" + separ + "smgame" + separ + "resource" + separ;
    final static String dirResource = "/org/smgame/resource/";
    //final static String dirResourceCard = dirResource + "cardimage" + separ;
    final static String dirResourceCard = dirResource + "cardimage/";

    /**Restituisce il percorso di lavoro corrente comprensivo di primo separatore
     *
     * @return percorso lavoro
     */
    public static String getWorkspace() {
        return curDir + separ;
    }

    /**Restituisce il percorso delle resource
     *
     * @return percorso resource
     */
    public static String getResource() {
        return dirResource;
    }

    /**Restituisce il percorso delle carte correnti
     *
     * @param typecard tipo mazzo usato
     * @return percorso carte
     */
    public static String getResourceCards(String typecard) {
        return dirResourceCard + typecard + separ;
    }

    /**Restituisce la cartella dei log
     *
     * @return workspace log
     */
    public static String getWorkspaceLog() {
        String log = curDir;
        File f = new File(log);
        if (!f.exists()) {
            f.mkdir();
        }
        return log + separ;
    }

    /**Restituisce data e ora in formato stringa
     *
     * @return aaaammgg_hhmmss
     */
    public static String getCurrentDateTime() {
        GregorianCalendar cal = new GregorianCalendar();
        String data = cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DATE) + "_" +
                cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND);
        return data;
    }

    /**Converte un percorso stringa in una URL
     *
     * @param path percorso
     * @return url
     */
    public static URL convertStringToURL(String path){
        URL url=null;
        if (separ.equalsIgnoreCase("\\")){
            path.replace('\\', '/');
        }
        try {
            url = new URL(path);
        } catch (MalformedURLException ex) {
        }
        return url;
    }
}//end class