package org.smgame.util;

import java.io.File;
import java.net.URL;

/**Classe Lacazione Risorse
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ResourceLocator {

    static String separ = File.separator;
    static String curDir ;
    
    final static String dirResource = "/org/smgame/resource/";
    final static String dirResourceCard = dirResource + "cardimage/";
    final static String dirResourceAuthor = dirResource + "authorimage/";
    final static String dirConfig = "config/";

    /**Restituisce il percorso di lavoro corrente comprensivo di primo separatore
     *
     * @return percorso lavoro
     */
    public static String getWorkspace() {
        return curDir;
    }

    /**imposta il percorso di lavoro
     *
     * @param dir directory
     */
    public static void setWorkspace(String dir) {
        String prefix = "file:";
        curDir = prefix + dir;
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
        return dirResourceCard + typecard + "/";
    }

    /**Restituisce il percorso delle immagini degli Autori del Gioco
     *
     * @return percorso img
     */
    public static String getResourceAuthors() {
        return dirResourceAuthor;
    }

    /**Converte un percorso stringa in una URL
     *
     * @param path percorso
     * @return url
     */
    public static URL convertStringToURL(String path) {
        return ResourceLocator.class.getResource(path);
    }

    /**Verifica se il sistema operativo in uso è windows
     *
     * @return true = windows, altrimenti false
     */
    public static boolean isWindows() {
        boolean windows = false;
        String osName = System.getProperty("os.name").toLowerCase();

        if (!osName.equals("linux")) {
            if ((osName.length() > 6) &&
                    (osName.substring(0, 7).toLowerCase().equalsIgnoreCase("windows"))) {
                windows = true;
            }
        }

        return windows;
    }

    /**Restituisce il percorso della cartella config
     *
     * @return configDir
     */
    public static String getResourceConfig() {        
        return curDir+dirConfig;
    }
}//end class